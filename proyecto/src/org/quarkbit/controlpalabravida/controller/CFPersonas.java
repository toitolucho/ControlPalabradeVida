package org.quarkbit.controlpalabravida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.ibatis.session.SqlSession;
import org.quarkbit.controlpalabravida.dao.Coneccion;
import org.quarkbit.controlpalabravida.dao.domain.Persona;
import org.quarkbit.controlpalabravida.dao.domain.PersonaExample;
import org.quarkbit.controlpalabravida.dao.domain.PersonaHuellaDigital;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaHuellaDigitalMapper;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaMapper;
import org.quarkbit.controlpalabravida.formularios.FPersonas;
import org.quarkbit.controlpalabravida.formularios.tablemodels.ModeloPersonas;
import org.quarkbit.controlpalabravida.formularios.tablemodels.ModeloPersonasHuellasDigitales;
import org.quarkbit.controlpalabravida.utils.CopyDirectory;
import org.quarkbit.controlpalabravida.utils.FormUtilities;
import org.quarkbit.controlpalabravida.utils.ImageFileView;
import org.quarkbit.controlpalabravida.utils.ImageFilter;
import org.quarkbit.controlpalabravida.utils.ImagePreview;

import com.griaule.grfingerjava.FingerprintImage;
import com.griaule.grfingerjava.GrFingerJava;
import com.griaule.grfingerjava.GrFingerJavaException;
import com.griaule.grfingerjava.IFingerEventListener;
import com.griaule.grfingerjava.IImageEventListener;
import com.griaule.grfingerjava.IStatusEventListener;
import com.griaule.grfingerjava.MatchingContext;
import com.griaule.grfingerjava.Template;

public class CFPersonas implements ActionListener, IStatusEventListener, IImageEventListener, IFingerEventListener, ListSelectionListener, MouseListener {

	private FPersonas formPersonas;
	/** Sets if template must be automatically extracted after capture. */
	private boolean autoExtract = true;
	/** The last fingerprint image acquired. */
	private FingerprintImage fingerprint;
	/** The template extracted from the last acquired image. */
	private Template template1;	
	private Template template2;
	private Template template3;
	private Template template;
	/**
	 * Fingerprint SDK context used for capture / extraction / matching of
	 * fingerprints.
	 */
	private MatchingContext fingerprintSDK;

	private char numeroImagen = '1';
	private String tipoOperacion = "";
	/***
	 * variable encargada de abrir la conección
	 */
	private SqlSession session;
	/***
	 * dao persisntencia para la manipulación de datos huellas dactilares de
	 * Personas
	 */
	private PersonaHuellaDigitalMapper daoPersonaHuellaDigitalMapper;
	private PersonaMapper daoPersonaMapper;
	
	
	private PersonaHuellaDigital personaHuellaDigitalActual;
	private Persona personaActual;
	private ModeloPersonas modeloPersona;
	private ModeloPersonasHuellasDigitales modeloPersonasHuellas;
	
	
	private String nombreFotografia = "";
	private String rutaFotografia = "";
	private String rutaLocal = "";
	boolean edicionFotografia = false;

	public CFPersonas(FPersonas formPersonas, boolean autoExtract) {
		this.formPersonas = formPersonas;

		initFingerprintSDK();

		session = Coneccion.getSqlSessionFactory().openSession();
		daoPersonaHuellaDigitalMapper = session.getMapper(PersonaHuellaDigitalMapper.class);
		this.autoExtract = autoExtract;
		
		personaActual = new Persona();
		personaHuellaDigitalActual = new PersonaHuellaDigital();
		
		
//		rutaLocal = formPersonas.getClass().getResource("").toString().substring(6,formPersonas.getClass().getResource("").toString().length()).replaceFirst("%20", " ").replaceAll("view", "personas");
//		
//		Class myClass = FPersonas.class; 
//		URL url = myClass.getResource("FPersonas.class");			
//		rutaLocal = url.getPath().substring(1).replaceAll("view/FJugadores.class", "personas/").trim();
//		if (rutaLocal.contains("%20"))
//		{
//			rutaLocal = rutaLocal.replace("%20", " ");
//		}		
//		System.out.println("Direcci\u00F3n Local" + rutaLocal);
//		
		rutaLocal = FormUtilities.obtenerRutaLocal()+ "imagenes/personas/";
		System.out.println("Direcci\u00F3n Local" + rutaLocal);
	}

