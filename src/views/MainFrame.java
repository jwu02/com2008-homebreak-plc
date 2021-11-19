package views;

import models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static database.OpenConnection.getConnection;

public class MainFrame extends JFrame {
    public static User loggedInUser;

    public JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

    public MainFrame(User loggedInUser) {
        super("Homebreak PLC");

        this.loggedInUser = loggedInUser;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(840,420);

        // 0 means null userID, i.e. user not logged in
        if (loggedInUser.getUserID() == 0) {
            SearchPropertyPanel searchPropertyPanel = new SearchPropertyPanel();
            RegistrationPanel registrationPanel = new RegistrationPanel();

            tabs.addTab("Search Property", searchPropertyPanel);

            // create property panel for testing, delete later
            CreatePropertyPanel createPropertyPanel = new CreatePropertyPanel();
            tabs.addTab("Create Property", createPropertyPanel);

            JPanel loginPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            loginPanel.add(new JLabel("Login"),gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            loginPanel.add(new JLabel("Email"),gbc);
            JTextField email = new JTextField(20);
            gbc.gridx = 1;
            gbc.gridy = 1;
            loginPanel.add(email,gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            loginPanel.add(new JLabel("Password"),gbc);
            JTextField password = new JPasswordField(20);
            gbc.gridx = 1;
            gbc.gridy = 2;
            loginPanel.add(password,gbc);

            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(e -> {
                try {
                    try (Connection con = getConnection()) {
                        String query = "SELECT * FROM Users WHERE Email=? AND Password=? LIMIT 1";
                        PreparedStatement statement = con.prepareStatement(query);
                        statement.clearParameters();
                        statement.setString(1, email.getText());
                        statement.setString(2, password.getText());

                        ResultSet resultSet = statement.executeQuery();

                        if (!resultSet.isBeforeFirst()) {
                            System.out.println("Invalid credentials");
                        } else {
                            System.out.println("You have successfully logged in!");
                            dispose();
                            MainFrame newFrame = new MainFrame(new User(resultSet));
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            loginPanel.add(loginButton,gbc);

            tabs.addTab("Login", loginPanel);
            tabs.addTab("Register", registrationPanel);
        } else {
            HomePanel homePanel = new HomePanel();
            SearchPropertyPanel searchPropertyPanel = new SearchPropertyPanel();

            tabs.addTab("Home", homePanel);
            tabs.addTab("Search Property", searchPropertyPanel);
            if (loggedInUser.getRole().equals("host")) {
                CreatePropertyPanel createPropertyPanel = new CreatePropertyPanel();
                tabs.addTab("Create Property", createPropertyPanel);
            }

            SettingsPanel settingsPanel = new SettingsPanel();
            tabs.addTab("Settings", settingsPanel);
        }

        add(tabs);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame(new User());
    }
}
