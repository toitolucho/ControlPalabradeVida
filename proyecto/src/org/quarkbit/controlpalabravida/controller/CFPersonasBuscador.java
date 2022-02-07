package org.quarkbit.controlpalabravida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.ibatis.session.SqlSession;
import org.quarkbit.controlpalabravida.dao.Coneccion;
import org.quarkbit.controlpalabravida.dao.domain.Persona;
import org.quarkbit.controlpalabravida.dao.domain.PersonaExample;
import org.quarkbit.controlpalabravida.dao.persistence.EventoPersonaMapper;
import org.quarkbit.controlpalabravida.dao.persistence.PersonaMapper;
import org.quarkbit.controlpalabravida.formularios.FPersonasBuscador;
import org.quarkbit.controlpalabravida.formularios.tablemodels.ModeloPersonas;
import org.quarkbit.controlpalabravida.utils.FormUtilities;

public class CFPersonasBuscador extends MouseAdapter implements ActionListener, DocumentListener{

	FPersonasBuscador formBuscador;
	public Persona personaActual;
	SqlSession session;
	PersonaMapper daoPersonaMapper;
	EventoPersonaMapper daoEventoPersonaMapper;
	String tipoPersona = "F";
	public String tipoBusqueda = "P"; //"P"persona, "R"->"permisos", "E"->EVENTOS
	public int idEvento;
	ModeloPersonas modeloPersonas;
	
	PersonaExample filtro;
	
	public CFPersonasBuscador(FPersonasBuscador formBuscador)
	{
		this.formBuscador = formBuscador;
		
		session = Coneccion.getSqlSessionFactory().openSession();
		daoPersonaMapper = session.getMapper(PersonaMapper.class);
		daoEventoPersonaMapper = session.getMapper(EventoPersonaMapper.class);
		filtro = new PersonaExample();
//		personaActual = new Persona();
		modeloPersonas = new ModeloPersonas();
		
	}
	
	public void onFormShown()
	{
		formBuscador.getTxtTextoBusqueda().grabFocus();
		FormUtilities.centrar(formBuscador);
		formBuscador.getTablePersonas().setModel(modeloPersonas);
		formBuscador.getBtnBuscar().addActionListener(this);
		formBuscador.getCancelButton().addActionListener(this);
		formBuscador.getOkButton().addActionListener(this);
		formBuscador.getTxtTextoBusqueda().addActionListener(this);
		formBuscador.getTablePersonas().addMouseListener(this);
		formBuscador.getTxtTextoBusqueda().getDocument().addDocumentListener(this);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().compareTo("Buscar") == 0)
		{
			if(formBuscador.getTxtTextoBusqueda().getText().isEmpty()
					|| formBuscador.getTxtTextoBusqueda().getText() == null)
			{
				JOptionPane.showMessageDialog(formBuscador, "No ha ingresado ning\u00FAn texto para realizar la busqueda", "Busqueda de personas", JOptionPane.WARNING_MESSAGE);
				return;
			}
			formBuscador.getTxtTextoBusqueda().selectAll();
			formBuscador.getTxtTextoBusqueda().grabFocus();
//			System.out.println(tipoPersona);
			filtro.clear();
			
			if(tipoBusqueda.compareTo("P") == 0)
			{
				filtro.or().andNombresLike("%" + formBuscador.getTxtTextoBusqueda().getText() + "%");
				filtro.or().andApellidosLike("%" + formBuscador.getTxtTextoBusqueda().getText() + "%");	
				modeloPersonas.setListPersona(daoPersonaMapper.selectByExample(filtro));
			}
			else if (tipoBusqueda.compareTo("R") == 0)				
			{				
				filtro.or().andNombresLike("%" + formBuscador.getTxtTextoBusqueda().getText() + "%").andIdEventoEqualTo(idEvento);
				filtro.or().andApellidosLike("%" + formBuscador.getTxtTextoBusqueda().getText() + "%").andIdEventoEqualTo(idEvento);
//				filtro.or().andIdEventoEqualTo(idEvento);
				modeloPersonas.setListPersona(daoPersonaMapper.selectBuscarParticipantesEventosByPrimaryKey(filtro));
			}
		}
		if(e.getActionCommand().compareTo("Cancel") == 0)
		{
			personaActual = null;
			this.formBuscador.setVisible(false);
			session.close();
		}
		if(e.getActionCommand().compareTo("OK") == 0)
		{
			if(formBuscador.getTablePersonas().getSelectedRow() >= 0)
			{
				personaActual = modeloPersonas.obtenerPersona(formBuscador.getTablePersonas().getSelectedRow());			
				this.formBuscador.setVisible(false);
			}
			else
			{
				JOptionPane.showMessageDialog(formBuscador, "No ha seleccionado ning\u00FAn elemento", "Personas", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
        	personaActual = modeloPersonas.obtenerPersona(formBuscador.getTablePersonas().getSelectedRow());
            this.formBuscador.setVisible(false);
        }
    }
	
	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		actionPerformed(new ActionEvent(formBuscador.getTxtTextoBusqueda(), 25, "Buscar"));
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if(formBuscador.getTxtTextoBusqueda().getText().isEmpty())
			actionPerformed(new ActionEvent(formBuscador.getTxtTextoBusqueda(), 25, "Buscar"));
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
