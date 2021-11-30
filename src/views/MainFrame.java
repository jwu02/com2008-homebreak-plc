package views;

import models.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static User loggedInUser;

    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

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
            tabbedPane.addTab("Search Property", new SearchPropertyPanel(this));
            tabbedPane.addTab("Login", new LoginPanel(this));
            tabbedPane.addTab("Register", new RegistrationPanel());
        } else {
            tabbedPane.addTab("Home", new HomePanel(this));
            if (loggedInUser.getRole().equals("guest")) {
                tabbedPane.addTab("Search Property", new SearchPropertyPanel(this));
            }

            if (loggedInUser.getRole().equals("host")) {
                tabbedPane.addTab("Create Property", new CreatePropertyPanel());
            }
            tabbedPane.addTab("Settings", new SettingsPanel(this));
        }
        add(tabbedPane);

        JScrollPane scrollPane = new JScrollPane(tabbedPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
