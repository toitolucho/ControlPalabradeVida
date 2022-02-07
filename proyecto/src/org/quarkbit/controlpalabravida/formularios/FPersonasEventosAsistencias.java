package org.quarkbit.controlpalabravida.formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JDialog;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

import org.quarkbit.controlpalabravida.controller.CFPersonasEventosAsistencias;
import org.quarkbit.controlpalabravida.utils.FormUtilities;
import org.quarkbit.controlpalabravida.utils.Util;
import org.quarkbit.controlpalabravida.utils.componentes.ClockLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class FPersonasEventosAsistencias extends JFrame {

	private JPanel contentPane;
	private JPanel pnlHuellaDigital;
	private JPanel pnlFotografia;
	private JLabel lblNombrecompleto;
	private CFPersonasEventosAsistencias control;

	  
	   //The image of the current fingerprint.
	private BufferedImage fingerprintImage = null;
	private JScrollPane scrollPane_1;
	private JTextArea txtListaEventos;
	private JLabel lblEventoActual;
	private JLabel lblRegistroCorrecto;
	private JLabel lblLblimagen;
	private JPanel pnlContendorHuella;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FPersonasEventosAsistencias frame = new FPersonasEventosAsistencias();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FPersonasEventosAsistencias() {
		
		setTitle("Seguimiento Asistencia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 736, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblEventoActual = new JLabel("Evento Actual : ");
		lblEventoActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblEventoActual.setForeground(Color.RED);
		lblEventoActual.setFont(new Font("Calibri", Font.BOLD, 20));
		panel.add(lblEventoActual);
		

		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane_1, BorderLayout.NORTH);
		
		txtListaEventos = new JTextArea();
		scrollPane_1.setViewportView(txtListaEventos);
		
		
		JPanel pnlDatosPersona = new JPanel();
		pnlDatosPersona.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlDatosPersona.setPreferredSize(new Dimension(400, 10));
		contentPane.add(pnlDatosPersona, BorderLayout.EAST);
		pnlDatosPersona.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		pnlDatosPersona.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(2, 0, 0, 0));
		
		pnlFotografia = new JPanel();
		pnlFotografia.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		
		panel_1.add(pnlFotografia);
		
		lblLblimagen = new JLabel("");
		lblLblimagen.setPreferredSize(new Dimension(200, 200));
		lblLblimagen.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblLblimagen.setBorder(new CompoundBorder (
	               new EmptyBorder (2,2,2,2),
	               new BevelBorder(BevelBorder.LOWERED)));
		
		pnlFotografia.add(lblLblimagen);
		
		pnlHuellaDigital = new JPanel(){
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
		pnlHuellaDigital.setPreferredSize(new Dimension(200, 200));
	       
	       
	    pnlContendorHuella = new JPanel();
	    pnlHuellaDigital.setBorder(new CompoundBorder (
	               new EmptyBorder (2,2,2,2),
	               new BevelBorder(BevelBorder.LOWERED)));
		
	    pnlContendorHuella.add(pnlHuellaDigital);
	    panel_1.add(pnlContendorHuella);
		
		
		
		
		JPanel panel_2 = new JPanel();
		pnlDatosPersona.add(panel_2, BorderLayout.SOUTH);
		
		lblNombrecompleto = new JLabel("NombreCompleto");
		panel_2.add(lblNombrecompleto);
		
		JPanel pnlTimer = new JPanel();
		pnlTimer.setBackground(Color.BLACK);
		pnlTimer.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(pnlTimer, BorderLayout.CENTER);
		
		ClockLabel lblDia = new ClockLabel('D');
		ClockLabel lblHora = new ClockLabel('T');
		ClockLabel lblFecha = new ClockLabel('F');
		pnlTimer.setLayout(new GridLayout(3, 1));
		pnlTimer.add(lblFecha);
		pnlTimer.add(lblHora);
		pnlTimer.add(lblDia);
		
		
		lblRegistroCorrecto = new JLabel("REGISTRO CORRECTO");
		lblRegistroCorrecto.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblRegistroCorrecto.setForeground(Color.BLUE);
		lblRegistroCorrecto.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblRegistroCorrecto, BorderLayout.SOUTH);
		
		
		
		this.control = new CFPersonasEventosAsistencias(this);
	}

	public JPanel getPnlHuellaDigital() {
		return pnlHuellaDigital;
	}

	public JPanel getPnlFotografia() {
		return pnlFotografia;
	}

	public JLabel getLblNombrecompleto() {
		return lblNombrecompleto;
	}
	
	
	
	public JLabel getLblEventoActual() {
		return lblEventoActual;
	}

	public JTextArea getTxtListaEventos() {
		return txtListaEventos;
	}
	
	

	public JLabel getLblRegistroCorrecto() {
		return lblRegistroCorrecto;
	}

	public JLabel getLblLblimagen() {
		return lblLblimagen;
	}

	public void showImage(BufferedImage image) {
	       //uses the imageProducer to create the fingerprint image
	       fingerprintImage = image;
	       //Repaint, so that the new image is shown.
	       repaint();        
	   }
	
	public void destroy() {
			this.control.destroy();
	   }

}
