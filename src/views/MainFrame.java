package views;

import models.User;

import javax.swing.*;

public class MainFrame extends JFrame {
    public JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
    public RegistrationPanel registrationPanel = new RegistrationPanel();
    public LoginPanel loginPanel = new LoginPanel();
    public SearchPropertyPanel searchPropertyPanel = new SearchPropertyPanel();
    public CreatePropertyPanel createPropertyPanel = new CreatePropertyPanel();
    public User loggedInUser;

    public MainFrame() {
        super("Homebreak PLC");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(840,420);

        tabs.addTab("Create Property", createPropertyPanel);
        tabs.addTab("Search Property", searchPropertyPanel);
        tabs.addTab("Login", loginPanel);
        tabs.addTab("Register", registrationPanel);
        getContentPane().add(tabs);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setSize(400,400);
        frame.setVisible(true);
    }
}
