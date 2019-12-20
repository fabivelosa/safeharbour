package com.ait.safeharbor.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ait.safeharbor.data.OperationData;
import com.ait.safeharbor.dto.AccidentBean;
import com.ait.safeharbor.dto.AccidentVehicleBean;
import com.ait.safeharbor.dto.MessageBean;

public class AccidentSearch extends JFrame implements ActionListener {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3824237972975871905L;

	// Create some Buttons
	private JButton searchRecordButton = new JButton(
			"                         Search Accident                         ");
	private JButton saveRecordButton = new JButton("          Save          ");
	private JButton updateRecordButton = new JButton("         Update         ");
	private JButton deleteRecordButton = new JButton("         Delete         ");
	private JButton deleDetailRecordButton = new JButton("         Delete         ");
	private JButton closeSearchButton = new JButton("        Close        ");

	// Create some Text Fields
	private JTextField recordIdT = new JTextField(10);
	private JTextField locationT = new JTextField(20);
	private JTextField accidentDateT = new JTextField(20);

	private JTextField licenceIdT = new JTextField(18);
	private JTextField registrationT = new JTextField(18);
	private JTextField amountT = new JTextField(18);

	// Create some Labels
	private JLabel recordIdL = new JLabel("Accident Registration Number    : ");
	private JLabel locationL = new JLabel("Accident address: ");
	private JLabel accidentDateL = new JLabel("Accident date       : ");

	private JLabel licenceIdL = new JLabel("Driver Licence         : ");
	private JLabel registrationL = new JLabel("Vehicle Registration : ");
	private JLabel amountL = new JLabel("Accident Amount      : ");

	JPanel r = new JPanel();
	JPanel s = new JPanel();

	// Manage database operation
	private OperationData optData;

	// Contructor that creates the UI components
	public AccidentSearch(String str) {
		super(str);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		s.setAlignmentX(Component.CENTER_ALIGNMENT);
		s.setPreferredSize(new Dimension(400, 200));
		s.setMaximumSize(new Dimension(400, 200)); // set max = pref
		s.setBorder(BorderFactory.createTitledBorder(" Search Accident "));

		r.setAlignmentX(Component.CENTER_ALIGNMENT);
		r.setPreferredSize(new Dimension(400, 200));
		r.setMaximumSize(new Dimension(400, 200)); // set max = pref
		r.setBorder(BorderFactory.createTitledBorder(" Accident Details "));

		// Add the GUI components
		getContentPane().add(s);
		getContentPane().add(r);

		s.add(recordIdL);
		s.add(recordIdT);
		s.add(searchRecordButton);
		s.add(locationL);
		s.add(locationT);
		s.add(accidentDateL);
		s.add(accidentDateT);
		s.add(deleteRecordButton);

		r.add(registrationL);
		r.add(registrationT);
		r.add(licenceIdL);
		r.add(licenceIdT);
		r.add(amountL);
		r.add(amountT);
		r.add(saveRecordButton);
		r.add(updateRecordButton);
		r.add(deleDetailRecordButton);

		getContentPane().add(closeSearchButton);

		// Add some listeners to monitor for actions (i.e. button presses)
		saveRecordButton.addActionListener(this);
		updateRecordButton.addActionListener(this);
		searchRecordButton.addActionListener(this);
		deleteRecordButton.addActionListener(this);
		deleDetailRecordButton.addActionListener(this);
		closeSearchButton.addActionListener(this);
		r.setVisible(false);

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
		AccidentBean accident = null;
		AccidentVehicleBean accidentDetail = null;
		MessageBean message = null;
		if (target.equals(searchRecordButton)) {
			if (recordIdT.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Enter a valid Accident Number!");
			} else {
				accident = optData.searchRecord(recordIdT.getText());
				if (accident != null) {
					r.setVisible(true);
					locationT.setText(accident.getLocation());
					SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
					accidentDateT
							.setText(date1.format(accident.getAccidentDate()) + " - " + accident.getAccidentTime());
					// Get accident-vehicle
					accidentDetail = optData.searchRecordDetail(recordIdT.getText());
					if (accidentDetail != null) {
						registrationT.setText(accidentDetail.getRegistration());
						licenceIdT.setText(accidentDetail.getLicenceId());
						amountT.setText(accidentDetail.getAmount().toString());
						saveRecordButton.setVisible(false);
						updateRecordButton.setVisible(true);

					} else {
						registrationT.setText(null);
						licenceIdT.setText(null);
						amountT.setText(null);
						updateRecordButton.setVisible(false);
						saveRecordButton.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Accident Number not found!");
					accidentDateT.setText(null);
					locationT.setText(null);
					registrationT.setText(null);
					licenceIdT.setText(null);
					amountT.setText(null);
					r.setVisible(false);
				}
			}

		} else if (target.equals(saveRecordButton)) {
			message = optData.saveRecord(registrationT.getText(), licenceIdT.getText(),
					Double.parseDouble(amountT.getText()), recordIdT.getText());
			JOptionPane.showMessageDialog(null, message.getMessage());
			//
		} else if (target.equals(deleDetailRecordButton)) {
			if (!registrationT.getText().isEmpty()) {
				message = optData.deleteRecordC(registrationT.getText(), licenceIdT.getText(), amountT.getText(),
						recordIdT.getText());
				registrationT.setText(null);
				licenceIdT.setText(null);
				amountT.setText(null);
			}
		} else if (target.equals(deleteRecordButton)) {
			message = optData.deleteRecord(recordIdT.getText());
			JOptionPane.showMessageDialog(null, message.getMessage());
			recordIdT.setText(null);
			locationT.setText(null);
			accidentDateT.setText(null);
			//
		} else if (target.equals(updateRecordButton)) {
			message = optData.updateRecord(registrationT.getText(), licenceIdT.getText(),
					Double.parseDouble(amountT.getText()), recordIdT.getText());
			JOptionPane.showMessageDialog(null, message.getMessage());
			//
		} else if (target.equals(closeSearchButton)) {
			setVisible(false);
		}

	}

}
