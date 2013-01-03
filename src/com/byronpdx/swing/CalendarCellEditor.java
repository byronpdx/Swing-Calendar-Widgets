/*
 * Copyright (c) 2009-2013 TriMet
 *  
 * Last modified on Jan 2, 2013 by palmerb
 */
package com.byronpdx.swing;

import java.awt.Component;
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
		TableCellEditor {

	private static final long serialVersionUID = -4011447494671904444L;
	private CalendarWidget widget;

	/**
	 * 
	 */
	public CalendarCellEditor() {
		widget = new CalendarWidget();
		widget.getTextField().requestFocusInWindow();
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		widget.selectAll();
		return true;
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		System.out.println("getCellEditorValue " + widget.getDate());
		return widget.getDate();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
		DateMidnight date = (DateMidnight) value;
		widget.setDate(date);
		widget.requestFocusInWindow();
		return widget;
	}
	
	/**
	 * Adds the date listener.
	 * 
	 * @param dateListener
	 *            the date listener
	 */
	public void addDateListener(PropertyChangeListener dateListener) {
		widget.addPropertyChangeListener("date", dateListener);
	}

	/**
	 * Removes the date listener.
	 * 
	 * @param dateListener
	 *            the date listener
	 */
	public void removeDateListener(PropertyChangeListener dateListener) {
		widget.removePropertyChangeListener(dateListener);
	}
	
	
	
	
}
