package org.quarkbit.controlpalabravida.formularios;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.quarkbit.controlpalabravida.controller.CFPersonasBuscador;
import java.awt.Font;

public class FPersonasBuscador extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tablePersonas;
	private JTextField txtTextoBusqueda;
	private JButton btnBuscar;
	private JButton okButton;
	private JButton cancelButton;
	public CFPersonasBuscador control;
	private JPanel pnlsuperior;
	public JLabel lblMensaje;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FPersonasBuscador dialog = new FPersonasBuscador(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FPersonasBuscador(JFrame owner) {
		super(owner, true);
		setTitle("Buscador de Personas");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		control = new CFPersonasBuscador(this);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlOpcionesBusqueda = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnlOpcionesBusqueda.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			contentPanel.add(pnlOpcionesBusqueda, BorderLayout.NORTH);
			{
				JLabel lblNewLabel = new JLabel("Texto Busqueda");
				pnlOpcionesBusqueda.add(lblNewLabel);
			}
			{
				txtTextoBusqueda = new JTextField();
				txtTextoBusqueda.setActionCommand("Buscar");
				pnlOpcionesBusqueda.add(txtTextoBusqueda);
				txtTextoBusqueda.setColumns(20);
			}
			{
				btnBuscar = new JButton("Buscar");
				btnBuscar.setMnemonic('B');
				pnlOpcionesBusqueda.add(btnBuscar);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tablePersonas = new JTable();
				scrollPane.setViewportView(tablePersonas);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			pnlsuperior = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnlsuperior.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			getContentPane().add(pnlsuperior, BorderLayout.NORTH);
			{
				lblMensaje = new JLabel("Seleccione a la persona");
				lblMensaje.setOpaque(true);
				lblMensaje.setFont(new Font("Tahoma", Font.BOLD, 13));
				pnlsuperior.add(lblMensaje);
			}
		}
		control.onFormShown();
	}

	public JTable getTablePersonas() {
		return tablePersonas;
	}

	public JTextField getTxtTextoBusqueda() {
		return txtTextoBusqueda;
	}

	

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}
	
	

}
