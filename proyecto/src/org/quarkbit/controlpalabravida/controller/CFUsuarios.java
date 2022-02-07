package org.quarkbit.controlpalabravida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.FormView;

import org.apache.ibatis.session.SqlSession;
import org.quarkbit.controlpalabravida.dao.Coneccion;
import org.quarkbit.controlpalabravida.dao.domain.Persona;
import org.quarkbit.controlpalabravida.dao.domain.Usuario;
import org.quarkbit.controlpalabravida.dao.domain.UsuarioExample;
import org.quarkbit.controlpalabravida.dao.persistence.UsuarioMapper;
import org.quarkbit.controlpalabravida.formularios.FPersonasBuscador;
import org.quarkbit.controlpalabravida.formularios.FUsuarios;
import org.quarkbit.controlpalabravida.formularios.tablemodels.ModeloUsuarios;
import org.quarkbit.controlpalabravida.utils.FormUtilities;


public class CFUsuarios extends MouseAdapter implements ActionListener, ListSelectionListener {
	
	FUsuarios formUsuario;
	SqlSession session;
	UsuarioMapper daoUsuarioMapper;
	ModeloUsuarios modeloUsuario;
	String tipoOperacion = "";
	Usuario usuarioActual;
	Persona personaActual;
	
	public CFUsuarios(FUsuarios formUsuario)
	{
		this.formUsuario = formUsuario;
		usuarioActual = new Usuario();
	}
	
	public void onLoad()
	{
		session = Coneccion.getSqlSessionFactory().openSession();
		daoUsuarioMapper = session.getMapper(UsuarioMapper.class);
		modeloUsuario = new ModeloUsuarios();
		
		formUsuario.getjTableUsuario().setModel(modeloUsuario);
		formUsuario.getTxtCodigo().setEnabled(false);	
		
		formUsuario.getBtnAceptar().addActionListener(this);
		formUsuario.getBtnNuevo().addActionListener(this);
		formUsuario.getBtnCancelar().addActionListener(this);
		formUsuario.getBtnModificar().addActionListener(this);
		formUsuario.getBtnEliminar().addActionListener(this);
		formUsuario.getBtnCerrar().addActionListener(this);
		
		
		habilitarControles(false);
		habilitarBotones(true, false, false, false, false);
		modeloUsuario.setListUsuario(daoUsuarioMapper.selectByExample(null));
		formUsuario.getjTableUsuario().getSelectionModel().addListSelectionListener(this);
		formUsuario.getjTableUsuario().getColumn("Código").setPreferredWidth(5);
		formUsuario.getjTableUsuario().addMouseListener(this);
		FormUtilities.centrar(formUsuario);
		
//		mostrarParaInsercion(null);
	}
	

	
	public void mostrarParaInsercionListado(Usuario Usuario, boolean lista)
	{
		if(Usuario != null) //para edicion
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
		formUsuario.mostrorSoloControles(lista);
		FormUtilities.centrar(formUsuario);
	}
	
	public void limpiarControles()
	{
		formUsuario.getTxtCodigo().setText("");		
		formUsuario.getTxtNombreCuenta().setText("");
		formUsuario.getTxtContrasenia().setText("");	
	}
	
	public void habilitarControles(boolean estadHabilitacion)
	{
		formUsuario.getTxtNombre().setEnabled(estadHabilitacion);		
		formUsuario.getTxtNombreCuenta().setEnabled(estadHabilitacion);
		formUsuario.getTxtContrasenia().setEnabled(estadHabilitacion);		
		formUsuario.getjTableUsuario().setEnabled(!estadHabilitacion);
	}
	
	public boolean validarControles()
	{
		if(formUsuario.getTxtNombreCuenta().getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(formUsuario, "A\u00FAn no ha ingresado el nombre del Usuario", "Usuario", JOptionPane.ERROR_MESSAGE);
			formUsuario.getTxtNombre().grabFocus();
			return false;
		}
		
		UsuarioExample filtro = new UsuarioExample();
		filtro.or().andNombreUsuarioEqualTo(formUsuario.getTxtNombre().getText().toUpperCase());
		if(tipoOperacion.compareTo("I") == 0 && daoUsuarioMapper.countByExample(filtro) > 0)
		{
			JOptionPane.showMessageDialog(formUsuario, "La cuenta provista para el Usuario ya se encuentra registrada", "Usuario Usuario", JOptionPane.ERROR_MESSAGE);
			formUsuario.getTxtNombre().grabFocus();
			return false;
		}
		
		if(formUsuario.getTxtContrasenia().getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(formUsuario, "A\u00FAn no ha ingresado la contraseña del Usuario", "Usuario", JOptionPane.ERROR_MESSAGE);
			formUsuario.getTxtContrasenia().grabFocus();
			return false;
		}
		
		
		return true;
	}
	
