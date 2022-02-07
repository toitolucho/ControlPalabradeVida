package org.quarkbit.controlpalabravida.formularios;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import org.quarkbit.controlpalabravida.controller.CFContrasenia;

public class FContrasenia extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jpnlCentral = null;
	private JPanel jpnlBotones = null;
	public JLabel jlblLogin = null;
	private JTextField jtxtLogin = null;
	public JLabel jlblPassword1 = null;
	private JPasswordField jPasswordField1 = null;
	public JLabel jlblPassword2 = null;
	private JPasswordField jPasswordField2 = null;
	private JButton jbtnAceptar = null;
	private JButton jbtnCancelar = null;
	public CFContrasenia control = null;  //  @jve:decl-index=0:
	public CFContrasenia getControl() {
		return control;
	}

	public void setControl(CFContrasenia control) {
		this.control = control;
	}

	/**
	 * This is the xxx default constructor
	 */
	public FContrasenia(JFrame owner) {
		super(owner, true);
		control = new CFContrasenia(this);
		this.setTitle("Cambio de Contrasenia");
		initialize();
	}
	public FContrasenia(JInternalFrame owner)
	{
		super(JOptionPane.getFrameForComponent(owner), true);
		control = new CFContrasenia(this);
		initialize();
	}


	public FContrasenia(Frame frame, boolean b) {
		// TODO Auto-generated constructor stub
		control = new CFContrasenia(this);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(348, 225);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJpnlCentral(), BorderLayout.CENTER);
			jContentPane.add(getJpnlBotones(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jpnlCentral	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnlCentral() {
		if (jpnlCentral == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setHgap(10);
			flowLayout1.setVgap(15);
			jlblPassword2 = new JLabel();
			jlblPassword2.setText("Confirmar Contrasenia :");
			jlblPassword1 = new JLabel();
			jlblPassword1.setText("Contrasenia :");
			jlblPassword1.setPreferredSize(new Dimension(135, 16));
			jlblLogin = new JLabel();
			jlblLogin.setText("Cuenta Usuario :");
			jlblLogin.setPreferredSize(new Dimension(135, 16));
			jpnlCentral = new JPanel();
			jpnlCentral.setLayout(flowLayout1);
			jpnlCentral.add(jlblLogin, null);
			jpnlCentral.add(getJtxtLogin(), null);
			jpnlCentral.add(jlblPassword1, null);
			jpnlCentral.add(getJPasswordField1(), null);
			jpnlCentral.add(jlblPassword2, null);
			jpnlCentral.add(getJPasswordField2(), null);
		}
		return jpnlCentral;
	}

	/**
	 * This method initializes jpnlBotones	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJpnlBotones() {
		if (jpnlBotones == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			flowLayout.setVgap(8);
			flowLayout.setHgap(15);
			jpnlBotones = new JPanel();
			jpnlBotones.setLayout(flowLayout);
			jpnlBotones.setPreferredSize(new Dimension(0, 45));
			jpnlBotones.add(getJbtnAceptar(), null);
			jpnlBotones.add(getJbtnCancelar(), null);
		}
		return jpnlBotones;
	}

	/**
	 * This method initializes jtxtLogin	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getJtxtLogin() {
		if (jtxtLogin == null) {
			jtxtLogin = new JTextField();
			jtxtLogin.setPreferredSize(new Dimension(150, 28));
			jtxtLogin.setActionCommand("login");
			jtxtLogin.addActionListener(control);
			jtxtLogin.setEditable(false);
			
		}
		return jtxtLogin;
	}

	/**
	 * This method initializes jPasswordField1	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	public JPasswordField getJPasswordField1() {
		if (jPasswordField1 == null) {
			jPasswordField1 = new JPasswordField();
			jPasswordField1.setPreferredSize(new Dimension(150, 28));
			jPasswordField1.setActionCommand("contrasenia1");
			jPasswordField1.addActionListener(control);			
		}
		return jPasswordField1;
	}

	/**
	 * This method initializes jPasswordField2	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	public JPasswordField getJPasswordField2() {
		if (jPasswordField2 == null) {
			jPasswordField2 = new JPasswordField();
			jPasswordField2.setPreferredSize(new Dimension(150, 28));
			jPasswordField2.setActionCommand("contrasenia2");
			jPasswordField2.addActionListener(control);			
		}
		return jPasswordField2;
	}

	/**
	 * This method initializes jbtnAceptar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJbtnAceptar() {
		if (jbtnAceptar == null) {
			jbtnAceptar = new JButton();
			jbtnAceptar.setText("Aceptar");
			jbtnAceptar.setIcon(new ImageIcon(getClass().getResource("/imagenes/botones/Aceptar01.png")));
			jbtnAceptar.setActionCommand("aceptar");
			jbtnAceptar.addActionListener(control);
		}
		return jbtnAceptar;
	}

	/**
	 * This method initializes jbtnCancelar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getJbtnCancelar() {
		if (jbtnCancelar == null) {
			jbtnCancelar = new JButton();
			jbtnCancelar.setText("Cancelar");
			jbtnCancelar.setIcon(new ImageIcon(getClass().getResource("/imagenes/botones/Cancelar01.png")));
			jbtnCancelar.setActionCommand("cancelar");
			jbtnCancelar.addActionListener(control);
		}
		return jbtnCancelar;
	}

}  //  @jve:decl-index=0:visual-constraint="176,43"
