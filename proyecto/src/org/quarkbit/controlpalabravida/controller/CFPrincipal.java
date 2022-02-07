package org.quarkbit.controlpalabravida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.ibatis.session.SqlSession;
import org.quarkbit.controlpalabravida.dao.domain.Usuario;
import org.quarkbit.controlpalabravida.dao.persistence.UsuarioMapper;
import org.quarkbit.controlpalabravida.formularios.FContrasenia;
import org.quarkbit.controlpalabravida.formularios.FEventos;
import org.quarkbit.controlpalabravida.formularios.FFechasRango;
import org.quarkbit.controlpalabravida.formularios.FPersonas;
import org.quarkbit.controlpalabravida.formularios.FPersonasBuscador;
import org.quarkbit.controlpalabravida.formularios.FPersonasEventosAsistencias;
import org.quarkbit.controlpalabravida.formularios.FPrincipal;
import org.quarkbit.controlpalabravida.formularios.FUsuarios;
import org.quarkbit.controlpalabravida.utils.FormUtilities;
import org.quarkbit.controlpalabravida.utils.GeneraReport;


public class CFPrincipal implements ActionListener {
	public FPrincipal formPrincipal;
	private Date fechaHoraServidor;
	private Usuario usuario;
	 private SqlSession session;
	 private UsuarioMapper daoUsuarioMapper;
	private String rutaLocal;
	 
	 
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getActionCommand().compareTo("Personas") == 0)
		{
			FPersonas formPersonas = new FPersonas(formPrincipal);
			FormUtilities.centrar(formPersonas);
			formPersonas.setVisible(true);
			formPersonas.control.destroyFingerprintSDK();
			formPersonas.dispose();
			
		}
		
		if(event.getActionCommand().compareTo("Eventos") == 0)
		{
			FEventos formPersonas = new FEventos(formPrincipal);
			FormUtilities.centrar(formPersonas);
			formPersonas.setVisible(true);			
			formPersonas.dispose();			
		}
		
		if(event.getActionCommand().compareTo("Seguimiento") == 0)
		{
			FPersonasEventosAsistencias formPersonas = new FPersonasEventosAsistencias();
			formPersonas.setVisible(true);			
			//formPersonas.dispose();			
		}
		
		if(event.getActionCommand().compareTo("Salir") == 0)
		{
			System.exit(0);
		}
		
		if(event.getActionCommand().compareTo("Usuarios") == 0)
		{
			FUsuarios formUsuarios = new FUsuarios(formPrincipal);
			FormUtilities.centrar(formUsuarios);
			formUsuarios.setVisible(true);
			formUsuarios.dispose();
		}
		
		if(event.getActionCommand().compareTo("Contrasenia") == 0)
		{
//			FContrasenia formUsuarios = new FContrasenia(formPrincipal);
//			FormUtilities.centrar(formUsuarios);
//			formUsuarios.setVisible(true);
//			formUsuarios.dispose();
			
			
			FContrasenia formContrasenia = new FContrasenia(formPrincipal);
			formContrasenia.getJtxtLogin().setText(usuario.getNombreUsuario());
			formContrasenia.getJtxtLogin().grabFocus();
			formContrasenia.getJtxtLogin().selectAll();
			FormUtilities.centrar(formContrasenia);
			formContrasenia.setVisible(true);
//			System.out.println(usuarioActual.getCodigoUsuario() + ", confirmado " + formContrasenia.getControl().confirmado);
			if(formContrasenia.getControl().confirmado)
			{
				
				usuario.setContrasenia(formContrasenia.getControl().password);
				daoUsuarioMapper.updateByPrimaryKey(usuario);
				session.commit();
				
				JOptionPane.showMessageDialog(formContrasenia, "Contrasenia cambiada correctamente", "Usuarios", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		if(event.getActionCommand().compareTo("ReporteGeneral") == 0)
		{
			FPersonasBuscador formBuscador = new FPersonasBuscador(formPrincipal);
//			formBuscador.control.setTipoPersona("N");
			formBuscador.lblMensaje.setText("Seleccione la persona de la cual desea ver su asistencia");
			formBuscador.setVisible(true);
			
//			if(formBuscador.control.personaActual == null || 
//					formBuscador.control.personaActual.getNombres() == "")
//			{
//				JOptionPane.showMessageDialog(formPrincipal, "No ha seleccionado ningun nino", "Kardex Infantil", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
			
			
			FFechasRango formFechas = new FFechasRango(formPrincipal);
			FormUtilities.centrar(formFechas);
			formFechas.visualizarFiltro(false);
			formFechas.setVisible(true);
			if(formFechas.getDateFechaFin().getDate() == null || formFechas.getDateFechaInicio().getDate() == null)
			{
				JOptionPane.showMessageDialog(formPrincipal, "No ha seleccionado un periodo de tiempo", "Ingresos", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String path = rutaLocal + "org/quarkbit/controlpalabravida/reportes/";
			GeneraReport gr = new GeneraReport();			
			gr.loadReportJasper(path + "robtener_reporte_Asistencia_por_persona.jasper");
			
			gr.setParameters("id_persona_aux",formBuscador.control.personaActual!= null ? formBuscador.control.personaActual.getIdPersona(): null);
			gr.setParameters("fecha_inicio_aux", formFechas.getDateFechaInicio().getDate());
			gr.setParameters("fecha_fin_aux", formFechas.getDateFechaFin().getDate());
			gr.fillReport(session.getConnection());			
			gr.previewReport();
		}
		if(event.getActionCommand().compareTo("ReportePorEvento") == 0)
		{
			FPersonasBuscador formBuscador = new FPersonasBuscador(formPrincipal);
//			formBuscador.control.setTipoPersona("N");
			formBuscador.lblMensaje.setText("Seleccione la persona de la cual desea ver su asistencia");
			formBuscador.setVisible(true);
			
			
			FFechasRango formFechas = new FFechasRango(formPrincipal);
			FormUtilities.centrar(formFechas);
			formFechas.visualizarFiltro(false);
			formFechas.setVisible(true);
			if(formFechas.getDateFechaFin().getDate() == null || formFechas.getDateFechaInicio().getDate() == null)
			{
				JOptionPane.showMessageDialog(formPrincipal, "No ha seleccionado un periodo de tiempo", "Ingresos", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String path = rutaLocal + "org/quarkbit/controlpalabravida/reportes/";
			GeneraReport gr = new GeneraReport();			
			gr.loadReportJasper(path + "robtener_reporte_Asistencia.jasper");
			gr.setParameters("id_persona_aux",formBuscador.control.personaActual!= null ? formBuscador.control.personaActual.getIdPersona(): null);
			gr.setParameters("fecha_inicio_aux", formFechas.getDateFechaInicio().getDate());
			gr.setParameters("fecha_fin_aux", formFechas.getDateFechaFin().getDate());
			gr.fillReport(session.getConnection());			
			gr.previewReport();
		}
		if(event.getActionCommand().compareTo("Backup") == 0)
		{
			System.out.println("Copia de Seguridad");
			try {
				//Variables de configuración
		        String path = FormUtilities.obtenerRutaLocal() + "Backups/control_palabra_vida_01.backup";
		        String servidor = "localhost";
		        String nombreBaseDatos = "control_palabra_vida";
		        String password = "postgres";
		        String nombre_usuario_db = "postgres";
		        
		        
		        System.out.println(path);		        
		        
				Runtime rutinaSistema = Runtime.getRuntime();
		        Process proceso;
		        ProcessBuilder pbConstructorProceso;		       		        

		        rutinaSistema = Runtime.getRuntime();        
//		        pb = new ProcessBuilder("D:/Program Files (x86)/PostgreSQL/8.4/bin/pg_dump.exe", "-v", "-D", "-f", path, "-U", usuario, nombreBaseDatos);
//		        System.out.println(rutaLocal  + ",  Path =" + System.getenv());
//				for (String string : System.getenv().toString().substring(1).split(",")) {
//					System.out.println(string);
//				}
		        //pbConstructorProceso = new ProcessBuilder("D:/Program Files (x86)/PostgreSQL/8.4/bin/pg_dump.exe", "-f", path, "-F", "c", "-Z", "9", "-v", "-o", "-h",servidor, "-U", usuario, nombreBaseDatos);		        
//		        pbConstructorProceso = new ProcessBuilder(System.getenv("ProgramFiles").replace('\\', '/') + "/PostgreSQL/9.2/bin/pg_dump.exe", "-f", path, "-F", "c", "-Z", "9", "-v", "-o", "-h",servidor, "-U", usuario, nombreBaseDatos);
		        
		        
		        //C:\ProgramFiles\PostgreSQL\9.2\bin\pg_dump.exe -f D:\Proyectos\ControlPalabradeVida\proyecto\src\Backups\control_palabra_vida_01.backup -F c -Z 9 -v -o -h localhost -U postgres control_palabra_vida
		        //C:\Program Files\PostgreSQL\9.2\bin\pg_dump.exe -f D:\Proyectos\ControlPalabradeVida\proyecto\src\Backups\control_palabra_vida_01.backup -F c -Z 9 -v -o -h localhost -U postgres control_palabra_vida
//		        pbConstructorProceso = new ProcessBuilder("" + System.getenv("ProgramFiles").replace('\\', '/').replace(" (x86)", "") + "/PostgreSQL/9.2/bin/pg_dump.exe" + "-f " + path + " -F c -Z 9 -v -o -h "+ servidor +" -U "+ nombre_usuario_db +" " + nombreBaseDatos);
		        pbConstructorProceso = new ProcessBuilder("%ProgramFiles%/PostgreSQL/9.2/bin/pg_dump.exe" + "-f " + path + " -F c -Z 9 -v -o -h "+ servidor +" -U "+ nombre_usuario_db +" " + nombreBaseDatos);
		        pbConstructorProceso.environment().put("PGPASSWORD", password);
		        pbConstructorProceso.redirectErrorStream(true);
		        proceso = pbConstructorProceso.start(); 
		        
		        JOptionPane.showMessageDialog(formPrincipal, "Copia de Seguridad Satisfactoria", "Copia de Seguridad", JOptionPane.INFORMATION_MESSAGE);
                
                

		    } catch (Exception e) {
		    	System.out.println("Ocurrio un error \n" + e + "\n" +e.getMessage());
		    }
		}
		if(event.getActionCommand().compareTo("AcercaDe") == 0)
		{
			JOptionPane.showMessageDialog(formPrincipal, "Desarrollado por Ing. Luis A. Molina", "Acerca de", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public CFPrincipal(FPrincipal formPrincipal)
	{
		this.formPrincipal = formPrincipal;
		rutaLocal  = FormUtilities.obtenerRutaLocal();
	}
	
	public void onFormShown()
	{
		formPrincipal.getMntmCopiaSeguridad().addActionListener(this);
		formPrincipal.getMntmEventos().addActionListener(this);
		formPrincipal.getMntmNewMenuItem().addActionListener(this);
		formPrincipal.getMntmPersonas().addActionListener(this);
		formPrincipal.getMntmSeguimiento().addActionListener(this);
		formPrincipal.getMntmUsuarios().addActionListener(this);
		formPrincipal.getMntmSalir().addActionListener(this);
		formPrincipal.getBtnEventos().addActionListener(this);
		formPrincipal.getBtnPersonas().addActionListener(this);
		formPrincipal.getBtnSeguimiento().addActionListener(this);
		formPrincipal.getMntmCambiarContrasea().addActionListener(this);
		formPrincipal.getMntmReportePersonasAsistencia().addActionListener(this);
		formPrincipal.getMntmReportePersonasAsistenciaporEvento().addActionListener(this);
		
		formPrincipal.lblLblusuario.setText("Usuario Actual :" + usuario.getNombreUsuario() +"   |  ") ;
		formPrincipal.lblFechasistema.setText("Fecha : " + FormUtilities.formatearDate(fechaHoraServidor) ) ;
		daoUsuarioMapper=session.getMapper(UsuarioMapper.class);
		FormUtilities.centrar(formPrincipal);
	}

	public Date getFechaHoraServidor() {
		return fechaHoraServidor;
	}

	public void setFechaHoraServidor(Date fechaHoraServidor) {
		this.fechaHoraServidor = fechaHoraServidor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public SqlSession getSession() {
		return session;
	}

	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	
	

}
