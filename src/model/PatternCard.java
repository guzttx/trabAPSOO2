package model;

public class PatternCard extends Card {

    private String whenToUse;

    public PatternCard(String title, String description, String whenToUse) {
        super(title, description);
        this.whenToUse = whenToUse;
    }

    @Override
    public String getType() {
        return "Pattern";
    }

    public String getWhenToUse() {
        return whenToUse;
    }

    @Override
    public String toString() {
        return getTitle() + " [Pattern] - " + whenToUse;
    }

    public boolean matchesSmell(CodeSmell smell) {
        if (smell == null) return false;
        String nome = smell.getNome();
        if (nome == null) return false;
        if (whenToUse.equalsIgnoreCase(nome)) return true;
        return whenToUse.toLowerCase().contains(nome.toLowerCase());
    }
}
