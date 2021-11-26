package views;

import models.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static User loggedInUser;

    private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

    public MainFrame(String title, User loggedInUser) {
        super(title);

        int width = 840;
        int height = 420;
        setSize(width,height);
        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setBounds(center.x - width / 2, center.y - height / 2, width, height);

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

        JScrollPane scrollPane = new JScrollPane(tabs);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame("Homebreak PLC",new User());
    }
}
