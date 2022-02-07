package org.quarkbit.controlpalabravida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.ibatis.session.SqlSession;
import org.quarkbit.controlpalabravida.dao.Coneccion;
import org.quarkbit.controlpalabravida.dao.domain.Evento;
import org.quarkbit.controlpalabravida.dao.domain.EventoExample;
import org.quarkbit.controlpalabravida.dao.domain.EventoPersona;
import org.quarkbit.controlpalabravida.dao.domain.Persona;
import org.quarkbit.controlpalabravida.dao.domain.PersonaEventoPermisos;
import org.quarkbit.controlpalabravida.dao.domain.PersonaEventoPermisosExample;
import org.quarkbit.controlpalabravida.dao.domain.PersonaExample;
import org.quarkbit.controlpalabravida.dao.persistence.EventoMapper;
import org.quarkbit.controlpalabravida.dao.persistence.EventoPersonaMapper;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaEventoPermisosMapper;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaMapper;
import org.quarkbit.controlpalabravida.formularios.FEventos;
import org.quarkbit.controlpalabravida.formularios.FPersonasBuscador;
import org.quarkbit.controlpalabravida.formularios.tablemodels.ModeloEventos;
import org.quarkbit.controlpalabravida.formularios.tablemodels.ModeloPersonas;
import org.quarkbit.controlpalabravida.utils.FormUtilities;


public class CFEventos extends MouseAdapter implements ActionListener, ListSelectionListener, DocumentListener {
	
	FEventos formEvento;
	SqlSession session;
	EventoMapper daoEventoMapper;
	PersonaMapper daoPersonaMapper;
	EventoPersonaMapper daoEventoPersonaMapper;
	PersonaEventoPermisosMapper daoPersonaEventoPermisosMapper;
	ModeloEventos modeloEvento;
	ModeloPersonas modeloPersonaBusqueda;
	ModeloPersonas modeloPersonaAsignacion;
	ModeloPersonas modeloPersonaPermisos;
	String tipoOperacion = "";
	Evento eventoActual;//yyyy-MM-dd HH:mm:ss
	DateFormat dfFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	DateFormat dfHora = new SimpleDateFormat("HH:mm:ss");
	Calendar horaInicio = Calendar.getInstance();
	Calendar horaFin = Calendar.getInstance();
	EventoExample filtroEventos;
	PersonaExample filtroPersonas;
	Persona personaSeleccionada;
	
	public CFEventos(FEventos formEvento)
	{
		this.formEvento = formEvento;
		eventoActual = new Evento();
		filtroEventos = new EventoExample();
		filtroPersonas = new PersonaExample();
	}
	
