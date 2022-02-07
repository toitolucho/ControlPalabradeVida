/*
 * GeneraReport.java
 *
 * Created on 19 de enero de 2005, 07:45 PM
 */

package org.quarkbit.controlpalabravida.utils;

import java.awt.BorderLayout;
import java.io.File;
import net.sf.jasperreports.view.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class GeneraReport {

    private Map parametros;
    private JasperPrint imp;
    private JasperReport report = null;
       
    /**
     * Metodo que permite cargar el archivo jasper generado para reports
     * @param pathJasper
     */
    public void loadReportJasper(String pathJasper){
        try{
//            in=this.getClass().getResource(pathJasper);
            File sourceFile = new File(pathJasper);
            //JasperDesign design = JRXmlLoader.load(new LegacyJasperInputStream(new FileInputStream(sourceFile)));
            //report=(JasperReport)JRLoader.loadObject(pathJasper);
            report=(JasperReport)JRLoader.loadObject(sourceFile);
            parametros=new HashMap();
            parametros.clear();
        }catch(Exception e){ 
            System.out.println("Fallo al cargar el reporte del "+pathJasper);
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Metodo que permite cargar el archivo jasper generado para reportes.
     * @param nameReport
     * @param reportPath
     */
    public void loadReportJasper(String nameReport, String reportPath){
        try{
            System.setProperty("jasper.reports.compile.temp",reportPath);
            //Codigo para compilar
            File sourceFile = new File(reportPath+nameReport+".jasper");
            System.out.println("Nombre del reporte: "+sourceFile.getAbsolutePath());
            //JasperDesign design = JRXmlLoader.load(new LegacyJasperInputStream(new FileInputStream(sourceFile)));
            //JasperDesign design = JRXmlLoader.load(reportPath+nameReport+".jrxml");
            //report = JasperCompileManager.compileReport(design);
            report = (JasperReport) JRLoader.loadObject(sourceFile);
            parametros=new HashMap();
            parametros.clear();
        }catch(Exception e){ 
            System.out.println("Fallo al cargar el reporte del "+nameReport);
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metodo que permite establecer los parametros para el reporte.
     * @param param
     * @param valor
     */
    public void setParameters(Map parameters){
        try{
            parametros.putAll(parameters);
        }catch(Exception e){
            System.out.println("El System Fallo al colocar los parametros: "+parameters);
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metodo que permite establecer los parametros para el reporte.
     * @param param
     * @param valor
     */
    public void setParameters(String param,Object valor){
        try{       
        	System.out.println("Nombre Parametro : " + param + ",  Valor " + valor.toString());
            parametros.put(param,valor);          
            
        }catch(Exception e){
            System.out.println("El System Fallo al colocar los parametros: "+param+" = "+valor);
            System.out.println(e.getMessage());
        }        
    }
    
    /**
     * Metodo que permite establecer los parametros para el reporte.
     * @param conn
     */
    public void fillReport(Connection conn){
//        try{            
//            imp=JasperFillManager.fillReport(report,parametros,conn);
//        }catch(JRException e){
//            System.out.println("Fallo al llenar el reporte por la Clase Connection.");
//            System.out.println(e.getMessage());
//        }
    	
    	try {
			imp=JasperFillManager.fillReport(report,parametros,conn);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			System.out.println("Fallo al llenar el reporte por la Clase Connection.");
			e.printStackTrace();
		}
    }

    /**
     * Metodo que permite establecer los parametros para el reporte.
     * @param ds
     */
    public void fillReport(JRDataSource ds){
        try{
            imp = JasperFillManager.fillReport( report, parametros, ds );
        }catch(JRException e){
            System.out.println("Fallo al llenar el reporte por DataSource.");
            System.out.println(e.getMessage());
        }
    }
     
    /**
     * Metodo que permite establecer los parametros para el reporte.
     * @param lista
     */
    public void fillReport(List lista){
        try{              
            //captura del Cuerpo:
            JRDataSource ds =  new JRBeanCollectionDataSource(lista);
            imp = JasperFillManager.fillReport( report, parametros, ds );
        }catch(JRException e){
            System.out.println("Fallo al llenar el reporte la Lista.");
            System.out.println(e.getMessage());
        }        
    }
    
    /**
     * Metodo que permite la muestra el preview del reporte.
     */
    public void previewReport(){
        try{        
        	//JasperViewer.
        	//myFrame.setExtendedState(myFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        	
            JasperViewer.viewReport(imp,false);  
            
        }catch(Exception e){
            System.out.println("Error al Visualizar el reporte");
            System.out.println(e.getMessage());
        }
    }
    
    public void previewReportModal(){
        try{        
        	//JasperViewer.
        	//myFrame.setExtendedState(myFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        	JasperViewer jasperViewer = new JasperViewer(imp,false);
        	jasperViewer.setExtendedState(jasperViewer.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        	
        	JRViewer visor  = new JRViewer(imp);
        	
        	JDialog ventana = new JDialog();
        	ventana.setLayout(new BorderLayout());
        	ventana.add(visor);
        	ventana.setModal(true);
        	ventana.setAlwaysOnTop(true);        	
        	FormUtilities.centrar(ventana);
        	ventana.setBounds(jasperViewer.getBounds());
        	ventana.setVisible(true);
        	
//            jasperViewer.setVisible(true);            
        }catch(Exception e){
            System.out.println("Error al Visualizar el reporte");
            System.out.println(e.getMessage());
        }
    }
    
    
    public void previewReport(boolean mostrarMaximizado){
        try{        
        	//JasperViewer.
        	//myFrame.setExtendedState(myFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        	JasperViewer jasperViewer = new JasperViewer(imp,false);
        	jasperViewer.setExtendedState(jasperViewer.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        	
        	
            jasperViewer.setVisible(true);            
        }catch(Exception e){
            System.out.println("Error al Visualizar el reporte");
            System.out.println(e.getMessage());
        }
    }
    
    public JasperPrint getImp(){
        return imp;
    }

    /*
    public void imprimir_reporte(){
        //Metodo que permite la muestra el preview del reporte
        try{            
           x.printReport(imp, false);
        }catch(Exception e){System.out.println("El sistema fallo al tratar de generar el reporte");};
    }
    */
}//fin de la Clase
