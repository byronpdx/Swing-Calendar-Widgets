/**
 * 
 */
package com.byronpdx.swing;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.joda.time.DateMidnight;

/**
 * @author byron
 * 
 */
public class CalendarCellEditor extends AbstractCellEditor implements
		TableCellEditor, PropertyChangeListener {

	private static final long serialVersionUID = -4011447494671904444L;
	private CalendarWidget widget;
	private DateMidnight date;

	public CalendarCellEditor() {
		widget = new CalendarWidget();
		widget.addPropertyChangeListener("date", this);
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		widget.grabFocus();
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		System.out.println("getCellEditorValue " + date);
		return date;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
		date = (DateMidnight) value;
		widget.setDate(date);
		return widget;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		date = widget.getDate();
		System.out.println("Date set " + date);
	}

}
