package org.quarkbit.controlpalabravida.utils.componentes;

import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;



public class JLabelTableCellRenderer extends JLabel implements
		TableCellRenderer, Serializable {

	protected static Border noFocusBorder;
	private Color unselectedForeground;
	private Color unselectedBackground;
	private JTableHorario m_tTopParent;

	public JLabelTableCellRenderer() {
		super();
		noFocusBorder = new EmptyBorder(1, 2, 1, 2);
		setOpaque(true);
		setBorder(noFocusBorder);
	}

	public void setForeground(Color c) {
		super.setForeground(c);
		unselectedForeground = c;
	}

	public void setBackground(Color c) {
		super.setBackground(c);
		unselectedBackground = c;
	}

	public void updateUI() {
		super.updateUI();
		setForeground(null);
		setBackground(null);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			super
					.setForeground((unselectedForeground != null) ? unselectedForeground
							: table.getForeground());

			if ((m_tTopParent != null) && (value != null)) {
				HashMap map = m_tTopParent.getDataAwareColorMap();
				Color c = (Color) map.get(value);
				if (c != null) {
					super.setBackground(c);
				} else {
					super
							.setBackground((unselectedBackground != null) ? unselectedBackground
									: table.getBackground());
				}
			} else {
				super
						.setBackground((unselectedBackground != null) ? unselectedBackground
								: table.getBackground());
			}
		}

		setFont(table.getFont());

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			if (table.isCellEditable(row, column)) {
				super.setForeground(UIManager
						.getColor("Table.focusCellForeground"));
				super.setBackground(UIManager
						.getColor("Table.focusCellBackground"));
			}
		} else {
			setBorder(noFocusBorder);
		}

		setValue(value);

		return this;
	}

	protected void setValue(Object value) {
		setText((value == null) ? "" : value.toString());
	}

	public static class UIResource extends JLabelTableCellRenderer implements
			javax.swing.plaf.UIResource {
	}

	public void addNotify() {
		super.addNotify();
		if (m_tTopParent == null) {
			Component c = this.getParent();
			while ((c != null) && !(c instanceof JTable)) {
				c = c.getParent();
			}
			m_tTopParent = (JTableHorario) c;
		}
	}
}
