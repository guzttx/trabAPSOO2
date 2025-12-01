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

    public MainWindow() {

        setTitle("Refactor Hero - Card Game");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JList<String> smellsList = new JList<>(smellsModel);
        JList<String> cardsList = new JList<>(cardsModel);

        JButton resolverBtn = new JButton("Resolver Smell");

        resolverBtn.addActionListener(e -> {

            int sIndex = smellsList.getSelectedIndex();
            int cIndex = cardsList.getSelectedIndex();

            if (sIndex == -1 || cIndex == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um smell e uma carta.");
                return;
            }

            CodeSmell smell = state.smellsAtivos.get(sIndex);
            Card carta = state.cartasJogador.get(cIndex);

            boolean ok = controller.tentarResolver(smell, carta);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Smell resolvido!");

                smellsModel.remove(sIndex);
                state.smellsAtivos.remove(sIndex);

                cardsModel.remove(cIndex);
                state.cartasJogador.remove(cIndex);

                updateEstabilidade();

            } else {
                JOptionPane.showMessageDialog(this, "Essa carta não resolve esse smell!");
            }
        });

        JPanel left = new JPanel(new BorderLayout());
        left.add(new JLabel("Smells ativos"), BorderLayout.NORTH);
        left.add(new JScrollPane(smellsList), BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Suas cartas"), BorderLayout.NORTH);
        right.add(new JScrollPane(cardsList), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(resolverBtn);
        bottom.add(estabilidadeLabel);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        carregarDadosIniciais();

        setVisible(true);
    }

    private void carregarDadosIniciais() {

        state.smellsAtivos.add(new CodeSmell(
                "Long Method",
                1,
                "Métodos muito longos dificultam leitura e manutenção.",
                new String[]{"Extract Method", "Strategy"}
        ));

        state.smellsAtivos.add(new CodeSmell(
                "God Class",
                3,
                "Classe faz coisas demais, muito acoplamento e baixa coesão.",
                new String[]{"Extract Class", "Facade"}
        ));

        state.smellsAtivos.add(new CodeSmell(
                "Primitive Obsession",
                2,
                "Uso excessivo de tipos primitivos em vez de objetos ricos.",
                new String[]{"Introduce Parameter Object"}
        ));

        for (CodeSmell s : state.smellsAtivos)
            smellsModel.addElement(s.getNome());

        state.cartasJogador.add(new RefactorCard(
                "Extract Method",
                "Divide um método longo em partes menores.",
                "Melhora legibilidade e modularidade."
        ));

        state.cartasJogador.add(new PatternCard(
                "Strategy",
                "Permite trocar algoritmos em tempo de execução.",
                "Bom para remover múltiplos if-else aninhados."
        ));

        state.cartasJogador.add(new PatternCard(
                "Facade",
                "Simplifica interfaces complexas.",
                "Reduz acoplamento expondo um ponto de acesso único."
        ));

        for (Card c : state.cartasJogador)
            cardsModel.addElement(c.getTitle());
    }


    private void updateEstabilidade() {
        estabilidadeLabel.setText("Estabilidade: " + state.estabilidade);
    }
}
