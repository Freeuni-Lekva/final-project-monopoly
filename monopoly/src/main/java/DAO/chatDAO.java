package DAO;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class chatDAO {
    private String myName;
    private String user;
    Connection connection;


    public chatDAO(String name, String user) throws SQLException {
        myName = name;
        this.user = user;
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/accounts");
        dataSource.setUsername("root");
        dataSource.setPassword("rootroot");
        connection = dataSource.getConnection();
    }

    public void getMessages(Vector<String> senders, Vector<String> messages) throws SQLException {
        HashMap<String, String> map = new HashMap<>();

        PreparedStatement statement = connection.prepareStatement("select * from chat where (sender = ? AND receiver = ?)OR (receiver = ? AND sender = ?) order by sendtime desc;");
        PreparedStatement statement2 = connection.prepareStatement("update chat set seen = 1 where receiver = ?");
        statement2.setString(1, myName);
        statement2.execute();
        statement.setString(1, myName);
        statement.setString(2, user);
        statement.setString(3, myName);
        statement.setString(4, user);
        ResultSet set = statement.executeQuery();
        int k = 0;
        while(k != 9 && set.next()) {
            String sendBy = (String)set.getString(1);
            String message = (String)set.getString(3);
            senders.add(sendBy);
            messages.add(message);
            k++;
        }
    }

    public void sendMessage(String text) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into chat(sender, receiver, message)  values(?, ?, ?)");
        statement.setString(1, myName);
        statement.setString(2, user);
        statement.setString(3, text);
        statement.execute();
    }

    public Vector<String> getInbox(Vector<Integer> seen) throws SQLException {
        Vector<String> nicknames = new Vector<>();
        PreparedStatement statement = connection.prepareStatement("select sender from chat where receiver = ? group by sender ORDER by max(sendtime)");
        statement.setString(1, myName);
        ResultSet set = statement.executeQuery();
        int k = 0;
        while(k != 10 && set.next()) {
            String name = set.getString(1);
            PreparedStatement statement1 = connection.prepareStatement("select seen from chat where receiver = ? AND sender = ? order by sendtime desc;");
            statement1.setString(2, name);
            statement1.setString(1,myName);
            ResultSet res = statement1.executeQuery();
            res.next();
            int didSee = res.getInt(1);
            nicknames.add(name);
            seen.add(didSee);
        }
        return nicknames;
    }

}