	/**
	 * Initializes Fingerprint SDK and enables fingerprint capture.
	 */
	private void initFingerprintSDK() {
		try {
			fingerprintSDK = new MatchingContext();
			// Starts fingerprint capture.
			GrFingerJava.initializeCapture(this);

			formPersonas.writeLog("**La funcionalidad de Reconocimiento de Huellas se ha iniciado correctamente**");

		} catch (Exception e) {
			// If any error ocurred while initializing GrFinger,
			// writes the error to log
			formPersonas.writeLog(e.getMessage());
		}
	}

	/**
	 * Stops fingerprint capture.
	 */
	public void destroyFingerprintSDK() {
		try {
			GrFingerJava.finalizeCapture();
			System.out.println("Libreria liberada");
		} catch (GrFingerJavaException e) {
			e.printStackTrace();
		}
	}

	public void onLoad() {
		session = Coneccion.getSqlSessionFactory().openSession();
		daoPersonaHuellaDigitalMapper = session.getMapper(PersonaHuellaDigitalMapper.class);
		daoPersonaMapper = session.getMapper(PersonaMapper.class);
		
		modeloPersona = new ModeloPersonas();
		modeloPersonasHuellas = new ModeloPersonasHuellasDigitales();
		
		formPersonas.getjTablePersonas().setModel(modeloPersona);
		formPersonas.getjTablePersonasHuellas().setModel(modeloPersonasHuellas);
		
		formPersonas.getTxtCodigo().setEnabled(false);	
		
		formPersonas.getBtnAceptar().addActionListener(this);
		formPersonas.getBtnNuevo().addActionListener(this);
		formPersonas.getBtnCancelar().addActionListener(this);
		formPersonas.getBtnModificar().addActionListener(this);
		formPersonas.getBtnEliminar().addActionListener(this);
		formPersonas.getBtnCerrar().addActionListener(this);
		formPersonas.getBtnAgregar().addActionListener(this);
		formPersonas.getBtnAgregarImagen().addActionListener(this);
		
		
		habilitarControles(false);
		habilitarBotones(true, false, false, false, false);
		modeloPersona.setListPersona(daoPersonaMapper.selectByExample(null));
		formPersonas.getjTablePersonas().getSelectionModel().addListSelectionListener(this);
		formPersonas.getjTablePersonasHuellas().getColumn("Nº Muestra").setPreferredWidth(5);
		formPersonas.getjTablePersonas().addMouseListener(this);
		FormUtilities.centrar(formPersonas);
	}

	public void limpiarControles()
	{
		formPersonas.getTxtCodigo().setText("");
		formPersonas.getTxtApellidos().setText("");
		formPersonas.getTxtCelular().setText("");
		formPersonas.getTxtCi().setText("");
		formPersonas.getTxtNombres().setText("");
		formPersonas.getLogTextArea().setText("");
		formPersonas.getcBoxSexo().setSelectedIndex(-1);
		formPersonas.getDateFechaNac().setDate(null);
		modeloPersonasHuellas.clear();
		formPersonas.showImage(null, '1');
		formPersonas.showImage(null, '2');
		formPersonas.showImage(null, '3');
		formPersonas.getLblImagen().setIcon(null);
	}
	
	public void habilitarControles(boolean estadHabilitacion)
	{
		formPersonas.getTxtCodigo().setEnabled(estadHabilitacion);
		formPersonas.getTxtApellidos().setEnabled(estadHabilitacion);
		formPersonas.getTxtCelular().setEnabled(estadHabilitacion);
		formPersonas.getTxtCi().setEnabled(estadHabilitacion);
		formPersonas.getTxtNombres().setEnabled(estadHabilitacion);
		formPersonas.getLogTextArea().setEnabled(estadHabilitacion);	
		formPersonas.getcBoxSexo().setEnabled(estadHabilitacion);
		formPersonas.getDateFechaNac().setEnabled(estadHabilitacion);
		formPersonas.getBtnAgregarImagen().setEnabled(estadHabilitacion);
//		formPersonas.getjTablePersonas().setEnabled(estadHabilitacion);
	}
	
	public void habilitarBotones(boolean nuevo, boolean aceptar, boolean cancelar,
			boolean modificar, boolean eliminar)
	{
		formPersonas.getBtnAceptar().setEnabled(aceptar);
		formPersonas.getBtnNuevo().setEnabled(nuevo);
		formPersonas.getBtnCancelar().setEnabled(cancelar);
		formPersonas.getBtnModificar().setEnabled(modificar);
		formPersonas.getBtnEliminar().setEnabled(eliminar);
	}
	
