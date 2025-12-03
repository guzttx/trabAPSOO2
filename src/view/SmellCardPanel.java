package view;

import model.CodeSmell;
import javax.swing.*;
import java.awt.*;

public class SmellCardPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel detailLabel;
    private JTextArea descriptionArea;
    private boolean selected = false;
    private CodeSmell currentSmell;

    public SmellCardPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(200, 50, 50)));
        setBackground(Color.WHITE);

        titleLabel = new JLabel("Smell");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(180, 30, 30));

        detailLabel = new JLabel("Severidade: -");
        detailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailLabel.setFont(new Font("Arial", Font.BOLD, 12));
        detailLabel.setForeground(new Color(200, 100, 100));

        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 10));
        descriptionArea.setBackground(new Color(255, 240, 240));

        add(titleLabel, BorderLayout.NORTH);
        add(detailLabel, BorderLayout.AFTER_LINE_ENDS);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
    }

    public void showSmell(CodeSmell smell) {
        if (smell == null) return;
        this.currentSmell = smell;
        titleLabel.setText("(STINK) " + smell.getNome());
        descriptionArea.setText(smell.getDescricao());
        int sev = Math.min(smell.getSeveridade(), 5);
        detailLabel.setText("Severidade: " + "*".repeat(sev));
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
        if (sel) {
            setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW));
            setBackground(new Color(255, 255, 220));
        } else {
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(200, 50, 50)));
            setBackground(Color.WHITE);
        }
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

    public CodeSmell getSmell() {
        return currentSmell;
    }
}
