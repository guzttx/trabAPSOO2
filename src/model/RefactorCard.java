package model;

public class RefactorCard extends Card {

    private String refactorName;
    private String effect;

    public RefactorCard(String refactorName, String description, String effect) {
        super(refactorName, description);
        this.refactorName = refactorName;
        this.effect = effect;
    }

    @Override
    public String getType() {
        return "Refactor";
    }

    public String getEffect() {
        return effect;
    }
}
