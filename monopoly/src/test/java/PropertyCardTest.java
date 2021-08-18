import cardTypes.PropertyCard;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Random;

public class PropertyCardTest extends TestCase {

    private PropertyCard property;
    private String[] data;

    @Override
    protected void setUp() {
        String[] data = {"N", "img.png", "brown", "200", "10", "50", "51", "52", "53", "54", "70", "100",
                "20", "30", "2"};
        this.data = data;
        property = new PropertyCard(data, 10);
    }

    @Test
    public void testBasics() {
        assertEquals("N", property.getCardName());
        assertEquals("img.png", property.getCardImageName());
        assertEquals("brown", property.getCardColor());
        assertEquals(200, property.getCost());
        assertEquals(100, property.getMortgageValue());
        assertEquals(20, property.getHouseCost());
        assertEquals(30, property.getHotelCost());
        assertEquals(0, property.getNumHouses());

        String[] newData = {"a", "b", "lightblue", "1000", "0", "-5", "-4", "-3", "-2", "-1", "10", "11",
                "12", "13", "2"};
        property = new PropertyCard(newData, 10);
        assertEquals("a", property.getCardName());
        assertEquals("b", property.getCardImageName());
        assertEquals("lightblue", property.getCardColor());
        assertEquals(1000, property.getCost());
        assertEquals(11, property.getMortgageValue());
        assertEquals(12, property.getHouseCost());
        assertEquals(13, property.getHotelCost());
        assertEquals(0, property.getNumHouses());
    }

    @Test
    public void testHouseAndHotelPlacements() {
        assertEquals(0, property.getNumHouses());

        property.setNumHouses(-1);
        assertEquals(0, property.getNumHouses());
        property.setNumHouses(6);
        assertEquals(0, property.getNumHouses());

        property.setNumHouses(1);
        assertEquals(1, property.getNumHouses());

        for (int i = 0; i <= 5; i++) {
            property.setNumHouses(i);
            assertEquals(i, property.getNumHouses());
        }

        Random rand = new Random();
        int randomNum;
        int lastNumHouses = 5;
        for (int i = 0; i < 30; i++) {
            randomNum = rand.nextInt(10) - 2;
            property.setNumHouses(randomNum);
            if (randomNum < 0 || randomNum > 5) {
                assertEquals(lastNumHouses, property.getNumHouses());
            } else {
                assertEquals(randomNum, property.getNumHouses());
                lastNumHouses = randomNum;
            }
        }
    }

    @Test
    public void testRent() {
        assertEquals(10, property.getRent(1));
        assertEquals(50, property.getRent(2));

        for (int i = 1; i <= 4; i++) {
            property.setNumHouses(i);
            assertEquals(50 + i, property.getRent(1));
            assertEquals(50 + i, property.getRent(2));
            assertEquals(50 + i, property.getRent(300));
            assertEquals(50 + i, property.getRent(0));
            assertEquals(50 + i, property.getRent(-50));
        }

        property.setNumHouses(5);
        assertEquals(70, property.getRent(1));
        assertEquals(70, property.getRent(2));
        assertEquals(70, property.getRent(300));
        assertEquals(70, property.getRent(0));
        assertEquals(70, property.getRent(-50));

        Random rand = new Random();
        int randomNum;
        for (int i = 0; i >= 30; i++) {
            randomNum = rand.nextInt(4) + 1;
            property.setNumHouses(randomNum);
            assertEquals(50 + randomNum, property.getRent(1));
            assertEquals(50 + randomNum, property.getRent(2));
            assertEquals(50 + randomNum, property.getRent(300));
            assertEquals(50 + randomNum, property.getRent(0));
            assertEquals(50 + randomNum, property.getRent(-50));
        }

        property.setNumHouses(0);
        assertEquals(10, property.getRent(1));
        assertEquals(50, property.getRent(2));
        assertEquals(50, property.getRent(300));
        assertEquals(10, property.getRent(0));
        assertEquals(10, property.getRent(-50));
    }

    @Test
    public void testMortgageAndInterest() {
        assertTrue(!property.isMortgaged());
        property.setMortgage(true);
        assertTrue(property.isMortgaged());
        property.setMortgage(true);
        assertTrue(property.isMortgaged());
        property.setMortgage(false);
        assertTrue(!property.isMortgaged());
        property.setMortgage(false);
        assertTrue(!property.isMortgaged());

        assertEquals(200, property.getCost());
        property.setMortgage(true);
        assertEquals(110, property.getCost());
        property.increasePercentage();
        assertEquals(121, property.getCost());
        property.increasePercentage();
        assertEquals(133, property.getCost());
        property.setPercentageToDefault();
        assertEquals(110, property.getCost());

        Random rand = new Random();
        int randNum = rand.nextInt(20) + 20;
        double actual = 110;
        for (int i = 0; i < randNum; i++) {
            property.increasePercentage();
            actual = actual * 1.1;
        }
        assertEquals((int)actual, property.getCost());
        property.setMortgage(false);
        assertEquals(200, property.getCost());
        property.setMortgage(true);
        assertEquals(110, property.getCost());

        property = new PropertyCard(data, 17);
        assertEquals(200, property.getCost());
        property.setMortgage(true);
        assertEquals(117, property.getCost());
        property.increasePercentage();
        assertEquals(136, property.getCost());
        property.setMortgage(false);
        assertEquals(200, property.getCost());
        property.setMortgage(true);
        assertEquals(117, property.getCost());
    }

}
