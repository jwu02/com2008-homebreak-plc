package views;

import models.User;

import javax.swing.*;

public class MainFrame extends JFrame {
    public static User loggedInUser;

    private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

    public MainFrame(User loggedInUser) {
        super("Homebreak PLC");
        setSize(840,420);

        this.loggedInUser = loggedInUser;

        // 0 means null userID, i.e. user not logged in
        if (loggedInUser.getUserID() == 0) {
            tabs.addTab("Search Property", new SearchPropertyPanel());
            tabs.addTab("Login", new LoginPanel(this));
            tabs.addTab("Register", new RegistrationPanel());
        } else {
            tabs.addTab("Home", new HomePanel());
            tabs.addTab("Search Property", new SearchPropertyPanel());
            if (loggedInUser.getRole().equals("host")) {
                tabs.addTab("Create Property", new CreatePropertyPanel());
            }
            tabs.addTab("Settings", new SettingsPanel());
        }
        add(tabs);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame(new User());
    }
}
