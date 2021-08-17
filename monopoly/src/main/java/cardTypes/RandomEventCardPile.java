package cardTypes;

import java.util.Random;
import java.util.Vector;

/*
 This class works on either the Chance card set or the Community Chest card set, depending on which set was
 passed to the constructor
*/
public class RandomEventCardPile {

    private String getOutOfJail;

    /*
     drawPile keeps all cards in the beginning, after a player takes a card from it, it is moved to
     the discardPile, unless it is the "Get out of Jail for free" card, in which case the player keeps
     the card until it is used. After the drawPile is depleted, it is replaced with the discardPile
     */
    private Vector<String> drawPile;
    private Vector<String> discardPile;
    private Random rand;
    private int originalSize;

    public RandomEventCardPile(Vector<String> cards, String getOutOfJail) {
        drawPile = cards;
        discardPile = new Vector<>();
        rand = new Random();
        this.getOutOfJail = getOutOfJail;
        originalSize = drawPile.size();
    }

    /* Returns a random card from the drawPile */
    public String getRandomCard() {
        if (drawPile.size() == 0) {
            drawPile = new Vector<>(discardPile);
            discardPile.clear();
        }
        int randomNum = 0;
        if (drawPile.size() > 1) randomNum = rand.nextInt(drawPile.size() - 1);
        String res = drawPile.get(randomNum);
        drawPile.remove(res);
        if (!res.equals(getOutOfJail)) discardPile.add(res);
        return res;
    }

    /* Used to return the "Get out of Jail for free" card to the discardPile after a player uses or sells the card */
    public void addGetOutOfJail() {
        if (!drawPile.contains(getOutOfJail) && !discardPile.contains(getOutOfJail))
            discardPile.add(getOutOfJail);
    }

    public boolean containsGetOutOfJailCard() {
        return !(drawPile.size() + discardPile.size() < originalSize);
    }

}
