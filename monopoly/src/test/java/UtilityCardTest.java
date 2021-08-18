import cardTypes.UtilityCard;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Random;

public class UtilityCardTest extends TestCase {

    @Test
    public void testBasics() {
        String[] data = {"NAME", "img-name.png", "200", "100"};
        UtilityCard utility = new UtilityCard(data, 10);

        assertEquals("NAME", utility.getCardName());
        assertEquals("img-name.png", utility.getCardImageName());
        assertEquals(200, utility.getCost());
        assertEquals(100, utility.getMortgageValue());

        data[0] = "DIFFERENT NAME";
        data[1] = "other-img.png";
        data[2] = "1234567";
        data[3] = "0";
        utility = new UtilityCard(data, 0);
        assertEquals("DIFFERENT NAME", utility.getCardName());
        assertEquals("other-img.png", utility.getCardImageName());
        assertEquals(1234567, utility.getCost());
        assertEquals(0, utility.getMortgageValue());
    }

    @Test
    public void testRent() {
        String[] data = {"N", "img", "100", "50"};
        UtilityCard utility = new UtilityCard(data, 10);

        assertEquals(4, utility.getRent(1, 1));
        assertEquals(16, utility.getRent(1, 4));
        assertEquals(48, utility.getRent(1, 12));
        assertEquals(10, utility.getRent(2, 1));
        assertEquals(0, utility.getRent(2, 0));
        assertEquals(0, utility.getRent(0, 1));
        assertEquals(0, utility.getRent(0, 400));

        assertEquals(0, utility.getRent(-150, 70));
        assertEquals(10, utility.getRent(700, 1));
        assertEquals(70, utility.getRent(700, 7));
    }

    @Test
    public void testMortgageAndInterest() {
        String[] data = {"N", "img", "200", "100"};
        UtilityCard utility = new UtilityCard(data, 10);

        assertTrue(!utility.isMortgaged());
        utility.setMortgage(true);
        assertTrue(utility.isMortgaged());
        utility.setMortgage(false);
        assertTrue(!utility.isMortgaged());

        assertEquals(200, utility.getCost());
        utility.setMortgage(true);
        assertEquals(110, utility.getCost());
        utility.increasePercentage();
        assertEquals(121, utility.getCost());
        utility.increasePercentage();
        assertEquals(133, utility.getCost());
        utility.setPercentageToDefault();
        assertEquals(110, utility.getCost());

        Random rand = new Random();
        int randNum = rand.nextInt(20) + 20;
        double actual = 110;
        for (int i = 0; i < randNum; i++) {
            utility.increasePercentage();
            actual = actual * 1.1;
        }
        assertEquals((int)actual, utility.getCost());
        utility.setMortgage(false);
        assertEquals(200, utility.getCost());
        utility.setMortgage(true);
        assertEquals(110, utility.getCost());

        utility = new UtilityCard(data, 17);
        assertEquals(200, utility.getCost());
        utility.setMortgage(true);
        assertEquals(117, utility.getCost());
        utility.increasePercentage();
        assertEquals(136, utility.getCost());
        utility.setMortgage(false);
        assertEquals(200, utility.getCost());
        utility.setMortgage(true);
        assertEquals(117, utility.getCost());
    }

}