	public void onLoad()
	{
		session = Coneccion.getSqlSessionFactory().openSession();
		daoEventoMapper = session.getMapper(EventoMapper.class);
		daoPersonaMapper = session.getMapper(PersonaMapper.class);
		daoEventoPersonaMapper = session.getMapper(EventoPersonaMapper.class);
		daoPersonaEventoPermisosMapper = session.getMapper(PersonaEventoPermisosMapper.class);
		modeloEvento = new ModeloEventos();
		modeloPersonaBusqueda = new ModeloPersonas();
		modeloPersonaAsignacion = new ModeloPersonas();
		modeloPersonaPermisos = new ModeloPersonas();
		
		formEvento.getjTableEvento().setModel(modeloEvento);		
		formEvento.getjTablePersonasBusqueda().setModel(modeloPersonaBusqueda);
		formEvento.getjTablePersonasAsignados().setModel(modeloPersonaAsignacion);
		formEvento.getjTablePermisos().setModel(modeloPersonaPermisos);
		
		formEvento.getTxtCodigo().setEnabled(false);
		
		formEvento.getBtnAceptar().addActionListener(this);
		formEvento.getBtnNuevo().addActionListener(this);
		formEvento.getBtnCancelar().addActionListener(this);
		formEvento.getBtnModificar().addActionListener(this);
		formEvento.getBtnEliminar().addActionListener(this);
		formEvento.getBtnCerrar().addActionListener(this);
		formEvento.getBtnBuscar().addActionListener(this);
		formEvento.getBtnBuscar_1().addActionListener(this);
		formEvento.getTxtPersonasBusqueda().addActionListener(this);
		formEvento.getBtnGuardarCambios().addActionListener(this);
		formEvento.getBtnAsignar().addActionListener(this);
		formEvento.getBtnDesasignar().addActionListener(this);
		formEvento.getBtnBuscarParticipante().addActionListener(this);
		
		formEvento.getBtnAceptarPermiso().addActionListener(this);
		formEvento.getBtnNuevoPermiso().addActionListener(this);
		formEvento.getBtnCancelarPermiso().addActionListener(this);		
		formEvento.getBtnEliminarPermiso().addActionListener(this);
		
		formEvento.getTxtPersonasBusqueda().getDocument().addDocumentListener(this);
		formEvento.getTxtTextoBusquedaEvento().addActionListener(this);
		formEvento.getTxtTextoBusquedaEvento().getDocument().addDocumentListener(this);
		
		
		habilitarControles(false);
		habilitarBotones(true, false, false, false, false);
		modeloEvento.setListEvento(daoEventoMapper.selectByExample(null));
		formEvento.getjTableEvento().getSelectionModel().addListSelectionListener(this);
		formEvento.getjTableEvento().getColumn("Codigo").setPreferredWidth(50);
		formEvento.getjTableEvento().getColumn("Evento").setPreferredWidth(190);
		formEvento.getjTableEvento().addMouseListener(this);
		formEvento.getjTablePersonasAsignados().addMouseListener(this);
		formEvento.getjTablePersonasBusqueda().addMouseListener(this);
		
		FormUtilities.centrar(formEvento);
		formEvento.getcBoxEstado().setSelectedIndex(-1);
		formEvento.getLblEventoSeleccionado().setText("");
		formEvento.getBtnBuscar().setActionCommand("buscarEvento");
		formEvento.getTxtTextoBusquedaEvento().setActionCommand("buscarEvento");
		formEvento.getBtnBuscar_1().setActionCommand("buscarPersona");
		formEvento.getTxtPersonasBusqueda().setActionCommand("buscarPersona");
		
		habilitarRestoComponentes(false);
		habilitarControlesPermisos(false);
		habilitarBotonesPermisos(false, false, false, false);
		
//		mostrarParaInsercion(null);
	}
	

	
	public void mostrarParaInsercionListado(Evento Evento, boolean lista)
	{
		if(Evento != null) //para edicion
		{
			tipoOperacion = "E";
		}
		else
		{
			tipoOperacion = "I";
		}
		
		limpiarControles();
		habilitarControles(true);
		habilitarBotones(false, true, false, false, false);
		formEvento.mostrorSoloControles(lista);
		FormUtilities.centrar(formEvento);
	}
	
	public void limpiarControlesPermiso()
	{
		formEvento.getTxtDescripcionPermiso().setText("");
		formEvento.getTxtNombreCompletoParticipante().setText("");
		formEvento.getcBoxTipoPermiso().setSelectedIndex(-1);
		formEvento.getDateFechaPermiso().setDate(null);
		formEvento.getDateFechaFinPermiso().setDate(null);
		personaSeleccionada = null;
	}
	
