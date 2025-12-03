package view;

import model.CodeSmell;

import javax.swing.*;
import java.awt.*;

public class SmellPanel extends JPanel {

    private JLabel smellNameLabel;
    private JTextArea smellDescriptionArea;
    private boolean selected = false;

    public SmellPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        smellNameLabel = new JLabel("Code Smell");
        smellNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        smellNameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        smellDescriptionArea = new JTextArea();
        smellDescriptionArea.setEditable(false);
        smellDescriptionArea.setLineWrap(true);
        smellDescriptionArea.setWrapStyleWord(true);

        add(smellNameLabel, BorderLayout.NORTH);
        add(new JScrollPane(smellDescriptionArea), BorderLayout.CENTER);
    }

    public void showSmell(CodeSmell smell) {
        if (smell == null) return;
        smellNameLabel.setText(smell.getNome());
        smellDescriptionArea.setText(smell.getDescricao() + "\nSeveridade: " + smell.getSeveridade());
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
        if (sel) {
            setBorder(BorderFactory.createLineBorder(Color.GREEN.darker(), 3));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }
}
