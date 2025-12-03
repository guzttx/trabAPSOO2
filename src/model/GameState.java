package model;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    public int estabilidade = 10;
    public List<CodeSmell> smellsAtivos = new ArrayList<>();
    public List<Card> cartasJogador = new ArrayList<>();
    
    // Discard Pile - cartas usadas que podem ser reembaralhadas
    public List<Card> cartasDescartadas = new ArrayList<>();

}
