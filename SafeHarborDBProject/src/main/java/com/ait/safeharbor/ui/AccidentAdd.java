package com.ait.safeharbor.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ait.safeharbor.data.OperationData;
import com.ait.safeharbor.dto.MessageBean;

public class AccidentAdd extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Create some Buttons
	private JButton addNewRecordButton = new JButton("       Save       ");
	private JButton closeRecordButton = new JButton("       Close       ");

	// Create some Text Fields
	private JTextField locationText = new JTextField(20);
	private JTextField dateText = new JTextField(20);
	private JTextField timeText = new JTextField(20);

	// Create some Labels
	private JLabel locationL = new JLabel("Accident Location: ");
	private JLabel accidentDateL = new JLabel("Accident Date    : ");
	private JLabel accidentTimeL = new JLabel("Accident Time    : ");

	// Manage database operation
	private OperationData optData;

	// Contructor that creates the UI components
	public AccidentAdd(String str) {
		super(str);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel x = new JPanel();
		x.setAlignmentX(Component.CENTER_ALIGNMENT);
		x.setPreferredSize(new Dimension(300, 400));
		x.setMaximumSize(new Dimension(400, 500)); // set max = pref
		x.setBorder(BorderFactory.createTitledBorder(" Add a new Accident "));

		optData = new OperationData();

		Object[] location = optData.loadLocation().toArray();

		final JComboBox<Object> cb = new JComboBox<Object>(location);

		// Add the GUI components
		getContentPane().add(x);
		x.add(locationL);
		x.add(cb);
		x.add(locationText);
		locationText.setVisible(false);
		x.add(accidentDateL);
		x.add(dateText);
		x.add(accidentTimeL);
		x.add(timeText);
		x.add(addNewRecordButton);
		x.add(closeRecordButton);

		// Add some listeners to monitor for actions (i.e. button presses)
		addNewRecordButton.addActionListener(this);
		closeRecordButton.addActionListener(this);

		// Set the Window Size
		setSize(500, 500);
		setLocation(500, 200);
		setResizable(false);
		setVisible(true);
		cb.setBounds(50, 50, 120, 20);

		locationText.setText((String) cb.getSelectedItem());
		dateText.setText("DD/MM/YYYY");
		timeText.setText("HH:MM:SS");

	}

	// Method main that call the main GUI
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		optData = new OperationData();
		MessageBean message = null;
		// When the addNewRecordButton button is clicked construct the SQL command
		if (target.equals(addNewRecordButton)) {
			message = optData.addRecord(locationText.getText(), dateText.getText(), timeText.getText());
			JOptionPane.showMessageDialog(null, message.getMessage());
		} else if (target.equals(closeRecordButton)) {
			setVisible(false);
		}
	}

}