package views;

import models.Booking;
import models.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static database.OpenConnection.getConnection;

public class PropertyReviewDetailsFrame extends JFrame implements ActionListener {
    private Booking booking;

    private JSpinner checkinScoreSpinner = new JSpinner(new SpinnerNumberModel(5,1,5,1));
    private JSpinner accuracyScoreSpinner = new JSpinner(new SpinnerNumberModel(5,1,5,1));
    private JSpinner locationScoreSpinner = new JSpinner(new SpinnerNumberModel(5,1,5,1));
    private JSpinner valueScoreSpinner = new JSpinner(new SpinnerNumberModel(5,1,5,1));
    private JSpinner cleaninessScoreSpinner = new JSpinner(new SpinnerNumberModel(5,1,5,1));
    private JSpinner communicationScoreSpinner = new JSpinner(new SpinnerNumberModel(5,1,5,1));

    public PropertyReviewDetailsFrame(Booking booking) {
        super("Property Review Details");

        this.booking = booking;

        setSize(400,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel reviewPanel = new JPanel(new GridBagLayout());
        reviewPanel.setBorder(BorderFactory.createTitledBorder("Review"));

        GridBagConstraints gbc = new GridBagConstraints();
        if (booking.getReview() == null) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            reviewPanel.add(new JLabel("Checkin Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            reviewPanel.add(checkinScoreSpinner, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            reviewPanel.add(new JLabel("Accuracy Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            reviewPanel.add(accuracyScoreSpinner, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            reviewPanel.add(new JLabel("Location Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            reviewPanel.add(locationScoreSpinner, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            reviewPanel.add(new JLabel("Value Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            reviewPanel.add(valueScoreSpinner, gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            reviewPanel.add(new JLabel("Cleaniness Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 4;
            reviewPanel.add(cleaninessScoreSpinner, gbc);
            gbc.gridx = 0;
            gbc.gridy = 5;
            reviewPanel.add(new JLabel("Communication Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 5;
            reviewPanel.add(communicationScoreSpinner, gbc);

            JButton addReviewButton = new JButton("Add Review");
            addReviewButton.addActionListener(this);
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            reviewPanel.add(addReviewButton, gbc);
        } else {
            Review review = booking.getReview();

            gbc.gridx = 0;
            gbc.gridy = 0;
            reviewPanel.add(new JLabel("Checkin Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            reviewPanel.add(new JLabel(String.valueOf(review.getCheckinScore())), gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            reviewPanel.add(new JLabel("Accuracy Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            reviewPanel.add(new JLabel(String.valueOf(review.getAccuracyScore())), gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            reviewPanel.add(new JLabel("Location Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            reviewPanel.add(new JLabel(String.valueOf(review.getLocationScore())), gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            reviewPanel.add(new JLabel("Value Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            reviewPanel.add(new JLabel(String.valueOf(review.getValueScore())), gbc);
            gbc.gridx = 0;
            gbc.gridy = 4;
            reviewPanel.add(new JLabel("Cleaniness Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 4;
            reviewPanel.add(new JLabel(String.valueOf(review.getCleaninessScore())), gbc);
            gbc.gridx = 0;
            gbc.gridy = 5;
            reviewPanel.add(new JLabel("Communication Score: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 5;
            reviewPanel.add(new JLabel(String.valueOf(review.getCommunicationScore())), gbc);
        }
        add(reviewPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Add Review")) {
            try (Connection con = getConnection()) {
                Review review = new Review (booking.getGuest().getUserID(), booking.getProperty().getPropertyID(),
                        (Integer) checkinScoreSpinner.getValue(), (Integer) accuracyScoreSpinner.getValue(),
                        (Integer) locationScoreSpinner.getValue(), (Integer) valueScoreSpinner.getValue(),
                        (Integer) cleaninessScoreSpinner.getValue(), (Integer) communicationScoreSpinner.getValue());

                String query = "INSERT INTO Reviews VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, review.getUserID());
                pst.setInt(2, review.getPropertyID());
                pst.setInt(3, review.getCheckinScore());
                pst.setInt(4, review.getAccuracyScore());
                pst.setInt(5, review.getLocationScore());
                pst.setInt(6, review.getValueScore());
                pst.setInt(7, review.getCleaninessScore());
                pst.setInt(8, review.getCommunicationScore());

                pst.executeUpdate();

                booking.setReview(review);
                JOptionPane.showMessageDialog(this, "Review has been added.");
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
