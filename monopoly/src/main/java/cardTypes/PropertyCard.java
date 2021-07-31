package cardTypes;

import DAO.CardsDAO;

import java.sql.SQLException;

public class PropertyCard implements Card {

    private final int MAX_HOUSES = 4;

    private String cardName;
    private String cardImageName;
    private String cardColor;
    private int cost;
    private int defaultRent;
    private int colorSetRent;

    /* If the player has placed i houses on this property, then the rent for this property will be houseRents[i - 1] */
    private int[] houseRents;
    private int hotelRent;
    private int mortgageValue;
    private int houseCost;
    private int hotelCost;
    private int numHouses;
    private boolean hotelPlaced;
    private boolean mortgaged;
    private int colorSetSize;

    /* The default interest rate of mortgaging */
    private int interest;

    /* current extra percentage that has to be paid when lifting the mortgage */
    private int percentage;

    public PropertyCard(String[] data, int interest) throws SQLException {
        cardName = data[0];
        cardImageName = data[1];
        cardColor = data[2];
        cost = Integer.parseInt(data[3]);
        defaultRent = Integer.parseInt(data[4]);
        colorSetRent = Integer.parseInt(data[5]);
        houseRents = new int[MAX_HOUSES];
        for (int i = 0; i < houseRents.length; i++) houseRents[i] = Integer.parseInt(data[6 + i]);
        hotelRent = Integer.parseInt(data[10]);
        mortgageValue = Integer.parseInt(data[11]);
        houseCost = Integer.parseInt(data[12]);
        hotelCost = Integer.parseInt(data[13]);
        CardsDAO cardsDAO = new CardsDAO();
        colorSetSize = cardsDAO.getColorSetSize(cardColor);
        numHouses = 0;
        hotelPlaced = false;
        mortgaged = false;
        this.interest = interest;
        percentage = interest;
    }

    @Override
    public int getCost() {
        if (mortgaged) return mortgageValue * (100 + percentage) / 100;
        return cost;
    }

    @Override
    public int getRent(int cardsInColorSetOwned) {
        int rent = defaultRent;
        if (hotelPlaced) {
            rent = hotelRent;
        } else if (numHouses > 0) {
            rent = houseRents[numHouses - 1];
        } else if (cardsInColorSetOwned >= colorSetSize) {
            rent = colorSetRent;
        }
        return rent;
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

    public String getCardColor() {
        return cardColor;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }

    /*
     Since properties with any buildings on them can't be traded or mortgaged, this function will help us check
     if there are any buildings on a property
     */
    public boolean buildingsPlaced() {
        return hotelPlaced || numHouses > 0;
    }

    /* If possible, increments the number of houses on the property and returns true, otherwise returns false */
    public boolean incrementHouses() {
        if (!hotelPlaced && numHouses < 4) {
            numHouses++;
            return true;
        }
        return false;
    }

    /* If possible, decrements the number of houses on the property and returns true, otherwise returns false */
    public boolean decrementHouses() {
        if (!hotelPlaced && numHouses > 0) {
            numHouses--;
            return true;
        }
        return false;
    }

    public int getNumHouses() {
        return numHouses;
    }

    /* If possible, places a hotel on the property instead of the houses and returns true, otherwise returns false */
    public boolean placeHotel() {
        if (!hotelPlaced && numHouses >= MAX_HOUSES) {
            numHouses = 0;
            hotelPlaced = true;
            return true;
        }
        return false;
    }

    /*
     If possible, removes the hotel from the property, returns houses on it and returns true,
     otherwise returns false
    */
    public boolean removeHotel() {
        if (hotelPlaced) {
            hotelPlaced = false;
            numHouses = MAX_HOUSES;
            return true;
        }
        return false;
    }

    public boolean isHotelPlaced() {
        return hotelPlaced;
    }
}
