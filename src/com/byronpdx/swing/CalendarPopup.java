package com.byronpdx.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.joda.time.LocalDate;

public class CalendarPopup extends JDialog {

	// serialVersionUID
	private static final long serialVersionUID = -7008841514983228970L;
	private final JPanel contentPanel = new JPanel();
	private final CalendarPanel calendarPanel = new CalendarPanel();
	private LocalDate date;
	protected boolean valid;
	private JButton okButton;

	/**
	 * Create the dialog.
	 */
	public CalendarPopup() {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Select Date");
		setBounds(100, 100, 288, 236);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(calendarPanel);
		calendarPanel.addPropertyChangeListener("date",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						okButton.doClick();
					}
				});
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
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

	public void setDate(LocalDate date2) {
		this.date = date2;
		calendarPanel.setDate(date2);
	}

	public LocalDate getDate() {
		return calendarPanel.getDate();
	}

	public boolean isDateValid() {
		return valid;
	}
}
