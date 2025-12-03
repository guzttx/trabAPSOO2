package controller;

import model.CodeSmell;
import model.Card;
import model.PatternCard;

public class GameController {

    public GameController() {
        // Controller sem estado - apenas lógica de validação
    }

    public boolean tentarResolver(CodeSmell smell, Card carta) {
        // Apenas valida se a carta resolve o smell, sem modificar o estado
        if (smell == null || carta == null) return false;
        
        for (String solucao : smell.getSolucoes()) {
            if (solucao.equalsIgnoreCase(carta.getTitle())) {
                // Validação bem-sucedida
                return true;
            }
        }
        return false;
    }

    public boolean aplicarPattern(PatternCard pattern, CodeSmell smell) {
        // Apenas valida se o pattern resolve o smell, sem modificar o estado
        if (pattern == null || smell == null) return false;
        return pattern.matchesSmell(smell);
    }

}
