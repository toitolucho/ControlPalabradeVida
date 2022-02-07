package org.quarkbit.controlpalabravida.formularios.tablemodels;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.quarkbit.controlpalabravida.dao.domain.Evento;


public class ModeloEventos implements TableModel{
	private LinkedList<Evento> listaEvento = new LinkedList<Evento>();
	DateFormat dfHora = new SimpleDateFormat("HH:mm:ss");

	public LinkedList<Evento> getListaEvento() {
		return listaEvento;
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
			return Integer.class;			
		case 1 :
			return String.class;
		case 2 :
			return String.class;
		case 3 :
			return String.class;
		default:
			return Object.class;
		}
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Codigo";			
		case 1 :
			return "Evento";
		case 2 :
			return "Hr. Inicio";
		case 3 :
			return "Hr. Fin";
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listaEvento.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return listaEvento.get(rowIndex).getIdEvento();
		case 1:
			return listaEvento.get(rowIndex).getNombreEvento();
		case 2:
			return listaEvento.get(rowIndex).getHoraInicio() != null ? dfHora.format(listaEvento.get(rowIndex).getHoraInicio()) : "";
		case 3:
			return listaEvento.get(rowIndex).getHoraFin() != null ? dfHora.format(listaEvento.get(rowIndex).getHoraFin()) : "";
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
		Evento Evento = listaEvento.get(rowIndex);
		switch (columnIndex) {
		case 0:
			Evento.setIdEvento(( Integer)aValue);
			break;
		case 1:
			Evento.setNombreEvento((String)aValue);
			break;
		
		default:
			break;
		}
		//actualiza los listeners
		actualizarListeners(new TableModelEvent(this, rowIndex, rowIndex, rowIndex));
	}
	public void AnadirEvento(Evento EventoNuevo)
	{
		if(EventoNuevo != null)
			listaEvento.add(EventoNuevo);
		else
			listaEvento.add(null);
		
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				getColumnCount() -1, getRowCount() -1, 
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		actualizarListeners(eventoNuevo);
	}
	public Evento eliminarEvento(int rowIndex)
	{
		Evento EventoEliminado = listaEvento.remove(rowIndex);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.DELETE);
		actualizarListeners(eventoNuevo);
		return EventoEliminado;
	}
	
	public Evento obtenerEvento(int rowIndex)
	{
		if(rowIndex >= 0)
			try {
				return listaEvento.get(rowIndex);
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				return null;
			}
		else 
			return null;
	}
	public void setEvento(int rowIndex, Evento EventoNuevo)
	{
		if(rowIndex >= 0)
		{
			listaEvento.set(rowIndex, EventoNuevo);
		}
		actualizarListeners(new TableModelEvent(this, rowIndex, rowIndex));
	}
	
	public void setListEvento(List<Evento> lista)
	{		
		
		
		listaEvento.clear();
		listaEvento.addAll(lista);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				0, lista.size(), TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.INSERT);
		actualizarListeners(eventoNuevo);
		actualizarListeners(eventoNuevo);
		
	}

	public void clear()
	{
		listaEvento.removeAll(listaEvento);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				0, getRowCount(), TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.DELETE);
		actualizarListeners(eventoNuevo);
	}

	public boolean existe(Evento EventoActual) {
		if(EventoActual != null)
		{
		// TODO Auto-generated method stub
			Collections.sort(listaEvento);
//			System.out.println("Existe " + Collections.binarySearch(listaEvento, EventoActual));
			return Collections.binarySearch(listaEvento, EventoActual) >= 0;
		}
		else
		{
			return false;
		
		}
	}

}
