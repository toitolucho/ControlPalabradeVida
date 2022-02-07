/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.quarkbit.controlpalabravida.utils;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.quarkbit.controlpalabravida.formularios.FUsuarios;




public class FormUtilities {

	public static int nroSolicitudInicio = 1;
	public static int nroAutorizacionAlquilerInicio = 1;
	public static int horaInicioNocturno = 19;
	public static int tiempoToleranciaReserva = 8;
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	public static void centrarLogo(JDesktopPane contenedor, JLabel Logo)
	{
		Toolkit toolkit = contenedor.getToolkit();
		Dimension screenSize = toolkit.getScreenSize();
//		Logo.setLocation((screenSize.width-contenedor.getSize().width)/2,(screenSize.height-contenedor.getSize().height)/2);
		Logo.setBounds(contenedor.getLocationOnScreen().x, contenedor.getLocationOnScreen().y, contenedor.getSize().width, contenedor.getSize().height);
	}

        public static String formatearDate(Date fechaHora)
        {
            String CadenaRetorno = "";
            if (fechaHora != null) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = "";
                fecha = df.format(fechaHora);
                CadenaRetorno = fecha;
            }
            return CadenaRetorno;
        }

	public static void centrar(JFrame ventana)//JDesktopPane
	{
		//ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		ventana.setLocation((screenSize.width-ventana.getSize().width)/2,(screenSize.height-ventana.getSize().height)/2);
	}

        public static void centrar2(Window ventanaHija, Window parent)
        {
            ventanaHija.setLocationRelativeTo(parent);
        }

	public static void centrar(JInternalFrame ventana)
	{
		//ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//ventana.getParent().getWidth()JDesktopPane
		System.out.println(" Nombre componente : " + ventana.getParent().getName());
		Toolkit toolkit = ventana.getParent().getToolkit().getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		ventana.setLocation((screenSize.width-ventana.getSize().width)/2,(screenSize.height-ventana.getSize().height)/2 - 100);
	}

	public static void centrar(JDialog ventana)
	{
		//ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		ventana.setLocation((screenSize.width-ventana.getSize().width)/2,(screenSize.height-ventana.getSize().height)/2);
	}

	public static void maximizar(JFrame ventana)
	{
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Dimension screenSize = toolkit.getScreenSize();
//		ventana.setSize(new Dimension(screenSize.width,screenSize.height - 30));
//
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		/*
		 * The next line determines if the taskbar (win) is covered if
		 * unremarked, the task will not be covered by the maximized JFRAME.
		 */
		ventana.setMaximizedBounds(env.getMaximumWindowBounds());
		ventana.setExtendedState(ventana.getExtendedState() | ventana.MAXIMIZED_BOTH);
	}
	


	public static void showMessage(String titulo,String mensaje, JFrame ventana)
	{
		JOptionPane.showMessageDialog(ventana, mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
	}
	public static void showMessage(String titulo,String mensaje, JInternalFrame ventana)
	{
		JOptionPane.showInternalMessageDialog(ventana, mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
	}
	public static void showMessage(String titulo,String mensaje, JDialog ventana)
	{
		JOptionPane.showMessageDialog(ventana, mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showMessage(String titulo,String mensaje, Component ventana)
	{
		JOptionPane.showMessageDialog(ventana, mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
	}

	public static int showConfirmDialog(String titulo,String mensaje, Component ventana, boolean fromDialog)
	{
		if(!fromDialog)
			return JOptionPane.showConfirmDialog(ventana, mensaje, titulo,
				  JOptionPane.YES_NO_OPTION);
		else
		{
			final JOptionPane optionPane = new JOptionPane(
	                mensaje,
	                JOptionPane.QUESTION_MESSAGE,
	                JOptionPane.YES_NO_OPTION);

			final JDialog dialog = new JDialog((JDialog) ventana, "Confirmación", true);
			dialog.setContentPane(optionPane);
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

			optionPane.addPropertyChangeListener(
				    new PropertyChangeListener() {
				        public void propertyChange(PropertyChangeEvent e) {
				            String prop = e.getPropertyName();

				            if (dialog.isVisible()
				             && (e.getSource() == optionPane)
				             && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
				                dialog.setVisible(false);
				            }
				        }
				    });
				dialog.pack();
				dialog.setLocationRelativeTo(ventana);
				dialog.setVisible(true);

				return  ((Integer)optionPane.getValue()).intValue();
		}
	}



	public static String getClassName(Class c) {
    String FQClassName = c.getName();
    int firstChar;
    firstChar = FQClassName.lastIndexOf ('.') + 1;
    if ( firstChar > 0 ) {
      FQClassName = FQClassName.substring ( firstChar );
      }
    return FQClassName;
    }

//    public static String obtenerRutaLocal()
//    {            
//        return GuiPrincipal.class.getResource("GuiPrincipal.class").getPath().substring(1).replaceAll("view/GuiPrincipal.class", "fotografiasPersonas/").trim().replace("%20", " ");
//    }
//
//    public static String obtenerRutaLocalReportes()
//    {
//        return GuiPrincipal.class.getResource("GuiPrincipal.class").getPath().substring(1).replaceAll("view/GuiPrincipal.class", "").trim().replace("%20", " ");
//    }

	public static void keyTyped(KeyEvent e, String tipo, Window form) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();
		if (tipo.compareTo("decimal") == 0 ) {			
			if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
					|| (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_COMMA)) || (c == KeyEvent.VK_PERIOD))) {
				if(form!= null)
					form.getToolkit().beep();
				e.consume();
			}
		}
		if (tipo.compareTo("entero") == 0 ) {			
			if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
					|| (c == KeyEvent.VK_DELETE) ))) {
				if(form!= null)
					form.getToolkit().beep();
				e.consume();
			}
		}
	}
	
	public static String obtenerNombreDia(int diaSemana)
	{
		switch (diaSemana) {
		case Calendar.MONDAY:
			return "LUNES";
		case Calendar.TUESDAY:
			return "MARTES";
		case Calendar.WEDNESDAY:
			return "MIERCOLES";
		case Calendar.THURSDAY:
			return "JUEVES";
		case Calendar.FRIDAY:
			return "VIERNES";
		case Calendar.SATURDAY:
			return "SABADO";
		case Calendar.SUNDAY:
			return "DOMINGO";
		default:
			return "LUNES";
		}
	}
	
	public static String obtenerNombreMes(int numeroMes)
	{
		switch (numeroMes) {
		case Calendar.JANUARY:
			return "ENERO";
		case Calendar.FEBRUARY:
			return "FEBRERO";
		case Calendar.MARCH:
			return "MARZO";
		case Calendar.APRIL:
			return "ABRIL";
		case Calendar.MAY:
			return "MAYO";
		case Calendar.JUNE:
			return "JUNIO";
		case Calendar.JULY:
			return "JULIO";
		case Calendar.AUGUST:
			return "AGOSTO";
		case Calendar.SEPTEMBER:
			return "SEPTIEMBRE";
		case Calendar.OCTOBER:
			return "OCTUBRE";
		case Calendar.NOVEMBER:
			return "NOVIEMBRE";
		case Calendar.DECEMBER:
			return "DICIEMBRE";
		default:
			return "DICIEMBRE";
		}
	}
	
	public static String obtenerHora(Date fechaHora)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaHora);
		
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
//		df.format(fechaHora);
//		return String.valueOf(cal.get(Calendar.HOUR_OF_DAY)) + ":" +  String.valueOf(cal.get(Calendar.MINUTE)) +  String.valueOf(cal.get(Calendar.SECOND));
		return df.format(fechaHora);
	}
	
	public static String obtenerRutaLocal()
	{
		Class myClass =FUsuarios.class; 
		URL url = myClass.getResource("FPrincipal.class");			
		String rutaLocal = url.getPath().substring(1).replaceAll("org/quarkbit/controlpalabravida/formularios/FPrincipal.class", "").trim();
		if (rutaLocal.contains("%20"))
		{
			rutaLocal = rutaLocal.replace("%20", " ");
		}
		
		return rutaLocal;
	}
	
	public String formatearFecha(Date fecha)
	{
		return df.format(fecha);
	}
}
