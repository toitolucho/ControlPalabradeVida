package org.quarkbit.controlpalabravida.utils.componentes;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

public class JDesktopPaneImage extends JDesktopPane {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image backImage;
    //in the constructor
    

    
    public JDesktopPaneImage() {        
        backImage = new ImageIcon(this.getClass().getResource("/imagenes/LOGO-SCIAC-Princ.png")).getImage();
    }


    public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backImage, 0, 0, this);
    }
}