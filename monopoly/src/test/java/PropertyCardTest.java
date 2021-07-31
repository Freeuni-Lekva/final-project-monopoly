import cardTypes.PropertyCard;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Random;

public class PropertyCardTest extends TestCase {

    private PropertyCard property;
    private String[] data;

    @Override
    protected void setUp() throws Exception {
        String[] data = {"N", "img.png", "brown", "200", "10", "50", "51", "52", "53", "54", "70", "100", "20", "30"};
        this.data = data;
        property = new PropertyCard(data, 10);
    }

    @Test
    public void testBasics() throws SQLException {
        assertEquals("N", property.getCardName());
        assertEquals("img.png", property.getCardImageName());
        assertEquals("brown", property.getCardColor());
        assertEquals(200, property.getCost());
        assertEquals(100, property.getMortgageValue());
        assertEquals(20, property.getHouseCost());
        assertEquals(30, property.getHotelCost());
        assertEquals(0, property.getNumHouses());
        assertTrue(!property.buildingsPlaced());

        String[] newData = {"a", "b", "lightblue", "1000", "0", "-5", "-4", "-3", "-2", "-1", "10", "11", "12", "13"};
        property = new PropertyCard(newData, 10);
        assertEquals("a", property.getCardName());
        assertEquals("b", property.getCardImageName());
        assertEquals("lightblue", property.getCardColor());
        assertEquals(1000, property.getCost());
        assertEquals(11, property.getMortgageValue());
        assertEquals(12, property.getHouseCost());
        assertEquals(13, property.getHotelCost());
        assertEquals(0, property.getNumHouses());
        assertTrue(!property.buildingsPlaced());
    }

    @Test
    public void testHouseAndHotelPlacements() {
        assertEquals(0, property.getNumHouses());
        assertTrue(!property.isHotelPlaced());
        assertTrue(!property.buildingsPlaced());

        assertTrue(!property.decrementHouses());
        assertEquals(0, property.getNumHouses());
        assertTrue(!property.isHotelPlaced());
        assertTrue(!property.buildingsPlaced());

        assertTrue(property.incrementHouses());
        assertEquals(1, property.getNumHouses());
        assertTrue(!property.isHotelPlaced());
        assertTrue(property.buildingsPlaced());

        assertTrue(!property.placeHotel());
        assertEquals(1, property.getNumHouses());
        assertTrue(!property.isHotelPlaced());
        assertTrue(property.buildingsPlaced());

        for (int i = 0; i < 3; i++) {
            assertTrue(property.incrementHouses());
            assertEquals(i + 2, property.getNumHouses());
            assertTrue(!property.isHotelPlaced());
            assertTrue(property.buildingsPlaced());
        }

        Random rand = new Random();
        int randomNum = rand.nextInt(50) + 20;
        for (int i = 0; i < randomNum; i++) {
            assertTrue(!property.incrementHouses());
            assertEquals(4, property.getNumHouses());
            assertTrue(!property.isHotelPlaced());
            assertTrue(property.buildingsPlaced());
        }

        assertTrue(property.placeHotel());
        assertEquals(0, property.getNumHouses());
        assertTrue(property.isHotelPlaced());
        assertTrue(property.buildingsPlaced());

        assertTrue(!property.placeHotel());
        assertEquals(0, property.getNumHouses());
        assertTrue(property.isHotelPlaced());
        assertTrue(property.buildingsPlaced());

        assertTrue(property.removeHotel());
        assertEquals(4, property.getNumHouses());
        assertTrue(!property.isHotelPlaced());
        assertTrue(property.buildingsPlaced());

        assertTrue(!property.removeHotel());
        assertEquals(4, property.getNumHouses());
        assertTrue(!property.isHotelPlaced());
        assertTrue(property.buildingsPlaced());

        for (int i = 3; i > 0; i--) {
            assertTrue(property.decrementHouses());
            assertEquals(i, property.getNumHouses());
            assertTrue(!property.isHotelPlaced());
            assertTrue(property.buildingsPlaced());
        }

        assertTrue(property.decrementHouses());
        assertEquals(0, property.getNumHouses());
        assertTrue(!property.isHotelPlaced());
        assertTrue(!property.buildingsPlaced());

        randomNum = rand.nextInt(50) + 20;
        for (int i = 0; i < randomNum; i++) {
            assertTrue(!property.decrementHouses());
            assertEquals(0, property.getNumHouses());
            assertTrue(!property.isHotelPlaced());
            assertTrue(!property.buildingsPlaced());
        }
    }

    @Test
    public void testRent() {
        assertEquals(10, property.getRent(1));
        assertEquals(50, property.getRent(2));
        for (int i = 1; i <= 4; i++) {
            property.incrementHouses();
            assertEquals(50 + i, property.getRent(1));
            assertEquals(50 + i, property.getRent(2));
            assertEquals(50 + i, property.getRent(300));
            assertEquals(50 + i, property.getRent(0));
            assertEquals(50 + i, property.getRent(-50));
        }
        property.placeHotel();
        assertEquals(70, property.getRent(1));
        assertEquals(70, property.getRent(2));
        assertEquals(70, property.getRent(300));
        assertEquals(70, property.getRent(0));
        assertEquals(70, property.getRent(-50));
        property.removeHotel();
        for (int i = 3; i >= 1; i--) {
            property.decrementHouses();
            assertEquals(50 + i, property.getRent(1));
            assertEquals(50 + i, property.getRent(2));
            assertEquals(50 + i, property.getRent(300));
            assertEquals(50 + i, property.getRent(0));
            assertEquals(50 + i, property.getRent(-50));
        }
        property.decrementHouses();
        assertEquals(10, property.getRent(1));
        assertEquals(50, property.getRent(2));
        assertEquals(50, property.getRent(300));
        assertEquals(10, property.getRent(0));
        assertEquals(10, property.getRent(-50));
    }

    @Test
    public void testMortgageAndInterest() throws SQLException {
        assertTrue(!property.isMortgaged());
        property.mortgageCard();
        assertTrue(property.isMortgaged());
        property.mortgageCard();
        assertTrue(property.isMortgaged());
        property.liftMortgage();
        assertTrue(!property.isMortgaged());
        property.liftMortgage();
        assertTrue(!property.isMortgaged());

        assertEquals(200, property.getCost());
        property.mortgageCard();
        assertEquals(110, property.getCost());
        property.setInterest(false);
        assertEquals(121, property.getCost());
        property.setInterest(false);
        assertEquals(133, property.getCost());
        property.setInterest(true);
        assertEquals(110, property.getCost());

        Random rand = new Random();
        int randNum = rand.nextInt(20) + 20;
        int actual = 110;
        for (int i = 0; i < randNum; i++) {
            property.setInterest(false);
            actual = actual * 110 / 100;
        }
        assertEquals(actual, property.getCost());
        property.liftMortgage();
        assertEquals(200, property.getCost());
        property.mortgageCard();
        assertEquals(110, property.getCost());

        property = new PropertyCard(data, 17);
        assertEquals(200, property.getCost());
        property.mortgageCard();
        assertEquals(117, property.getCost());
        property.setInterest(false);
        assertEquals(136, property.getCost());
        property.liftMortgage();
        assertEquals(200, property.getCost());
        property.mortgageCard();
        assertEquals(117, property.getCost());
    }

}
