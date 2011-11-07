package com.byronpdx.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.joda.time.DateMidnight;

public class CalendarPopup extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final CalendarPanel calendarPanel = new CalendarPanel();
	private DateMidnight date;
	protected boolean valid;

	/**
	 * Create the dialog.
	 */
	public CalendarPopup() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Select Date");
		setBounds(100, 100, 284, 209);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(calendarPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						valid = true;
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						valid = false;
						setVisible(false);
						dispose();
					}
				});
			}
		}
		doLayout();
		valid = false;
	}

	public void setDate(DateMidnight date) {
		this.date = date;
		calendarPanel.setDate(date);
	}

	public DateMidnight getDate() {
		return calendarPanel.getDate();
	}

	public boolean isDateValid() {
		return valid;
	}
}
