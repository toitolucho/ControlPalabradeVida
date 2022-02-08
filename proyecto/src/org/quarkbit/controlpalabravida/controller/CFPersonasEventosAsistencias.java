package org.quarkbit.controlpalabravida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.ibatis.session.SqlSession;
import org.quarkbit.controlpalabravida.dao.Coneccion;
import org.quarkbit.controlpalabravida.dao.domain.Evento;
import org.quarkbit.controlpalabravida.dao.domain.EventoExample;
import org.quarkbit.controlpalabravida.dao.domain.Persona;
import org.quarkbit.controlpalabravida.dao.domain.PersonaEventoAsistencia;
import org.quarkbit.controlpalabravida.dao.domain.PersonaHuellaDigital;
import org.quarkbit.controlpalabravida.dao.persistence.EventoMapper;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaEventoAsistenciaMapper;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaHuellaDigitalMapper;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaMapper;
import org.quarkbit.controlpalabravida.formularios.FPersonasEventosAsistencias;
import org.quarkbit.controlpalabravida.utils.FormUtilities;

import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.GrFingerJava;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.griaule.grfingerjava.IFingerEventListener;
import com.griaule.grfingerjava.IImageEventListener;
import com.griaule.grfingerjava.IStatusEventListener;
import com.griaule.grfingerjava.MatchingContext;
import com.griaule.grfingerjava.Template;


public class CFPersonasEventosAsistencias implements IStatusEventListener, IImageEventListener, IFingerEventListener, KeyListener {
	
	 /** Fingerprint SDK context used for capture / extraction / matching of fingerprints. */
	   private MatchingContext fingerprintSDK;
	   /** User interface, where logs, images and other things will be sent. */
	   FPersonasEventosAsistencias ui;

	   /** Sets if template must be automatically extracted after capture. */
	   private boolean autoExtract = true;
	   /** Sets if template must be automatically identified after capture.
	    It's only effective when *autoExtract == true) */
	   private boolean autoIdentify = false;


	   /** The last fingerprint image acquired. */
	   private FingerprintImage fingerprint;
	   /** The template extracted from the last acquired image. */
	   private Template template;

	   private SqlSession session;
	   private PersonaHuellaDigitalMapper daoPersonaHuellaDigitalMapper;
	   private EventoMapper daoEventoMapper;
	   private PersonaMapper daoPersonaMapper; 
	   private PersonaEventoAsistenciaMapper daoPersonaEventoAsistencia;
	   private List<Evento> listaEventos;
	   private List<PersonaHuellaDigital> listaHuellasDigitales;
	   private EventoExample filtroEventos;
	   private Persona personaActual;
	   private PersonaEventoAsistencia asistencia;
	   private Evento eventoActual;
	   private Thread hilo;
	   protected String rutaLocal;
	   protected Media archivoCorrecto;
	   protected Media archivoIncorrecto;
	   protected MediaPlayer mediaPlayerCorrecto;
	   protected MediaPlayer mediaPlayerIncorrecto;

