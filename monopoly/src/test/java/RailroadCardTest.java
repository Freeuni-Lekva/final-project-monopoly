import cardTypes.RailroadCard;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Random;

public class RailroadCardTest extends TestCase {

    @Test
    public void testBasics() {
        String[] data = {"NAME", "img-name.png", "10", "20", "30", "40", "50", "60"};
        RailroadCard railroad = new RailroadCard(data);

        assertEquals("NAME", railroad.getCardName());
        assertEquals("img-name.png", railroad.getCardImageName());
        assertEquals(10, railroad.getCost());
        assertEquals(60, railroad.getMortgageValue());
        assertEquals(66, railroad.getMortgageLiftCost());

        data[0] = "N";
        data[1] = "img";
        data[2] = "37";
        data[7] = "-90";
        railroad = new RailroadCard(data);
        assertEquals("N", railroad.getCardName());
        assertEquals("img", railroad.getCardImageName());
        assertEquals(37, railroad.getCost());
        assertEquals(-90, railroad.getMortgageValue());
        assertEquals(-99, railroad.getMortgageLiftCost());
    }

    @Test
    public void testRent() {
        String[] data = {"a", "b", "10", "20", "30", "40", "50", "60"};
        RailroadCard railroad = new RailroadCard(data);

        assertEquals(20, railroad.getRent(1));
        assertEquals(30, railroad.getRent(2));
        assertEquals(40, railroad.getRent(3));
        assertEquals(50, railroad.getRent(4));
        assertEquals(50, railroad.getRent(400));
        assertEquals(0, railroad.getRent(0));
        assertEquals(0, railroad.getRent(-100));
    }

    @Test
    public void testMortgage() {
        String[] data = {"N", "img", "200", "100", "200", "300", "400", "100"};
        RailroadCard railroad = new RailroadCard(data);

        assertTrue(!railroad.isMortgaged());
        railroad.setMortgage(true);
        assertTrue(railroad.isMortgaged());
        railroad.setMortgage(true);
        assertTrue(railroad.isMortgaged());
        railroad.setMortgage(false);
        assertTrue(!railroad.isMortgaged());
        railroad.setMortgage(false);
        assertTrue(!railroad.isMortgaged());
    }

}
