package cardTypes;

public class RailroadCard implements Card {

    private final int MAX_NUM_RAILROADS = 4;

    private String cardName;
    private String cardImageName;
    private int cost;

    /* If the player owns i railroads, then the rent for all his railroads will be rents[i - 1] */
    private int[] rents;
    private int mortgageValue;
    private boolean mortgaged;

    /* The default interest rate of mortgaging */
    private int interest;

    /* current extra percentage that has to be paid when lifting the mortgage */
    private int percentage;

    public RailroadCard(String[] data, int interest) {
        cardName = data[0];
        cardImageName = data[1];
        cost = Integer.parseInt(data[2]);
        rents = new int[MAX_NUM_RAILROADS];
        for (int i = 0; i < rents.length; i++) rents[i] = Integer.parseInt(data[3 + i]);
        mortgageValue = Integer.parseInt(data[7]);
        mortgaged = false;
        this.interest = interest;
        percentage = interest;
    }

    @Override
    public int getRent(int railroadsOwned) {
        if (railroadsOwned < 1) return 0;
        if (railroadsOwned > 4) railroadsOwned = 4;
        return rents[railroadsOwned - 1];
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
