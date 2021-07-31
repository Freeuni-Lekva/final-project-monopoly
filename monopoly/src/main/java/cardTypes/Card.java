package cardTypes;

public interface Card {

    String getCardName();
    String getCardImageName();
    int getCost();
    int getMortgageValue();

    /*
     similarCardsOwned for PropertyCard class objects will be the number of cards the player owns in the color set,
     for RailroadCard class objects it will be how many railroads the player owns in total and for
     UtilityCard class objects it will be
     */
    int getRent(int similarCardsOwned);
    boolean isMortgaged();
    void mortgageCard();
    void liftMortgage();

    /*
     If toDefault is true, then the extra percentage is set to the default interest rate,
     if it is false, then extra percentage is increased according to the default interest
     */
    void setInterest(boolean toDefault);

}
