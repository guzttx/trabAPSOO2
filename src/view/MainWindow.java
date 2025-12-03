package view;

import model.*;
import controller.*;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private GameState state = new GameState();
    private GameController controller = new GameController();

    private JPanel tablePanel;
    private JPanel handPanel;
    private java.util.List<SmellCardPanel> smellPanels = new java.util.ArrayList<>();
    private java.util.List<CardPanel> cardPanels = new java.util.ArrayList<>();
    private JLabel estabilidadeLabel;
    private JLabel pontosLabel;
    private JLabel turnoLabel;
    private JLabel deckLabel;
    private int pontos = 0;
    private int turno = 1;
    private String ultimoSmellAdicionado = ""; // Previne duplicatas consecutivas

    public MainWindow() {
        setTitle("Refactor Hero - Card Game");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(new Color(40, 60, 100));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        estabilidadeLabel = new JLabel("[HEART] Estabilidade: 12");
        estabilidadeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        estabilidadeLabel.setForeground(Color.WHITE);

        pontosLabel = new JLabel("[STAR] Pontos: 0");
        pontosLabel.setFont(new Font("Arial", Font.BOLD, 16));
        pontosLabel.setForeground(new Color(255, 215, 0));

        turnoLabel = new JLabel("[PLAY] Turno: 1");
        turnoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnoLabel.setForeground(Color.WHITE);

        deckLabel = new JLabel("[DECK] Baralho: " + GameData.getDeckSize());
        deckLabel.setFont(new Font("Arial", Font.BOLD, 16));
        deckLabel.setForeground(new Color(150, 200, 255));

        topPanel.add(estabilidadeLabel);
        topPanel.add(Box.createHorizontalStrut(50));
        topPanel.add(pontosLabel);
        topPanel.add(Box.createHorizontalStrut(50));
        topPanel.add(turnoLabel);
        topPanel.add(Box.createHorizontalStrut(50));
        topPanel.add(deckLabel);

        JScrollPane tablePaneScroll = new JScrollPane();
        tablePanel = new JPanel(new GridLayout(0, 3, 15, 15));
        tablePanel.setBackground(new Color(230, 240, 250));
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(100, 150, 200)), 
            "[RED] MESA - Smells Ativos", 0, 0, new Font("Arial", Font.BOLD, 14), Color.BLACK));
        tablePaneScroll.setViewportView(tablePanel);

        JScrollPane handPaneScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        handPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        handPanel.setBackground(new Color(240, 250, 240));
        handPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(100, 200, 100)), 
            "[CARDS] SUA MAO", 0, 0, new Font("Arial", Font.BOLD, 14), Color.BLACK));
        handPaneScroll.setViewportView(handPanel);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(tablePaneScroll, BorderLayout.CENTER);
        centerPanel.add(handPaneScroll, BorderLayout.SOUTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.setBackground(new Color(240, 240, 240));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton aplicarBtn = new JButton("[APPLY] Aplicar Carta");
        aplicarBtn.setFont(new Font("Arial", Font.BOLD, 14));
        aplicarBtn.setBackground(new Color(0, 180, 100));
        aplicarBtn.setForeground(Color.WHITE);
        aplicarBtn.setFocusPainted(false);
        aplicarBtn.addActionListener(e -> applySelectedCardToSmell());

        JButton comprarBtn = new JButton("[DRAW] Comprar Carta");
        comprarBtn.setFont(new Font("Arial", Font.BOLD, 14));
        comprarBtn.setBackground(new Color(30, 100, 200));
        comprarBtn.setForeground(Color.WHITE);
        comprarBtn.setFocusPainted(false);
        comprarBtn.addActionListener(e -> comprarCarta());

        JButton mulliganBtn = new JButton("[SWAP] Trocar Mão (-1 ❤)");
        mulliganBtn.setFont(new Font("Arial", Font.BOLD, 14));
        mulliganBtn.setBackground(new Color(180, 80, 180));
        mulliganBtn.setForeground(Color.WHITE);
        mulliganBtn.setFocusPainted(false);
        mulliganBtn.addActionListener(e -> executarMulligan());

        JButton dicaBtn = new JButton("[HELP] Dica");
        dicaBtn.setFont(new Font("Arial", Font.BOLD, 14));
        dicaBtn.setBackground(new Color(200, 150, 50));
        dicaBtn.setForeground(Color.WHITE);
        dicaBtn.setFocusPainted(false);
        dicaBtn.addActionListener(e -> mostrarDica());

        controlPanel.add(aplicarBtn);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(comprarBtn);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(mulliganBtn);
        controlPanel.add(Box.createHorizontalStrut(10));
        controlPanel.add(dicaBtn);
        controlPanel.add(Box.createHorizontalGlue());

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

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

        for (CodeSmell s : state.smellsAtivos) {
            SmellCardPanel sp = new SmellCardPanel();
            sp.showSmell(s);
            sp.setPreferredSize(new Dimension(180, 150));
            sp.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent me) {
                    for (SmellCardPanel other : smellPanels) other.setSelected(false);
                    sp.setSelected(true);
                }
            });
            smellPanels.add(sp);
            tablePanel.add(sp);
        }

        // Começar com 7 cartas (ao invés de 5) para mais opções estratégicas
        for (int i = 0; i < 7; i++) {
            Card c = GameData.drawCard();
            if (c == null) break;
            state.cartasJogador.add(c);
        }

        for (Card c : state.cartasJogador) {
            addCardToHand(c);
        }

        updateDeckLabel();
        revalidate();
        repaint();
    }

    private void addCardToHand(Card c) {
        CardPanel cp = new CardPanel();
        cp.showCard(c);
        cp.setPreferredSize(new Dimension(180, 150));
        cp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent me) {
                for (CardPanel other : cardPanels) other.setSelected(false);
                cp.setSelected(true);
            }
        });
        cardPanels.add(cp);
        handPanel.add(cp);
    }

    private void updateEstabilidade() {
        estabilidadeLabel.setText("[HEART] Estabilidade: " + state.estabilidade);
        if (state.estabilidade <= 0) {
            gameOver();
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "GAME OVER! (DEAD)\nEstabilidade chegou a zero!\nPontos finais: " + pontos, 
                                      "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new StartScreen();
    }

    private void checkVictory() {
        if (state.smellsAtivos.isEmpty()) {
            String message = "🎉 PARABÉNS! VOCÊ VENCEU! 🎉\n\n" +
                           "Você resolveu todos os Code Smells!\n\n" +
                           "📊 ESTATÍSTICAS FINAIS:\n" +
                           "⭐ Pontos: " + pontos + "\n" +
                           "🎮 Turnos: " + turno + "\n" +
                           "❤️ Estabilidade final: " + state.estabilidade + "\n\n" +
                           "Você é um verdadeiro Refactor Hero!";
            
            JOptionPane.showMessageDialog(this, message, 
                                        "VITÓRIA!", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new StartScreen();
        }
    }

    private void applySelectedCardToSmell() {
        SmellCardPanel target = null;
        for (SmellCardPanel sp : smellPanels) if (sp.isSelected()) target = sp;
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
            JOptionPane.showMessageDialog(this, "Selecione um SMELL na mesa e uma CARTA na sua mão.");
            return;
        }

        int smellIndex = smellPanels.indexOf(target);
        if (smellIndex < 0 || smellIndex >= state.smellsAtivos.size()) return;

        CodeSmell smell = state.smellsAtivos.get(smellIndex);
        Card carta = state.cartasJogador.get(cardIndex);

        boolean resolved = false;
        int pontosGanhos = 0;

        if (carta instanceof PatternCard) {
            resolved = controller.aplicarPattern((PatternCard) carta, smell);
            pontosGanhos = 15;
        } else if (carta instanceof RefactorCard) {
            resolved = controller.tentarResolver(smell, carta);
            pontosGanhos = 10;
        }

        if (resolved) {
            JOptionPane.showMessageDialog(this, "[OK] Smell resolvido! +"+pontosGanhos+" pontos");
            tablePanel.remove(target);
            smellPanels.remove(target);
            state.smellsAtivos.remove(smellIndex);

            handPanel.remove(selectedCardPanel);
            cardPanels.remove(selectedCardPanel);
            state.cartasJogador.remove(cardIndex);
            
            // Adiciona carta usada à pilha de descarte (reshuffle mechanic)
            GameData.addToDiscard(carta);

            pontos += pontosGanhos;
            turno++;
            pontosLabel.setText("[STAR] Pontos: " + pontos);
            turnoLabel.setText("[PLAY] Turno: " + turno);

            // Bonus: +1 estabilidade a cada 3 smells resolvidos (max 12)
            if (turno % 3 == 0 && state.estabilidade < 12) {
                state.estabilidade++;
                JOptionPane.showMessageDialog(this, "🎉 Bonus! +1 Estabilidade (bom trabalho!)");
            }

            // Novos smells a cada 3 turnos (era 2) para menos pressão
            if (state.smellsAtivos.size() < 10 && turno % 3 == 0) {
                adicionarNovoSmell();
            }

            updateEstabilidade();
            updateDeckLabel();
            revalidate();
            repaint();
            
            checkVictory();
        } else {
            JOptionPane.showMessageDialog(this, "[X] A carta NAO resolve esse smell!\nEstabilidade -1");
            state.estabilidade--;
            updateEstabilidade();
        }
    }

    // Smells balanceados com múltiplas soluções viáveis (anti-duplicata)
    private void adicionarNovoSmell() {
        java.util.Random r = new java.util.Random();
        CodeSmell novoSmell;
        int tentativas = 0;
        
        // Tenta até 10x para evitar duplicata do último smell adicionado
        do {
            int tipo = r.nextInt(6);
            
            switch(tipo) {
                case 0:
                    novoSmell = new CodeSmell("Speculative Generality", 2, 
                        "Código preparado para 'futuro' que nunca vem.", 
                        new String[]{"Extract Class", "Move Method"});
                    break;
                case 1:
                    novoSmell = new CodeSmell("Temporary Field", 2, 
                        "Campo que nem sempre é usado.", 
                        new String[]{"Replace Temp with Query", "Introduce Parameter Object", "Extract Class"});
                    break;
                case 2:
                    novoSmell = new CodeSmell("Lazy Class", 1, 
                        "Classe que faz muito pouco.", 
                        new String[]{"Extract Class", "Move Method"});
                    break;
                case 3:
                    novoSmell = new CodeSmell("Middle Man", 2, 
                        "Classe que apenas delega para outras.", 
                        new String[]{"Move Method", "Extract Class"});
                    break;
                case 4:
                    novoSmell = new CodeSmell("Inappropriate Intimacy", 2, 
                        "Classes muito dependentes entre si.", 
                        new String[]{"Move Method", "Extract Class", "Introduce Parameter Object"});
                    break;
                default:
                    novoSmell = new CodeSmell("Incomplete Library", 2, 
                        "Biblioteca não oferece tudo que precisa.", 
                        new String[]{"Adapter", "Facade"});
            }
            
            tentativas++;
        } while (novoSmell.getNome().equals(ultimoSmellAdicionado) && tentativas < 10);
        
        ultimoSmellAdicionado = novoSmell.getNome();
        state.smellsAtivos.add(novoSmell);

        SmellCardPanel sp = new SmellCardPanel();
        sp.showSmell(novoSmell);
        sp.setPreferredSize(new Dimension(180, 150));
        sp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent me) {
                for (SmellCardPanel other : smellPanels) other.setSelected(false);
                sp.setSelected(true);
            }
        });
        smellPanels.add(sp);
        tablePanel.add(sp);
    }

    private void comprarCarta() {
        int deckSizeAntes = GameData.getDeckSize();
        Card nova = GameData.drawCard();
        
        if (nova == null) {
            JOptionPane.showMessageDialog(this, "Sem cartas disponíveis!\n(Baralho e descarte vazios)");
            return;
        }
        
        // Feedback de reshuffle
        if (deckSizeAntes == 0 && GameData.getDeckSize() > 0) {
            JOptionPane.showMessageDialog(this, "♻️ Descarte reembaralhado no baralho!");
        }
        
        state.cartasJogador.add(nova);
        addCardToHand(nova);
        updateDeckLabel();
        revalidate();
        repaint();
    }

    // Mulligan: descarta toda mão e compra novas cartas (custo: 1 estabilidade)
    private void executarMulligan() {
        if (state.cartasJogador.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sua mão já está vazia!");
            return;
        }
        
        if (state.estabilidade <= 1) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "⚠️ AVISO: Trocar mão com 1 de estabilidade causará GAME OVER!\n\nContinuar mesmo assim?",
                "Mulligan Perigoso", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm != JOptionPane.YES_OPTION) return;
        }
        
        int numCartas = state.cartasJogador.size();
        
        // Descarta mão atual
        for (Card c : state.cartasJogador) {
            GameData.addToDiscard(c);
        }
        state.cartasJogador.clear();
        
        // Limpa UI da mão
        for (CardPanel cp : cardPanels) {
            handPanel.remove(cp);
        }
        cardPanels.clear();
        
        // Compra mesma quantidade de cartas
        for (int i = 0; i < numCartas; i++) {
            Card nova = GameData.drawCard();
            if (nova != null) {
                state.cartasJogador.add(nova);
                addCardToHand(nova);
            }
        }
        
        // Custo de estabilidade
        state.estabilidade--;
        updateEstabilidade();
        updateDeckLabel();
        revalidate();
        repaint();
        
        JOptionPane.showMessageDialog(this, "♻️ Mão trocada! (-1 estabilidade)");
    }
    
    private void updateDeckLabel() {
        int deckSize = GameData.getDeckSize();
        int discardSize = GameData.getDiscardSize();
        deckLabel.setText("[DECK] Baralho: " + deckSize + " | Descarte: " + discardSize);
    }

    private void mostrarDica() {
        String dica = "DICAS:\n\n" +
                "- Padroes (AZUIS): Resolvem problemas estruturais (+15 pts)\n" +
                "- Refatoracoes (VERDES): Melhoram o codigo (+10 pts)\n" +
                "- [SWAP] Trocar Mao: Descarta e compra novas cartas (-1 estabilidade)\n" +
                "- Cartas usadas vao para descarte e voltam quando baralho acaba\n" +
                "- Se a ESTABILIDADE chegar a 0, GAME OVER!\n" +
                "- Aplicar carta ERRADA diminui estabilidade\n" +
                "- Vence quando resolver TODOS os smells!";
        JOptionPane.showMessageDialog(this, dica, "[HELP] Dicas do Jogo", JOptionPane.INFORMATION_MESSAGE);
    }
}
