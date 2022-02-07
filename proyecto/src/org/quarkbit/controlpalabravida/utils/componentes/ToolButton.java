package org.quarkbit.controlpalabravida.utils.componentes;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


public class ToolButton extends JButton implements MouseListener
{

	private static final long serialVersionUID = 1L;
	protected Border borde_Elevado;
	  protected Border borde_presionado;
	  protected Border borde_inactivo;

	  public ToolButton(Action act, String MensajeToolTip) {
		  
	
//		super((Icon)act.getValue(Action.SMALL_ICON));
		
                            
	    borde_Elevado = new BevelBorder(BevelBorder.RAISED);

	    borde_presionado = new BevelBorder(BevelBorder.LOWERED);

	    borde_inactivo = new EmptyBorder(2, 2, 2, 2);

	    setBorder(borde_inactivo);

	    setMargin(new Insets(1,1,1,1));

	    setToolTipText(MensajeToolTip);//jbtn.addactioListener(control),

	    addActionListener(act);

	    addMouseListener(this);

	    setRequestFocusEnabled(false);
	    
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	  }

	  public float getAlignmentY() { return 0.5f; }

	  public void mousePressed(MouseEvent e) {

	    setBorder(borde_presionado);

	  }

	  public void mouseReleased(MouseEvent e) {

	    setBorder(borde_inactivo);

	  }

	  public void mouseClicked(MouseEvent e) {}

	  public void mouseEntered(MouseEvent e) {

	    setBorder(borde_Elevado);

	  }

	  public void mouseExited(MouseEvent e) {

	    setBorder(borde_inactivo);

	  }

	}