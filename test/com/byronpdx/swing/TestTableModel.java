package com.byronpdx.swing;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.joda.time.DateMidnight;

public class TestTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -807256944481978823L;
	private Person[] people = {
			new Person("Byron", new DateMidnight(1949, 8, 27), 62),
			new Person("Helen", new DateMidnight(1946, 5, 16), 65),
			new Person("Nettle", new DateMidnight(1977, 7, 11), 33) };
	private final JTable table;

	public TestTableModel(JTable table) {
		this.table = table;
		table.setModel(this);
		// setup columns
		TableColumnModel colModel = table.getColumnModel();
		TableColumn col = colModel.getColumn(1);
		col.setCellRenderer(new CalendarCellRenderer("MM/dd/yyyy"));
		CalendarCellEditor editor = new CalendarCellEditor();
		col.setCellEditor(editor);
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return people.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Person person = people[row];
		switch (col) {
		case 0:
			return person.getName();
		case 1:
			return person.getDob();
		case 2:
			return Integer.toString(person.getAge());
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Person person = people[rowIndex];
		switch (columnIndex) {
		case 0:
			person.setName((String) aValue);
			break;
		case 1:
			System.out.println("Setting " + aValue);
			person.setDob((DateMidnight) aValue);
			break;
		case 2:
			person.setAge(Integer.parseInt((String) aValue));
			break;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

}
