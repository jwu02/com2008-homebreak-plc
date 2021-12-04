package views.createProperty;

import models.ChargeBand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static database.OpenConnection.getConnection;

public class ChargeBandDetailsPanelCard extends JPanel implements ActionListener {
    private CreatePropertyPanel backReferencePanel;
    private JPanel addedChargeBandsPanel = new JPanel();

    private ArrayList<ChargeBand> chargeBandsList = new ArrayList<>();
    private JTextField startDate = new JTextField("yyyy-mm-dd");
    private JTextField endDate = new JTextField("yyyy-mm-dd");
    private JTextField serviceCharge = new JTextField(10);
    private JTextField cleaningCharge = new JTextField(10);

    public ChargeBandDetailsPanelCard(CreatePropertyPanel backReferencePanel) {
        this.backReferencePanel = backReferencePanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel chargeBandDetailsInputPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        chargeBandDetailsInputPanel.add(new JLabel("Charge Band Details"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        chargeBandDetailsInputPanel.add(new JLabel("Start Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        chargeBandDetailsInputPanel.add(startDate,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        chargeBandDetailsInputPanel.add(new JLabel("End Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        chargeBandDetailsInputPanel.add(endDate,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        chargeBandDetailsInputPanel.add(new JLabel("Service Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        chargeBandDetailsInputPanel.add(serviceCharge,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        chargeBandDetailsInputPanel.add(new JLabel("Cleaning Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        chargeBandDetailsInputPanel.add(cleaningCharge,gbc);

        JButton addChargeBandButton = new JButton("Add Charge Band");
        addChargeBandButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        chargeBandDetailsInputPanel.add(addChargeBandButton,gbc);
        add(chargeBandDetailsInputPanel);

        addedChargeBandsPanel.setLayout(new BoxLayout(addedChargeBandsPanel,BoxLayout.Y_AXIS));
        add(addedChargeBandsPanel);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        add(nextButton);

        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(this);
        add(previousButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String dialogMessage;

        if (command.equals("Add Charge Band")) {
            dialogMessage = validateChargeBand();
            if (dialogMessage != null) {
                JOptionPane.showMessageDialog(this, dialogMessage, "Add Charge Band", JOptionPane.WARNING_MESSAGE);
            } else {
                // add charge band to list
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(this.startDate.getText(), dtf);
                LocalDate endDate = LocalDate.parse(this.endDate.getText(), dtf);
                BigDecimal serviceCharge = new BigDecimal(this.serviceCharge.getText());
                BigDecimal cleaningCharge = new BigDecimal(this.cleaningCharge.getText());

                ChargeBand newChargeBand = new ChargeBand(startDate, endDate, serviceCharge, cleaningCharge);
                chargeBandsList.add(newChargeBand);
                addChargeBandToPanel();
                clearChargeBandFields();

                JOptionPane.showMessageDialog(this,"Added charge band.");
            }
        } else if (command.equals("Previous")) {
            backReferencePanel.getCardLayout().previous(backReferencePanel);
        } else if (command.equals("Next")) {
            backReferencePanel.getCardLayout().next(backReferencePanel);
        }
    }

    public String validateChargeBand() {
        if (startDate.getText().equals("yyyy-mm-dd") || endDate.getText().equals("yyyy-mm-dd") ||
                serviceCharge.getText().equals("") || cleaningCharge.getText().equals("")) {
            return "Please fill in all fields";
        } else {
            // check if date can be parsed and start date is before end date
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(this.startDate.getText(), dtf);
                LocalDate endDate = LocalDate.parse(this.endDate.getText(), dtf);

                if (!startDate.isBefore(endDate)) {
                    return "End date cannot be on or before start date.";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return "Dates cannot be parsed. Please enter date in yyyy-MM-dd format.";
            }

            try {
                new BigDecimal(this.serviceCharge.getText());
                new BigDecimal(this.cleaningCharge.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                return "Please enter a valid numerical charge.";
            }
        }

        return null;
    }

    public void addChargeBandToPanel() {
        JPanel chargeBandDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        chargeBandDetailsPanel.add(new JLabel("Start Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        chargeBandDetailsPanel.add(new JLabel(startDate.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        chargeBandDetailsPanel.add(new JLabel("End Date"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        chargeBandDetailsPanel.add(new JLabel(endDate.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        chargeBandDetailsPanel.add(new JLabel("Service Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        chargeBandDetailsPanel.add(new JLabel(serviceCharge.getText()),gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        chargeBandDetailsPanel.add(new JLabel("Cleaning Charge"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        chargeBandDetailsPanel.add(new JLabel(cleaningCharge.getText()),gbc);
        addedChargeBandsPanel.add(chargeBandDetailsPanel,0);

        revalidate();
        repaint();
    }

    public void insertChargeBands() throws SQLException {
        try (Connection con = getConnection()) {
            String query = "INSERT INTO ChargeBands VALUES (null, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            for (ChargeBand cb : chargeBandsList) {
                pst.clearParameters();
                pst.setDate(1, Date.valueOf(cb.getStartDate()));
                pst.setDate(2, Date.valueOf(cb.getEndDate()));
                pst.setBigDecimal(3, cb.getServiceCharge());
                pst.setBigDecimal(4, cb.getCleaningCharge());
                pst.setInt(5, CreatePropertyPanel.lastInsertedPropertyID);
                pst.executeUpdate();
            }

            addedChargeBandsPanel.removeAll();
            revalidate();
            repaint();
        }
    }

    public void clearChargeBandFields() {
        startDate.setText("yyyy-mm-dd");
        endDate.setText("yyyy-mm-dd");
        serviceCharge.setText("");
        cleaningCharge.setText("");
    }
}
