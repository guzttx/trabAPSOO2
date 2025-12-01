package model;

public class CodeSmell {

    private String nome;
    private int severidade;
    private String descricao;
    private String[] solucoes;

    public CodeSmell(String nome, int severidade, String descricao, String[] solucoes) {
        this.nome = nome;
        this.severidade = severidade;
        this.descricao = descricao;
        this.solucoes = solucoes;
    }

    public String getNome() { return nome; }

    public int getSeveridade() { return severidade; }

    public String getDescricao() { return descricao; }

    public String[] getSolucoes() { return solucoes; }
}
