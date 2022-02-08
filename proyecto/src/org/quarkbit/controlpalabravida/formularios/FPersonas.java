package org.quarkbit.controlpalabravida.formularios;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.quarkbit.controlpalabravida.controller.CFPersonas;

import com.toedter.calendar.JDateChooser;

public class FPersonas extends JDialog {
	private JButton btnNuevo;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnCerrar;
	private JPanel pnlPersonales;
	private JPanel pnlHuellas;
	private JScrollPane scrollPane;
	private JTable jTablePersonas;
	private JPanel pnlDatos;
	private JLabel lblCodigo;
	private JTextField txtCodigo;
	private JLabel lblCi;
	private JTextField txtCi;
	private JLabel lblNombres;
	private JTextField txtNombres;
	private JLabel lblApellidos;
	private JTextField txtApellidos;
	private JLabel lblSexo;
	private JComboBox<String> cBoxSexo;
	private JLabel lblCelular;
	private JTextField txtCelular;
	private JLabel lblFechaDeNac;
	private JDateChooser dateFechaNac;
	private JPanel pnlCentral;
	private JPanel pnlFoto;
	private JLabel lblImagen;
	private JButton btnAgregarImagen;
	private JScrollPane scrollPane2;
	private JTable jTablePersonasHuellas;
	private JPanel pnlDivisionHuellas;
	private JPanel pnlHuella1;
	private JPanel pnlHuella2;
	private JPanel pnlHuella3;
	private JPanel pnlHuellaConfiguracion;
	private JLabel lblMano;
	private JLabel lblDedo;
	private JComboBox<String> cBoxMano;
	private JComboBox<String> cBoxDedo;
	private JButton btnAgregar;
	private JTextField txtObservacionMuestra1;
	private JTextField txtObservacionMuestra2;
	private JTextField txtObservacionMuestra3;
	private JPanel pnlImagenMuestra1;
	private JPanel pnlImagenMuestra2;
	private JPanel pnlImagenMuestra3;
	public CFPersonas control;
	private JPanel panel;
	private JTextArea logTextArea;
	private JScrollPane logScrollPane;
	
	
	
	private BufferedImage fingerprintImage1 = null;
	private BufferedImage fingerprintImage2 = null;
	private BufferedImage fingerprintImage3 = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FPersonas dialog = new FPersonas(null);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public FPersonas(Frame formPadre) {
		super(formPadre, true);
		setTitle("Personas Huellas Digitales");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1069, 674);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		pnlPersonales = new JPanel();
		tabbedPane.addTab("Personales", null, pnlPersonales, null);
		pnlPersonales.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		pnlPersonales.add(scrollPane, BorderLayout.CENTER);
		
		jTablePersonas = new JTable();
		jTablePersonas.setModel(new DefaultTableModel(
			new Object[][] {
				{"123", "Padres"},
			},
			new String[] {
				"Codigo", "Nombre"
			}
		));
		scrollPane.setViewportView(jTablePersonas);
		
		pnlCentral = new JPanel();
		pnlPersonales.add(pnlCentral, BorderLayout.WEST);
		pnlCentral.setLayout(new BorderLayout(0, 0));
		
		pnlDatos = new JPanel();
		pnlDatos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlCentral.add(pnlDatos, BorderLayout.CENTER);
		FlowLayout flowLayout = (FlowLayout) pnlDatos.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnlDatos.setPreferredSize(new Dimension(260, 10));
		
		lblCodigo = new JLabel("Codigo");
		lblCodigo.setPreferredSize(new Dimension(100, 14));
		pnlDatos.add(lblCodigo);
		
		txtCodigo = new JTextField();
		pnlDatos.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		lblCi = new JLabel("Ci:");
		lblCi.setPreferredSize(new Dimension(100, 14));
		pnlDatos.add(lblCi);
		
		txtCi = new JTextField();
		pnlDatos.add(txtCi);
		txtCi.setColumns(10);
		
		lblNombres = new JLabel("Nombres");
		lblNombres.setPreferredSize(new Dimension(100, 14));
		pnlDatos.add(lblNombres);
		
		txtNombres = new JTextField();
		pnlDatos.add(txtNombres);
		txtNombres.setColumns(10);
		
		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setPreferredSize(new Dimension(100, 14));
		pnlDatos.add(lblApellidos);
		
