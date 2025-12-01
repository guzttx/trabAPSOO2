package view;

import model.Card;
import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    private JLabel titleLabel;
    private JTextArea descriptionArea;

    public CardPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        titleLabel = new JLabel("Card");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        add(titleLabel, BorderLayout.NORTH);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
    }

    public void showCard(Card card) {
        titleLabel.setText(card.getTitle() + " (" + card.getType() + ")");
        descriptionArea.setText(card.getDescription());
    }
}
