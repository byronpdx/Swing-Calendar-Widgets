package com.byronpdx.swing;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class CalendarTestFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7841305252981488958L;
	private JPanel contentPane;
	private JTextField txtDate;
	private CalendarWidget calendarWidget;
	private CalendarPanel calendarDiag;
	private JTextField txtCalefld;
	private JScrollPane scrollPane;
	private JTable table;
	private final JButton btnPrint = new JButton("Print");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarTestFrame frame = new CalendarTestFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CalendarTestFrame() {
		setTitle("Test Calendar Widget");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 447);
		contentPane = new JPanel();
		contentPane
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 43, 0, 157, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblDate = new JLabel("Date");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 0;
		contentPane.add(lblDate, gbc_lblDate);

		calendarWidget = new CalendarWidget();
		calendarWidget.setPreferredSize(new Dimension(140, 24));
		GridBagConstraints gbc_calendarWidget = new GridBagConstraints();
		gbc_calendarWidget.insets = new Insets(0, 0, 5, 0);
		gbc_calendarWidget.anchor = GridBagConstraints.NORTHWEST;
		gbc_calendarWidget.gridx = 1;
		gbc_calendarWidget.gridy = 0;
		contentPane.add(calendarWidget, gbc_calendarWidget);
		calendarWidget.addPropertyChangeListener("date",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						System.out.println("Date set");
						txtDate.setText(calendarWidget.getDate() == null ? "null"
								: calendarWidget.getDate().toString());
						calendarDiag.setDate(calendarWidget.getDate());
					}
				});

		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(calendarWidget.getDate());
				txtDate.setText(calendarWidget.getDate() == null ? "Null"
						: calendarWidget.getDate().toString(
							"MM/dd/yyyy"));
			}
		});
		GridBagConstraints gbc_btnShow = new GridBagConstraints();
		gbc_btnShow.insets = new Insets(0, 0, 5, 5);
		gbc_btnShow.gridx = 0;
		gbc_btnShow.gridy = 1;
		contentPane.add(btnShow, gbc_btnShow);

		txtDate = new JTextField();
		GridBagConstraints gbc_txtDate = new GridBagConstraints();
		gbc_txtDate.insets = new Insets(0, 0, 5, 0);
		gbc_txtDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDate.gridx = 1;
		gbc_txtDate.gridy = 1;
		contentPane.add(txtDate, gbc_txtDate);
		txtDate.setColumns(10);

		calendarDiag = new CalendarPanel();
		calendarDiag.addPropertyChangeListener("date",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						txtCalefld.setText(calendarDiag.getDate().toString());
					}
				});
		GridBagConstraints gbc_calendarDiag = new GridBagConstraints();
		gbc_calendarDiag.anchor = GridBagConstraints.WEST;
		gbc_calendarDiag.insets = new Insets(0, 0, 5, 0);
		gbc_calendarDiag.fill = GridBagConstraints.VERTICAL;
		gbc_calendarDiag.gridx = 1;
		gbc_calendarDiag.gridy = 2;
		contentPane.add(calendarDiag, gbc_calendarDiag);

		txtCalefld = new JTextField();
		GridBagConstraints gbc_txtCalefld = new GridBagConstraints();
		gbc_txtCalefld.insets = new Insets(0, 0, 5, 0);
		gbc_txtCalefld.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCalefld.gridx = 1;
		gbc_txtCalefld.gridy = 3;
		contentPane.add(txtCalefld, gbc_txtCalefld);
		txtCalefld.setColumns(10);
		{
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Person[] people = ((TestTableModel)table.getModel()).getPeople();
					for(Person p:people) {
						System.out.println(p);
					}
				}
			});
			contentPane.add(btnPrint, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
		}

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 4;
		contentPane.add(scrollPane, gbc_scrollPane);
		table = new JTable();
		table.setRowHeight(18);
		new TestTableModel(table);
		scrollPane.setViewportView(table);
	}

}
