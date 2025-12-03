package view;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {

    public StartScreen() {
        setTitle("Refactor Hero - Menu Inicial");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(25, 50, 100), 
                                                           getWidth(), getHeight(), new Color(50, 100, 150));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(true);

        JLabel titleLabel = new JLabel("Refactor Hero");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Jogo Educativo de Engenharia de Software");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitleLabel.setForeground(new Color(200, 220, 240));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startBtn = new JButton("[START] Comeco do Jogo");
        startBtn.setFont(new Font("Arial", Font.BOLD, 20));
        startBtn.setBackground(new Color(0, 180, 50));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setPreferredSize(new Dimension(200, 50));
        startBtn.setMaximumSize(new Dimension(200, 50));
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(e -> iniciarJogo());

        JButton exitBtn = new JButton("[EXIT] Sair");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        exitBtn.setBackground(new Color(180, 50, 50));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFocusPainted(false);
        exitBtn.setPreferredSize(new Dimension(200, 50));
        exitBtn.setMaximumSize(new Dimension(200, 50));
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addActionListener(e -> System.exit(0));

        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(60));
        centerPanel.add(startBtn);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(exitBtn);
        centerPanel.add(Box.createVerticalStrut(50));

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void iniciarJogo() {
        this.dispose();
        new MainWindow();
    }
}
