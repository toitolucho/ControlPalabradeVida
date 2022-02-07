package org.quarkbit.controlpalabravida.formularios;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FFechasRango extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private List listaFiltro;
	private JLabel lblFiltro;
	private JDateChooser dateFechaInicio;
	private JDateChooser dateFechaFin;
	private JComboBox cBoxFiltro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FFechasRango dialog = new FFechasRango(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FFechasRango(JFrame owner) {
		super(owner, true);
		setTitle("Seleccion de Fechas");
		setBounds(100, 100, 487, 183);
		getContentPane().setLayout(new BorderLayout());
		FlowLayout fl_contentPanel = new FlowLayout();
		fl_contentPanel.setAlignment(FlowLayout.LEFT);
		contentPanel.setLayout(fl_contentPanel);
		contentPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Seleccione el periodo de tiempo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblDel = new JLabel("Del :");
			lblDel.setPreferredSize(new Dimension(100, 16));
			contentPanel.add(lblDel);
		}
		{
			dateFechaInicio = new JDateChooser();
			dateFechaInicio.setPreferredSize(new Dimension(130, 22));
			dateFechaInicio.setDateFormatString("dd-MM-yyyy");
			contentPanel.add(dateFechaInicio);
		}
		{
			JLabel lblAl = new JLabel("Al :");
			contentPanel.add(lblAl);
		}
		{
			dateFechaFin = new JDateChooser();
			dateFechaFin.setPreferredSize(new Dimension(130, 22));
			dateFechaFin.setDateFormatString("dd-MM-yyyy");
			contentPanel.add(dateFechaFin);
		}
		{
			lblFiltro = new JLabel("Filtro :");
			lblFiltro.setPreferredSize(new Dimension(100, 16));
			contentPanel.add(lblFiltro);
		}
		{
			cBoxFiltro = new JComboBox();
			cBoxFiltro.setPreferredSize(new Dimension(280, 25));
			contentPanel.add(cBoxFiltro);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(dateFechaInicio.getDate() == null ||
								dateFechaFin.getDate() == null)
						{
							JOptionPane.showMessageDialog(getOwner(), "No ha seleccionado una fecha", "Seleccion de fechas", JOptionPane.ERROR_MESSAGE);
							return;
						}
						FFechasRango.this.setVisible(false);
						
					}
				});
				okButton.setActionCommand("aceptar");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						FFechasRango.this.setVisible(false);
					}
				});
				cancelButton.setActionCommand("cancelar");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void visualizarFiltro(boolean visualizar)
	{
		lblFiltro.setVisible(visualizar);
		cBoxFiltro.setVisible(visualizar);		
	}
	
	public void cargarFiltro()
	{
		if(listaFiltro != null)
		{
			for (Object elemento : listaFiltro) {
				cBoxFiltro.addItem(elemento);
			}
		}
	}

	public List getListaFiltro() {
		return listaFiltro;
	}

	public void setListaFiltro(List listaFiltro) {
		this.listaFiltro = listaFiltro;
	}

	public JLabel getLblFiltro() {
		return lblFiltro;
	}

	public JDateChooser getDateFechaInicio() {
		return dateFechaInicio;
	}

	public JDateChooser getDateFechaFin() {
		return dateFechaFin;
	}

	public JComboBox getcBoxFiltro() {
		return cBoxFiltro;
	}

	
	
}
