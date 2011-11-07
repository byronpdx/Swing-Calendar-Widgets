package com.byronpdx.swing;

import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CalendarCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 3085894133452186022L;
	private DateTimeFormatter formatter;
	private String format;

	/**
	 * A cell render for DateMidnight that uses the default format of
	 * MM/dd/yyyy.
	 */
	public CalendarCellRenderer() {
		this("MM/dd/yyyy");
	}

	public CalendarCellRenderer(String format) {
		setFormat(format);
	}

	@Override
	protected void setValue(Object value) {
		System.out.println("setValue " + value);
		setText((value == null) ? "" : formatter.print((DateMidnight) value));
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
		formatter = DateTimeFormat.forPattern(format);
	}

}
