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
}
