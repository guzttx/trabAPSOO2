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
    private Card currentCard;

    public CardPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setBackground(Color.WHITE);

        titleLabel = new JLabel("Card");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(60, 100, 180));

        detailLabel = new JLabel("");
        detailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        detailLabel.setForeground(new Color(100, 100, 100));

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        add(titleLabel, BorderLayout.NORTH);
        add(detailLabel, BorderLayout.AFTER_LAST_LINE);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
    }

    public void showCard(Card card) {
        this.currentCard = card;
        titleLabel.setText(card.getTitle());
        descriptionArea.setText(card.getDescription());
        if (card instanceof PatternCard) {
            PatternCard p = (PatternCard) card;
            detailLabel.setText("(POWER) " + p.getWhenToUse());
            titleLabel.setBackground(new Color(30, 100, 200));
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(30, 100, 200)));
        } else if (card instanceof RefactorCard) {
            RefactorCard r = (RefactorCard) card;
            detailLabel.setText("(FIX) " + r.getEffect());
            titleLabel.setBackground(new Color(20, 160, 80));
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(20, 160, 80)));
        } else {
            detailLabel.setText("");
        }
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
        if (sel) {
            setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW));
            setBackground(new Color(255, 255, 220));
        } else {
            if (currentCard instanceof PatternCard) {
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(30, 100, 200)));
            } else if (currentCard instanceof RefactorCard) {
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(20, 160, 80)));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
            setBackground(Color.WHITE);
        }
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }
}
