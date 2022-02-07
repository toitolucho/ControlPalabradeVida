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

import org.quarkbit.controlpalabravida.controller.CFEventos;

import com.toedter.calendar.JDateChooser;

import java.awt.Cursor;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.BevelBorder;

public class FEventos extends JDialog {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton btnNuevo;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnCerrar;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextArea txtObservacion;
	private JTable jTableEvento;
	public CFEventos control;
	private JPanel pnlControles;
	private JScrollPane scrollPane_1;
	private JSpinner spinnerGestion;
	private JLabel lblGestion;
	private JCheckBox chckbxLunes;
	private JCheckBox chckbxMartes;
	private JCheckBox chckbxMircoles;
	private JCheckBox chckbxJueves;
	private JCheckBox chckbxViernes;
	private JCheckBox chckbxSbado;
	private JCheckBox chckbxDomingo;
	private JPanel pnlDias;
	private JLabel lblHoraInicio;
	private JSpinner spinnerHoraInicio;
	private JLabel lblHoraFin;
	private JSpinner spinnerHoraFin;
	private JLabel lblHrTolerancia;
	private JSpinner spinnerHoraAntes;
	private JLabel lblHrDespes;
	private JSpinner spinnerHoraDespues;
	private JLabel lblFechaInicio;
	private JDateChooser dateFechaInicio;
	private JLabel lblFechaFin;
	private JDateChooser dateFechaFin;
	private JLabel lblEspacio;
	private JLabel label;
	private JPanel pnlDatosBusqueda;
	private JPanel pnlBusquedaEventos;
	private JLabel lblTextoABuscar;
	private JTextField txtTextoBusquedaEvento;
	private JButton btnBuscar;
	private JTabbedPane tabbedPane;
	private JPanel pnlRegistro;
	private JPanel pnlAsignacion;
	private JLabel lblEventoSeleccionado;
	private JPanel pnlListaAsignados;
	private JPanel pnlBusquedaAsignacion;
	private JPanel pnlBusqueda2;
	private JLabel lblNombresABuscar;
	private JTextField txtPersonasBusqueda;
	private JButton btnBuscar_1;
	private JScrollPane scrollPane_2;
	private JTable jTablePersonasBusqueda;
	private JPanel pnlBotonesAsignacion;
	private JButton btnAsignar;
	private JTable jTablePersonasAsignados;
	private JScrollPane scrollPane_3;
	private JButton btnDesasignar;
	private JLabel lblEstado;
	private JComboBox<String> cBoxEstado;
	private JButton btnGuardarCambios;
	private JPanel pnlPermisos;
	private JPanel pnlCentralPermisos;
	private JPanel pnlDatosPermisos;
	private JLabel lblNombreEventoPermisos;
	private JPanel panel;
	private JPanel pnlBotonesPermisos;
	private JLabel lblParticipante;
	private JTextField txtNombreCompletoParticipante;
	private JButton btnBuscarParticipante;
	private JLabel lblFechaDeTolerancia;
	private JDateChooser dateFechaPermiso;
	private JLabel lblTipo;
	private JComboBox<String> cBoxTipoPermiso;
	private JLabel lblDescripcin_1;
	private JTextArea txtDescripcionPermiso;
	private JButton btnNuevoPermiso;
	private JButton btnAceptarPermiso;
	private JButton btnCancelarPermiso;
	private JButton btnEliminarPermiso;
	private JTable jTablePermisos;
	private JLabel lblFechaFin_1;
	private JDateChooser dateFechaFinPermiso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FEventos frame = new FEventos(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// public void habilitarEventos()
	// {
	// control = new CFEventos(this);
	// btnAceptar.addActionListener(control);
	// btnCancelar.addActionListener(control);
	// btnNuevo.addActionListener(control);
	// btnEliminar.addActionListener(control);
	// btnModificar.addActionListener(control);
	// }

	/**
	 * Create the frame.
	 */
	public FEventos(Frame formPadre) {

		super(formPadre, true);
		setTitle("Eventos");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 700, 528);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		pnlRegistro = new JPanel();
		tabbedPane.addTab("Registro", null, pnlRegistro, null);
		pnlRegistro.setLayout(new BorderLayout(0, 0));

		pnlControles = new JPanel();
		pnlRegistro.add(pnlControles, BorderLayout.WEST);
		pnlControles.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Datos del Evento",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		FlowLayout flowLayout = (FlowLayout) pnlControles.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(7);
		pnlControles.setPreferredSize(new Dimension(335, 10));

		JLabel lblCodigo = new JLabel("Codigo :");
		lblCodigo.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblCodigo);

