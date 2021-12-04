package views;

import models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements ActionListener {
    private MainFrame mainFrame;
    private JButton logoutButton = new JButton("Logout");

    public SettingsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        logoutButton.addActionListener(this);
        add(logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Logout")) {
            mainFrame.dispose();
            new MainFrame("Homebreak PLC",new User());
        }
    }
}
