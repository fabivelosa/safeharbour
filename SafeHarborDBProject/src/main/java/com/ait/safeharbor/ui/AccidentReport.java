package com.ait.safeharbor.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ait.safeharbor.data.OperationData;

public class AccidentReport extends JFrame implements ActionListener {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// Create some Buttons
	private JButton createReportButton = new JButton("         View Report          ");
	private JButton closeSearchButton = new JButton("        Close        ");

	JRadioButton r1 = new JRadioButton("Average AMOUNT by County             ");
	JRadioButton r2 = new JRadioButton("Sum AMOUNT by City/County WITH ROLLUP");
	JRadioButton r3 = new JRadioButton("AVERAGE AGE by Gender                ");
	JPanel x = new JPanel();
	JTable jt = null;
	JScrollPane jsp = null;
	

	// Manage database operation
	private OperationData optData;

	public AccidentReport(String title) {
		super(title);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		x.setAlignmentX(Component.CENTER_ALIGNMENT);
		x.setPreferredSize(new Dimension(300, 400));
		x.setMaximumSize(new Dimension(400, 500)); // set max = pref
		x.setBorder(BorderFactory.createTitledBorder(" Reports "));
		// radio buttons
		r1.setBounds(75, 50, 100, 30);
		r2.setBounds(75, 100, 100, 30);
		r3.setBounds(75, 100, 100, 30);
		ButtonGroup bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		bg.add(r3);

		// Add the GUI components
		getContentPane().add(x);
		x.add(r1);
		x.add(r2);
		x.add(r3);
		x.add(createReportButton);

		x.setSize(300, 400);
		x.setVisible(true);

		// Add some listeners to monitor for actions (i.e. button presses)
		createReportButton.addActionListener(this);
		closeSearchButton.addActionListener(this);

		// Set the Window Size
		setSize(500, 500);
		setLocation(500, 200);
		setResizable(true);
		setVisible(true);

	}

	// Method main that call the main GUI
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		optData = new OperationData();
	
		 String[] column = new String[3];
		int userChoice = 0;

		if (target.equals(createReportButton)) {
			
			if (r1.isSelected()) {
				userChoice = 1;
				  column[0]="COUNTY";column[1]="AVG AMOUNT";column[2]= " ";
			} else if (r2.isSelected()) {
				userChoice = 2;
				  column[0]="COUNTY";column[1]="CITY";column[2]= "TOTAL AMOUNT";
			} else if (r3.isSelected()) {
				userChoice = 3;
				 column[0]="AVG AGE";column[1]="GENDER";column[2]= " ";
			}

			List<String[]> list = optData.createReport(userChoice);
			String[][] data = new String[list.size()][3];
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < 3; j++) {
					String[] a = list.get(i);
					data[i][j] = a[j];
				}
			}

			jt = new JTable(data, column);
			jsp = new JScrollPane(jt);
			jsp.setPreferredSize(new Dimension(300, 100));
			x.add(jsp);
			x.revalidate();
			x.repaint();
		} else if (target.equals(closeSearchButton)) {
			setVisible(false);
		}
	}
}
