/*
package views;

import models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static database.OpenConnection.getConnection;

public class LoginPanel extends JPanel {
    public JTextField email = new JTextField();
    public JTextField password = new JPasswordField();
    public JButton loginButton = new JButton("Login");

    public LoginPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        add(new JLabel("Login"));
        JPanel loginSection = new JPanel(new GridLayout(2,2));
        loginSection.add(new JLabel("Email"));
        JTextField email = new JTextField();
        loginSection.add(email);

        loginSection.add(new JLabel("Password"));
        JTextField password = new JPasswordField();
        loginSection.add(password);
        add(loginSection);

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
                        loggedInUser = new User(resultSet);
                        MainFrame newFrame = new MainFrame(MainFrame.loggedInUser);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        add(loginButton);
    }
}
*/