package views;

import javax.swing.*;

public class HomePanel extends JPanel {
    public HomePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if (MainFrame.loggedInUser.getRole().equals("guest")) {

        } else if (MainFrame.loggedInUser.getRole().equals("host")) {

        }
    }
}
