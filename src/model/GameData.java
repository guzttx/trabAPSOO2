package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class GameData {

    private static Deque<Card> deck = new ArrayDeque<>();
    private static List<Card> discardPile = new ArrayList<>();

    static {
        seed();
    }

    public static void seed() {
        deck.clear();
        discardPile.clear();

        ArrayList<Card> initial = new ArrayList<>();

        // Refactor Cards - resolvem smells específicos pelo nome exato
        initial.add(new RefactorCard("Extract Method", "Divide um método longo em partes menores.", "Melhora legibilidade e modularidade."));
        initial.add(new RefactorCard("Introduce Parameter Object", "Criar um objeto parâmetro para agrupar dados.", "Reduz primitives e melhora expressividade."));
        initial.add(new RefactorCard("Move Method", "Mover método para classe mais apropriada.", "Melhora coesão."));
        initial.add(new RefactorCard("Extract Class", "Extrai responsabilidades para nova classe.", "Reduz tamanho de classes e acoplamento."));
        initial.add(new RefactorCard("Replace Temp with Query", "Remover variáveis temporárias substituindo por métodos.", "Melhora clareza de expressões complexas."));
        
        // Pattern Cards - o campo whenToUse deve mencionar o nome do smell para dar match
        initial.add(new PatternCard("Strategy", "Permite trocar algoritmos em tempo de execução.", "Resolve Long Method com múltiplos if-else."));
        initial.add(new PatternCard("Facade", "Simplifica interfaces complexas.", "Resolve God Class, Shotgun Surgery e Message Chains."));
        initial.add(new PatternCard("Decorator", "Adiciona comportamento sem alterar a classe original.", "Resolve Divergent Change permitindo extensões."));
        initial.add(new PatternCard("Builder", "Simplifica criação de objetos complexos.", "Resolve Long Parameter List com construção fluente."));
        initial.add(new PatternCard("Template Method", "Define esqueleto de algoritmo, delegando passos.", "Resolve Duplicate Code com estrutura reutilizável."));
        
        // Patterns extras (BUGFIX: Adapter precisa mencionar "Incomplete Library" no whenToUse)
        initial.add(new PatternCard("Observer", "Notifica objetos dependentes quando o estado muda.", "Útil para desacoplamento entre componentes."));
        initial.add(new PatternCard("Adapter", "Faz a ponte entre interfaces incompatíveis.", "Resolve Incomplete Library permitindo integração."));
        initial.add(new PatternCard("Singleton", "Garante uma única instância.", "Útil para gerenciar recursos globais."));

        for (int i = 0; i < initial.size(); i++) {
            deck.addLast(initial.get(i));
        }
    }

    // Reshuffle mechanic: reembaralha descarte quando baralho acaba
    public static Card drawCard() {
        if (deck.isEmpty() && !discardPile.isEmpty()) {
            reshuffleDiscardIntoDeck();
        }
        return deck.pollFirst();
    }
    
    public static void addToDiscard(Card card) {
        if (card != null) {
            discardPile.add(card);
        }
    }
    
    private static void reshuffleDiscardIntoDeck() {
        Collections.shuffle(discardPile);
        deck.addAll(discardPile);
        discardPile.clear();
    }
    
    public static int getDeckSize() {
        return deck.size();
    }
    
    public static int getDiscardSize() {
        return discardPile.size();
    }

    public static void resetDeck() {
        seed();
    }
}
