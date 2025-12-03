package view;

import model.Card;
import model.PatternCard;
import model.RefactorCard;
import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel detailLabel;
    private JTextArea descriptionArea;
    private boolean selected = false;

    public CardPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        titleLabel = new JLabel("Card");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        detailLabel = new JLabel("");
        detailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        add(titleLabel, BorderLayout.NORTH);
        add(detailLabel, BorderLayout.AFTER_LAST_LINE);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
    }

    public void showCard(Card card) {
        titleLabel.setText(card.getTitle() + " (" + card.getType() + ")");
        descriptionArea.setText(card.getDescription());
        // detalhe específico para tipos
        if (card instanceof PatternCard) {
            PatternCard p = (PatternCard) card;
            detailLabel.setText("Quando usar: " + p.getWhenToUse());
        } else if (card instanceof RefactorCard) {
            RefactorCard r = (RefactorCard) card;
            detailLabel.setText("Efeito: " + r.getEffect());
        } else {
            detailLabel.setText("");
        }
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
        if (sel) {
            setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }
}