	public void habilitarBotones(boolean nuevo, boolean aceptar, boolean cancelar,
			boolean modificar, boolean eliminar)
	{
		formUsuario.getBtnAceptar().setEnabled(aceptar);
		formUsuario.getBtnNuevo().setEnabled(nuevo);
		formUsuario.getBtnCancelar().setEnabled(cancelar);
		formUsuario.getBtnModificar().setEnabled(modificar);
		formUsuario.getBtnEliminar().setEnabled(eliminar);
	}
	
	public void cargarDatos(Usuario usuarioAux)
	{
		if(usuarioAux != null)
		{
			this.usuarioActual = usuarioAux;
			formUsuario.getTxtCodigo().setText(usuarioAux.getIdUsuario() .toString());
			formUsuario.getTxtNombre().setText(usuarioAux.getNombreUsuario());
			formUsuario.getTxtContrasenia().setText(usuarioAux.getContrasenia());		
						
			habilitarBotones(true, false, false, true, true);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(formUsuario.getBtnAceptar().equals(event.getSource()))
		{
			if(validarControles())
			{
				try {
					usuarioActual = new Usuario();
					usuarioActual.setIdUsuario(-1);
					usuarioActual.setNombreUsuario(formUsuario.getTxtNombre().getText());
					usuarioActual.setContrasenia(formUsuario.getTxtContrasenia().getText());					
					
					if(tipoOperacion.compareTo("I")==0)
					{
						daoUsuarioMapper.insert(usuarioActual);
						session.commit();
						
						Map<String, Integer> mapa = new HashMap<String, Integer>();
						mapa.put("ultimoId", 0);
						daoUsuarioMapper.obtenerUltimoId(mapa); //("myMappedStatement",myParams);
						usuarioActual.setIdUsuario(mapa.get("ultimoId"));
						
//					System.out.println("codigo actual = " + UsuarioActual.getIdUsuario());
						modeloUsuario.AnadirUsuario(usuarioActual);
					}
					else
					{
						modeloUsuario.setUsuario(formUsuario.getjTableUsuario().getSelectedRow(), usuarioActual);
						usuarioActual.setIdUsuario(Integer.parseInt(formUsuario.getTxtCodigo().getText()));
						daoUsuarioMapper.updateByPrimaryKey(usuarioActual);
						session.commit();	
					}
					
					if(!formUsuario.getBtnNuevo().isVisible())
					{
						
						formUsuario.setVisible(false);
						//formUsuario.dispose();
						return;
					}
					tipoOperacion ="";
					habilitarBotones(true, false, false, true, true);
					habilitarControles(false);
					JOptionPane.showMessageDialog(formUsuario, "Registro almacenado correctamente", "Usuarios", JOptionPane.INFORMATION_MESSAGE);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(formUsuario, "Ocurri\u00F3 la siguiente excepcion " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		if(formUsuario.getBtnNuevo().equals(event.getSource()))
		{
			
			
			habilitarBotones(false, true, true, false, false);
			habilitarControles(true);
			limpiarControles();
			tipoOperacion = "I";
			formUsuario.getjTableUsuario().clearSelection();			
			
			
		}
		if(formUsuario.getBtnCerrar().equals(event.getSource()))
		{
			usuarioActual = null;
			formUsuario.setVisible(false);
			
			formUsuario.dispose();
			
		}
		if(formUsuario.getBtnModificar().equals(event.getSource()))
		{
			habilitarBotones(false, true, true, false, false);
			habilitarControles(true);			
			tipoOperacion = "E";
			
		}
		if(formUsuario.getBtnEliminar().equals(event.getSource()))
		{
			if(JOptionPane.showConfirmDialog(formUsuario, "¿Se encuentra seguro de eliminar el registro actual?", "Usuarios", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{
				System.out.println("Elemento a eliminar " + Integer.parseInt(formUsuario.getTxtCodigo().getText()));
				
				modeloUsuario.eliminarUsuario(formUsuario.getjTableUsuario().getSelectedRow());
				
				
				daoUsuarioMapper.deleteByPrimaryKey(Integer.parseInt(formUsuario.getTxtCodigo().getText()));
				
				session.commit();
				
				limpiarControles();
				JOptionPane.showMessageDialog(formUsuario, "Objeto eliminado correctamente", "Usuario", JOptionPane.INFORMATION_MESSAGE);
				habilitarBotones(true, false, false, false, false);
			}
			
		}
		
		if(formUsuario.getBtnCancelar().equals(event.getSource()))
		{
			limpiarControles();
			habilitarControles(false);
			habilitarBotones(true, false, false, false, false);
			tipoOperacion = "";
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if(e.getValueIsAdjusting())
			return;
		int indice = ((ListSelectionModel) e.getSource()).getMinSelectionIndex();
		cargarDatos(modeloUsuario.obtenerUsuario(indice));
		
	}
	
	@Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
            this.formUsuario.setVisible(false);
        }
    }

}
