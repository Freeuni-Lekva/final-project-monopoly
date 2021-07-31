package cardTypes;

public class UtilityCard implements Card {

    private final int SINGLE_UTILITY_MULTIPLIER = 4;
    private final int DOUBLE_UTILITY_MULTIPLIER = 10;

    private String cardName;
    private String cardImageName;
    private int cost;
    private int mortgageValue;
    private boolean mortgaged;

    /* The default interest rate of mortgaging */
    private int interest;

    /* current extra percentage that has to be paid when lifting the mortgage */
    private int percentage;

    public UtilityCard(String[] data, int interest) {
        cardName = data[0];
        cardImageName = data[1];
        cost = Integer.parseInt(data[2]);
        mortgageValue = Integer.parseInt(data[3]);
        mortgaged = false;
        this.interest = interest;
        percentage = interest;
    }

    /* This will return the rent for each space moved after rolling */
    @Override
    public int getRent(int utilitiesOwned) {
        if (utilitiesOwned > 1) return DOUBLE_UTILITY_MULTIPLIER;
        if (utilitiesOwned < 1) return 0;
        return SINGLE_UTILITY_MULTIPLIER;
    }

    @Override
    public int getCost() {
        if (mortgaged) return mortgageValue * (100 + percentage) / 100;
        return cost;
    }

    @Override
    public int getMortgageValue() {
        return mortgageValue;
    }

    @Override
    public String getCardName() {
        return cardName;
    }

    @Override
    public String getCardImageName() {
        return cardImageName;
    }

    @Override
    public boolean isMortgaged() {
        return mortgaged;
    }

    @Override
    public void mortgageCard() {
        mortgaged = true;
    }

    @Override
    public void liftMortgage() {
        mortgaged = false;
        setInterest(true);
    }

    @Override
    public void setInterest(boolean toDefault) {
        if (toDefault) {
            percentage = interest;
        } else {
            percentage = (100 + percentage) * (100 + interest) / 100 - 100;
        }
    }
}
