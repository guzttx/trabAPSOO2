package model;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    public int estabilidade = 12; // Aumentado de 10 para 12 (2 erros extras permitidos)
    public List<CodeSmell> smellsAtivos = new ArrayList<>();
    public List<Card> cartasJogador = new ArrayList<>();
    
    // Discard Pile - cartas usadas que podem ser reembaralhadas
    public List<Card> cartasDescartadas = new ArrayList<>();

}
