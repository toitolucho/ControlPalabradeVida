package org.quarkbit.controlpalabravida.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.quarkbit.controlpalabravida.formularios.FContrasenia;
import org.quarkbit.controlpalabravida.utils.FormUtilities;

public class CFContrasenia implements ActionListener {
	FContrasenia formContrasenia = null;	
	public String password;
	public String login;
	public boolean confirmado = false;
	public CFContrasenia(FContrasenia fcontrasenia )
	{
		this.formContrasenia = fcontrasenia;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String accion = e.getActionCommand();
		if(accion.compareTo("aceptar")==0)
		{
			if(coincidenContrasenias())
			{
				this.login = formContrasenia.getJtxtLogin().getText();
				password = formContrasenia.getJPasswordField1().getText();
				confirmado = true;
				formContrasenia.setVisible(false);
				System.out.println("Contraseñas coinciden!!!!!!!");
			}
			else
			{
				System.out.println("Contraseñas NO COINCIDEN!!!!!!!");
				JOptionPane.showMessageDialog(formContrasenia, "Las Contrasenias ingresadas no coinciden o no ingreso su contrasenia actual", "Configuraci\u00F3n cuenta", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(accion.compareTo("cancelar")==0)
		{
			formContrasenia.setVisible(false);
		}
		if(accion.compareTo("login")==0)
		{
			formContrasenia.getJPasswordField1().grabFocus();
			formContrasenia.getJPasswordField1().selectAll();
		}
		if(accion.compareTo("contrasenia1")==0)
		{
			formContrasenia.getJPasswordField2().grabFocus();
			formContrasenia.getJPasswordField2().selectAll();
		}
		if(accion.compareTo("contrasenia2")==0)
		{
			if(coincidenContrasenias())
			{
				this.login = formContrasenia.getJtxtLogin().getText();
				password = formContrasenia.getJPasswordField1().getText();
				confirmado = true;
				formContrasenia.setVisible(false);
			}
		}
	}
	
	public boolean coincidenContrasenias()
	{
		boolean coinciden = false;	
		if(formContrasenia.getJPasswordField1().getText().isEmpty())
		{
			return false;
		}
		if(formContrasenia.getJPasswordField1().getText().compareTo(formContrasenia.getJPasswordField2().getText())==0)
		{
			coinciden = true;
		}
		else
		{
			FormUtilities.showMessage("Error en las Contrasenias", "La confirmacion de la contrasenia no coincide", formContrasenia);
			formContrasenia.getJPasswordField1().grabFocus();
			formContrasenia.getJPasswordField1().selectAll();
		}
		return coinciden;
	}
	
}
