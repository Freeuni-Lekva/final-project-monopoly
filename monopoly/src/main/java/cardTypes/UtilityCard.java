package cardTypes;

public class UtilityCard extends Card {

    private static final int MAX_NUM_UTILITIES = 2;
    private static final int SINGLE_UTILITY_MULTIPLIER = 4;
    private static final int DOUBLE_UTILITY_MULTIPLIER = 10;

    public UtilityCard(String[] data, int interest) {
        cardName = data[0];
        cardImageName = data[1];
        cost = Integer.parseInt(data[2]);
        mortgageValue = Integer.parseInt(data[3]);
        mortgaged = false;
        defaultPercentage = 1 + (double)interest / 100;
        percentage = defaultPercentage;
    }

    public int getRent(int utilitiesOwned, int rolledAmount) {
        int multiplier = 0;
        if (utilitiesOwned == 1) multiplier = SINGLE_UTILITY_MULTIPLIER;
        if (utilitiesOwned >= MAX_NUM_UTILITIES) multiplier = DOUBLE_UTILITY_MULTIPLIER;
        return rolledAmount * multiplier;
    }

}
