package com.byronpdx.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.joda.time.LocalDate;

public class TestTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -807256944481978823L;
	private Person[] people = {
			new Person("Byron", new LocalDate(1949, 8, 27), 62),
			new Person("Helen", new LocalDate(1946, 5, 16), 65),
			new Person("Nettle", new LocalDate(1977, 7, 11), 33),
			new Person("Bruce", new LocalDate(1952, 3, 23), 59) };
	private TableRowSorter<TableModel> sorter;

	public TestTableModel(final JTable table) {
		table.setModel(this);
		table.setSurrendersFocusOnKeystroke(true);
		// setup columns
		TableColumnModel colModel = table.getColumnModel();
		colModel.getColumn(0).setHeaderValue("Name");
		TableColumn col = colModel.getColumn(1);
		col.setHeaderValue("DOB");
		col.setCellRenderer(new CalendarCellRenderer("MM/dd/yyyy"));
		CalendarCellEditor editor = new CalendarCellEditor();
		col.setCellEditor(editor);
		editor.addDateListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (table.getEditingRow() < 0)
					return;
				table.getModel().setValueAt(evt.getNewValue(),
						sorter.convertRowIndexToModel(table.getEditingRow()),
						table.getEditingColumn());
			}

		});
		colModel.getColumn(2).setHeaderValue("Age");
		sorter = new TableRowSorter<TableModel>(this);
		table.setRowSorter(sorter);
		sorter.setSortsOnUpdates(false);
		sorter.setComparator(1, new Comparator<LocalDate>() {

			@Override
			public int compare(LocalDate o1, LocalDate o2) {
				if (o1 == null)
					return -1;
				if (o2 == null)
					return 1;
				return o2.compareTo(o1);
			}
		});
		sorter.toggleSortOrder(1);
	}

	public Person[] getPeople() {
		return people;
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
			person.setDob((LocalDate) aValue);
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
