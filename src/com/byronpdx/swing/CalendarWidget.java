/**
 * 
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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

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

		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				System.out.println(textField.getText() + "-" + e.getKeyChar()
						+ " " + e.getKeyCode());
				if (e.getKeyCode() == 112) {
					popupCalendar();
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
				if (checkDate() && date != null) {
					textField.setText(date.toString(formatters.get(formatters
							.size() - 1)));
				}
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
		this.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				textField.requestFocusInWindow();
				textField.selectAll();
				System.out.println("Calendar widget focus gained");
			}
		});
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent arg0) {
				System.out.println("widget shown");
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				System.out.println("widget resized");
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				System.out.println("widget moved");
				textField.requestFocusInWindow();
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				System.out.println("widget hidden");
			}
		});

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
			System.out.println("popuyp" + date);
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
		System.out.println("checkdate:" + txt);
		DateMidnight dt = null;
		if (txt.isEmpty() && date != null) {
			firePropertyChange("date", date, date = null);
			return true;
		}
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
		checkDate();
		return date;
	}

	public void setDate(DateMidnight date) {
		this.date = date;
		if (date != null) {
			textField
					.setText(date.toString(formatters.get(formatters.size() - 1)));
		} else {
			textField.setText("");
		}
	}

	public void selectAll() {
		textField.selectAll();
		textField.grabFocus();
	}

}
