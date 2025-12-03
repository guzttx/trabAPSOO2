package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class GameData {

    private static Deque<Card> deck = new ArrayDeque<>();

    static {
        seed();
    }

    public static void seed() {
        deck.clear();

        ArrayList<Card> initial = new ArrayList<>();

        initial.add(new RefactorCard("Extract Method", "Divide um método longo em partes menores.", "Melhora legibilidade e modularidade."));
        initial.add(new RefactorCard("Introduce Parameter Object", "Criar um objeto parâmetro para agrupar dados.", "Reduz primitives e melhora expressividade."));
        initial.add(new RefactorCard("Move Method", "Mover método para classe mais apropriada.", "Melhora coesão."));
        initial.add(new PatternCard("Strategy", "Permite trocar algoritmos em tempo de execução.", "Bom para remover múltiplos if-else aninhados."));
        initial.add(new PatternCard("Facade", "Simplifica interfaces complexas.", "Reduz acoplamento expondo um ponto de acesso único."));
        initial.add(new PatternCard("Observer", "Notifica objetos dependentes quando o estado muda.", "Útil para desacoplamento entre componentes."));
        initial.add(new PatternCard("Decorator", "Adicionar comportamento sem alterar a classe original.", "Bom para estender funcionalidades dinamicamente."));
        initial.add(new PatternCard("Adapter", "Faz a ponte entre interfaces incompatíveis.", "Facilita integração de módulos legados."));
        initial.add(new RefactorCard("Extract Class", "Extrai responsabilidades para nova classe.", "Reduz tamanho de classes e acoplamento."));
        initial.add(new PatternCard("Builder", "Simplifica criação de objetos complexos.", "Ajuda com construtores telescópicos."));
        initial.add(new RefactorCard("Replace Temp with Query", "Remover variáveis temporárias substituindo por métodos.", "Melhora clareza de expressões complexas."));
        initial.add(new PatternCard("Singleton", "Garante uma única instância.", "Útil para gerenciar recursos globais com cuidado."));

        for (int i = 0; i < initial.size(); i++) {
            deck.addLast(initial.get(i));
        }
    }

    public static Card drawCard() {
        return deck.pollFirst();
    }

    public static void resetDeck() {
        seed();
    }
}