		txtApellidos = new JTextField();
		pnlDatos.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		lblSexo = new JLabel("Sexo:");
		lblSexo.setPreferredSize(new Dimension(100, 14));
		pnlDatos.add(lblSexo);
		
		cBoxSexo = new JComboBox<String>();
		cBoxSexo.setModel(new DefaultComboBoxModel<String>(new String[] {"MASCULINO", "FEMENINO"}));
		cBoxSexo.setPreferredSize(new Dimension(100, 17));
		pnlDatos.add(cBoxSexo);
		
		lblCelular = new JLabel("Celular:");
		lblCelular.setPreferredSize(new Dimension(100, 14));
		pnlDatos.add(lblCelular);
		
		txtCelular = new JTextField();
		pnlDatos.add(txtCelular);
		txtCelular.setColumns(10);
		
		lblFechaDeNac = new JLabel("Fecha de Nac.:");
		lblFechaDeNac.setPreferredSize(new Dimension(100, 14));
		pnlDatos.add(lblFechaDeNac);
		
		dateFechaNac = new JDateChooser();
		dateFechaNac.setPreferredSize(new Dimension(170, 22));
		dateFechaNac.setDateFormatString("dd-MM-yyyy");
		dateFechaNac.setPreferredSize(new Dimension(100, 17));
		pnlDatos.add(dateFechaNac);
		
		pnlFoto = new JPanel();
		pnlFoto.setPreferredSize(new Dimension(270, 120));
		pnlCentral.add(pnlFoto, BorderLayout.SOUTH);
		pnlFoto.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblImagen = new JLabel("imagen");
		lblImagen.setIconTextGap(2);
		lblImagen.setPreferredSize(new Dimension(100, 100));
		lblImagen.setIcon(new ImageIcon(FPersonas.class.getResource("/imagenes/personas/persona.png")));
		pnlFoto.add(lblImagen);
		
		btnAgregarImagen = new JButton("+");
		pnlFoto.add(btnAgregarImagen);
		
		pnlHuellas = new JPanel();
		tabbedPane.addTab("Huellas", null, pnlHuellas, null);
		pnlHuellas.setLayout(new BorderLayout(0, 0));
		
		scrollPane2 = new JScrollPane();
		scrollPane2.setPreferredSize(new Dimension(100,100));
		pnlHuellas.add(scrollPane2, BorderLayout.SOUTH);
		
		jTablePersonasHuellas = new JTable();
		jTablePersonasHuellas.setModel(new DefaultTableModel(
			new Object[][] {
				{"123", "Padres"},
			},
			new String[] {
				"Codigo", "Nombre"
			}
		));
		scrollPane2.setViewportView(jTablePersonasHuellas);
		
		pnlDivisionHuellas = new JPanel();
		pnlHuellas.add(pnlDivisionHuellas, BorderLayout.CENTER);
		pnlDivisionHuellas.setLayout(new GridLayout(1, 0, 0, 0));
		
		pnlHuella1 = new JPanel();
		pnlHuella1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Muestra 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDivisionHuellas.add(pnlHuella1);
		pnlHuella1.setLayout(new BorderLayout(10, 10));
		
		txtObservacionMuestra1 = new JTextField();
		pnlHuella1.add(txtObservacionMuestra1, BorderLayout.SOUTH);
		txtObservacionMuestra1.setColumns(10);
		
		pnlImagenMuestra1 = new JPanel(){
	           //Overrides the paintComponent method for painting the image
	           public void paintComponent(Graphics g) {
	               super.paintComponent(g);

	               //If there's a image to be drawn...
	               if (fingerprintImage1!=null) {
	                   //calculates the size/position of the image being drawn,
	                   //so it's size is stretched to fill the whole space
	                   Insets insets = getInsets();
	                   int transX = insets.left;
	                   int transY = insets.top;
	                   int width  = getWidth()  - getInsets().right  - getInsets().left;
	                   int height = getHeight() - getInsets().bottom - getInsets().top;

	                   //draw it!
	                   g.drawImage(fingerprintImage1, transX, transY, width, height, null);
	               }

	           }

	       };
		pnlImagenMuestra1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlHuella1.add(pnlImagenMuestra1, BorderLayout.CENTER);
		
		pnlHuella2 = new JPanel();
		pnlHuella2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Muestra 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDivisionHuellas.add(pnlHuella2);
		pnlHuella2.setLayout(new BorderLayout(10, 10));
		
