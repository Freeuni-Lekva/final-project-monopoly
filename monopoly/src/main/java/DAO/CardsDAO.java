package DAO;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;
import java.util.Vector;

public class CardsDAO {

    private Connection connection;
    private static final int PROPERTY_CARDS_DATA_SIZE = 15;
    private static final int RAILROAD_CARDS_DATA_SIZE = 8;
    private static final int UTILITY_CARDS_DATA_SIZE = 4;

    public CardsDAO() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        /* !!!!!!!!!!! Add your database name, your user name and your password here !!!!!!! */
        dataSource.setUrl("jdbc:mysql://localhost:3306/database_name_here");
        dataSource.setUsername("user_name_here");
        dataSource.setPassword("password_here");
        connection = dataSource.getConnection();
    }

    /* Returns all information about all of the Property Cards in  the database as a 2d String array */
    public String[][] getPropertyCardsData() throws SQLException {
        String[][] data;
        Statement statement = connection.createStatement();
        ResultSet rowCount = statement.executeQuery("SELECT COUNT(*) FROM property_cards");
        rowCount.next();
        int rows = rowCount.getInt(1);
        data = new String[rows][PROPERTY_CARDS_DATA_SIZE];
        ResultSet res = statement.executeQuery("SELECT * FROM property_cards");
        while (res.next()) {
            int currRow = res.getRow() - 1;
            for (int i = 0; i < data[currRow].length - 1; i++) {
                data[currRow][i] = res.getString(i + 1);
            }
            data[currRow][data[currRow].length - 1] = String.valueOf(getColorSetSize(data[currRow][2]));
        }
        return data;
    }

    private int getColorSetSize(String color) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM property_cards " +
                "WHERE color = ?");
        statement.setString(1, color);
        ResultSet res = statement.executeQuery();
        res.next();
        return res.getInt(1);
    }

    /* Returns all information about all of the Railroad Cards in  the database as a 2d String array */
    public String[][] getRailroadCardsData() throws SQLException {
        String[][] data;
        Statement statement = connection.createStatement();
        ResultSet rowCount = statement.executeQuery("SELECT COUNT(*) FROM railroad_cards");
        rowCount.next();
        int rows = rowCount.getInt(1);
        data = new String[rows][RAILROAD_CARDS_DATA_SIZE];
        ResultSet res = statement.executeQuery("SELECT * FROM railroad_cards");
        while (res.next()) {
            int currRow = res.getRow() - 1;
            for (int i = 0; i < data[currRow].length; i++) {
                data[currRow][i] = res.getString(i + 1);
            }
        }
        return data;
    }

    /* Returns all information about all of the Utility Cards in  the database as a 2d String array */
    public String[][] getUtilityCardsData() throws SQLException {
        String[][] data;
        Statement statement = connection.createStatement();
        ResultSet rowCount = statement.executeQuery("SELECT COUNT(*) FROM utility_cards");
        rowCount.next();
        int rows = rowCount.getInt(1);
        data = new String[rows][UTILITY_CARDS_DATA_SIZE];
        ResultSet res = statement.executeQuery("SELECT * FROM utility_cards");
        while (res.next()) {
            int currRow = res.getRow() - 1;
            for (int i = 0; i < data[currRow].length; i++) {
                data[currRow][i] = res.getString(i + 1);
            }
        }
        return data;
    }

    /* Returns a list of the names of the Chance card images */
    public Vector<String> getChanceCards() throws SQLException {
        Vector<String> cards = new Vector<>();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT card_image_name FROM random_event_cards WHERE " +
                "card_type = \'chance\'");
        while (res.next()) cards.add(res.getString(1));
        return cards;
    }

    /* Returns a list of the names of the Community Chest card images */
    public Vector<String> getCommunityChestCards() throws SQLException {
        Vector<String> cards = new Vector<>();
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT card_image_name FROM random_event_cards WHERE " +
                "card_type = \'community-chest\'");
        while (res.next()) cards.add(res.getString(1));
        return cards;
    }

}
