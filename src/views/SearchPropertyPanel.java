package views;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static database.OpenConnection.getConnection;

public class SearchPropertyPanel extends JPanel {

    private JTextField searchPropertyField = new JTextField();
    private JButton searchPropertyButton = new JButton("Search");

    public SearchPropertyPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        add(new JLabel("Search Property"));

        JPanel searchPropertyTools = new JPanel();
        searchPropertyTools.add(searchPropertyField);
        searchPropertyButton.addActionListener(e -> {

        });
        searchPropertyTools.add(searchPropertyButton);
        add(searchPropertyTools);

        try (Connection con = getConnection()) {
            String query = "SELECT * FROM Properties";
            PreparedStatement pst = con.prepareStatement(query);

        } catch (Exception ex) {

        }
    }
}
