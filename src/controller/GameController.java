package controller;

import model.CodeSmell;
import model.GameState;
import model.Card;

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

}
