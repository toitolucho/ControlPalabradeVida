package org.quarkbit.controlpalabravida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.quarkbit.controlpalabravida.dao.Coneccion;
import org.quarkbit.controlpalabravida.dao.domain.Usuario;
import org.quarkbit.controlpalabravida.dao.domain.UsuarioExample;
import org.quarkbit.controlpalabravida.dao.persistence.UsuarioMapper;
import org.quarkbit.controlpalabravida.formularios.FAutenticacion;
import org.quarkbit.controlpalabravida.formularios.FPrincipal;
import org.quarkbit.controlpalabravida.utils.FormUtilities;


public class CFAutenticacion implements ActionListener, KeyListener {

	private FAutenticacion formAutenticacion = null;
	private static SqlSession session;
	private static UsuarioMapper dao;
	private Usuario usuario = null;
	private Date fechaHoraServidor;
	/**
	 * Constructor de la Clase
	 * 
	 * @param formAutenticacion
	 */
	public CFAutenticacion(FAutenticacion formAutenticacion) {
		super();
		this.formAutenticacion = formAutenticacion;
		usuario = new Usuario();
		session = Coneccion.getSqlSessionFactory().openSession();
		dao = (UsuarioMapper) session.getMapper(UsuarioMapper.class);
		
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();		
		if (action.compareTo("aceptar") == 0) {
			try {
				autenticar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (action.compareTo("cancelar") == 0) {
			System.exit(0);
		}

	}

	public void setFormAutenticacion(FAutenticacion formAutenticacion) {
		this.formAutenticacion = formAutenticacion;
	}

	public FAutenticacion getFormAutenticacion() {
		return formAutenticacion;
	}


	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			try {
				autenticar();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void autenticar() throws Exception {
		if (!formAutenticacion.getJtxtPassword().getText().isEmpty()
				&& !formAutenticacion.getJtxtUsuario().getText().isEmpty()) {
			
			usuario.setNombreUsuario(formAutenticacion.getJtxtUsuario().getText());
			usuario.setContrasenia(formAutenticacion.getJtxtPassword().getText());
			
			UsuarioExample filtro =new UsuarioExample();
			filtro.or().andNombreUsuarioEqualTo(usuario.getNombreUsuario()).andContraseniaEqualTo(usuario.getContrasenia());
			
			if (dao.selectByExample(filtro).size() > 0) {
				
				// Mostrar el principal y los menus habilitados para este
				// usuario
				
//										
				usuario = dao.selectByExample(filtro).get(0);
				
//				fechaHoraServidor = dao.obtenerFechaHoraServidor();
				
//				Map<String, String> mapa = new HashMap<String, String>();
//				mapa.put("fechaHoraServidor", null);
//				dao.obtenerFechaHoraServidor(mapa); //("myMappedStatement",myParams);
//				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//				fechaHoraServidor= formatter.parse(mapa.get( "fechaHoraServidor"));
//				System.out.println(mapa.get( "fechaHoraServidor"));
				
				
				
				Map<String, Date> mapa = new HashMap<String, Date>();
				mapa.put("fechaHoraServidor", null);
				dao.obtenerFechaHoraServidor(mapa); //("myMappedStatement",myParams);
				
				fechaHoraServidor= mapa.get( "fechaHoraServidor");
//				System.out.println(mapa.get( "fechaHoraServidor"));
//				comidaActual.setCodigoComida(mapa.get("fechaHoraServidor"));
				
				FPrincipal formPrincipal = new FPrincipal();
				formPrincipal.control.setFechaHoraServidor(fechaHoraServidor);
				formPrincipal.control.setUsuario(usuario);
				formPrincipal.control.setSession(session);
				formPrincipal.control.onFormShown();

				//FormUtilities.maximizar(formPrincipal);
				// FormUtilities.centrar(formPrincipal);
//				this.formAutenticacion.dispose();
				this.formAutenticacion.setVisible(false);
				formPrincipal.setVisible(true);
//					formPrincipal.control.mostrarDatosConfiguracion();
			} else
				FormUtilities
						.showMessage(
								"Autenticacion Incorrecta",
								"Probablemente sus datos esten escritos Incorrectamente",
								formAutenticacion);
		} else {
			FormUtilities.showMessage("Error de Autenticacion",
					"Probablemente sus datos esten escritos Incorrectamente",
					formAutenticacion);
		}
	}
}
