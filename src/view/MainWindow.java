package view;

import model.*;
import controller.*;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private GameState state = new GameState();
    private GameController controller = new GameController(state);

    private DefaultListModel<String> smellsModel = new DefaultListModel<>();
    private DefaultListModel<String> cardsModel = new DefaultListModel<>();
    private JLabel estabilidadeLabel = new JLabel("Estabilidade: 10");
    private JPanel tablePanel;
    private JPanel handPanel;
    private java.util.List<SmellPanel> smellPanels = new java.util.ArrayList<>();
    private java.util.List<CardPanel> cardPanels = new java.util.ArrayList<>();
    private JLabel pontosLabel = new JLabel("Pontos: 0");
    private int pontos = 0;

    public MainWindow() {

        setTitle("Refactor Hero - Card Game");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tablePanel = new JPanel(new GridLayout(0, 3, 10, 10));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Mesa - Smells ativos"));

        handPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        handPanel.setBorder(BorderFactory.createTitledBorder("Sua mão"));

        JPanel center = new JPanel(new BorderLayout());
        center.add(tablePanel, BorderLayout.CENTER);
        center.add(handPanel, BorderLayout.SOUTH);

        JPanel controls = new JPanel();
        JButton aplicarBtn = new JButton("Aplicar Carta");
        JButton comprarBtn = new JButton("Comprar Carta");
        controls.add(aplicarBtn);
        controls.add(comprarBtn);
        controls.add(estabilidadeLabel);
        controls.add(pontosLabel);

        add(center, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        aplicarBtn.addActionListener(e -> applySelectedCardToSmell());
        comprarBtn.addActionListener(e -> comprarCarta());

        carregarDadosIniciais();

        setVisible(true);
    }

    private void carregarDadosIniciais() {
        state.smellsAtivos.add(new CodeSmell("Long Method", 2, "Métodos muito longos dificultam leitura e manutenção.", new String[]{"Extract Method", "Strategy"}));
        state.smellsAtivos.add(new CodeSmell("God Class", 4, "Classe faz coisas demais, muito acoplamento e baixa coesão.", new String[]{"Extract Class", "Facade"}));
        state.smellsAtivos.add(new CodeSmell("Primitive Obsession", 2, "Uso excessivo de tipos primitivos em vez de objetos ricos.", new String[]{"Introduce Parameter Object"}));
        state.smellsAtivos.add(new CodeSmell("Shotgun Surgery", 3, "Mudanças exigem pequenas edições em muitas classes.", new String[]{"Move Method", "Facade"}));
        state.smellsAtivos.add(new CodeSmell("Feature Envy", 2, "Método usa mais dados de outra classe do que da sua própria.", new String[]{"Move Method", "Extract Class"}));
        state.smellsAtivos.add(new CodeSmell("Data Clump", 2, "Grupos de variáveis que aparecem juntos frequentemente.", new String[]{"Introduce Parameter Object", "Extract Class"}));
        state.smellsAtivos.add(new CodeSmell("Divergent Change", 3, "Muitas mudanças em uma classe por motivos diferentes.", new String[]{"Extract Class", "Decorator"}));
        state.smellsAtivos.add(new CodeSmell("Message Chains", 2, "Sequências longas de acesso a propriedades/objetos.", new String[]{"Introduce Parameter Object", "Facade"}));
        state.smellsAtivos.add(new CodeSmell("Duplicate Code", 3, "Trechos repetidos em vários locais.", new String[]{"Extract Method", "Template Method"}));
        state.smellsAtivos.add(new CodeSmell("Long Parameter List", 2, "Métodos com muitos parâmetros.", new String[]{"Introduce Parameter Object", "Builder"}));

        // popular painel de mesa
        for (CodeSmell s : state.smellsAtivos) {
            SmellPanel sp = new SmellPanel();
            sp.showSmell(s);
            sp.setPreferredSize(new Dimension(160, 140));
            sp.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent me) {
                    for (SmellPanel other : smellPanels) other.setSelected(false);
                    sp.setSelected(true);
                }
            });
            smellPanels.add(sp);
            tablePanel.add(sp);
        }

        for (int i = 0; i < 5; i++) {
            Card c = GameData.drawCard();
            if (c == null) break;
            state.cartasJogador.add(c);
        }

        for (Card c : state.cartasJogador) {
            CardPanel cp = new CardPanel();
            cp.showCard(c);
            cp.setPreferredSize(new Dimension(200, 140));
            cp.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent me) {
                    for (CardPanel other : cardPanels) other.setSelected(false);
                    cp.setSelected(true);
                }
            });
            cardPanels.add(cp);
            handPanel.add(cp);
        }

        revalidate();
        repaint();

    }

    private void updateEstabilidade() {
        estabilidadeLabel.setText("Estabilidade: " + state.estabilidade);
    }

    private void applySelectedCardToSmell() {
        SmellPanel target = null;
        for (SmellPanel sp : smellPanels) if (sp.isSelected()) target = sp;
        CardPanel selectedCardPanel = null;
        int cardIndex = -1;
        for (int i = 0; i < cardPanels.size(); i++) {
            if (cardPanels.get(i).isSelected()) {
                selectedCardPanel = cardPanels.get(i);
                cardIndex = i;
                break;
            }
        }

        if (target == null || selectedCardPanel == null) {
            JOptionPane.showMessageDialog(this, "Selecione um smell na mesa e uma carta na sua mão.");
            return;
        }

        int smellIndex = smellPanels.indexOf(target);
        if (smellIndex < 0 || smellIndex >= state.smellsAtivos.size()) return;

        CodeSmell smell = state.smellsAtivos.get(smellIndex);
        Card carta = state.cartasJogador.get(cardIndex);

        boolean resolved = false;
        if (carta instanceof PatternCard) {
            resolved = controller.aplicarPattern((PatternCard) carta, smell);
        } else {
            resolved = controller.tentarResolver(smell, carta);
        }

        if (resolved) {
            JOptionPane.showMessageDialog(this, "Smell resolvido!");
            tablePanel.remove(target);
            smellPanels.remove(target);
            state.smellsAtivos.remove(smellIndex);

            handPanel.remove(selectedCardPanel);
            cardPanels.remove(selectedCardPanel);
            state.cartasJogador.remove(cardIndex);

            pontos += 10;
            pontosLabel.setText("Pontos: " + pontos);

            updateEstabilidade();
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "A carta não resolve esse smell.");
        }
    }
    

    private void comprarCarta() {
        Card nova = GameData.drawCard();
        if (nova == null) {
            JOptionPane.showMessageDialog(this, "Baralho vazio.");
            return;
        }
        state.cartasJogador.add(nova);
        CardPanel cp = new CardPanel();
        cp.showCard(nova);
        cp.setPreferredSize(new Dimension(200, 140));
        cp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent me) {
                for (CardPanel other : cardPanels) other.setSelected(false);
                cp.setSelected(true);
            }
        });
        cardPanels.add(cp);
        handPanel.add(cp);
        revalidate();
        repaint();
    }
}
