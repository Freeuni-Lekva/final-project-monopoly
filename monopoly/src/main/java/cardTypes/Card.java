package cardTypes;

public abstract class Card {

    private static final double MORTGAGE_LIFT_MULTIPLIER = 1.1;
    String cardName;
    String cardImageName;
    int cost;
    int mortgageValue;
    boolean mortgaged;

    public int getCost() {
        return cost;
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

    public int getMortgageLiftCost() {
        return (int)(mortgageValue * MORTGAGE_LIFT_MULTIPLIER);
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
    }

}
