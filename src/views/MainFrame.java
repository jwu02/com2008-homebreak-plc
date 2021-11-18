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

            JPanel loginPanel = new JPanel();
            loginPanel.setLayout(new BoxLayout(loginPanel,BoxLayout.Y_AXIS));
            loginPanel.add(new JLabel("Login"));
            JPanel loginSection = new JPanel(new GridLayout(2,2));
            loginSection.add(new JLabel("Email"));
            JTextField email = new JTextField();
            loginSection.add(email);

            loginSection.add(new JLabel("Password"));
            JTextField password = new JPasswordField();
            loginSection.add(password);
            loginPanel.add(loginSection);

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
            loginPanel.add(loginButton);

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
