package controller;

import model.CodeSmell;
import model.GameState;
import model.Card;
import model.PatternCard;

public class GameController {

    private GameState state;

    public GameController(GameState state) {
        this.state = state;
    }

    public boolean tentarResolver(CodeSmell smell, Card carta) {

        for (String solucao : smell.getSolucoes()) {
            if (solucao.equalsIgnoreCase(carta.getTitle())) {
                state.smellsAtivos.remove(smell);
                state.estabilidade = Math.min(10, state.estabilidade + 1);
                return true;
            }
        }
        return false;
    }

    public boolean aplicarPattern(PatternCard pattern, CodeSmell smell) {
        if (pattern == null || smell == null) return false;
        if (pattern.matchesSmell(smell)) {
            state.smellsAtivos.remove(smell);
            state.estabilidade = Math.min(10, state.estabilidade + 2);
            return true;
        }
        return false;
    }

}
