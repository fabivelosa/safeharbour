package com.ait.safeharbor.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ait.safeharbor.data.OperationData;
import com.ait.safeharbor.dto.MessageBean;

class MainUI extends JFrame implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6370195080256632864L;
	
	// Create some Buttons
	private JButton addNewRecordButton = new JButton("          Add Accident           ");
	private JButton exportButton =       new JButton("         Accident Export         ");
	private JButton updateRecordButton = new JButton("         Accident Search         ");
	private JButton reportButton = 		 new JButton("               Reports               ");
	private JButton closeApp =           new JButton(" Close Application ");

	// Manage database operation
	private OperationData optData;

	//Contructor that creates the UI components 
	public MainUI(String str) {
		super(str);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// Panel to group the buttons
		JPanel a = new JPanel();
		a.setAlignmentX(Component.CENTER_ALIGNMENT);
		a.setPreferredSize(new Dimension(300, 300));
		a.setMaximumSize(new Dimension(300, 300)); // set max = pref
		a.setBorder(BorderFactory.createTitledBorder("Operations"));

		// Add the GUI components
		getContentPane().add(a);
		a.add(addNewRecordButton);
		a.add(updateRecordButton);
		a.add(exportButton);
		a.add(reportButton);
		getContentPane().add(closeApp);
		
		//reportButton.setBounds(130,100,200, 40);

		// Add some listeners to monitor for actions (i.e. button presses)
		exportButton.addActionListener(this);
		addNewRecordButton.addActionListener(this);
		updateRecordButton.addActionListener(this);
		reportButton.addActionListener(this);
		closeApp.addActionListener(this);

		// Set the Window Size
		setSize(500, 500);
		setLocation(500, 200);
		setResizable(false);
		setVisible(true);

	}

	// When an action is performed (i.e user clicks a button) - get the Event and do
	// the operation
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		optData = new OperationData();
		MessageBean message = null;

		if (target.equals(exportButton)) {
			message = optData.exportRecords();
			JOptionPane.showMessageDialog(null,message.getMessage());
		} else if (target.equals(addNewRecordButton)) {
			new AccidentAdd(" Add Accident ");
		} else if (target.equals(updateRecordButton)) {
			new AccidentSearch(" Search Accident ");
		} else if (target.equals(reportButton)) {
			new AccidentReport(" Accident Reports ");
		} else if (target.equals(closeApp)) {
			System.exit(0);
		}
		
	}
	//Method main that call the main GUI
	public static void main(String args[]) {
		// Set the window title
		new MainUI(" Safe Harbor - Accident Report ");
	}

}