		txtObservacionMuestra2 = new JTextField();
		pnlHuella2.add(txtObservacionMuestra2, BorderLayout.SOUTH);
		txtObservacionMuestra2.setColumns(10);
		
		pnlImagenMuestra2 = new JPanel(){
	           //Overrides the paintComponent method for painting the image
	           public void paintComponent(Graphics g) {
	               super.paintComponent(g);

	               //If there's a image to be drawn...
	               if (fingerprintImage2!=null) {
	                   //calculates the size/position of the image being drawn,
	                   //so it's size is stretched to fill the whole space
	                   Insets insets = getInsets();
	                   int transX = insets.left;
	                   int transY = insets.top;
	                   int width  = getWidth()  - getInsets().right  - getInsets().left;
	                   int height = getHeight() - getInsets().bottom - getInsets().top;

	                   //draw it!
	                   g.drawImage(fingerprintImage2, transX, transY, width, height, null);
	               }

	           }

	       };
		pnlImagenMuestra2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlHuella2.add(pnlImagenMuestra2, BorderLayout.CENTER);
		
		pnlHuella3 = new JPanel();
		pnlHuella3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Muestra3", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDivisionHuellas.add(pnlHuella3);
		pnlHuella3.setLayout(new BorderLayout(10, 10));
		
		txtObservacionMuestra3 = new JTextField();
		pnlHuella3.add(txtObservacionMuestra3, BorderLayout.SOUTH);
		txtObservacionMuestra3.setColumns(10);
		
		pnlImagenMuestra3 = new JPanel(){
	           //Overrides the paintComponent method for painting the image
	           public void paintComponent(Graphics g) {
	               super.paintComponent(g);

	               //If there's a image to be drawn...
	               if (fingerprintImage3!=null) {
	                   //calculates the size/position of the image being drawn,
	                   //so it's size is stretched to fill the whole space
	                   Insets insets = getInsets();
	                   int transX = insets.left;
	                   int transY = insets.top;
	                   int width  = getWidth()  - getInsets().right  - getInsets().left;
	                   int height = getHeight() - getInsets().bottom - getInsets().top;

	                   //draw it!
	                   g.drawImage(fingerprintImage3, transX, transY, width, height, null);
	               }

	           }

	       };
		pnlImagenMuestra3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlHuella3.add(pnlImagenMuestra3, BorderLayout.CENTER);
		
		pnlHuellaConfiguracion = new JPanel();
		pnlHuellas.add(pnlHuellaConfiguracion, BorderLayout.NORTH);
		pnlHuellaConfiguracion.setLayout(new BorderLayout(0, 0));
		
		logTextArea = new JTextArea();
//		contentPane.add(logTextArea, BorderLayout.NORTH);
		
		
		logScrollPane = new JScrollPane(logTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

       //Sets it's size
       logScrollPane.setPreferredSize(new java.awt.Dimension(0, 65));
       //Enables auto-scrolling
       logScrollPane.setAutoscrolls(true);

       //Adds a little border around it
       logScrollPane.setBorder(new CompoundBorder (
               new EmptyBorder (2,2,2,2),
               new BevelBorder(BevelBorder.LOWERED)));
		
       pnlHuellaConfiguracion.add(logScrollPane, BorderLayout.CENTER);
		
		
		panel = new JPanel();
		pnlHuellaConfiguracion.add(panel, BorderLayout.NORTH);
		
		lblMano = new JLabel("Mano :");
		panel.add(lblMano);
		
		cBoxMano = new JComboBox<String>();
		panel.add(cBoxMano);
		cBoxMano.setModel(new DefaultComboBoxModel<String>(new String[] {"DERECHA", "IZQUIERDA"}));
		
		lblDedo = new JLabel("Dedo:");
		panel.add(lblDedo);
		
		cBoxDedo = new JComboBox<String>();
		panel.add(cBoxDedo);
		cBoxDedo.setModel(new DefaultComboBoxModel<String>(new String[] {"PULGAR", "INDICE", "CENTRO", "ANULAR", "ME\u00D1IQUE"}));
		
		btnAgregar = new JButton("Agregar");
		panel.add(btnAgregar);
		
		
		JPanel pnlBotones = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnlBotones.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(pnlBotones, BorderLayout.SOUTH);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNuevo.setMnemonic('N');
		btnNuevo.setIcon(new ImageIcon(FPrincipal.class.getResource("/imagenes/BOTONES/Nuevo01.png")));
		pnlBotones.add(btnNuevo);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setIcon(new ImageIcon(FPrincipal.class.getResource("/imagenes/BOTONES/Aceptar01.png")));
		btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAceptar.setMnemonic('A');
		pnlBotones.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(FPrincipal.class.getResource("/imagenes/BOTONES/Cancelar01.png")));
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelar.setMnemonic('C');
		pnlBotones.add(btnCancelar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setIcon(new ImageIcon(FPrincipal.class.getResource("/imagenes/BOTONES/Actualizar01.png")));
		btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModificar.setMnemonic('M');
		pnlBotones.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(FPrincipal.class.getResource("/imagenes/BOTONES/Eliminar01.png")));
		btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminar.setMnemonic('E');
		pnlBotones.add(btnEliminar);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setIcon(new ImageIcon(FPrincipal.class.getResource("/imagenes/BOTONES/Cerrar01.png")));
		btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrar.setMnemonic('r');
		pnlBotones.add(btnCerrar);
		
