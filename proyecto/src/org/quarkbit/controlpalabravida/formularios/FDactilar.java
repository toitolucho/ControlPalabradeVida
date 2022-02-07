package org.quarkbit.controlpalabravida.formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import org.quarkbit.controlpalabravida.utils.*;

import javax.swing.JButton;

public class FDactilar extends JFrame {

	private JPanel contentPane;
	private final JButton btnAceptar = new JButton("Aceptar");

	private Util fingerprintSDKSample;

	  
	   //The image of the current fingerprint.
	   private BufferedImage fingerprintImage = null;

	   //Panel used to display the current fingerprint.
	   private JPanel fingerprintViewPanel = null;
	   private JTextArea logTextArea;
	   private JScrollPane logScrollPane = null;
	   private JButton btnVerificar;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					
//					 String grFingerNativeDirectory = new File(".").getAbsolutePath();
//				       if (args.length > 0)
//				           grFingerNativeDirectory  = args[0];
//				       Util.setFingerprintSDKNativeDirectory(grFingerNativeDirectory);
//					
//					FDactilar frame = new FDactilar();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	public static void main(String[] args)  {
	       //Sets the directory from which native libraries / license file 
	       //will be loaded
	       String grFingerNativeDirectory = new File(".").getAbsolutePath();
	       if (args.length > 0)
	           grFingerNativeDirectory  = args[0];
	       Util.setFingerprintSDKNativeDirectory(grFingerNativeDirectory);
	       
	       FDactilar frame = new FDactilar();
	       frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public FDactilar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlBotones = new JPanel();
		contentPane.add(pnlBotones, BorderLayout.SOUTH);
		pnlBotones.add(btnAceptar);
		
		btnVerificar = new JButton("verificar");
		pnlBotones.add(btnVerificar);
		
		btnVerificar.addActionListener(new java.awt.event.ActionListener() {
	           //Asks the user for a template ID, and then makes a verification against it
	           public void actionPerformed(java.awt.event.ActionEvent e) {
	               //Shows a input dialog asking the template ID to verify
	               String id = JOptionPane.showInputDialog(rootPane, "Enter the ID to verify.", "Verify", JOptionPane.QUESTION_MESSAGE);

	               //If the user did not cancel the verification
	               if (id != null) {
	                   try {
	                       //Makes the verification
	                       fingerprintSDKSample.verify(Integer.parseInt(id));

	                   } catch (NumberFormatException e1) {
	                       //If the id is not a number, shows an error message.
	                       writeLog("Invalid ID.");
	                   }
	               }
	           }
	       });
		
		//Adds an event-handler to this button
		btnAceptar.addActionListener(new java.awt.event.ActionListener() {
	           //enrolls the current fingerprint.
	           public void actionPerformed(java.awt.event.ActionEvent e) {
	               try {
					fingerprintSDKSample.enroll();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	           }
	       });
		
		
		fingerprintViewPanel = new JPanel(){
	           //Overrides the paintComponent method for painting the image
	           public void paintComponent(Graphics g) {
	               super.paintComponent(g);

	               //If there's a image to be drawn...
	               if (fingerprintImage!=null) {
	                   //calculates the size/position of the image being drawn,
	                   //so it's size is stretched to fill the whole space
	                   Insets insets = getInsets();
	                   int transX = insets.left;
	                   int transY = insets.top;
	                   int width  = getWidth()  - getInsets().right  - getInsets().left;
	                   int height = getHeight() - getInsets().bottom - getInsets().top;

	                   //draw it!
	                   g.drawImage(fingerprintImage, transX, transY, width, height, null);
	               }

	           }

	       };

	       //Adds a border around it
	       fingerprintViewPanel.setBorder(new CompoundBorder (
	               new EmptyBorder (2,2,2,2),
	               new BevelBorder(BevelBorder.LOWERED)));
		contentPane.add(fingerprintViewPanel, BorderLayout.CENTER);
		
		JPanel pnlDatos = new JPanel();
		contentPane.add(pnlDatos, BorderLayout.WEST);
		
		logTextArea = new JTextArea();
//		contentPane.add(logTextArea, BorderLayout.NORTH);
		
		
		logScrollPane = new JScrollPane(logTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

       //Sets it's size
       logScrollPane.setPreferredSize(new java.awt.Dimension(0, 100));
       //Enables auto-scrolling
       logScrollPane.setAutoscrolls(true);

       //Adds a little border around it
       logScrollPane.setBorder(new CompoundBorder (
               new EmptyBorder (2,2,2,2),
               new BevelBorder(BevelBorder.LOWERED)));
		
       contentPane.add(logScrollPane, BorderLayout.NORTH);
       
       this.addWindowListener(new java.awt.event.WindowAdapter() {
           public void windowClosing(java.awt.event.WindowEvent e) {
               destroy();
               System.exit(0);
           }
       });
       
		this.fingerprintSDKSample = new Util(this);
	}
	
	public void writeLog(String text) {
	       //Appends the text
	       logTextArea.append(text + "\n");

	       //Auto-scrolls to the last message.
	       SwingUtilities.invokeLater(new Runnable() {
	           public void run() {
	               //picks the vertical scrollBar, and sets it to the maximum.
	               JScrollBar vbar = logScrollPane.getVerticalScrollBar();
	               vbar.setValue(vbar.getMaximum());
	           }
	       });
	   }
	/**
	    * Sets the current fingerprint image.
	    * It is shown on the middle of the frame.
	    */
	   public void showImage(BufferedImage image) {
	       //uses the imageProducer to create the fingerprint image
	       fingerprintImage = image;
	       //Repaint, so that the new image is shown.
	       repaint();        
	   }
	   
	   public void enableImage() {
//	       saveMenuItem.setEnabled (true);
//
//	       extractButton.setEnabled(true);
//	       enrollButton.setEnabled  (false);
//	       verifyButton.setEnabled  (false);
//	       identifyButton.setEnabled(false);
	   }

	   /**
	    * This method is called after an template has been
	    * extracted from the current Image, in order to enable
	    * "Enroll", "Verify" and "Identify" buttons
	    */
	   public void enableTemplate() {
//	       enrollButton.setEnabled(true);
//	       verifyButton.setEnabled(true);
//	       identifyButton.setEnabled(true);
	   }
	   
	   /**
	    * Frees Fingerprint SDK resources and finished the program.
	    */
	   public void destroy() {
	       fingerprintSDKSample.destroy();
	   }
}
