package views.createProperty;

import javax.swing.*;
import java.awt.*;

public class CreatePropertyPanel extends JPanel {
    private CardLayout cardLayout = new CardLayout();

    private ConfidentialDetailsPanelCard confidentialDetailsPanelCard;
    private ChargeBandDetailsPanelCard chargeBandDetailsPanelCard;
    private FacilityDetailsPanelCard facilityDetailsPanelCard;

    public static int lastInsertedPropertyID;

    public CreatePropertyPanel() {
        setLayout(cardLayout);

        // card 1
        confidentialDetailsPanelCard = new ConfidentialDetailsPanelCard(this);
        add("confidentialDetails", confidentialDetailsPanelCard);

        // card 2
        chargeBandDetailsPanelCard = new ChargeBandDetailsPanelCard(this);
        add("chargeBandsDetails", chargeBandDetailsPanelCard);

        // card 3
        facilityDetailsPanelCard = new FacilityDetailsPanelCard(this);
        add("facilitiesDetails", facilityDetailsPanelCard);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public ConfidentialDetailsPanelCard getConfidentialDetailsPanelCard() {
        return confidentialDetailsPanelCard;
    }

    public ChargeBandDetailsPanelCard getChargeBandDetailsPanelCard() {
        return chargeBandDetailsPanelCard;
    }

    public void clearAllFields() {
        confidentialDetailsPanelCard.clearConfidentialDetailFields();
        chargeBandDetailsPanelCard.clearChargeBandFields();
        facilityDetailsPanelCard.clearFacilityFields();
    }
}