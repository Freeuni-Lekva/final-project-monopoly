package cardTypes;

public abstract class Card {

    String cardName;
    String cardImageName;
    int cost;
    int mortgageValue;
    boolean mortgaged;

    /* The default percentage of mortgaging */
    double defaultPercentage;

    /* current extra percentage that has to be paid when lifting the mortgage */
    double percentage;

    public int getCost() {
        if (mortgaged) return (int)(mortgageValue * percentage);
        return cost;
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardImageName() {
        return cardImageName;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgage(boolean mortgaged) {
        this.mortgaged = mortgaged;
        if (!mortgaged) setPercentageToDefault();
    }

    public void setPercentageToDefault() {
        percentage = defaultPercentage;
    }

    public void increasePercentage() {
        percentage *= defaultPercentage;
    }

}
