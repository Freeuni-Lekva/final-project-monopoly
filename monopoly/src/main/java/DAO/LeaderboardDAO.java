package DAO;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardDAO {
    private Connection connection;

    public LeaderboardDAO() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/accounts");
        dataSource.setUsername("root");
        dataSource.setPassword("rootroot");
        connection = dataSource.getConnection();
    }

    public void moneyWon(String user, int money) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from Leaderboard where username = ?;");
        statement.setString(1, user);
        ResultSet set = statement.executeQuery();
        if(set.next()) {
            int cash = set.getInt(2);
            if(money > cash) {
                PreparedStatement st = connection.prepareStatement("update Leaderboard set moneyWon = ? where username = ?;");
                st.setInt(1, money);
                st.setString(2, user);
                st.execute();
            }
        } else {
            PreparedStatement st = connection.prepareStatement("insert into Leaderboard values(?, ?, 0)");
            st.setString(1, user);
            st.setInt(2, money);
            st.execute();
        }
    }

    public void userWon(String user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from Leaderboard where username = ?;");
        statement.setString(1, user);
        ResultSet set = statement.executeQuery();
        if (set.next()) {
            PreparedStatement st = connection.prepareStatement("update Leaderboard set timeswon = timeswon + 1 where username = ?;");
            st.setString(1, user);
            st.execute();
        } else {
            PreparedStatement st = connection.prepareStatement("insert into Leaderboard values(?, 0, ?)");
            st.setString(1, user);
            st.setInt(2, 1);
            st.execute();
        }
    }

    public HashMap<String, Integer> topTenWins() {
        Map<String, Integer> winningBoard = new HashMap<>();
        return (HashMap<String, Integer>) winningBoard;
    }

    public HashMap<String, Integer> topTenMoney() {
        Map<String, Integer> moneyWon = new HashMap<>();
        return (HashMap<String, Integer>) moneyWon;
    }

}
