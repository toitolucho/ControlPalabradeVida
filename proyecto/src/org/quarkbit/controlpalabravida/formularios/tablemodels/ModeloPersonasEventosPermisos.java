package org.quarkbit.controlpalabravida.formularios.tablemodels;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.quarkbit.controlpalabravida.dao.domain.Evento;
import org.quarkbit.controlpalabravida.dao.domain.Persona;


public class ModeloPersonasEventosPermisos implements TableModel{
	protected LinkedList<Persona> listaPersona = new LinkedList<Persona>();
	

	public LinkedList<Persona> getListaPersona() {
		return listaPersona;
	}

	private LinkedList<TableModelListener> listeners = new LinkedList<TableModelListener>();
	@Override
	public void addTableModelListener(TableModelListener evento) {
		listeners.add(evento);
	}
	


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;			
		case 1 :
			return String.class;
		
		default:
			return Object.class;
		}
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "CI";			
		case 1 :
			return "Nombre Completo";

		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listaPersona.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return listaPersona.get(rowIndex).getCi();
		case 1:
			return listaPersona.get(rowIndex).getNombreCompleto();
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		listeners.remove(l);
	}
	private void actualizarListeners(TableModelEvent evento)
	{		
		for(int i = 0; i< listeners.size(); i++)
		{
			listeners.get(i).tableChanged(evento);			
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Persona Persona = listaPersona.get(rowIndex);
		switch (columnIndex) {
		case 0:
			Persona.setIdPersona(( Integer)aValue);
			break;
		case 1:
			Persona.setNombres((String)aValue);
			break;
		
		default:
			break;
		}
		//actualiza los listeners
		actualizarListeners(new TableModelEvent(this, rowIndex, rowIndex, rowIndex));
	}
	public void AnadirPersona(Persona PersonaNuevo)
	{
		if(PersonaNuevo != null)
			listaPersona.add(PersonaNuevo);
		else
			listaPersona.add(null);
		
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				getColumnCount() -1, getRowCount() -1, 
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		actualizarListeners(eventoNuevo);
	}
	public Persona eliminarPersona(int rowIndex)
	{
		Persona PersonaEliminado = listaPersona.remove(rowIndex);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.DELETE);
		actualizarListeners(eventoNuevo);
		return PersonaEliminado;
	}
	
	public Persona obtenerPersona(int rowIndex)
	{
		try {
			if(rowIndex >= 0)
				return listaPersona.get(rowIndex);
			else 
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public void setPersona(int rowIndex, Persona PersonaNuevo)
	{
		if(rowIndex >= 0)
		{
			listaPersona.set(rowIndex, PersonaNuevo);
		}
		actualizarListeners(new TableModelEvent(this, rowIndex, rowIndex));
	}
	
	public void setListPersona(List<Persona> lista)
	{	
//		System.out.println("Tamaño" + lista.size());
		listaPersona.clear();
		listaPersona.addAll(lista);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				0, lista.size(), TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.INSERT);
		actualizarListeners(eventoNuevo);
		
	}
	
	public boolean existe(Persona personaActual) {
		if(personaActual != null)
		{
			Collections.sort(listaPersona);
			return Collections.binarySearch(listaPersona, personaActual) >= 0;
		}
		else
		{
			return false;
		
		}
	}

}
