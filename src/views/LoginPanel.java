package views;

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
        loginSection.add(email);
        loginSection.add(new JLabel("Password"));
        loginSection.add(password);
        add(loginSection);

        loginButton.addActionListener(e -> {
            try {
                login();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        add(loginButton);
    }

    public void login() throws SQLException {
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
            }
        }
    }
}
