/*
 * Copyright (c) 2009-2013 TriMet
 *  
 * Last modified on Feb 12, 2013 by palmerb
 */
package com.byronpdx.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Calendar widget Configured so key F1 brings up a popup for entering the date.
 * 
 * @author byron
 * 
 */
public class CalendarWidget extends JPanel {
	private static final long serialVersionUID = -3552440588908953823L;
	private JTextField textField;
	private DateMidnight date;
	// formatters
	private ArrayList<DateTimeFormatter> formatters = new ArrayList<DateTimeFormatter>();
	private String[] fmtStrings;

	public CalendarWidget() {
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setPreferredSize(new Dimension(140, 26));
		setMinimumSize(new Dimension(125, 24));
		if (fmtStrings == null) {
			fmtStrings = new String[] { "MM/dd", "MM/dd/yy", "MM/dd/yyyy" };
		}
		setupFormatters(fmtStrings);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 114, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 22, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		// textField = this;
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(85, 22));
		textField.setMinimumSize(new Dimension(85, 15));
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setAlignmentY(Component.TOP_ALIGNMENT);
		textField.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 0, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		this.add(textField, gbc_textField);
		// add(textField);
		textField.setColumns(10);

		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				makeChange();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				makeChange();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				makeChange();
			}
			
		});

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				popupCalendar();
			}
		});
		button.setMinimumSize(new Dimension(12, 12));
		button.setPreferredSize(new Dimension(18, 18));
		button.setIconTextGap(0);
		button.setMaximumSize(new Dimension(24, 24));
		button.setMargin(new Insets(1, 0, 1, 0));
		button.setIcon(new ImageIcon(CalendarWidget.class
				.getResource("/com/byronpdx/swing/calendar_icon.png")));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.gridx = 1;
		gbc_button.gridy = 0;
		add(button, gbc_button);
		this.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				textField.requestFocusInWindow();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
			}
			
		});
	}

	/**
	 * Make change tests to see if anything has changed and updates the date.
	 */
	private void makeChange() {
		checkDate();
	}
	
	private void popupCalendar() {
		CalendarPopup popup = new CalendarPopup();
		popup.setDate(date);
		popup.setLocation(this.getLocationOnScreen());
		popup.setVisible(true);
		if (popup.isDateValid()) {
			DateMidnight dt = this.date;
			DateMidnight date = popup.getDate();
			setDate(date);
			firePropertyChange("date", dt, date);
			System.out.println("popup" + date);
		}
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
		DateMidnight dt = null;
		if (txt.isEmpty() && date != null) {
			firePropertyChange("date", date, date = null);
			System.out.println("checkDate-Date set to null");
			return true;
		}
		for (DateTimeFormatter dtf : formatters) {
			try {
				DateMidnight dto = date;
				dt = dtf.parseDateTime(txt).toDateMidnight();
				date = dt;
				firePropertyChange("date", dto, dt);
				System.out.println("checkDate-Date set to "+dt);
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
		checkDate();
		return date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date
	 *            the new date
	 */
	public void setDate(DateMidnight date) {
		this.date = date;
		if (date != null) {
			textField
					.setText(date.toString(formatters.get(formatters.size() - 1)));
		} else {
			textField.setText("");
		}
		textField.grabFocus();
		textField.setFocusable(true);
		textField.grabFocus();
	}
	
	public void selectAll() {
		System.out.println("SelectAll");
		textField.selectAll();
		System.out.println("selectAll "+textField.hasFocus());
	}

	public JTextField getTextField() {
		return textField;
	}
	
}
