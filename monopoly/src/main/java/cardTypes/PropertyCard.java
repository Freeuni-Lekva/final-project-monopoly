package cardTypes;

public class PropertyCard extends Card {

    private static final int MAX_HOUSES = 5;

    private String cardColor;
    private int defaultRent;
    private int colorSetRent;

    /* If the player has placed i houses on this property, then the rent for this property will be houseRents[i - 1] */
    private int[] houseRents;
    private int houseCost;
    private int hotelCost;

    /* numHouses = 5 means that a hotel is placed */
    private int numHouses;
    private int colorSetSize;

    public PropertyCard(String[] data, int interest) {
        cardName = data[0];
        cardImageName = data[1];
        cardColor = data[2];
        cost = Integer.parseInt(data[3]);
        defaultRent = Integer.parseInt(data[4]);
        colorSetRent = Integer.parseInt(data[5]);
        houseRents = new int[MAX_HOUSES];
        for (int i = 0; i < houseRents.length; i++) houseRents[i] = Integer.parseInt(data[6 + i]);
        mortgageValue = Integer.parseInt(data[11]);
        houseCost = Integer.parseInt(data[12]);
        hotelCost = Integer.parseInt(data[13]);
        colorSetSize = Integer.parseInt(data[14]);
        numHouses = 0;
        mortgaged = false;
        defaultPercentage = 1 + (double)interest / 100;
        percentage = defaultPercentage;
    }

    public int getRent(int cardsInColorSetOwned) {
        int rent = defaultRent;
        if (numHouses > 0) {
            rent = houseRents[numHouses - 1];
        } else if (cardsInColorSetOwned >= colorSetSize) {
            rent = colorSetRent;
        }
        return rent;
    }

    public String getCardColor() {
        return cardColor;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }

    public void setNumHouses(int numHouses) {
        if (numHouses >= 0 && numHouses <= MAX_HOUSES) this.numHouses = numHouses;
    }

    public int getNumHouses() {
        return numHouses;
    }

}
