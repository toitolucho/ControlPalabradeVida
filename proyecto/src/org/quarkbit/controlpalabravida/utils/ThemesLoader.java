package org.quarkbit.controlpalabravida.utils;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

public class ThemesLoader {
	
	
	public static NimRODTheme nt = new NimRODTheme();
	public static NimRODLookAndFeel nf = new NimRODLookAndFeel();
	
	public void seleccionarTemadeArchivo(Component formularioPincipal, String pathTheme)
	{
		Properties props = new Properties();
        try {
          props.load( new FileInputStream( pathTheme));//this.getClass().getResourceAsStream( nomFich));
        }
        catch ( Exception ex) {
          ex.printStackTrace();
          return;
        }
        
        nt = NimRODUtils.iniCustomColors( nt, props.getProperty( "nimrodlf.selection"), 
                                     props.getProperty( "nimrodlf.background"),
                                     props.getProperty( "nimrodlf.p1"),
                                     props.getProperty( "nimrodlf.p2"),
                                     props.getProperty( "nimrodlf.p3"),                                  
                                     props.getProperty( "nimrodlf.s1"),
                                     props.getProperty( "nimrodlf.s2"),
                                     props.getProperty( "nimrodlf.s3"),
                                     props.getProperty( "nimrodlf.w"),
                                     props.getProperty( "nimrodlf.b"),
                                     props.getProperty( "nimrodlf.menuOpacity"),
                                     props.getProperty( "nimrodlf.frameOpacity"));
        
        cambiaTema(formularioPincipal);
	}
	
	public void seleccionarTema(Component formularioPincipal)
	{
	      
          JFileChooser fc = new JFileChooser();
          fc.setCurrentDirectory( new File( "."));
          if ( fc.showOpenDialog( formularioPincipal) != JFileChooser.APPROVE_OPTION ) {
            return;
          }
          String nomFich = fc.getSelectedFile().getPath();
          
          Properties props = new Properties();
          try {
            props.load( new FileInputStream( nomFich));//this.getClass().getResourceAsStream( nomFich));
          }
          catch ( Exception ex) {
            ex.printStackTrace();
            return;
          }
          
          nt = NimRODUtils.iniCustomColors( nt, props.getProperty( "nimrodlf.selection"), 
                                       props.getProperty( "nimrodlf.background"),
                                       props.getProperty( "nimrodlf.p1"),
                                       props.getProperty( "nimrodlf.p2"),
                                       props.getProperty( "nimrodlf.p3"),                                  
                                       props.getProperty( "nimrodlf.s1"),
                                       props.getProperty( "nimrodlf.s2"),
                                       props.getProperty( "nimrodlf.s3"),
                                       props.getProperty( "nimrodlf.w"),
                                       props.getProperty( "nimrodlf.b"),
                                       props.getProperty( "nimrodlf.menuOpacity"),
                                       props.getProperty( "nimrodlf.frameOpacity"));
          
          cambiaTema(formularioPincipal);	            
	      
	}
	protected void cambiaTema(Component formularioPincipal) { 
	    NimRODLookAndFeel.setCurrentTheme( nt);
	    try {
	      UIManager.setLookAndFeel( nf);
	    }
	    catch ( Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    SwingUtilities.updateComponentTreeUI( formularioPincipal);

  }
}
