import DAO.CardsDAO;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class CardsDAOTest extends TestCase {

    @Test
    public void testGetPropertyCardsData() throws SQLException {
        CardsDAO cardsDAO = new CardsDAO();
        String[][] actual = {
                {"MEDITERRANEAN AVENUE", "mediterranean-avenue.png", "brown", "60", "2", "5", "10", "30", "90", "160",
                        "250", "30", "50", "50", "2"},
                {"BALTIC AVENUE", "baltic-avenue.png", "brown", "60", "4", "8", "20", "60", "180", "320", "450", "30",
                        "50", "50", "2"},
                {"ORIENTAL AVENUE", "oriental-avenue.png", "lightblue", "100", "6", "12", "30", "90", "270", "400",
                        "550", "50", "50", "50", "3"},
                {"VERMONT AVENUE", "vermont-avenue.png", "lightblue", "100", "6", "12", "30", "90", "270", "400",
                        "550", "50", "50", "50", "3"},
                {"CONNECTICUT AVENUE", "connecticut-avenue.png", "lightblue", "120", "8", "16", "40", "100", "300",
                        "450", "600", "60", "50", "50", "3"},
                {"ST. CHARLES PLACE", "st-charles-place.png", "pink", "140", "10", "20", "50", "150", "450", "625",
                        "750", "70", "100", "100", "3"},
                {"STATES AVENUE", "states-avenue.png", "pink", "140", "10", "20", "50", "150", "450", "625", "750",
                        "70", "100", "100", "3"},
                {"VIRGINIA AVENUE", "virginia-avenue.png", "pink", "160", "12", "24", "60", "180", "500", "700", "900",
                        "80", "100", "100", "3"},
                {"ST. JAMES PLACE", "st-james-place.png", "orange", "180", "14", "28", "70", "200", "550", "750",
                        "950", "90", "100", "100", "3"},
                {"TENNESSEE AVENUE", "tennessee-avenue.png", "orange", "180", "14", "28", "70", "200", "550", "750",
                        "950", "90", "100", "100", "3"},
                {"NEW YORK AVENUE", "new-york-avenue.png", "orange", "200", "16", "32", "80", "220", "600", "800",
                        "1000", "100", "100", "100", "3"},
                {"KENTUCKY AVENUE", "kentucky-avenue.png", "red", "220", "18", "36", "90", "250", "700", "875", "1050",
                        "110", "150", "150", "3"},
                {"INDIANA AVENUE", "indiana-avenue.png", "red", "220", "18", "36", "90", "250", "700", "875", "1050",
                        "110", "150", "150", "3"},
                {"ILLINOIS AVENUE", "illinois-avenue.png", "red", "240", "20", "40", "100", "300", "750", "925",
                        "1100", "120", "150", "150", "3"},
                {"ATLANTIC AVENUE", "atlantic-avenue.png", "yellow", "260", "22", "44", "110", "330", "800", "975",
                        "1150", "130", "150", "150", "3"},
                {"VENTNOR AVENUE", "ventnor-avenue.png", "yellow", "260", "22", "44", "110", "330", "800", "975",
                        "1150", "130", "150", "150", "3"},
                {"MARVIN GARDENS", "marvin-gardens.png", "yellow", "280", "24", "48", "120", "360", "850", "1025",
                        "1200", "140", "150", "150", "3"},
                {"PACIFIC AVENUE", "pacific-avenue.png", "green", "300", "26", "52", "130", "390", "900", "1100",
                        "1275", "150", "200", "200", "3"},
                {"NORTH CAROLINA AVENUE", "north-carolina-avenue.png", "green", "300", "26", "52", "130", "390", "900",
                        "1100", "1275", "150", "200", "200", "3"},
                {"PENNSYLVANIA AVENUE", "pennsylvania-avenue.png", "green", "320", "28", "56", "150", "450", "1000",
                        "1200", "1400", "160", "200", "200", "3"},
                {"PARK PLACE", "park-place.png", "blue", "350", "35", "70", "175", "500", "1100", "1300", "1500",
                        "175", "200", "200", "2"},
                {"BOARDWALK", "boardwalk.png", "blue", "400", "50", "100", "200", "600", "1400", "1700", "2000", "200",
                        "200", "200", "2"}
        };
        assertTrue(Arrays.deepEquals(actual, cardsDAO.getPropertyCardsData()));
    }

    @Test
    public void testGetRailroadCardsData() throws SQLException {
        CardsDAO cardsDAO = new CardsDAO();
        String[][] actual = {
                {"READING RAILROAD", "reading-railroad.png", "200", "25", "50", "100", "200", "100"},
                {"PENNSYLVANIA RAILROAD", "pennsylvania-railroad.png", "200", "25", "50", "100", "200", "100"},
                {"B. & O. RAILROAD", "b&o-railroad.png", "200", "25", "50", "100", "200", "100"},
                {"SHORT LINE", "short-line.png", "200", "25", "50", "100", "200", "100"}
        };
        assertTrue(Arrays.deepEquals(actual, cardsDAO.getRailroadCardsData()));
    }

    @Test
    public void testGetUtilityCardsData() throws SQLException {
        CardsDAO cardsDAO = new CardsDAO();
        String[][] actual = {
                {"ELECTRIC COMPANY", "electric-company.png", "150", "75"},
                {"WATER WORKS", "water-works.png", "150", "75"}
        };
        assertTrue(Arrays.deepEquals(actual, cardsDAO.getUtilityCardsData()));
    }

    @Test
    public void testGetChanceCards() throws SQLException {
        CardsDAO cardsDAO = new CardsDAO();
        Vector<String> actual = new Vector<>();
        actual.add("chance-advance-to-boardwalk.png");
        actual.add("chance-advance-to-go.png");
        actual.add("chance-advance-to-illinois.png");
        actual.add("chance-advance-to-st-charles.png");
        actual.add("chance-bank-pays-50.png");
        actual.add("chance-building-loan-matures.png");
        actual.add("chance-chairman.png");
        actual.add("chance-get-out-of-jail.png");
        actual.add("chance-go-back-3-spaces.png");
        actual.add("chance-go-to-jail.png");
        actual.add("chance-go-to-reading-railroad.png");
        actual.add("chance-nearest-railroad.png");
        actual.add("chance-nearest-railroad.png");
        actual.add("chance-nearest-utility.png");
        actual.add("chance-repairs.png");
        actual.add("chance-speeding-fine.png");
        assertTrue(actual.equals(cardsDAO.getChanceCards()));
    }

    @Test
    public void testGetCommunityChestCards() throws SQLException {
        CardsDAO cardsDAO = new CardsDAO();
        Vector<String> actual = new Vector<>();
        actual.add("chest-advance-to-go.png");
        actual.add("chest-bank-error.png");
        actual.add("chest-beauty-contest.png");
        actual.add("chest-birthday.png");
        actual.add("chest-consultancy-fee.png");
        actual.add("chest-doctors-fee.png");
        actual.add("chest-get-out-of-jail.png");
        actual.add("chest-go-to-jail.png");
        actual.add("chest-holiday-fund-matures.png");
        actual.add("chest-hospital-fees.png");
        actual.add("chest-income-tax-refund.png");
        actual.add("chest-inherit.png");
        actual.add("chest-life-insurance-matures.png");
        actual.add("chest-sale-of-stock.png");
        actual.add("chest-school-fees.png");
        actual.add("chest-street-repairs.png");
        assertTrue(actual.equals(cardsDAO.getCommunityChestCards()));
    }

}
