package org.quarkbit.controlpalabravida.formularios.tablemodels;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.quarkbit.controlpalabravida.dao.domain.PersonaHuellaDigital;


public class ModeloPersonasHuellasDigitales implements TableModel{
	protected LinkedList<PersonaHuellaDigital> listaPersonaHuellaDigital = new LinkedList<PersonaHuellaDigital>();


	public LinkedList<PersonaHuellaDigital> getListaPersonaHuellaDigital() {
		return listaPersonaHuellaDigital;
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
			return "Nº Muestra";			
		case 1 :
			return "Mano";
		case 2 :
			return "Dedo";
		case 3 :
			return "Cod. Muestra";
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listaPersonaHuellaDigital.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return listaPersonaHuellaDigital.get(rowIndex).getIdHuella();
		case 1:
			return listaPersonaHuellaDigital.get(rowIndex).obtenerMano();
		case 2:
			return listaPersonaHuellaDigital.get(rowIndex).obtenerDedo();
		case 3:
			return listaPersonaHuellaDigital.get(rowIndex).getHuellaDigital().toString();
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
		PersonaHuellaDigital PersonaHuellaDigital = listaPersonaHuellaDigital.get(rowIndex);
		switch (columnIndex) {
		case 0:
			PersonaHuellaDigital.setIdHuella(( Integer)aValue);
			break;
		case 1:
			PersonaHuellaDigital.setCodigoTipoMano((String)aValue);
			break;
		
		default:
			break;
		}
		//actualiza los listeners
		actualizarListeners(new TableModelEvent(this, rowIndex, rowIndex, rowIndex));
	}
	public void AnadirPersonaHuellaDigital(PersonaHuellaDigital PersonaHuellaDigitalNuevo)
	{
		if(PersonaHuellaDigitalNuevo != null)
			listaPersonaHuellaDigital.add(PersonaHuellaDigitalNuevo);
		else
			listaPersonaHuellaDigital.add(null);
		
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				getColumnCount() -1, getRowCount() -1, 
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		actualizarListeners(eventoNuevo);
	}
	public PersonaHuellaDigital eliminarPersonaHuellaDigital(int rowIndex)
	{
		PersonaHuellaDigital PersonaHuellaDigitalEliminado = listaPersonaHuellaDigital.remove(rowIndex);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.DELETE);
		actualizarListeners(eventoNuevo);
		return PersonaHuellaDigitalEliminado;
	}
	
	public PersonaHuellaDigital obtenerPersonaHuellaDigital(int rowIndex)
	{
		try {
			if(rowIndex >= 0)
				return listaPersonaHuellaDigital.get(rowIndex);
			else 
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public void setPersonaHuellaDigital(int rowIndex, PersonaHuellaDigital PersonaHuellaDigitalNuevo)
	{
		if(rowIndex >= 0)
		{
			listaPersonaHuellaDigital.set(rowIndex, PersonaHuellaDigitalNuevo);
		}
		actualizarListeners(new TableModelEvent(this, rowIndex, rowIndex));
	}
	
	public void setListPersonaHuellaDigital(List<PersonaHuellaDigital> lista)
	{	
//		System.out.println("Tamaño" + lista.size());
		listaPersonaHuellaDigital.clear();
		listaPersonaHuellaDigital.addAll(lista);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				0, lista.size(), TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.INSERT);
		actualizarListeners(eventoNuevo);
		
	}
	
	public void clear()
	{
		listaPersonaHuellaDigital.removeAll(listaPersonaHuellaDigital);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				0, getRowCount(), TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.DELETE);
		actualizarListeners(eventoNuevo);
	}

	public boolean existe(PersonaHuellaDigital EspacioFisicoActual) {
		if(EspacioFisicoActual != null)
		{
		// TODO Auto-generated method stub
			Collections.sort(listaPersonaHuellaDigital);
//			System.out.println("Existe " + Collections.binarySearch(listaEspacioFisico, EspacioFisicoActual));
			return Collections.binarySearch(listaPersonaHuellaDigital, EspacioFisicoActual) >= 0;
		}
		else
		{
			return false;
		
		}
	}

}