	public void cargarDatos(Persona personaAux)
	{
		if(personaAux != null)
		{
			this.personaActual = personaAux;
			formPersonas.getTxtCodigo().setText(personaAux.getIdPersona().toString());
			formPersonas.getTxtNombres().setText(personaAux.getNombres());
			formPersonas.getTxtApellidos().setText(personaAux.getApellidos());
			formPersonas.getcBoxSexo().setSelectedItem(personaAux.obtenerSexo());
			formPersonas.getTxtCelular().setText(personaAux.getCelular());
			formPersonas.getDateFechaNac().setDate(personaAux.getFechaNacimiento());
			formPersonas.getTxtCi().setText(personaAux.getCi());
			
			if (personaActual.getRutaFotografia() == "" || personaActual.getRutaFotografia() == null) {
				formPersonas.getLblImagen().setIcon(new ImageIcon(getClass().getResource("/imagenes/personas/persona.png")));
			} else {
				if (new File(rutaLocal + personaActual.getRutaFotografia()).exists()) {
					formPersonas.getLblImagen().setIcon(new ImageIcon(rutaLocal + personaActual.getRutaFotografia()));
				}

			}
			((ImageIcon) formPersonas.getLblImagen().getIcon()).setImage(((ImageIcon) formPersonas.getLblImagen().getIcon()).getImage().getScaledInstance(100, 100, 0));
			formPersonas.getLblImagen().updateUI();
			
			
			habilitarBotones(true, false, false, true, true);
		}
		
	}
	
	public boolean validarControles()
	{
		if(formPersonas.getTxtNombres().getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(formPersonas, "A\u00FAn no ha ingresado el nombre de la persona", "Personas", JOptionPane.ERROR_MESSAGE);
			formPersonas.getTxtNombres().grabFocus();
			return false;
		}
		
		if(formPersonas.getTxtApellidos().getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(formPersonas, "A\u00FAn no ha ingresado el nombre de la persona", "Personas", JOptionPane.ERROR_MESSAGE);
			formPersonas.getTxtApellidos().grabFocus();
			return false;
		}
		
		if(formPersonas.getcBoxSexo().getSelectedIndex() < 0)
		{
			JOptionPane.showMessageDialog(formPersonas, "A\u00FAn no ha seleccionado el sexo", "Personas", JOptionPane.ERROR_MESSAGE);
			formPersonas.getcBoxSexo().grabFocus();
			return false;
		}
		
		if(formPersonas.getDateFechaNac().getDate() == null)
		{
			JOptionPane.showMessageDialog(formPersonas, "A\u00FAn no ha ingresado la fecha de Nacimiento", "Personas", JOptionPane.ERROR_MESSAGE);
			formPersonas.getDateFechaNac().grabFocus();
			return false;
		}
		
		PersonaExample filtro = new PersonaExample();
		filtro.or().andNombreCompletoEqualTo(formPersonas.obtenerNombreCompleto().toUpperCase());
		if(tipoOperacion.compareTo("I") == 0 && daoPersonaMapper.countByExample(filtro) > 0)
		{
			JOptionPane.showMessageDialog(formPersonas, "El nombre completo de la persona ya se encuentra registrada", "Cargo Cargo", JOptionPane.ERROR_MESSAGE);
			formPersonas.getTxtNombres().grabFocus();
			return false;
		}
		
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource().equals(formPersonas.getBtnNuevo()))
		{
			habilitarBotones(false, true, true, false, false);
			habilitarControles(true);
			limpiarControles();
			tipoOperacion = "I";
			formPersonas.getjTablePersonas().clearSelection();
		}
		
