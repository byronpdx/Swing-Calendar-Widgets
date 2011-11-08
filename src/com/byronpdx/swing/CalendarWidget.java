/**
 * 
 */
package com.byronpdx.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Calendar widget Configured so key F1 brings up a popup for entering the date.
 * 
 * @author byron
 * 
 */
public class CalendarWidget extends JTextField {
	private static final long serialVersionUID = -3552440588908953823L;
	private JTextField textField;
	private DateMidnight date;
	// formatters
	private ArrayList<DateTimeFormatter> formatters = new ArrayList<DateTimeFormatter>();
	private String[] fmtStrings;

	public CalendarWidget() {
		setMinimumSize(new Dimension(135, 25));
		if (fmtStrings == null) {
			fmtStrings = new String[] { "MM/dd", "MM/dd/yy", "MM/dd/yyyy" };
		}
		setupFormatters(fmtStrings);
		setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
		textField = this;
		// textField = new JTextField();
		textField.setMinimumSize(new Dimension(100, 19));
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setAlignmentY(Component.TOP_ALIGNMENT);
		textField.setAlignmentX(Component.LEFT_ALIGNMENT);
		// add(textField);
		textField.setColumns(10);

		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				System.out.println(textField.getText() + "-" + e.getKeyChar()
						+ " " + e.getKeyCode());
				if (e.getKeyCode() == 112) {
					CalendarPopup popup = new CalendarPopup();
					popup.setDate(date);
					popup.setVisible(true);
					if (popup.isDateValid()) {
						date = popup.getDate();
						setDate(date);
					}
				}
				if (checkDate()) {
					textField.setBackground(Color.WHITE);
				} else {
					textField.setBackground(Color.YELLOW);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("keyCode=" + e.getKeyCode());
				super.keyTyped(e);
			}
		});

		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("Focus gained");
				super.focusGained(e);
				textField.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (checkDate()) {
					textField.setText(date.toString(formatters.get(formatters
							.size() - 1)));
				}
			}
		});
		this.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textField.grabFocus();
				textField.selectAll();
				System.out.println("Calendar widget focus gained");
			}
		});

		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("actionPerformed" + e);
			}
		});

	}

	/**
	 * Creates a CalendarWidget with the format strings specified.
	 * 
	 * @param fmtStrings
	 */
	public CalendarWidget(String[] fmtStrings) {
		this();
		this.fmtStrings = fmtStrings;
		setupFormatters(fmtStrings);
	}

	/**
	 * Setup the formatters. The format strings should be assigned from the
	 * shortest format to the longest so that if it matches the short form the
	 * parsing can be stopped.
	 * 
	 * @param fmtStrings
	 */
	private void setupFormatters(String[] fmtStrings) {
		DateMidnight dt = new DateMidnight();
		formatters.clear();
		for (String fmt : fmtStrings) {
			DateTimeFormatter dtf = DateTimeFormat.forPattern(fmt)
					.withDefaultYear(dt.getYear());
			formatters.add(dtf);
		}
	}

	private boolean checkDate() {
		String txt = textField.getText();
		System.out.println("checkdate:" + txt);
		DateMidnight dt = null;
		for (DateTimeFormatter dtf : formatters) {
			try {
				DateMidnight dto = date;
				dt = dtf.parseDateTime(txt).toDateMidnight();
				date = dt;
				firePropertyChange("date", dto, dt);
				return true;
			} catch (IllegalArgumentException e) {
				// ignore
			}
		}
		return false;
	}

	/**
	 * Get the date
	 * 
	 * @return the date
	 */
	public DateMidnight getDate() {
		System.out.println("get date" + date);
		return date;
	}

	public void setDate(DateMidnight date) {
		this.date = date;
		textField.setText(date.toString(formatters.get(formatters.size() - 1)));
	}

}