	public void limpiarControles()
	{
		formEvento.getTxtCodigo().setText("");
		formEvento.getTxtObservacion().setText("");
		formEvento.getTxtNombre().setText("");
		formEvento.getChckbxDomingo().setSelected(false);
		formEvento.getChckbxJueves().setSelected(false);
		formEvento.getChckbxLunes().setSelected(false);
		formEvento.getChckbxMartes().setSelected(false);
		formEvento.getChckbxMircoles().setSelected(false);
		formEvento.getChckbxSbado().setSelected(false);
		formEvento.getChckbxViernes().setSelected(false);
		formEvento.getcBoxEstado().setSelectedIndex(-1);
		
		Date fecha = Calendar.getInstance().getTime();
		try {
			fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-01-01 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		formEvento.getSpinnerGestion().setValue(2016);
		formEvento.getSpinnerHoraAntes().setValue(fecha);
		formEvento.getSpinnerHoraDespues().setValue(fecha);
		formEvento.getSpinnerHoraFin().setValue(fecha);
		formEvento.getSpinnerHoraInicio().setValue(fecha);
		formEvento.getDateFechaFin().setDate(null);
		formEvento.getDateFechaInicio().setDate(null);
		formEvento.getLblEventoSeleccionado().setText("");
		
	}
	
	public void habilitarControlesPermisos(boolean estadHabilitacion)
	{
		formEvento.getTxtDescripcionPermiso().setEnabled(estadHabilitacion);		
		formEvento.getcBoxTipoPermiso().setEnabled(estadHabilitacion);
		formEvento.getDateFechaPermiso().setEnabled(estadHabilitacion);
		formEvento.getDateFechaFinPermiso().setEnabled(estadHabilitacion);
		formEvento.getBtnBuscarParticipante().setEnabled(estadHabilitacion);
	}
	
	public void habilitarControles(boolean estadHabilitacion)
	{
		formEvento.getTxtNombre().setEnabled(estadHabilitacion);
		formEvento.getTxtObservacion().setEnabled(estadHabilitacion);
		formEvento.getChckbxDomingo().setEnabled(estadHabilitacion);
		formEvento.getChckbxJueves().setEnabled(estadHabilitacion);
		formEvento.getChckbxLunes().setEnabled(estadHabilitacion);
		formEvento.getChckbxMartes().setEnabled(estadHabilitacion);
		formEvento.getChckbxMircoles().setEnabled(estadHabilitacion);
		formEvento.getChckbxSbado().setEnabled(estadHabilitacion);
		formEvento.getChckbxViernes().setEnabled(estadHabilitacion);
		formEvento.getSpinnerGestion().setEnabled(estadHabilitacion);
		formEvento.getSpinnerHoraAntes().setEnabled(estadHabilitacion);
		formEvento.getSpinnerHoraDespues().setEnabled(estadHabilitacion);
		formEvento.getSpinnerHoraFin().setEnabled(estadHabilitacion);
		formEvento.getSpinnerHoraInicio().setEnabled(estadHabilitacion);
		formEvento.getDateFechaFin().setEnabled(estadHabilitacion);
		formEvento.getDateFechaInicio().setEnabled(estadHabilitacion);
		formEvento.getTxtPersonasBusqueda().setEditable(!estadHabilitacion);
		formEvento.getTxtTextoBusquedaEvento().setEditable(!estadHabilitacion);
		formEvento.getBtnAsignar().setEnabled(!estadHabilitacion);
		formEvento.getBtnDesasignar().setEnabled(!estadHabilitacion);
		formEvento.getBtnBuscar().setEnabled(!estadHabilitacion);
		formEvento.getBtnBuscar_1().setEnabled(!estadHabilitacion);
		formEvento.getcBoxEstado().setEnabled(estadHabilitacion);
	}
	
	public boolean validarControlesPermisos()
	{
		if(formEvento.getTxtNombreCompletoParticipante() .getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha seleccionado al participante", "Permisos de Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getTxtNombreCompletoParticipante().grabFocus();
			return false;
		}
		if(formEvento.getcBoxTipoPermiso().getSelectedIndex() < 0)
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha seleccionado el tipo de permiso", "Permisos de Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getcBoxTipoPermiso().grabFocus();
			return false;
		}
		
		if(formEvento.getDateFechaPermiso().getDate() == null)
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha ingresado la fecha inicio del permiso", "Permisos de Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getDateFechaPermiso().grabFocus();
			return false;
		}
		
