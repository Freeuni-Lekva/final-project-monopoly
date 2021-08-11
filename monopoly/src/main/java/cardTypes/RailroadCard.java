package cardTypes;

public class RailroadCard extends Card {

    private static final int MAX_NUM_RAILROADS = 4;

    /* If the player owns i railroads, then the rent for all his railroads will be rents[i - 1] */
    private int[] rents;

    public RailroadCard(String[] data, int interest) {
        cardName = data[0];
        cardImageName = data[1];
        cost = Integer.parseInt(data[2]);
        rents = new int[MAX_NUM_RAILROADS];
        for (int i = 0; i < rents.length; i++) rents[i] = Integer.parseInt(data[3 + i]);
        mortgageValue = Integer.parseInt(data[7]);
        mortgaged = false;
        defaultPercentage = 1 + (double)interest / 100;
        percentage = defaultPercentage;
    }

    public int getRent(int railroadsOwned) {
        if (railroadsOwned < 1) return 0;
        if (railroadsOwned > 4) railroadsOwned = 4;
        return rents[railroadsOwned - 1];
    }
}
