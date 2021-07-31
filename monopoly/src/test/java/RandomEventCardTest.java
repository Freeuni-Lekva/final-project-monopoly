import DAO.CardsDAO;
import cardTypes.RandomEventCard;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Vector;

public class RandomEventCardTest extends TestCase {

    @Test
    public void testChanceCards() throws SQLException {
        CardsDAO cardsDAO = new CardsDAO();
        RandomEventCard rec = new RandomEventCard(cardsDAO.getChanceCards(), "chance-get-out-of-jail.png");
        Vector<String> allChanceCards = cardsDAO.getChanceCards();

        while (allChanceCards.size() > 0) {
            String chanceCard = rec.getRandomCard();
            assertTrue(allChanceCards.contains(chanceCard));
            allChanceCards.remove(chanceCard);
        }

        for (int i = 0; i < 3; i++) {
            allChanceCards = cardsDAO.getChanceCards();
            allChanceCards.remove("chance-get-out-of-jail.png");
            while (allChanceCards.size() > 0) {
                String chanceCard = rec.getRandomCard();
                assertTrue(allChanceCards.contains(chanceCard));
                allChanceCards.remove(chanceCard);
            }
        }

        rec.addGetOutOfJail();
        allChanceCards = cardsDAO.getChanceCards();
        while (allChanceCards.size() > 0) {
            String chanceCard = rec.getRandomCard();
            assertTrue(allChanceCards.contains(chanceCard));
            allChanceCards.remove(chanceCard);
        }
    }

    @Test
    public void testCommunityChestCards() throws SQLException {
        CardsDAO cardsDAO = new CardsDAO();
        RandomEventCard rec = new RandomEventCard(cardsDAO.getCommunityChestCards(),
                "chest-get-out-of-jail.png");
        Vector<String> allCommunityChestCards = cardsDAO.getCommunityChestCards();

        while (allCommunityChestCards.size() > 0) {
            String communityChestCard = rec.getRandomCard();
            assertTrue(allCommunityChestCards.contains(communityChestCard));
            allCommunityChestCards.remove(communityChestCard);
        }

        for (int i = 0; i < 3; i++) {
            allCommunityChestCards = cardsDAO.getCommunityChestCards();
            allCommunityChestCards.remove("chest-get-out-of-jail.png");
            while (allCommunityChestCards.size() > 0) {
                String communityChestCard = rec.getRandomCard();
                assertTrue(allCommunityChestCards.contains(communityChestCard));
                allCommunityChestCards.remove(communityChestCard);
            }
        }

        rec.addGetOutOfJail();
        allCommunityChestCards = cardsDAO.getCommunityChestCards();
        while (allCommunityChestCards.size() > 0) {
            String communityChestCard = rec.getRandomCard();
            assertTrue(allCommunityChestCards.contains(communityChestCard));
            allCommunityChestCards.remove(communityChestCard);
        }
    }

}
