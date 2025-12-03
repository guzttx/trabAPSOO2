package view;

import model.CodeSmell;

import javax.swing.*;
import java.awt.*;

public class SmellPanel extends JPanel {

    private JLabel smellNameLabel;
    private JTextArea smellDescriptionArea;
    private JLabel severityLabel;
    private boolean selected = false;

    public SmellPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(220, 50, 50)));
        setBackground(Color.WHITE);

        smellNameLabel = new JLabel("Code Smell");
        smellNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        smellNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        smellNameLabel.setForeground(Color.WHITE);
        smellNameLabel.setOpaque(true);
        smellNameLabel.setBackground(new Color(180, 30, 30));

        severityLabel = new JLabel("Severidade: -");
        severityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        severityLabel.setFont(new Font("Arial", Font.BOLD, 12));
        severityLabel.setForeground(new Color(200, 100, 100));

        smellDescriptionArea = new JTextArea();
        smellDescriptionArea.setEditable(false);
        smellDescriptionArea.setLineWrap(true);
        smellDescriptionArea.setWrapStyleWord(true);
        smellDescriptionArea.setFont(new Font("Arial", Font.PLAIN, 10));
        smellDescriptionArea.setBackground(new Color(255, 240, 240));

        add(smellNameLabel, BorderLayout.NORTH);
        add(severityLabel, BorderLayout.AFTER_LINE_ENDS);
        add(new JScrollPane(smellDescriptionArea), BorderLayout.CENTER);
    }

    public void showSmell(CodeSmell smell) {
        if (smell == null) return;
        smellNameLabel.setText("(STINK) " + smell.getNome());
        smellDescriptionArea.setText(smell.getDescricao());
        severityLabel.setText("Severidade: " + "*".repeat(Math.min(smell.getSeveridade(), 5)));
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
        if (sel) {
            setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW));
            setBackground(new Color(255, 255, 220));
        } else {
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(220, 50, 50)));
            setBackground(Color.WHITE);
        }
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }
}