	public CFPersonasEventosAsistencias(final FPersonasEventosAsistencias ui) {
		 	this.ui = ui;	      
	       //Initializes Fingerprint SDK and enables fingerprint capture.
	       initFingerprintSDK();
	       new JFXPanel();
	       
	       //cargamos los archivos de audio
	       try {
			archivoCorrecto = new Media(new File(FormUtilities.obtenerRutaLocal() + "sonidos/02.mp3").toURI().toURL().toExternalForm()  );
			   mediaPlayerCorrecto = new MediaPlayer(archivoCorrecto);
			   
			   archivoIncorrecto = new Media(new File(FormUtilities.obtenerRutaLocal() + "sonidos/01.mp3").toURI().toURL().toExternalForm() );
			   mediaPlayerIncorrecto = new MediaPlayer(archivoIncorrecto);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	       
//	       rutaLocal = FormUtilities.obtenerRutaLocal()+ "imagenes/personas/";
	       rutaLocal = FormUtilities.obtenerRutaLocal()+ "imagenes/personas/";
//	       JOptionPane.showMessageDialog(ui, rutaLocal);
	       
	       session = Coneccion.getSqlSessionFactory().openSession();
	       daoPersonaHuellaDigitalMapper = session.getMapper(PersonaHuellaDigitalMapper.class);
	       daoEventoMapper = session.getMapper(EventoMapper.class);
	       daoPersonaMapper = session.getMapper(PersonaMapper.class);
	       daoPersonaEventoAsistencia = session.getMapper(PersonaEventoAsistenciaMapper.class);
	       filtroEventos = new EventoExample();
	       this.ui.addKeyListener(this);
	       
	       autoExtract = autoIdentify = true;
	       asistencia = new PersonaEventoAsistencia();
	       
	       
	       hilo = new Thread();
	       
	       Runnable eventoRevisor = new Runnable() {
			
			@Override
			public void run() {
				listaEventos = daoEventoMapper.selectByValidos();	
			    listaHuellasDigitales = daoPersonaHuellaDigitalMapper.selectByValidasHuellas();
			       if(listaEventos.size()>0)
			       {
			    	   ui.getLblEventoActual().setText("EVENTO :" + listaEventos.get(0).getNombreEvento());
			    	   eventoActual =listaEventos.get(0);
			       }
			       else
			       {
			    	   JOptionPane.showMessageDialog(ui, "No existen eventos que controlar", "Control", JOptionPane.WARNING_MESSAGE);
			    	   ui.getLblEventoActual().setText("  ");
			    	   ui.getLblRegistroCorrecto().setText("  ");
	                   ui.getLblNombrecompleto().setText("  ");
	                   ui.showImage(null);
			    	   
			       }
				
			}
	       };
	       
	       ScheduledExecutorService ejecutor = Executors.newScheduledThreadPool(1);
	       ejecutor.scheduleAtFixedRate(eventoRevisor, 0, 2, TimeUnit.MINUTES);
	       FormUtilities.maximizar(ui);
	       ui.setUndecorated(true);
	       
//	       ui.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeStroke, dispatchWindowClosingActionMapKey);
//	       ui.getRootPane().getActionMap().put(dispatchWindowClosingActionMapKey, dispatchclosing);
	       InputMap im = ui.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	       ActionMap am = ui.getRootPane().getActionMap();
	       im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"cancel");
	       am.put("cancel", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				session.commit();
				destroyFingerprintSDK();
				//ui.dispose();
				System.exit(0);
			}
		});
	       
	}
	
		/**
	    * Initializes Fingerprint SDK and enables fingerprint capture.
	    */
	   private void initFingerprintSDK() {
	       try {
	           fingerprintSDK = new MatchingContext();
	           //Starts fingerprint capture.
	           GrFingerJava.initializeCapture(this);

	           
	           System.out.println("**Fingerprint SDK Initialized Successfull**");

	       } catch (Exception e) {
	           //If any error ocurred while initializing GrFinger,
	           //writes the error to log
	    	   System.out.println(e.getMessage());
	       }
	   }
	   
	   public void destroy() {
	       destroyFingerprintSDK();
	       session.close();
	   }
	   /**
	    * Stops fingerprint capture.
	    */
	   private void destroyFingerprintSDK() {
	       try {
	           GrFingerJava.finalizeCapture();
	       } catch (GrFingerJavaException e) {
	           e.printStackTrace();
	       }
	   }
	   
	@Override
	public void onImageAcquired(String idSensor, FingerprintImage fingerprint) {
	       //Logs that an Image Event occurred.
		   System.out.println("Sensor: "+idSensor+". Event: Image Captured.");

	       //Stores the captured Fingerprint Image
	       this.fingerprint=fingerprint;

	       //Display fingerprint image
	       ui.showImage(fingerprint);

	       
	       //If auto-extraction is enabled, let's extract the image.
	       if (autoExtract) {
	           extract();

	           //If auto-Identify is also enabled, let's identify it.
	           if (autoIdentify)
	               identify();
	       }

	   }
	
	public void extract() {
	       
	       try {
	           //Extracts a template from the current fingerprint image. 
	           template = fingerprintSDK.extract(fingerprint);
	           
	           //Notifies it has been extracted and the quality of the extraction
	           String msg = "Template extracted successfully. ";
	           //write template quality to log
	           switch (template.getQuality()){
	               case Template.HIGH_QUALITY:     msg +="High quality.";   break;
	               case Template.MEDIUM_QUALITY:   msg +="Medium quality."; break;
	               case Template.LOW_QUALITY:      msg +="Low quality.";    break;
	           }
	           System.out.println(msg);	          
	           ui.showImage(GrFingerJava.getBiometricImage(template,fingerprint));
	           
	       } catch (GrFingerJavaException e) {
	           //write error to log
	    	   System.out.println(e.getMessage());
	       }
	       
	   }

	@Override
	public void onSensorPlug(String idSensor) {
		//Logs the sensor has been pluged.
		System.out.println("Sensor: "+idSensor+". Event: Plugged.");
	       try {
	           //Start capturing from plugged sensor.
	           GrFingerJava.startCapture(idSensor, this, this);
	       } catch (GrFingerJavaException e) {
	           //write error to log
	    	   System.out.println(e.getMessage());
	       }
		
	}

	@Override
	public void onSensorUnplug(String idSensor) {
		//Logs the sensor has been unpluged.
		System.out.println("Sensor: "+idSensor+". Event: Unplugged.");
	       try {
	           GrFingerJava.stopCapture(idSensor);
	       } catch (GrFingerJavaException e) {
	    	   System.out.println(e.getMessage());
	       }
		
	}

	@Override
	public void onFingerDown(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFingerUp(String arg0) {
		// TODO Auto-generated method stub
		
	}

	 public void identify() {
	       try {
	         //Starts identification process by supplying query template.
	           fingerprintSDK.prepareForIdentification(this.template);	           
	           // Iterate over all templates in database
	           for(PersonaHuellaDigital huella : listaHuellasDigitales) {
	               //Reads the current template data on a buffer
	        	   
	               byte[] templateBuffer = huella.getHuellaDigital();
	               //And creates a new Template
	               Template referenceTemplate = new Template(templateBuffer);	               
	               //Compares current template.
	               boolean matched = fingerprintSDK.identify(referenceTemplate);
	               
	               //If the templates match, display matching minutiae/segments/directions.
	               if (matched){
	                   //displays minutiae/segments/directions that matched.
	                   ui.showImage(GrFingerJava.getBiometricImage(template, fingerprint, fingerprintSDK));
	                   //Notifies the template was identified.
	                   System.out.println("Fingerprint identified. ID = "+ huella.getIdHuella()+". Score = " + fingerprintSDK.getScore() + ".");
	                   personaActual = daoPersonaMapper.selectByPrimaryKey(huella.getIdPersona());
	                   ui.getLblNombrecompleto().setText(personaActual.getNombreCompleto());
	                   
	                   //mostrar imagen
//	                   ui.getLblLblimagen().setIcon(null);
	                   try {
						if (personaActual.getRutaFotografia() == "" || personaActual.getRutaFotografia() == null) {
							   ui.getLblLblimagen().setIcon(new ImageIcon(getClass().getResource("/imagenes/personas/persona.png")));
							} else {
								if (new File(rutaLocal + personaActual.getRutaFotografia()).exists()) {
									ui.getLblLblimagen().setIcon(new ImageIcon(rutaLocal + personaActual.getRutaFotografia()));
								}

							}
							((ImageIcon) ui.getLblLblimagen().getIcon()).setImage(((ImageIcon) ui.getLblLblimagen().getIcon()).getImage().getScaledInstance(200, 200, 0));
							ui.getLblLblimagen().updateUI();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						//JOptionPane.showMessageDialog(ui, e1.getMessage());
						
					}
	                   
	                   
	                   //incorporar sonido positivo de reconocimiento
	                   try {
						mediaPlayerCorrecto.play();
						   try {
								Thread.sleep(1100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		   				 
						   mediaPlayerCorrecto.stop();
	                   } catch (Exception e1) {
						// TODO Auto-generated catch block
	                	   e1.printStackTrace();
	                   }
	                   
	                   
	                   
	                   asistencia.setIdPersona(huella.getIdPersona());
	                   asistencia.setIdEvento(eventoActual.getIdEvento());
	                   asistencia.setTipoIngreso("E");
	                   daoPersonaEventoAsistencia.insert(asistencia);
	                   session.commit();
	                   
	                   
	                   
	                   ui.getLblRegistroCorrecto().setText("REGISTRO CORRECTO");
	                   
	                   try {
						hilo.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                   
	                   ui.getLblRegistroCorrecto().setText("  ");
	                   ui.getLblNombrecompleto().setText("  ");
	                   ui.showImage(null);
	                   ui.getLblLblimagen().setIcon(null);
	                   //Stops searching
	                   return;
	               }
	           }
	           
	           //If all templates on the DB have been compared, and none of them
	           //match, notifies it has not been found.
	           System.out.println("Huella Digital no encontrada.");
	           ui.getLblNombrecompleto().setText("Persona no registrada, intente de nuevo");
	           ui.getLblRegistroCorrecto().setText("  ");               
               ui.showImage(null);
               ui.getLblLblimagen().setIcon(null);
	           try {
					mediaPlayerIncorrecto.play();
					   try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		   				 
					   mediaPlayerIncorrecto.stop();
                  } catch (Exception e1) {
					// TODO Auto-generated catch block
               	   e1.printStackTrace();
                  }
	           
	           
	           //incorporar sonido negativa de no reconocimiento
	           
	           
//	       } catch (SQLException e) {
//	           //write error to log            
//	           ui.writeLog("Error accessing database.");
	       } catch (GrFingerJavaException e) {
	           //write error to log
	    	   System.out.println(e.getMessage());
	       }
	       
	   }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_ESCAPE)
		{
			this.ui.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