		if(formPersonas.getBtnCerrar().equals(event.getSource()))
		{
			personaActual = null;
			formPersonas.setVisible(false);
			
			formPersonas.dispose();
			
		}
		if(formPersonas.getBtnModificar().equals(event.getSource()))
		{
			habilitarBotones(false, true, true, false, false);
			habilitarControles(true);			
			tipoOperacion = "E";
			
		}
		if(formPersonas.getBtnEliminar().equals(event.getSource()))
		{
			if(JOptionPane.showConfirmDialog(formPersonas, "¿Se encuentra seguro de eliminar el registro actual?", "Cargos", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
				System.out.println("Elemento a eliminar " + Integer.parseInt(formPersonas.getTxtCodigo().getText()));				
				modeloPersona.eliminarPersona(formPersonas.getjTablePersonas().getSelectedRow());				
				daoPersonaMapper.deleteByPrimaryKey(Integer.parseInt(formPersonas.getTxtCodigo().getText()));
				
				session.commit();
				
				limpiarControles();
				JOptionPane.showMessageDialog(formPersonas, "Objeto eliminado correctamente", "Cargo", JOptionPane.INFORMATION_MESSAGE);
				habilitarBotones(true, false, false, false, false);
			}
			
		}
		
		if(formPersonas.getBtnCancelar().equals(event.getSource()))
		{
			limpiarControles();
			habilitarControles(false);
			habilitarBotones(true, false, false, false, false);
			tipoOperacion = "";
		}
		if(formPersonas.getBtnAgregar().equals(event.getSource()))
		{
			System.out.println("evento agregar");
			if(formPersonas.getFingerprintImage1() != null)
			{
				System.out.println(formPersonas.getcBoxMano().getSelectedItem().toString());
				personaHuellaDigitalActual = new PersonaHuellaDigital();
				personaHuellaDigitalActual.setIdHuella(1);
				personaHuellaDigitalActual.setIdPersona(personaActual.getIdPersona());
				personaHuellaDigitalActual.enviarMano(formPersonas.getcBoxMano().getSelectedItem().toString());
				personaHuellaDigitalActual.enviarDedo(formPersonas.getcBoxDedo().getSelectedItem().toString());
				personaHuellaDigitalActual.setObservacion(formPersonas.getTxtObservacionMuestra1().getText().toString());
//				formPersonas.getFingerprintImage1().
//				FingerprintImage dato2 = (FingerprintImage) formPersonas.getFingerprintImage1();
				
				
				ByteArrayInputStream dato = new ByteArrayInputStream(        template1.getData());				
		        byte[] buffer = new byte[8192];
		        int bytesRead;
		        ByteArrayOutputStream output = new ByteArrayOutputStream();
		        try {
					while((bytesRead = dato.read(buffer)) != -1)
					{
					   output.write(buffer, 0, bytesRead);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         
		        personaHuellaDigitalActual.setHuellaDigital(output.toByteArray());
		        modeloPersonasHuellas.AnadirPersonaHuellaDigital(personaHuellaDigitalActual);   
				
			}
			if(formPersonas.getFingerprintImage2() != null)
			{
				personaHuellaDigitalActual = new PersonaHuellaDigital();
				personaHuellaDigitalActual.setIdHuella(2);
				personaHuellaDigitalActual.setIdPersona(personaActual.getIdPersona());
				personaHuellaDigitalActual.enviarMano(formPersonas.getcBoxMano().getSelectedItem().toString());
				personaHuellaDigitalActual.enviarDedo(formPersonas.getcBoxDedo().getSelectedItem().toString());
				personaHuellaDigitalActual.setObservacion(formPersonas.getTxtObservacionMuestra2().getText().toString());
				ByteArrayInputStream dato = new ByteArrayInputStream(template2.getData());
		        byte[] buffer = new byte[8192];
		        int bytesRead;
		        ByteArrayOutputStream output = new ByteArrayOutputStream();
		        try {
					while((bytesRead = dato.read(buffer)) != -1)
					{
					   output.write(buffer, 0, bytesRead);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		         
		        personaHuellaDigitalActual.setHuellaDigital(output.toByteArray());
		        modeloPersonasHuellas.AnadirPersonaHuellaDigital(personaHuellaDigitalActual);
			}
			if(formPersonas.getFingerprintImage3() != null)
			{
				personaHuellaDigitalActual = new PersonaHuellaDigital();
				personaHuellaDigitalActual.setIdHuella(3);
				personaHuellaDigitalActual.setIdPersona(personaActual.getIdPersona());
				personaHuellaDigitalActual.enviarMano(formPersonas.getcBoxMano().getSelectedItem().toString());
				personaHuellaDigitalActual.enviarDedo(formPersonas.getcBoxDedo().getSelectedItem().toString());
				personaHuellaDigitalActual.setObservacion(formPersonas.getTxtObservacionMuestra3().getText().toString());
				ByteArrayInputStream dato = new ByteArrayInputStream(template3.getData());
		        byte[] buffer = new byte[8192];
		        int bytesRead;
		        ByteArrayOutputStream output = new ByteArrayOutputStream();
		        try {
					while((bytesRead = dato.read(buffer)) != -1)
					{
					   output.write(buffer, 0, bytesRead);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		         
		        personaHuellaDigitalActual.setHuellaDigital(output.toByteArray());
		        modeloPersonasHuellas.AnadirPersonaHuellaDigital(personaHuellaDigitalActual);
			}
			
		}
		if(formPersonas.getBtnAgregarImagen().equals(event.getSource()))
		{
			edicionFotografia = true;
			String ruta = "";
			javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);

			fc.removeChoosableFileFilter(new ImageFilter());
			fc.addChoosableFileFilter(new ImageFilter());
			fc.setFileView(new ImageFileView());
			fc.setAccessory(new ImagePreview(fc));
			try {
				int option = fc.showOpenDialog(formPersonas);
				if (option == javax.swing.JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFile() != null) {
						ruta = fc.getSelectedFile().getAbsolutePath();
						nombreFotografia = fc.getSelectedFile().getName();
						rutaFotografia = fc.getSelectedFile().getParent();
//						formPersonas.getJtxtPathImagen().setText(ruta);
						formPersonas.getLblImagen().setIcon(
								new ImageIcon(ruta));

						((ImageIcon) formPersonas.getLblImagen().getIcon())
								.setImage(((ImageIcon) formPersonas
										.getLblImagen().getIcon())
										.getImage().getScaledInstance(100, 100,
												0));
						formPersonas.getLblImagen().updateUI();
					} else {
						 System.out.println("La seleccion ha sido cancelado");
					}

				}
			} catch (Exception e) {

			}
		}
		
		if(formPersonas.getBtnAceptar().equals(event.getSource()))
		{
			if(validarControles())
			{
				try {
					personaActual = new Persona();
					personaActual.setIdPersona(-1);
					personaActual.setCi(formPersonas.getTxtCi().getText());
					personaActual.setNombres(formPersonas.getTxtNombres().getText());
					personaActual.setApellidos(formPersonas.getTxtApellidos().getText());
					personaActual.enviarSexo(formPersonas.getcBoxSexo().getSelectedItem().toString());
					personaActual.setCelular(formPersonas.getTxtCelular().getText());
					personaActual.setFechaNacimiento(formPersonas.getDateFechaNac().getDate());					
					if (!nombreFotografia.isEmpty())
						personaActual.setRutaFotografia(nombreFotografia);
//					personaActual.setObservaciones(formCargo.getTxtObservacion().getText());
					
					
					if(tipoOperacion.compareTo("I")==0)
					{
						daoPersonaMapper.insert(personaActual);
						session.commit();
						
						Map<String, Integer> mapa = new HashMap<String, Integer>();
						mapa.put("ultimoId", 0);
						daoPersonaMapper.obtenerUltimoId(mapa); //("myMappedStatement",myParams);
						personaActual.setIdPersona(mapa.get("ultimoId"));
						System.out.println("Ultimo id " + personaActual.getIdPersona());						
//					System.out.println("codigo actual = " + CargoActual.getIdCargo());
						modeloPersona.AnadirPersona(personaActual);
						
						for(PersonaHuellaDigital huella : modeloPersonasHuellas.getListaPersonaHuellaDigital())
						{
							huella.setIdPersona(personaActual.getIdPersona());
							daoPersonaHuellaDigitalMapper.insert(huella);
						}
						session.commit();
						
						
						
						
					}
					else
					{
						modeloPersona.setPersona(formPersonas.getjTablePersonas().getSelectedRow(), personaActual);
						personaActual.setIdPersona(Integer.parseInt(formPersonas.getTxtCodigo().getText()));
						daoPersonaMapper.updateByPrimaryKey(personaActual);
						session.commit();	
					}
					
					if(!formPersonas.getBtnNuevo().isVisible())
					{
						
						formPersonas.setVisible(false);
						//formCargo.dispose();
						return;
					}
					
					
					//para la fotografia
					CopyDirectory copiar = new CopyDirectory();
					if (edicionFotografia) {
						try {
							if (new File(rutaLocal, nombreFotografia).exists()) {
								if (JOptionPane.YES_OPTION == JOptionPane.showInternalOptionDialog(this.formPersonas,""+ "Existe un archivo ya registrado con el mismo nombre,  Desea Sobreescribirlo?","Registro de Fotografia del Jugador",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null,null, null)) {
									if (!nombreFotografia.isEmpty()
											&& !new File(rutaLocal).isDirectory())
										CopyDirectory.deleteFile(rutaLocal
												+ nombreFotografia);
								} else {
									FormUtilities.showMessage("Insertado de Personas","No se pudo Completar satisfactoriamente la Operacion de Insertado, debido a que intenta ingresar un fotografia repetida",formPersonas);
									return;
								}
							}
							if (new File(rutaLocal).isDirectory()&& !nombreFotografia.isEmpty())
								copiar.copyDirectory(new File(rutaFotografia, nombreFotografia), new File(rutaLocal, nombreFotografia));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						edicionFotografia = false;
					}
					
					
					
					
					
					tipoOperacion ="";
					habilitarBotones(true, false, false, true, true);
					habilitarControles(false);
					JOptionPane.showMessageDialog(formPersonas, "Registro almacenado correctamente", "Personas", JOptionPane.INFORMATION_MESSAGE);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(formPersonas, "Ocurri\u00F3 la siguiente excepcion " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
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

	@Override
	public void onImageAcquired(String idSensor, FingerprintImage fingerprint) {
		// TODO Auto-generated method stub
		// Logs that an Image Event occurred.
		formPersonas.writeLog("Sensor: " + idSensor + ". Event: Image Captured.");

		// Stores the captured Fingerprint Image
		this.fingerprint = fingerprint;

		// Display fingerprint image
		formPersonas.showImage(fingerprint, numeroImagen);

		// If auto-extraction is enabled, let's extract the image.
		if (autoExtract) {
			extract();

			// If auto-Identify is also enabled, let's identify it.
			// if (autoIdentify)
			// identify();
		}
		switch (numeroImagen) {
		case '1':
			numeroImagen = '2';
			break;
		case '2':
			numeroImagen = '3';
			break;

		default:
			numeroImagen = '1';
			break;
		}
	}

	/**
	 * Esta función es invocada cada vez que el Lector de Huella es conectado
	 */
	@Override
	public void onSensorPlug(String idSensor) {
		// TODO Auto-generated method stub
		// realiza el seguimiento y registro en el Log de que el Lector ha sido
		// conectado a la PC
		formPersonas.writeLog("Sensor: " + idSensor + ". Evento: Lector Conectado.");
		try {
			// Start capturing from plugged sensor.
			GrFingerJava.startCapture(idSensor, this, this);
		} catch (GrFingerJavaException e) {
			// write error to log
			formPersonas.writeLog(e.getMessage());
		}
	}

	/**
	 * Esta función es invocada cada vez que el Lector de Huella es desconectado
	 */
	@Override
	public void onSensorUnplug(String idSensor) {
		// TODO Auto-generated method stub
		// realiza el seguimiento y registro en el Log de que el Lector ha sido
		// desconectado
		formPersonas.writeLog("Sensor: " + idSensor + ". Evento: Lector Desconectado.");
		try {
			GrFingerJava.stopCapture(idSensor);
		} catch (GrFingerJavaException e) {
			formPersonas.writeLog(e.getMessage());
		}
	}

	/**
	 * Extract a fingerprint template from current image. Estrae la plantilla de
	 * impresión dactilar de la imagen capturada
	 */
	public void extract() {

		try {
			// Extracts a template from the current fingerprint image.
			switch (numeroImagen) {
			case '1':
				template = template1 = fingerprintSDK.extract(fingerprint);
				break;
			case '2':
				template = template2 = fingerprintSDK.extract(fingerprint);
				break;
			case '3':
				template = template3 = fingerprintSDK.extract(fingerprint);
				break;
			default:
				break;
			}
			

			// Notifies it has been extracted and the quality of the extraction
			// String msg = "Template extracted successfully. ";
			String msg = "Plantilla de la Huella Dactilar extraida satisfactoriamente. ";
			// write template quality to log
			switch (template.getQuality()) {
			case Template.HIGH_QUALITY:
				msg += "Calidad ALTA.";
				break;
			case Template.MEDIUM_QUALITY:
				msg += "Calidad MEDIA.";
				break;
			case Template.LOW_QUALITY:
				msg += "Calidad BAJA.";
				break;
			}
			formPersonas.writeLog(msg);

			// Notifies the UI that template operations can be enabled.
			// formPersonas.enableTemplate();
			// display minutiae/segments/directions into image
			formPersonas.showImage(GrFingerJava.getBiometricImage(template, fingerprint), numeroImagen);

		} catch (GrFingerJavaException e) {
			// write error to log
			formPersonas.writeLog(e.getMessage());
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if(e.getValueIsAdjusting())
			return;
		int indice = ((ListSelectionModel) e.getSource()).getMinSelectionIndex();
		cargarDatos(modeloPersona.obtenerPersona(indice));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