		control = new CFPersonas(this, true);
		control.onLoad();

	}

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public JTable getjTablePersonas() {
		return jTablePersonas;
	}

	public JPanel getPnlDatos() {
		return pnlDatos;
	}

	public JTextField getTxtCodigo() {
		return txtCodigo;
	}

	public JTextField getTxtCi() {
		return txtCi;
	}

	public JTextField getTxtNombres() {
		return txtNombres;
	}

	public JTextField getTxtApellidos() {
		return txtApellidos;
	}

	public JComboBox<String> getcBoxSexo() {
		return cBoxSexo;
	}

	public JTextField getTxtCelular() {
		return txtCelular;
	}

	public JDateChooser getDateFechaNac() {
		return dateFechaNac;
	}

	public JPanel getPnlCentral() {
		return pnlCentral;
	}

	public JPanel getPnlFoto() {
		return pnlFoto;
	}

	public JLabel getLblImagen() {
		return lblImagen;
	}

	public JButton getBtnAgregarImagen() {
		return btnAgregarImagen;
	}

	public JTable getjTablePersonasHuellas() {
		return jTablePersonasHuellas;
	}

	public JComboBox<String> getcBoxMano() {
		return cBoxMano;
	}

	public JComboBox<String> getcBoxDedo() {
		return cBoxDedo;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JTextField getTxtObservacionMuestra1() {
		return txtObservacionMuestra1;
	}

	public JTextField getTxtObservacionMuestra2() {
		return txtObservacionMuestra2;
	}

	public JTextField getTxtObservacionMuestra3() {
		return txtObservacionMuestra3;
	}

	public JPanel getPnlImagenMuestra1() {
		return pnlImagenMuestra1;
	}

	public JPanel getPnlImagenMuestra2() {
		return pnlImagenMuestra2;
	}

	public JPanel getPnlImagenMuestra3() {
		return pnlImagenMuestra3;
	}

	public JTextArea getLogTextArea() {
		return logTextArea;
	}
	
	public void writeLog(String text) {
	       //Appends the text
	       logTextArea.append(text + "\n");

	       //Auto-scrolls to the last message.
	       SwingUtilities.invokeLater(new Runnable() {
	           public void run() {
	               //picks the vertical scrollBar, and sets it to the maximum.
	               JScrollBar vbar = logScrollPane.getVerticalScrollBar();
	               vbar.setValue(vbar.getMaximum());
	           }
	       });
	   }
	/**
	    * Sets the current fingerprint image.
	    * It is shown on the middle of the frame.
	    */
	   public void showImage(BufferedImage image, char nroPanel) {
		 //uses the imageProducer to create the fingerprint image
		   switch (nroPanel) {
		case '1':
			fingerprintImage1 = image;
			break;
		case '2':
			fingerprintImage2 = image;
					break;
		case '3':
			fingerprintImage3 = image;
			break;
		default:
			break;
		}
	       
	       
	       //Repaint, so that the new image is shown.
	       repaint();        
	   }
	   
	   public BufferedImage getFingerprintImage1() {
			return fingerprintImage1;
		}

		public BufferedImage getFingerprintImage2() {
			return fingerprintImage2;
		}

		public BufferedImage getFingerprintImage3() {
			return fingerprintImage3;
		}
		
		public String obtenerNombreCompleto()
		{
			return txtNombres.getText().trim() + " " + txtApellidos.getText().trim();
		}
		
		

}
