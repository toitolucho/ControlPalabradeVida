package org.quarkbit.controlpalabravida.formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import org.quarkbit.controlpalabravida.controller.CFUsuarios;

import java.awt.Cursor;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class FUsuarios extends JDialog {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton btnNuevo;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnCerrar;
	private JTextField txtCodigo;
	private JTextField txtNombreCuenta;
	private JTable jTableUsuario;
	public CFUsuarios control;
	private JPanel pnlControles;
	private JLabel lblcontrasea;
	private JPasswordField txtContrasenia;
	public JTextField getTxtNombreCuenta() {
		return txtNombreCuenta;
	}

	public JPasswordField getTxtContrasenia() {
		return txtContrasenia;
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FUsuarios frame = new FUsuarios(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
//	public void habilitarEventos()
//	{
//		control = new CFUsuarios(this);
//		btnAceptar.addActionListener(control);
//		btnCancelar.addActionListener(control);
//		btnNuevo.addActionListener(control);
//		btnEliminar.addActionListener(control);
//		btnModificar.addActionListener(control);
//	}

	/**
	 * Create the frame.
	 */
	public FUsuarios(Frame formPadre) {
		
		super(formPadre, true);
		setTitle("Usuarios");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 616, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlBotones = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnlBotones.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		contentPane.add(pnlBotones, BorderLayout.SOUTH);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNuevo.setMnemonic('N');
		btnNuevo.setIcon(new ImageIcon(FUsuarios.class.getResource("/imagenes/BOTONES/Nuevo01.png")));
		pnlBotones.add(btnNuevo);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setIcon(new ImageIcon(FUsuarios.class.getResource("/imagenes/BOTONES/Aceptar01.png")));
		btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAceptar.setMnemonic('A');
		pnlBotones.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(FUsuarios.class.getResource("/imagenes/BOTONES/Cancelar01.png")));
		btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelar.setMnemonic('C');
		pnlBotones.add(btnCancelar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setIcon(new ImageIcon(FUsuarios.class.getResource("/imagenes/BOTONES/Actualizar01.png")));
		btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnModificar.setMnemonic('M');
		pnlBotones.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(FUsuarios.class.getResource("/imagenes/BOTONES/Eliminar01.png")));
		btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminar.setMnemonic('E');
		pnlBotones.add(btnEliminar);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setIcon(new ImageIcon(FUsuarios.class.getResource("/imagenes/BOTONES/Cerrar01.png")));
		btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrar.setMnemonic('r');
		pnlBotones.add(btnCerrar);
		
		pnlControles = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlControles.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(15);
		pnlControles.setPreferredSize(new Dimension(320, 10));
		contentPane.add(pnlControles, BorderLayout.WEST);
		
		JLabel lblCodigo = new JLabel("Codigo :");
		lblCodigo.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblCodigo);
		
		txtCodigo = new JTextField();
		lblCodigo.setLabelFor(txtCodigo);
		pnlControles.add(txtCodigo);
		txtCodigo.setColumns(15);
		
		JLabel lblUsuario = new JLabel("Cuenta :");
		lblUsuario.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblUsuario);
		
		txtNombreCuenta = new JTextField();
		lblUsuario.setLabelFor(txtNombreCuenta);
		txtNombreCuenta.setColumns(15);
		pnlControles.add(txtNombreCuenta);
		
		lblcontrasea = new JLabel("Contrase\u00F1a :");
		lblcontrasea.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblcontrasea);
		
		txtContrasenia = new JPasswordField();
		txtContrasenia.setColumns(15);
		txtContrasenia.setEchoChar('*');
		pnlControles.add(txtContrasenia);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		jTableUsuario = new JTable();
		jTableUsuario.setModel(new DefaultTableModel(
			new Object[][] {
				{"123", "Padres"},
			},
			new String[] {
				"Codigo", "Nombre"
			}
		));
		scrollPane.setViewportView(jTableUsuario);
		
		control = new CFUsuarios(this);
		control.onLoad();
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
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

	public JTextField getTxtCodigo() {
		return txtCodigo;
	}

	public JTextField getTxtNombre() {
		return txtNombreCuenta;
	}



	public JTable getjTableUsuario() {
		return jTableUsuario;
	}

	public void mostrorSoloControles(boolean lista)
	{
		if(lista)
		{
			this.remove(pnlControles);
			this.setSize(320, 300);
			btnAceptar.setVisible(false);
		}
		else
		{
			this.remove(scrollPane);
			this.setSize(350, 250);
		}
		btnCancelar.setVisible(false);
		btnEliminar.setVisible(false);
		btnModificar.setVisible(false);
		btnNuevo.setVisible(false);
		btnAceptar.setEnabled(true);
	}
	
	
}

