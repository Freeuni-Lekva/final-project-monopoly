package DAO;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class LeaderboardDAO {
    private Connection connection;

    public LeaderboardDAO() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/database_name_here");
        dataSource.setUsername("user_name_here");
        dataSource.setPassword("password_here");
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

    private void fullHashMap(String name, Map<String, Integer> mymap, int column, Vector<String> keys) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from Leaderboard order by "+ name + " desc;");
        ResultSet set = statement.executeQuery();
        int i = 0;
        while(i != 10 && set.next()) {
            mymap.put(set.getString(1), set.getInt(column));
            keys.add(set.getString(1));
            i++;
        }
    }

    public HashMap<String, Integer> topTenWins(Vector<String> keys) throws SQLException {
        Map<String, Integer> winningBoard = new HashMap<>();
        fullHashMap("timeswon", winningBoard, 3, keys);
        return (HashMap<String, Integer>) winningBoard;
    }

    public HashMap<String, Integer> topTenMoney(Vector<String> keys) throws SQLException {
        Map<String, Integer> moneyWon = new HashMap<>();
        fullHashMap("moneywon", moneyWon, 2, keys);
        return (HashMap<String, Integer>) moneyWon;
    }


}
