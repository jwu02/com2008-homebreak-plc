package views;

import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static database.OpenConnection.getConnection;

public class LoginPanel extends JPanel implements ActionListener {
    private JTextField email = new JTextField(20);
    private JPasswordField password = new JPasswordField(20);

    // login panel takes mainframe in constructor, so we can dispose mainframe later when logging in
    private MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {
        setLayout(new GridBagLayout());

        this.mainFrame = mainFrame;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(new JLabel("Login"),gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Email"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(email,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password"),gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(password,gbc);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton,gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String dialogMessage;

        if (command.equals("Login")) {
            if (email.getText().equals("") || String.valueOf(password.getPassword()).equals("")) {
                dialogMessage = "Please enter an email and password.";
                JOptionPane.showMessageDialog(this, dialogMessage, "Login", JOptionPane.WARNING_MESSAGE);
            } else {
                try (Connection con = getConnection()) {
                    String query = "SELECT * FROM Users WHERE Email=? AND Password=SHA1(?) LIMIT 1";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, email.getText());
                    pst.setString(2, String.valueOf(password.getPassword()));

                    ResultSet resultSet = pst.executeQuery();

                    // if no records are returned
                    if (!resultSet.isBeforeFirst()) {
                        dialogMessage = "Invalid credentials.";
                        JOptionPane.showMessageDialog(this, dialogMessage, "Login", JOptionPane.WARNING_MESSAGE);
                    } else {
                        resultSet.next();
                        int userID = resultSet.getInt("UserID");
                        String forename = resultSet.getString("Forename");
                        String surname = resultSet.getString("Surname");
                        String email = resultSet.getString("Email");
                        String mobile = resultSet.getString("Mobile");
                        String role = resultSet.getString("Role");

                        User user = new User(userID, forename, surname, email, mobile, role);

                        mainFrame.dispose();
                        new MainFrame("Homebreak PLC", user);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    dialogMessage = "An error occurred while logging in, please try again.";
                    JOptionPane.showMessageDialog(this, dialogMessage, "Login", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
