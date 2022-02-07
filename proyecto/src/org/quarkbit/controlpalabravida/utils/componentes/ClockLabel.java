package org.quarkbit.controlpalabravida.utils.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class ClockLabel extends JLabel implements ActionListener{
	char tipo;
	SimpleDateFormat sdf;
	
	public ClockLabel(char tipo) {
		this.tipo = tipo;
		setForeground(Color.GREEN);
		switch (tipo) {
		case 'F':
			sdf = new SimpleDateFormat("dd MMMM yyyy");
			setFont(new Font("sans-serif", Font.PLAIN, 35));
			setHorizontalAlignment(SwingConstants.LEFT);
			break;
		case 'T':
			sdf = new SimpleDateFormat("hh:mm:ss a");
			setFont(new Font("sans-serif", Font.PLAIN, 55));
			setHorizontalAlignment(SwingConstants.CENTER);
			break;
		case 'D':
			sdf = new SimpleDateFormat("EEEE ");
			setFont(new Font("sans-serif", Font.PLAIN, 25));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;
		default:
			sdf = new SimpleDateFormat();
			break;
		}
		Timer t = new Timer(1000,this);
		t.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Date fecha = new Date();
		setText(sdf.format(fecha));
	}

}
