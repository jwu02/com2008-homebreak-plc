package views;

import javax.swing.*;

public class CreatePropertyPanel extends JPanel {
    private JTextField name;
    private JTextArea description;
    private JTextField location;
    private JRadioButton offerBreakfast;

    private JTextField house;
    private JTextField street;
    private JTextField place;
    private JTextField postcode;

    public CreatePropertyPanel() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

    }

    public void insertProperty() {

    }
}
