package com.byronpdx.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.joda.time.DateMidnight;

public class CalendarTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 2112882394942755548L;

	public enum DAYOFWEEK {
		Sunday("Su"), Monday("M"), Tuesday("Tu"), Wednesday("W"), Thursday("Th"), Friday(
				"F"), Saturday("Sa");

		protected final String abbr;

		private DAYOFWEEK(String abbr) {
			this.abbr = abbr;
		}

		public String getAbbr() {
			return abbr;
		}
	}

	private int year;
	private int month;
	private DateMidnight date;
	private DateMidnight[][] days = new DateMidnight[6][7];
	private int rows = 6;
	private final JTable table;

	public CalendarTableModel(JTable table) {
		this.table = table;
		setDate(new DateMidnight());
		table.setModel(this);
		setupColumns();
	}

	private void setupColumns() {
		TableColumnModel tcmodel = table.getColumnModel();
		for(int col=0; col<7; col++) {
			TableColumn colmn = tcmodel.getColumn(col);
			colmn.setCellRenderer(new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 7183054785504010326L;

				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					// TODO Auto-generated method stub
					Component cell = super.getTableCellRendererComponent(table,
							value, isSelected, hasFocus, row, column);
					cell.setFont(new Font("Serif", Font.BOLD, 11));
					((JLabel) cell).setHorizontalAlignment(JLabel.CENTER);
					if (days[row][column].getMonthOfYear() == month) {
						cell.setForeground(Color.BLACK);
					} else {
						cell.setForeground(Color.GRAY);
					}
					if (getDateAt(row, column).equals(date)) {
						cell.setBackground(Color.lightGray);
						System.out.println("blue " + row + "," + column);
					} else {
						cell.setBackground(table.getBackground());
					}
					return cell;
				}
				
			});
		}
	}

	@Override
	public String getColumnName(int column) {
		DAYOFWEEK wkd = DAYOFWEEK.values()[column];
		return wkd.getAbbr();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return rows;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return Integer.toString(days[arg0][arg1].getDayOfMonth());
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public DateMidnight getDate() {
		return date;
	}

	public void setDate(DateMidnight date) {
		this.date = date;
		year = date.getYear();
		month = date.getMonthOfYear();
		setupCalendar();
	}

	private void setupCalendar() {
		DateMidnight dt = date.withDayOfMonth(1);
		dt = dt.minusDays(dt.getDayOfWeek());
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				days[row][col] = dt;
				dt = dt.plusDays(1);
			}
			if (dt.getMonthOfYear() > month) {
				rows = row + 1;
				break;
			}
		}
		fireTableDataChanged();
	}

	public DateMidnight getDateAt(int row, int col) {
		return days[row][col];
	}

}
