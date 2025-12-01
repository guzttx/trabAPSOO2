package model;

public abstract class Card {

    protected String title;
    protected String description;

    public Card(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getType();
}
