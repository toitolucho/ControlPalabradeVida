package org.quarkbit.controlpalabravida.formularios.tablemodels;


import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.quarkbit.controlpalabravida.dao.domain.Usuario;


public class ModeloUsuarios implements TableModel{
	protected LinkedList<Usuario> listaUsuario = new LinkedList<Usuario>();


	public LinkedList<Usuario> getListaUsuario() {
		return listaUsuario;
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
			return "Código";			
		case 1 :
			return "Cuenta";
		
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return listaUsuario.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return listaUsuario.get(rowIndex).getIdUsuario();
		case 1:
			return listaUsuario.get(rowIndex).getNombreUsuario();
		
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
		Usuario usuario = listaUsuario.get(rowIndex);
		switch (columnIndex) {
		case 0:
			usuario.setIdUsuario(( Integer)aValue);
			break;
		case 1:
			usuario.setNombreUsuario((String)aValue);
			break;		
		default:
			break;
		}
		//actualiza los listeners
		actualizarListeners(new TableModelEvent(this, rowIndex, rowIndex, rowIndex));
	}
	public void AnadirUsuario(Usuario UsuarioNuevo)
	{
		if(UsuarioNuevo != null)
			listaUsuario.add(UsuarioNuevo);
		else
			listaUsuario.add(null);
		
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				getColumnCount() -1, getRowCount() -1, 
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		actualizarListeners(eventoNuevo);
	}
	public Usuario eliminarUsuario(int rowIndex)
	{
		Usuario UsuarioEliminado = listaUsuario.remove(rowIndex);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.DELETE);
		actualizarListeners(eventoNuevo);
		return UsuarioEliminado;
	}
	
	public Usuario obtenerUsuario(int rowIndex)
	{
		try {
			if(rowIndex >= 0)
				return listaUsuario.get(rowIndex);
			else 
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	public void setUsuario(int rowIndex, Usuario UsuarioNuevo)
	{
		if(rowIndex >= 0)
		{
			listaUsuario.set(rowIndex, UsuarioNuevo);
		}
		actualizarListeners(new TableModelEvent(this, rowIndex, rowIndex));
	}
	
	public void setListUsuario(List<Usuario> lista)
	{	
//		System.out.println("Tamaño" + lista.size());
		listaUsuario.clear();
		listaUsuario.addAll(lista);
		TableModelEvent eventoNuevo = new TableModelEvent(this, 
				0, lista.size(), TableModelEvent.ALL_COLUMNS, 
				TableModelEvent.INSERT);
		actualizarListeners(eventoNuevo);
		
	}

}
