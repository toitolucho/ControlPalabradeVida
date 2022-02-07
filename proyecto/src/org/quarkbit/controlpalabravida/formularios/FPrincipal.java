package org.quarkbit.controlpalabravida.formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.quarkbit.controlpalabravida.controller.CFPrincipal;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class FPrincipal extends JFrame {

	private JPanel contentPane;
	public CFPrincipal control;
	private JMenuItem mntmCopiaSeguridad;
	private JMenuItem mntmUsuarios;
	private JMenuItem mntmSalir;
	private JMenuItem mntmPersonas;
	private JMenuItem mntmEventos;
	private JMenuItem mntmSeguimiento;
	private JMenuItem mntmNewMenuItem;	
	private JMenuItem mntmReportePersonasAsistenciaporEvento;	
	private JMenuItem mntmReportePersonasAsistencia;
	
	
	private JButton btnPersonas;
	private JButton btnEventos;
	private JButton btnSeguimiento;
	private JMenuItem mntmCambiarContrasea;
	private JPanel pnlDatos;
	public JLabel lblLblusuario;
	public JLabel lblFechasistema;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FPrincipal frame = new FPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FPrincipal() {
		setTitle("Sistema de Control de Asistencia PALABRA DE VIDA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 146);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setActionCommand("Archivo");
		menuBar.add(mnArchivo);
		
		mntmCopiaSeguridad = new JMenuItem("Copia Seguridad");
		mntmCopiaSeguridad.setActionCommand("Backup");
		mnArchivo.add(mntmCopiaSeguridad);
		
		mntmUsuarios = new JMenuItem("Usuarios");
		mnArchivo.add(mntmUsuarios);
		
		mnArchivo.add("-");
		
		mntmCambiarContrasea = new JMenuItem("Cambiar Contrase\u00F1a");
		mntmCambiarContrasea.setActionCommand("Contrasenia");
		mnArchivo.add(mntmCambiarContrasea);
		
		mntmSalir = new JMenuItem("Salir");
		mnArchivo.add(mntmSalir);
		
		JMenu mnAcciones = new JMenu("Acciones");
		menuBar.add(mnAcciones);
		
		mntmPersonas = new JMenuItem("Personas");
		mnAcciones.add(mntmPersonas);
		
		mntmEventos = new JMenuItem("Eventos");
		mnAcciones.add(mntmEventos);
		
		mntmSeguimiento = new JMenuItem("Seguimiento");
		mnAcciones.add(mntmSeguimiento);
		
		mntmReportePersonasAsistenciaporEvento = new JMenuItem("Reporte por Evento");
		mntmReportePersonasAsistenciaporEvento.setActionCommand("ReportePorEvento");
		mnAcciones.add(mntmReportePersonasAsistenciaporEvento);
		
		mntmReportePersonasAsistencia = new JMenuItem("Reporte General");
		mntmReportePersonasAsistencia.setActionCommand("ReporteGeneral");
		mnAcciones.add(mntmReportePersonasAsistencia);
		
		JMenu mnAcercaDe = new JMenu("?");
		menuBar.add(mnAcercaDe);
		
		mntmNewMenuItem = new JMenuItem("Acerca de");
		mntmNewMenuItem.setActionCommand("AcercaDe");
		mnAcercaDe.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnPersonas = new JButton("Personas");
//		btnPersonas.setActionCommand("Personas");
		panel.add(btnPersonas);
		
		btnEventos = new JButton("Eventos");
		panel.add(btnEventos);
		
		btnSeguimiento = new JButton("Seguimiento");
		panel.add(btnSeguimiento);
		
		pnlDatos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlDatos.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(pnlDatos, BorderLayout.SOUTH);
		
		lblLblusuario = new JLabel("lblUsuario");
		pnlDatos.add(lblLblusuario);
		
		lblFechasistema = new JLabel("FechaSistema");
		pnlDatos.add(lblFechasistema);
		
		control = new CFPrincipal(this);
//		control.onFormShown();
	}

	public JMenuItem getMntmReportePersonasAsistenciaporEvento() {
		return mntmReportePersonasAsistenciaporEvento;
	}

	public JMenuItem getMntmReportePersonasAsistencia() {
		return mntmReportePersonasAsistencia;
	}

	public JMenuItem getMntmCopiaSeguridad() {
		return mntmCopiaSeguridad;
	}

	public JMenuItem getMntmUsuarios() {
		return mntmUsuarios;
	}

	public JMenuItem getMntmSalir() {
		return mntmSalir;
	}

	public JMenuItem getMntmPersonas() {
		return mntmPersonas;
	}

	public JMenuItem getMntmEventos() {
		return mntmEventos;
	}

	public JMenuItem getMntmSeguimiento() {
		return mntmSeguimiento;
	}

	public JMenuItem getMntmNewMenuItem() {
		return mntmNewMenuItem;
	}

	public JButton getBtnPersonas() {
		return btnPersonas;
	}

	public JButton getBtnEventos() {
		return btnEventos;
	}

	public JButton getBtnSeguimiento() {
		return btnSeguimiento;
	}

	public JMenuItem getMntmCambiarContrasea() {
		return mntmCambiarContrasea;
	}
	
	

}