		txtCodigo = new JTextField();
		pnlControles.add(txtCodigo);
		txtCodigo.setColumns(15);

		JLabel lblEvento = new JLabel("Evento :");
		lblEvento.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblEvento);

		txtNombre = new JTextField();
		txtNombre.setColumns(15);
		pnlControles.add(txtNombre);

		lblGestion = new JLabel("Gestion :");
		lblGestion.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblGestion);

		spinnerGestion = new JSpinner();
		spinnerGestion.setModel(new SpinnerNumberModel(new Short((short) 2016), null, null, new Short((short) 1)));
		spinnerGestion.setPreferredSize(new Dimension(120, 20));
		pnlControles.add(spinnerGestion);

		pnlDias = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) pnlDias.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		pnlDias.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "D\u00EDas del Evento",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		pnlDias.setPreferredSize(new Dimension(320, 80));
		pnlControles.add(pnlDias);

		chckbxLunes = new JCheckBox("Lunes  ");
		pnlDias.add(chckbxLunes);

		chckbxMartes = new JCheckBox("Martes ");
		pnlDias.add(chckbxMartes);

		chckbxMircoles = new JCheckBox("Mi\u00E9rcoles");
		pnlDias.add(chckbxMircoles);

		chckbxJueves = new JCheckBox("Jueves");
		pnlDias.add(chckbxJueves);

		chckbxViernes = new JCheckBox("Viernes");
		pnlDias.add(chckbxViernes);

		chckbxSbado = new JCheckBox("S\u00E1bado");
		pnlDias.add(chckbxSbado);

		chckbxDomingo = new JCheckBox("Domingo");
		pnlDias.add(chckbxDomingo);

		lblHoraInicio = new JLabel("Hora inicio :");
		lblHoraInicio.setPreferredSize(new Dimension(60, 14));
		pnlControles.add(lblHoraInicio);

		spinnerHoraInicio = new JSpinner(
				new SpinnerDateModel(new Date(1465683169964L), null, null, Calendar.DAY_OF_MONTH));
		spinnerHoraInicio.setPreferredSize(new Dimension(70, 20));
		JSpinner.DateEditor de_timeHoraInicio = new JSpinner.DateEditor(spinnerHoraInicio, "HH:mm:ss");
		// de_timeHoraInicio.setPreferredSize(new Dimension(60, 19));
		spinnerHoraInicio.setEditor(de_timeHoraInicio);

		pnlControles.add(spinnerHoraInicio);

		lblHoraFin = new JLabel("Hora Fin :");
		lblHoraFin.setPreferredSize(new Dimension(60, 14));
		pnlControles.add(lblHoraFin);

		spinnerHoraFin = new JSpinner(
				new SpinnerDateModel(new Date(1465683180750L), null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor de_timeHoraFin = new JSpinner.DateEditor(spinnerHoraFin, "HH:mm:ss");
		spinnerHoraFin.setEditor(de_timeHoraFin);
		spinnerHoraFin.setPreferredSize(new Dimension(70, 20));
		pnlControles.add(spinnerHoraFin);

		lblHrTolerancia = new JLabel("Hr. Antes :");
		lblHrTolerancia.setPreferredSize(new Dimension(60, 14));
		pnlControles.add(lblHrTolerancia);

		spinnerHoraAntes = new JSpinner(
				new SpinnerDateModel(new Date(1465683198185L), null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor de_timeHoraAntes = new JSpinner.DateEditor(spinnerHoraAntes, "HH:mm:ss");
		spinnerHoraAntes.setEditor(de_timeHoraAntes);
		spinnerHoraAntes.setPreferredSize(new Dimension(70, 20));
		pnlControles.add(spinnerHoraAntes);

		lblHrDespes = new JLabel("Hr.Desp\u00FAes :");
		lblHrDespes.setPreferredSize(new Dimension(60, 14));
		pnlControles.add(lblHrDespes);

		spinnerHoraDespues = new JSpinner(
				new SpinnerDateModel(new Date(1465596804246L), null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor de_timeHoraDespues = new JSpinner.DateEditor(spinnerHoraDespues, "HH:mm:ss");
		spinnerHoraDespues.setEditor(de_timeHoraDespues);
		spinnerHoraDespues.setPreferredSize(new Dimension(70, 20));
		pnlControles.add(spinnerHoraDespues);

		lblFechaInicio = new JLabel("Fecha Inicio :");
		lblFechaInicio.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblFechaInicio);

		dateFechaInicio = new JDateChooser();
		dateFechaInicio.setPreferredSize(new Dimension(170, 22));
		dateFechaInicio.setDateFormatString("dd-MM-yyyy");
		dateFechaInicio.setPreferredSize(new Dimension(100, 17));
		pnlControles.add(dateFechaInicio);

		lblEspacio = new JLabel("");
		lblEspacio.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblEspacio);

		lblFechaFin = new JLabel("Fecha Inicio :");
		lblFechaFin.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblFechaFin);

		dateFechaFin = new JDateChooser();
		dateFechaFin.setPreferredSize(new Dimension(170, 22));
		dateFechaFin.setDateFormatString("dd-MM-yyyy");
		dateFechaFin.setPreferredSize(new Dimension(100, 17));
		pnlControles.add(dateFechaFin);

		label = new JLabel("");
		label.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(label);
		
		lblEstado = new JLabel("Estado :");
		lblEstado.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblEstado);
		
		cBoxEstado = new JComboBox<String>();
		cBoxEstado.setPreferredSize(new Dimension(150, 20));
		cBoxEstado.setModel(new DefaultComboBoxModel<String>(new String[] {"HABILITADO", "BAJA", "SUSPENDIDO"}));
		pnlControles.add(cBoxEstado);

		JLabel lblDescripcin = new JLabel("Descripci\u00F3n :");
		lblDescripcin.setPreferredSize(new Dimension(85, 14));
		pnlControles.add(lblDescripcin);

		txtObservacion = new JTextArea();
		txtObservacion.setColumns(20);
		txtObservacion.setRows(2);
		// pnlControles.add(txtObservacion);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setViewportView(txtObservacion);
		pnlControles.add(scrollPane_1);

		JPanel pnlBotones = new JPanel();
		pnlRegistro.add(pnlBotones, BorderLayout.SOUTH);
		FlowLayout flowLayout_1 = (FlowLayout) pnlBotones.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);

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

		pnlDatosBusqueda = new JPanel();
		pnlRegistro.add(pnlDatosBusqueda);
		pnlDatosBusqueda.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Selecci\u00F3n y Busqueda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlDatosBusqueda.setLayout(new BorderLayout(0, 0));

		pnlBusquedaEventos = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) pnlBusquedaEventos.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		pnlDatosBusqueda.add(pnlBusquedaEventos, BorderLayout.NORTH);

		lblTextoABuscar = new JLabel("Texto a Buscar");
		pnlBusquedaEventos.add(lblTextoABuscar);

		txtTextoBusquedaEvento = new JTextField();
		pnlBusquedaEventos.add(txtTextoBusquedaEvento);
		txtTextoBusquedaEvento.setColumns(12);

		btnBuscar = new JButton("Buscar");
		pnlBusquedaEventos.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnlDatosBusqueda.add(scrollPane);

		jTableEvento = new JTable();
		jTableEvento.setModel(
				new DefaultTableModel(new Object[][] { { "123", "Padres" }, }, new String[] { "Codigo", "Nombre" }));
		scrollPane.setViewportView(jTableEvento);

		pnlAsignacion = new JPanel();
		tabbedPane.addTab("Asignaci\u00F3n", null, pnlAsignacion, null);
		pnlAsignacion.setLayout(new BorderLayout(0, 0));

		lblEventoSeleccionado = new JLabel("Evento : Evento 01");
		lblEventoSeleccionado.setHorizontalAlignment(SwingConstants.CENTER);
		lblEventoSeleccionado.setFont(new Font("Tahoma", Font.BOLD, 15));
		pnlAsignacion.add(lblEventoSeleccionado, BorderLayout.NORTH);

		pnlListaAsignados = new JPanel();
		pnlListaAsignados.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Lista de Personas Asignadas", TitledBorder.CENTER, TitledBorder.TOP, null, Color.RED));
		pnlAsignacion.add(pnlListaAsignados, BorderLayout.CENTER);
		pnlListaAsignados.setLayout(new BorderLayout(0, 0));

		pnlBotonesAsignacion = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) pnlBotonesAsignacion.getLayout();
		flowLayout_4.setAlignment(FlowLayout.RIGHT);
		pnlListaAsignados.add(pnlBotonesAsignacion, BorderLayout.SOUTH);
		
		btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.setMnemonic('C');
		btnGuardarCambios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnlBotonesAsignacion.add(btnGuardarCambios);

		btnDesasignar = new JButton("Desasignar");
		btnDesasignar.setMnemonic('D');
		btnDesasignar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnlBotonesAsignacion.add(btnDesasignar);

		jTablePersonasAsignados = new JTable();
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setViewportView(jTablePersonasAsignados);
		// pnlBusquedaAsignacion.add(scrollPane_3);
		pnlListaAsignados.add(scrollPane_3);

		pnlBusquedaAsignacion = new JPanel();
		pnlBusquedaAsignacion.setPreferredSize(new Dimension(10, 200));
		pnlBusquedaAsignacion.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Busqueda para Asignaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		pnlAsignacion.add(pnlBusquedaAsignacion, BorderLayout.SOUTH);
		pnlBusquedaAsignacion.setLayout(new BorderLayout(0, 0));

		jTablePersonasBusqueda = new JTable();
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setViewportView(jTablePersonasBusqueda);
		pnlBusquedaAsignacion.add(scrollPane_2);

		pnlBusqueda2 = new JPanel();
		pnlBusquedaAsignacion.add(pnlBusqueda2, BorderLayout.NORTH);

		lblNombresABuscar = new JLabel("Nombres a Buscar :");
		pnlBusqueda2.add(lblNombresABuscar);

		txtPersonasBusqueda = new JTextField();
		pnlBusqueda2.add(txtPersonasBusqueda);
		txtPersonasBusqueda.setColumns(20);

		btnBuscar_1 = new JButton("Buscar");
		btnBuscar_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnlBusqueda2.add(btnBuscar_1);

		btnAsignar = new JButton("Asignar");
		btnAsignar.setMnemonic('A');
		btnAsignar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnlBusqueda2.add(btnAsignar);
		
		pnlPermisos = new JPanel();
		tabbedPane.addTab("Permisos", null, pnlPermisos, null);
		pnlPermisos.setLayout(new BorderLayout(0, 0));
		
		pnlCentralPermisos = new JPanel();
		pnlCentralPermisos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Permisos Registrados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlPermisos.add(pnlCentralPermisos, BorderLayout.CENTER);
		pnlCentralPermisos.setLayout(new BorderLayout(0, 0));
		
		lblNombreEventoPermisos = new JLabel("Evento : Evento 01");
		pnlCentralPermisos.add(lblNombreEventoPermisos, BorderLayout.NORTH);
		lblNombreEventoPermisos.setForeground(Color.RED);
		lblNombreEventoPermisos.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreEventoPermisos.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		pnlDatosPermisos = new JPanel();
		pnlDatosPermisos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Registro de Permisos", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		pnlPermisos.add(pnlDatosPermisos, BorderLayout.SOUTH);
		pnlDatosPermisos.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setPreferredSize(new Dimension(10, 120));
		FlowLayout flowLayout_5 = (FlowLayout) panel.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		pnlDatosPermisos.add(panel);
		
		lblParticipante = new JLabel("Participante : ");
		lblParticipante.setPreferredSize(new Dimension(115, 14));
		panel.add(lblParticipante);
		
		txtNombreCompletoParticipante = new JTextField();
		txtNombreCompletoParticipante.setEditable(false);
		panel.add(txtNombreCompletoParticipante);
		txtNombreCompletoParticipante.setColumns(30);
		
		btnBuscarParticipante = new JButton("");
		btnBuscarParticipante.setPreferredSize(new Dimension(25, 25));
		btnBuscarParticipante.setIcon(new ImageIcon(FEventos.class.getResource("/imagenes/botones/Buscar01.png")));
		btnBuscarParticipante.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(btnBuscarParticipante);
		
		lblTipo = new JLabel("Tipo:");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setPreferredSize(new Dimension(40, 14));
		panel.add(lblTipo);
		
		cBoxTipoPermiso = new JComboBox<String>();
		cBoxTipoPermiso.setPreferredSize(new Dimension(130, 20));
		cBoxTipoPermiso.setModel(new DefaultComboBoxModel<String>(new String[] {"SALIDA TEMPORAL", "LICENCIA DIA", "OTRO"}));
		panel.add(cBoxTipoPermiso);
		
		lblFechaDeTolerancia = new JLabel("Inicio. de Tolerancia");
		lblFechaDeTolerancia.setPreferredSize(new Dimension(115, 14));
		panel.add(lblFechaDeTolerancia);
		
		dateFechaPermiso = new JDateChooser();
		dateFechaPermiso.setPreferredSize(new Dimension(170, 22));
		dateFechaPermiso.setDateFormatString("dd-MM-yyyy");
		panel.add(dateFechaPermiso);
		
		lblFechaFin_1 = new JLabel("Fin Permiso");
		lblFechaFin_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaFin_1.setPreferredSize(new Dimension(115, 14));
		panel.add(lblFechaFin_1);
		
		dateFechaFinPermiso = new JDateChooser();
		dateFechaFinPermiso.setPreferredSize(new Dimension(170, 22));
		dateFechaFinPermiso.setDateFormatString("dd-MM-yyyy");
		panel.add(dateFechaFinPermiso);
		
		lblDescripcin_1 = new JLabel("Descripci\u00F3n");
		lblDescripcin_1.setPreferredSize(new Dimension(115, 14));
		panel.add(lblDescripcin_1);
		
		txtDescripcionPermiso = new JTextArea();
		txtDescripcionPermiso.setColumns(40);
		txtDescripcionPermiso.setRows(3);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setViewportView(txtDescripcionPermiso);
		panel.add(scrollPane_1);
		
		pnlBotonesPermisos = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) pnlBotonesPermisos.getLayout();
		flowLayout_6.setAlignment(FlowLayout.RIGHT);
		pnlDatosPermisos.add(pnlBotonesPermisos, BorderLayout.SOUTH);
		
		btnNuevoPermiso = new JButton("Nuevo");
		btnNuevoPermiso.setIcon(new ImageIcon(FEventos.class.getResource("/imagenes/botones/Nuevo01.png")));
		btnNuevoPermiso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNuevoPermiso.setMnemonic('N');
		pnlBotonesPermisos.add(btnNuevoPermiso);
		
		btnAceptarPermiso = new JButton("Aceptar");
		btnAceptarPermiso.setIcon(new ImageIcon(FEventos.class.getResource("/imagenes/botones/Aceptar01.png")));
		btnAceptarPermiso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAceptarPermiso.setMnemonic('A');
		pnlBotonesPermisos.add(btnAceptarPermiso);
		
		btnCancelarPermiso = new JButton("Cancelar");
		btnCancelarPermiso.setIcon(new ImageIcon(FEventos.class.getResource("/imagenes/botones/Cancelar01.png")));
		btnCancelarPermiso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCancelarPermiso.setMnemonic('C');
		pnlBotonesPermisos.add(btnCancelarPermiso);
		
		btnEliminarPermiso = new JButton("Eliminar");
		btnEliminarPermiso.setIcon(new ImageIcon(FEventos.class.getResource("/imagenes/botones/Eliminar01.png")));
		btnEliminarPermiso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEliminarPermiso.setMnemonic('E');
		pnlBotonesPermisos.add(btnEliminarPermiso);

		
		jTablePermisos = new JTable();
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setViewportView(jTablePermisos);
		pnlCentralPermisos.add(scrollPane_4, BorderLayout.CENTER);
		
		control = new CFEventos(this);
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
		return txtNombre;
	}

	public JTextArea getTxtObservacion() {
		return txtObservacion;
	}

	public JTable getjTableEvento() {
		return jTableEvento;
	}

	public void mostrorSoloControles(boolean lista) {
		if (lista) {
			this.remove(pnlControles);
			this.setSize(320, 300);
			btnAceptar.setVisible(false);
		} else {
			this.remove(scrollPane);
			this.setSize(350, 250);
		}
		btnCancelar.setVisible(false);
		btnEliminar.setVisible(false);
		btnModificar.setVisible(false);
		btnNuevo.setVisible(false);
		btnAceptar.setEnabled(true);
	}

	public JSpinner getSpinnerGestion() {
		return spinnerGestion;
	}

	public JCheckBox getChckbxLunes() {
		return chckbxLunes;
	}

	public JCheckBox getChckbxMartes() {
		return chckbxMartes;
	}

	public JCheckBox getChckbxMircoles() {
		return chckbxMircoles;
	}

	public JCheckBox getChckbxJueves() {
		return chckbxJueves;
	}

	public JCheckBox getChckbxViernes() {
		return chckbxViernes;
	}

	public JCheckBox getChckbxSbado() {
		return chckbxSbado;
	}

	public JCheckBox getChckbxDomingo() {
		return chckbxDomingo;
	}

	public JSpinner getSpinnerHoraInicio() {
		return spinnerHoraInicio;
	}

	public JSpinner getSpinnerHoraFin() {
		return spinnerHoraFin;
	}

	public JSpinner getSpinnerHoraAntes() {
		return spinnerHoraAntes;
	}

	public JSpinner getSpinnerHoraDespues() {
		return spinnerHoraDespues;
	}

	public JDateChooser getDateFechaInicio() {
		return dateFechaInicio;
	}

	public JDateChooser getDateFechaFin() {
		return dateFechaFin;
	}

	public JTextField getTxtTextoBusquedaEvento() {
		return txtTextoBusquedaEvento;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JLabel getLblEventoSeleccionado() {
		return lblEventoSeleccionado;
	}

	public JTextField getTxtPersonasBusqueda() {
		return txtPersonasBusqueda;
	}

	public JButton getBtnBuscar_1() {
		return btnBuscar_1;
	}

	public JTable getjTablePersonasBusqueda() {
		return jTablePersonasBusqueda;
	}

	public JButton getBtnAsignar() {
		return btnAsignar;
	}

	public JTable getjTablePersonasAsignados() {
		return jTablePersonasAsignados;
	}

	public JButton getBtnDesasignar() {
		return btnDesasignar;
	}

	public JComboBox<String> getcBoxEstado() {
		return cBoxEstado;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public JPanel getPnlAsignacion() {
		return pnlAsignacion;
	}

	public JButton getBtnGuardarCambios() {
		return btnGuardarCambios;
	}

	public JDateChooser getDateFechaPermiso() {
		return dateFechaPermiso;
	}

	public JComboBox<String> getcBoxTipoPermiso() {
		return cBoxTipoPermiso;
	}

	public JTextArea getTxtDescripcionPermiso() {
		return txtDescripcionPermiso;
	}

	public JButton getBtnNuevoPermiso() {
		return btnNuevoPermiso;
	}

	public JButton getBtnAceptarPermiso() {
		return btnAceptarPermiso;
	}

	public JButton getBtnCancelarPermiso() {
		return btnCancelarPermiso;
	}

	public JButton getBtnEliminarPermiso() {
		return btnEliminarPermiso;
	}

	public JPanel getPnlRegistro() {
		return pnlRegistro;
	}

	public JPanel getPnlPermisos() {
		return pnlPermisos;
	}

	public JLabel getLblNombreEventoPermisos() {
		return lblNombreEventoPermisos;
	}

	public JTextField getTxtNombreCompletoParticipante() {
		return txtNombreCompletoParticipante;
	}

	public JTable getjTablePermisos() {
		return jTablePermisos;
	}

	public JDateChooser getDateFechaFinPermiso() {
		return dateFechaFinPermiso;
	}

	public JButton getBtnBuscarParticipante() {
		return btnBuscarParticipante;
	}
	
	
}
