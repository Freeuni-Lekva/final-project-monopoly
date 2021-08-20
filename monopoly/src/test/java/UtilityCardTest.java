import cardTypes.UtilityCard;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Random;

public class UtilityCardTest extends TestCase {

    @Test
    public void testBasics() {
        String[] data = {"NAME", "img-name.png", "200", "100"};
        UtilityCard utility = new UtilityCard(data);

        assertEquals("NAME", utility.getCardName());
        assertEquals("img-name.png", utility.getCardImageName());
        assertEquals(200, utility.getCost());
        assertEquals(100, utility.getMortgageValue());

        data[0] = "DIFFERENT NAME";
        data[1] = "other-img.png";
        data[2] = "1234567";
        data[3] = "0";
        utility = new UtilityCard(data);
        assertEquals("DIFFERENT NAME", utility.getCardName());
        assertEquals("other-img.png", utility.getCardImageName());
        assertEquals(1234567, utility.getCost());
        assertEquals(0, utility.getMortgageValue());
    }

    @Test
    public void testRent() {
        String[] data = {"N", "img", "100", "50"};
        UtilityCard utility = new UtilityCard(data);

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
        UtilityCard utility = new UtilityCard(data);

        assertTrue(!utility.isMortgaged());
        utility.setMortgage(true);
        assertTrue(utility.isMortgaged());
        utility.setMortgage(true);
        assertTrue(utility.isMortgaged());
        utility.setMortgage(false);
        assertTrue(!utility.isMortgaged());
        utility.setMortgage(false);
        assertTrue(!utility.isMortgaged());
    }

}