		if(formEvento.getDateFechaFinPermiso().getDate() == null)
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha ingresado la fecha inicio del permiso", "Permisos de Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getDateFechaFinPermiso().grabFocus();
			return false;
		}
		
		if(formEvento.getDateFechaFinPermiso().getDate().before(formEvento.getDateFechaPermiso().getDate()))
		{
			JOptionPane.showMessageDialog(formEvento, "La fecha final de permiso no puede estar antes de la fecha del permiso", "Permisos de Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getDateFechaFinPermiso().grabFocus();
			return false;
		}
		return true;
	}
	public boolean validarControles()
	{
		
		
		
		if(formEvento.getTxtNombre().getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha ingresado el nombre del Evento", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getTxtNombre().grabFocus();
			return false;
		}
		
		EventoExample filtro = new EventoExample();
		filtro.or().andNombreEventoEqualTo(formEvento.getTxtNombre().getText().toUpperCase());
		if(tipoOperacion.compareTo("I") == 0 && daoEventoMapper.countByExample(filtro) > 0)
		{
			JOptionPane.showMessageDialog(formEvento, "El nombre de la Evento ya se encuentra registrada", "Evento Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getTxtNombre().grabFocus();
			return false;
		}
		
		if(!formEvento.getChckbxDomingo().isSelected() &&
			!formEvento.getChckbxJueves().isSelected() &&
			!formEvento.getChckbxLunes().isSelected() &&
			!formEvento.getChckbxMartes().isSelected() &&
			!formEvento.getChckbxMircoles().isSelected() &&
			!formEvento.getChckbxSbado().isSelected() &&
			!formEvento.getChckbxViernes().isSelected())
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha seleccionado ningún día para el Evento", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getChckbxLunes().grabFocus();
			return false;
		}
		
		if(formEvento.getSpinnerGestion().getValue() == null)
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha ingresado la gestión del Evento", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getTxtNombre().grabFocus();
			return false;
		}
		
		horaInicio.setTime((Date) formEvento.getSpinnerHoraInicio().getValue() );
		horaFin.setTime((Date) formEvento.getSpinnerHoraFin().getValue() );
		if(horaInicio.after(horaFin))
		{
			JOptionPane.showMessageDialog(formEvento, "La hora de inicio debe estar mas antes que la hora de culminacion", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getSpinnerHoraInicio().grabFocus();
			return false;
		}
		
		horaInicio.setTime((Date) formEvento.getSpinnerHoraAntes().getValue() );
		horaFin.setTime((Date) formEvento.getSpinnerHoraInicio().getValue() );
		if(horaInicio.after(horaFin))
		{
			JOptionPane.showMessageDialog(formEvento, "La hora de tolerancia antes del ingreso no puede estar despues de la Hora de inicio", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getSpinnerHoraAntes().grabFocus();
			return false;
		}
		
		horaInicio.setTime((Date) formEvento.getSpinnerHoraFin().getValue() );
		horaFin.setTime((Date) formEvento.getSpinnerHoraDespues().getValue() );
		if(horaInicio.after(horaFin))
		{
			JOptionPane.showMessageDialog(formEvento, "La hora de tolerancia despues de la hora de salida no puede estar antes de la misma", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getSpinnerHoraInicio().grabFocus();
			return false;
		}
		
		
		if(formEvento.getDateFechaInicio().getDate() == null)
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha ingresado la fecha en la que se empezará controlar", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getDateFechaInicio().grabFocus();
			return false;
		}
		
		horaInicio.setTime(formEvento.getDateFechaInicio().getDate());
		if(horaInicio.get(Calendar.YEAR) !=  ((int) formEvento.getSpinnerGestion().getValue()))
		{
			JOptionPane.showMessageDialog(formEvento, "La fecha de inicio que ha seleccionado no corresponde a la gestion seleccionada", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getDateFechaInicio().grabFocus();
			return false;
		}
		
		if(formEvento.getDateFechaFin().getDate() == null)
		{
			JOptionPane.showMessageDialog(formEvento, "A\u00FAn no ha ingresado la fecha en la que se culminara de controlar", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getDateFechaFin().grabFocus();
			return false;
		}
		
		horaInicio.setTime(formEvento.getDateFechaFin().getDate());
		if(horaInicio.get(Calendar.YEAR) !=  ((int) formEvento.getSpinnerGestion().getValue()))
		{
			JOptionPane.showMessageDialog(formEvento, "La fecha de inicio que ha seleccionado no corresponde a la gestion seleccionada", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getDateFechaFin().grabFocus();
			return false;
		}
		
		horaInicio.setTime(formEvento.getDateFechaInicio().getDate() );
		horaFin.setTime(formEvento.getDateFechaFin().getDate() );
		if(horaInicio.after(horaFin))
		{
			JOptionPane.showMessageDialog(formEvento, "La fecha de inicio debe estar mas antes que la fecha en la que culmina el evento", "Evento", JOptionPane.ERROR_MESSAGE);
			formEvento.getSpinnerHoraInicio().grabFocus();
			return false;
		}
		
		return true;
	}
	
	public void habilitarBotones(boolean nuevo, boolean aceptar, boolean cancelar,
			boolean modificar, boolean eliminar)
	{
		formEvento.getBtnAceptar().setEnabled(aceptar);
		formEvento.getBtnNuevo().setEnabled(nuevo);
		formEvento.getBtnCancelar().setEnabled(cancelar);
		formEvento.getBtnModificar().setEnabled(modificar);
		formEvento.getBtnEliminar().setEnabled(eliminar);
	}
	
	public void cargarDatos(Evento eventoAux)
	{
		if(eventoAux != null)
		{
			this.eventoActual = eventoAux;
			formEvento.getTxtCodigo().setText(eventoAux.getIdEvento() .toString());
			formEvento.getTxtNombre().setText(eventoAux.getNombreEvento());
			formEvento.getTxtObservacion().setText(eventoAux.getDescripcion());
			formEvento.getSpinnerGestion().setValue(eventoAux.getGestion());
			formEvento.getChckbxDomingo().setSelected(eventoAux.getControlDomingo());
			formEvento.getChckbxJueves().setSelected(eventoAux.getControlJueves());
			formEvento.getChckbxLunes().setSelected(eventoAux.getControlLunes());
			formEvento.getChckbxMartes().setSelected(eventoAux.getControlMartes());
			formEvento.getChckbxMircoles().setSelected(eventoAux.getControlMiercoles());
			formEvento.getChckbxSbado().setSelected(eventoAux.getControlSabado());
			formEvento.getChckbxViernes().setSelected(eventoAux.getControlViernes());
			formEvento.getSpinnerHoraAntes().setValue(eventoAux.getHoraInicioAntes());
			formEvento.getSpinnerHoraInicio().setValue(eventoAux.getHoraInicio());
			formEvento.getSpinnerHoraFin().setValue(eventoAux.getHoraFin());
			formEvento.getSpinnerHoraDespues().setValue(eventoAux.getHoraFinRetraso());
			formEvento.getDateFechaInicio().setDate(eventoAux.getFechaInicio());
			formEvento.getDateFechaFin().setDate(eventoAux.getFechaFin());
			formEvento.getcBoxEstado().setSelectedItem(eventoAux.obtenerEstado());
			formEvento.getLblEventoSeleccionado().setText("EVENTO :" + eventoAux.getNombreEvento());
			
			habilitarBotones(true, false, false, true, true);
			
			modeloPersonaAsignacion.setListPersona(daoPersonaMapper.selectParticipantesEventosByPrimaryKey(eventoAux.getIdEvento()));
			modeloPersonaPermisos.setListPersona(daoPersonaMapper.selectParticipantesEventosPermisosByPrimaryKey(eventoAux.getIdEvento()));
			
			habilitarControlesPermisos(false);
			habilitarRestoComponentes(true);
			habilitarBotonesPermisos(true, false, false, modeloPersonaPermisos.getListaPersona().size()> 0);
			
		}
		personaSeleccionada = null;
	}
	
	public void habilitarBotonesPermisos(boolean nuevo, boolean aceptar, boolean cancelar, boolean eliminar)
	{
		formEvento.getBtnNuevoPermiso().setEnabled(nuevo);
		formEvento.getBtnAceptarPermiso().setEnabled(aceptar);
		formEvento.getBtnCancelarPermiso().setEnabled(cancelar);
		formEvento.getBtnEliminarPermiso().setEnabled(eliminar);
	}
	public void habilitarRestoComponentes(boolean habilitar)
	{
		formEvento.getBtnBuscar_1().setEnabled(habilitar);
		formEvento.getBtnAsignar().setEnabled(habilitar);
		formEvento.getBtnDesasignar().setEnabled(habilitar);
		formEvento.getBtnGuardarCambios().setEnabled(habilitar);
		formEvento.getjTablePermisos().setEnabled(habilitar);
		formEvento.getjTablePersonasAsignados().setEnabled(habilitar);
		formEvento.getjTablePersonasBusqueda().setEnabled(habilitar);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(formEvento.getBtnAceptar().equals(event.getSource()))
		{
			if(validarControles())
			{
				try {
					eventoActual = new Evento();
					eventoActual.setIdEvento(-1);
					eventoActual.setNombreEvento(formEvento.getTxtNombre().getText().toUpperCase());
					eventoActual.setGestion((int) formEvento.getSpinnerGestion().getValue());
					eventoActual.setControlLunes(formEvento.getChckbxLunes().isSelected());
					eventoActual.setControlMartes(formEvento.getChckbxMartes().isSelected());
					eventoActual.setControlMiercoles(formEvento.getChckbxMircoles().isSelected());
					eventoActual.setControlJueves(formEvento.getChckbxJueves().isSelected());
					eventoActual.setControlViernes(formEvento.getChckbxViernes().isSelected());
					eventoActual.setControlSabado(formEvento.getChckbxSbado().isSelected());
					eventoActual.setControlDomingo(formEvento.getChckbxDomingo().isSelected());
					eventoActual.setHoraInicio((Date) formEvento.getSpinnerHoraInicio().getValue());
					eventoActual.setHoraFin((Date) formEvento.getSpinnerHoraFin().getValue());
					eventoActual.setHoraInicioAntes ((Date) formEvento.getSpinnerHoraAntes().getValue());
					eventoActual.setHoraFinRetraso ((Date) formEvento.getSpinnerHoraDespues().getValue());
					eventoActual.setFechaInicio(formEvento.getDateFechaInicio().getDate());
					eventoActual.setFechaFin(formEvento.getDateFechaFin().getDate());
					eventoActual.enviarEstado(formEvento.getcBoxEstado().getSelectedItem().toString());
					eventoActual.setDescripcion(formEvento.getTxtObservacion().getText());
					
					if(tipoOperacion.compareTo("I")==0)
					{
						daoEventoMapper.insert(eventoActual);
						session.commit();
						
						Map<String, Integer> mapa = new HashMap<String, Integer>();
						mapa.put("ultimoId", 0);
						daoEventoMapper.obtenerUltimoId(mapa); //("myMappedStatement",myParams);
						eventoActual.setIdEvento(mapa.get("ultimoId"));
						
//					System.out.println("codigo actual = " + EventoActual.getIdEvento());
						modeloEvento.AnadirEvento(eventoActual);
						limpiarControlesPermiso();
						formEvento.getLblEventoSeleccionado().setText("EVENTO : " + eventoActual.getNombreEvento());
						formEvento.getLblNombreEventoPermisos().setText("EVENTO : " + eventoActual.getNombreEvento());
					}
					else
					{
						modeloEvento.setEvento(formEvento.getjTableEvento().getSelectedRow(), eventoActual);
						eventoActual.setIdEvento(Integer.parseInt(formEvento.getTxtCodigo().getText()));
						daoEventoMapper.updateByPrimaryKey(eventoActual);
						session.commit();	
					}
					
					if(!formEvento.getBtnNuevo().isVisible())
					{
						
						formEvento.setVisible(false);
						//formEvento.dispose();
						return;
					}
					tipoOperacion ="";
					habilitarBotones(true, false, false, true, true);
					habilitarControles(false);
					JOptionPane.showMessageDialog(formEvento, "Registro almacenado correctamente", "Eventos", JOptionPane.INFORMATION_MESSAGE);
					
					
					habilitarControlesPermisos(true);
					habilitarBotonesPermisos(true, false, false, false);
					habilitarRestoComponentes(true);
					
					
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(formEvento, "Ocurri\u00F3 la siguiente excepcion " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
			
		}		
		
		if(formEvento.getBtnNuevo().equals(event.getSource()))
		{
			habilitarBotones(false, true, true, false, false);
			habilitarControles(true);
			limpiarControles();
			tipoOperacion = "I";
			formEvento.getjTableEvento().clearSelection();
			formEvento.getcBoxEstado().setEnabled(false);
			formEvento.getcBoxEstado().setSelectedItem("HABILITADO");
			habilitarControlesPermisos(false);
			habilitarRestoComponentes(false);
			habilitarBotonesPermisos(false, false, false, false);
		}
		if(formEvento.getBtnCerrar().equals(event.getSource()))
		{
			eventoActual = null;
			formEvento.setVisible(false);
			
			formEvento.dispose();
			
		}
		if(formEvento.getBtnModificar().equals(event.getSource()))
		{
			habilitarBotones(false, true, true, false, false);
			habilitarControles(true);			
			tipoOperacion = "E";
			habilitarControlesPermisos(false);
			habilitarRestoComponentes(false);
			habilitarBotonesPermisos(false, false, false, false);
			
		}
		if(formEvento.getBtnEliminar().equals(event.getSource()))
		{
			if(JOptionPane.showConfirmDialog(formEvento, "¿Se encuentra seguro de eliminar el registro actual?", "Eventos", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
				System.out.println("Elemento a eliminar " + Integer.parseInt(formEvento.getTxtCodigo().getText()));
				
				modeloEvento.eliminarEvento(formEvento.getjTableEvento().getSelectedRow());
				
				
				daoEventoMapper.deleteByPrimaryKey(Integer.parseInt(formEvento.getTxtCodigo().getText()));
				
				session.commit();
				
				limpiarControles();
				JOptionPane.showMessageDialog(formEvento, "Objeto eliminado correctamente", "Evento", JOptionPane.INFORMATION_MESSAGE);
				habilitarBotones(true, false, false, false, false);
				
				habilitarControlesPermisos(false);
				habilitarRestoComponentes(false);
				habilitarBotonesPermisos(false, false, false, false);
			}
			
		}
		
		if(formEvento.getBtnCancelar().equals(event.getSource()))
		{
			limpiarControles();
			habilitarControles(false);
			habilitarBotones(true, false, false, false, false);
			tipoOperacion = "";
			
			habilitarControlesPermisos(false);
			habilitarRestoComponentes(false);
			habilitarBotonesPermisos(false, false, false, false);
		}
		if(event.getActionCommand().compareTo("buscarEvento") == 0)
		{
			if(formEvento.getTxtTextoBusquedaEvento().getText().isEmpty()
					|| formEvento.getTxtTextoBusquedaEvento().getText() == null)
			{
				JOptionPane.showMessageDialog(formEvento, "No ha ingresado ning\u00FAn texto para realizar la busqueda", "Busqueda de personas", JOptionPane.WARNING_MESSAGE);
				formEvento.getTxtTextoBusquedaEvento().grabFocus();
				return;
			}
			formEvento.getTxtTextoBusquedaEvento().selectAll();
			formEvento.getTxtTextoBusquedaEvento().grabFocus();
//			System.out.println(tipoPersona);
			filtroEventos.clear();
			filtroEventos.or().andNombreEventoLike("%" + formEvento.getTxtTextoBusquedaEvento().getText().toUpperCase() + "%");		
			List<Evento> lista = daoEventoMapper.selectByExample(filtroEventos);
			
			modeloEvento.setListEvento(lista);
//			
			
		}
		if(event.getActionCommand().compareTo("buscarPersona") == 0)
		{
			if(formEvento.getTxtPersonasBusqueda().getText().isEmpty()
					|| formEvento.getTxtPersonasBusqueda().getText() == null)
			{
				JOptionPane.showMessageDialog(formEvento, "No ha ingresado ning\u00FAn texto para realizar la busqueda", "Busqueda de personas", JOptionPane.WARNING_MESSAGE);
				formEvento.getTxtPersonasBusqueda().grabFocus();
				return;
			}
			formEvento.getTxtPersonasBusqueda().selectAll();
			formEvento.getTxtPersonasBusqueda().grabFocus();

			filtroPersonas.clear();
			filtroPersonas.or().andNombreCompletoLike("%" + formEvento.getTxtPersonasBusqueda().getText().toUpperCase() + "%");		
			List<Persona> lista2 = daoPersonaMapper.selectByExample(filtroPersonas);
			modeloPersonaBusqueda.setListPersona( lista2);			
			
		}
		if(formEvento.getBtnAsignar().equals(event.getSource()))
		{
			asignarPersona();
		}
		if(formEvento.getBtnDesasignar().equals(event.getSource()))
		{
			desAsignarPersona();
		}
		if(formEvento.getBtnGuardarCambios().equals(event.getSource()))
		{
			if(formEvento.getLblEventoSeleccionado().getText().isEmpty())
			{
				JOptionPane.showMessageDialog(formEvento, "Aun no ha seleccionado ningun evento", "Asignacion de Personas", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(formEvento, "¿Se encuentra seguro de guardar todos los cambios?", "Asignacion de personas", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
				daoEventoMapper.deleteParticipantesByPrimaryKey(eventoActual.getIdEvento());
				session.commit();
				for(Persona participante : modeloPersonaAsignacion.getListaPersona())
				{
					EventoPersona record = new EventoPersona();
					record.setIdEvento(eventoActual.getIdEvento());
					record.setIdPersona(participante.getIdPersona());
					record.setFechaInicio(eventoActual.getFechaInicio());
					record.setFechaFin(eventoActual.getFechaFin());
					daoEventoPersonaMapper.insert(record);
					
					
				}
				session.commit();
				JOptionPane.showMessageDialog(formEvento, "Participantes registrados correctamente", "Asignacion de Participantes", JOptionPane.INFORMATION_MESSAGE);
				formEvento.getTabbedPane().setSelectedIndex(0);
			}
		}
		if(formEvento.getBtnNuevoPermiso().equals(event.getSource()))
		{
			limpiarControlesPermiso();
			habilitarControlesPermisos(true);
			habilitarBotonesPermisos(false, true, true, false);
		}
		if(formEvento.getBtnCancelarPermiso().equals(event.getSource()))
		{
			limpiarControlesPermiso();
			habilitarControlesPermisos(false);
			habilitarBotonesPermisos(true, false, false, false);
		}
		if(formEvento.getBtnAceptarPermiso().equals(event.getSource()))
		{
			if(formEvento.getLblNombreEventoPermisos().getText().isEmpty())
			{
				JOptionPane.showMessageDialog(formEvento, "No ha seleccionado ningún evento");
				return;
			}
			if(validarControlesPermisos())
			{
				//falta validar el permiso de una persona para el mismo evento,en la misma fecha
				
				PersonaEventoPermisos record = new PersonaEventoPermisos();
				record.setId_persona(personaSeleccionada.getIdPersona());
				record.setId_evento(eventoActual.getIdEvento());
				record.setMotivo(formEvento.getTxtDescripcionPermiso().getText());
				record.setTipo_permiso(String.valueOf(formEvento.getcBoxTipoPermiso().getSelectedItem().toString().charAt(0)));
				record.setFecha_permiso(formEvento.getDateFechaPermiso().getDate());
				record.setFechaFinPermiso(formEvento.getDateFechaFinPermiso().getDate());
				
				daoPersonaEventoPermisosMapper.insert(record);
				session.commit();
				
				Map<String, Integer> mapa = new HashMap<String, Integer>();
				mapa.put("ultimoId", 0);
				daoPersonaEventoPermisosMapper.obtenerUltimoId(mapa); 
				record.setId_permiso(mapa.get("ultimoId"));
				modeloPersonaPermisos.AnadirPersona(personaSeleccionada);
				
				habilitarBotonesPermisos(true, false, false, true);
				habilitarControlesPermisos(false);
				limpiarControlesPermiso();
				JOptionPane.showMessageDialog(formEvento, "Registro correcto", "Permisos", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if(formEvento.getBtnEliminarPermiso().equals(event.getSource()))
		{
			int indice = formEvento.getjTablePermisos().getSelectedRow();
			if(indice<0)
			{
				JOptionPane.showMessageDialog(formEvento, "No ha seleccionado ninguna persona con permiso", "Permisos", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				
				//eliminar el permiso
				PersonaEventoPermisosExample filtro = new PersonaEventoPermisosExample();
				filtro.or().andId_eventoEqualTo(eventoActual.getIdEvento()).andId_personaEqualTo(modeloPersonaPermisos.obtenerPersona(indice).getIdPersona());
				daoPersonaEventoPermisosMapper.deleteByExample(filtro);
				session.commit();				
				modeloPersonaPermisos.eliminarPersona(indice);
				JOptionPane.showMessageDialog(formEvento, "Permiso eliminado correctamente", "Permisos", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(formEvento.getBtnBuscarParticipante().equals(event.getSource()))
		{
			FPersonasBuscador formBuscador = new FPersonasBuscador(null);
			formBuscador.control.tipoBusqueda = "R";
			formBuscador.control.idEvento = eventoActual.getIdEvento();
			formBuscador.setVisible(true);
			if(formBuscador.control.personaActual != null && formBuscador.control.personaActual.getNombreCompleto() != null)
			{
				personaSeleccionada = formBuscador.control.personaActual;
				formEvento.getTxtNombreCompletoParticipante().setText(personaSeleccionada.getNombreCompleto());
				
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if(e.getValueIsAdjusting())
			return;
		int indice = ((ListSelectionModel) e.getSource()).getMinSelectionIndex();
		cargarDatos(modeloEvento.obtenerEvento(indice));
		
	}
	
	private void asignarPersona()
	{
		if(formEvento.getLblEventoSeleccionado().getText().isEmpty())
		{
			JOptionPane.showMessageDialog(formEvento, "Aun no ha seleccionado un evento", "Asignacion de personas", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int indice = formEvento.getjTablePersonasBusqueda().getSelectedRow();
		if(indice< 0)
		{
			JOptionPane.showMessageDialog(formEvento, "Aun no ha seleccionado una persona para asignar al evento", "Asignacion de personas", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!modeloPersonaAsignacion.existe(modeloPersonaBusqueda.obtenerPersona(indice)))
			modeloPersonaAsignacion.AnadirPersona(modeloPersonaBusqueda.obtenerPersona(indice));
		else
		{
			JOptionPane.showMessageDialog(formEvento, "La persona que ha seleccionado ya se encuentra registrada en el evento", "Asignacion de personas", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void desAsignarPersona()
	{
		if(formEvento.getLblEventoSeleccionado().getText().isEmpty())
		{
			JOptionPane.showMessageDialog(formEvento, "Aun no ha seleccionado un evento", "Asignacion de personas", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int indice = formEvento.getjTablePersonasAsignados().getSelectedRow();
		if(indice< 0)
		{
			JOptionPane.showMessageDialog(formEvento, "Aun no ha seleccionado una persona para desasignar del evento", "Asignacion de personas", JOptionPane.ERROR_MESSAGE);
			return;
		}
		modeloPersonaAsignacion.eliminarPersona(indice);
	}
	
	@Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2 ) {
        	if(e.getSource().equals(formEvento.getjTableEvento()))
        		this.formEvento.getTabbedPane().setSelectedComponent(formEvento.getPnlAsignacion());
        	if(e.getSource().equals(formEvento.getjTablePersonasBusqueda()))
        	{
        		asignarPersona();
        	}
        	if(e.getSource().equals(formEvento.getjTablePersonasAsignados()))
        	{
        		desAsignarPersona();
        	}
        }
    }
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument().equals(formEvento.getTxtTextoBusquedaEvento().getDocument()))
			actionPerformed(new ActionEvent(formEvento.getTxtTextoBusquedaEvento(), 25, "buscarEvento"));
		if(e.getDocument().equals(formEvento.getTxtPersonasBusqueda().getDocument()))
			actionPerformed(new ActionEvent(formEvento.getTxtPersonasBusqueda(), 25, "buscarPersona"));
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(e.getDocument().equals(formEvento.getTxtTextoBusquedaEvento().getDocument()) 
				&&!formEvento.getTxtTextoBusquedaEvento().getText().isEmpty())
			actionPerformed(new ActionEvent(formEvento.getTxtTextoBusquedaEvento(), 25, "buscarEvento"));
		if(e.getDocument().equals(formEvento.getTxtPersonasBusqueda().getDocument()) 
				&&!formEvento.getTxtPersonasBusqueda().getText().isEmpty())
			actionPerformed(new ActionEvent(formEvento.getTxtPersonasBusqueda(), 25, "buscarPersona"));
	}


	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
