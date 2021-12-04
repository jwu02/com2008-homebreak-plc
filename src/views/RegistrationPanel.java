package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static database.OpenConnection.getConnection;

public class RegistrationPanel extends JPanel implements ActionListener {
    private JTextField forename = new JTextField(20);
    private JTextField surname = new JTextField(20);
    private JTextField email = new JTextField(20);
    private JTextField password = new JPasswordField(20);
    private JTextField mobile = new JTextField(20);

    private ButtonGroup roleButtonGroup = new ButtonGroup();
    private JRadioButton hostRadioButton = new JRadioButton("Host");
    private JRadioButton guestRadioButton = new JRadioButton("Guest");

    private InputAddressDetailsPanel inputAddressDetailsPanel;

    public RegistrationPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JPanel userDetailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        userDetailsPanel.add(new JLabel("User Details"),gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        userDetailsPanel.add(new JLabel("Forename"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        userDetailsPanel.add(forename,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        userDetailsPanel.add(new JLabel("Surname"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        userDetailsPanel.add(surname,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        userDetailsPanel.add(new JLabel("Email"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        userDetailsPanel.add(email,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        userDetailsPanel.add(new JLabel("Password"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        userDetailsPanel.add(password,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        userDetailsPanel.add(new JLabel("Mobile"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        userDetailsPanel.add(mobile,gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        userDetailsPanel.add(new JLabel("Register as"),gbc);
        roleButtonGroup.add(hostRadioButton);
        roleButtonGroup.add(guestRadioButton);
        gbc.gridx = 1;
        gbc.gridy = 6;
        userDetailsPanel.add(hostRadioButton,gbc);
        gbc.gridx = 2;
        gbc.gridy = 6;
        userDetailsPanel.add(guestRadioButton,gbc);
        add(userDetailsPanel);

        inputAddressDetailsPanel = new InputAddressDetailsPanel();
        add(inputAddressDetailsPanel);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        add(registerButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String dialogMessage;

        if (command.equals("Register")) {
            if (forename.getText().equals("") || email.getText().equals("") || password.getText().equals("") ||
                    mobile.getText().equals("") || !(hostRadioButton.isSelected() || guestRadioButton.isSelected())) {
                dialogMessage = "Please fill in all fields and select a role.";
                JOptionPane.showMessageDialog(this, dialogMessage, "Registration", JOptionPane.WARNING_MESSAGE);
            } else {
                try (Connection con = getConnection()) {
                    String query = "SELECT UserID FROM Users WHERE Email=? LIMIT 1";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.clearParameters();
                    pst.setString(1, email.getText());

                    ResultSet resultSet = pst.executeQuery();

                    // if user with given email doesn't exist already insert otherwise return error message
                    if (!resultSet.isBeforeFirst()) {
                        query = "INSERT INTO Users VALUES (null, ?, ?, ?, SHA1(?), ?, ?, ?)";
                        pst = con.prepareStatement(query);
                        pst.clearParameters();
                        pst.setString(1, forename.getText());
                        pst.setString(2, surname.getText());
                        pst.setString(3, email.getText());
                        pst.setString(4, password.getText());
                        pst.setString(5, mobile.getText());
                        pst.setString(6, hostRadioButton.isSelected() ? "host" : "guest");

                        // validate address details first before adding details to database
                        if (inputAddressDetailsPanel.validateAddressDetails()) {
                            pst.setInt(7, inputAddressDetailsPanel.insertAddress()); // obtain id of address
                            pst.executeUpdate();

                            // clear all field entries if registration was successful
                            clearRegistrationFields();
                            inputAddressDetailsPanel.clearAddressDetailFields();

                            dialogMessage = "Successfully registered.";
                            JOptionPane.showMessageDialog(this, dialogMessage);
                        } else {
                            dialogMessage = "Please fill in all address details.";
                            JOptionPane.showMessageDialog(this, dialogMessage, "Registration", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        dialogMessage = "User with provided email already exists.";
                        JOptionPane.showMessageDialog(this, dialogMessage, "Registration", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                    dialogMessage = "An error occurred while registering, please try again.";
                    JOptionPane.showMessageDialog(this, dialogMessage, "Registration", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public void clearRegistrationFields() {
        forename.setText("");
        surname.setText("");
        email.setText("");
        password.setText("");
        mobile.setText("");
        roleButtonGroup.clearSelection();
    }
}
