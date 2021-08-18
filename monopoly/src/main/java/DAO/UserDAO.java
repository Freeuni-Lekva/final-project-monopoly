package DAO;

import jdk.internal.net.http.common.Pair;
import org.apache.commons.dbcp.BasicDataSource;
import register.AccountsDao;


import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    BasicDataSource dataSource;
    Connection connection;
    AccountsDao accountsDao;
    public UserDAO(String url, String username, String password) throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        connection = dataSource.getConnection();

        accountsDao = new AccountsDao();
    }

    public Object[] getUserData(String username) throws Exception {
        if(!this.isUser(username)) throw new Exception();

        Object[] ret = new Object[5];
        ret[0] = username;

        ret[1] = new ArrayList<String>();
        PreparedStatement statement = connection.prepareStatement("select username2 from friendPairs where username1 = ?");
        statement.setString(1,username);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            ((ArrayList)ret[1]).add(rs.getString("username2"));
        }

        ret[2] = new ArrayList<String>();
        PreparedStatement statement1 = connection.prepareStatement("select username2 from requestPairs where username1 = ?");
        statement1.setString(1,username);
        ResultSet rs1 = statement1.executeQuery();
        while (rs1.next()) {
            ((ArrayList)ret[2]).add(rs1.getString("username2"));
        }

        ret[3] = new ArrayList<String>();
        PreparedStatement statement2 = connection.prepareStatement("select lobby from invitations where username = ?");
        statement2.setString(1,username);
        ResultSet rs2 = statement2.executeQuery();
        while (rs2.next()) {
            ((ArrayList)ret[3]).add(rs2.getString("lobby"));
        }

        ret[4] = new ArrayList<String>();
        PreparedStatement statement3 = connection.prepareStatement("select inviter from invitations where username = ?");
        statement3.setString(1,username);
        ResultSet rs3 = statement2.executeQuery();
        while (rs3.next()) {
            ((ArrayList)ret[4]).add(rs3.getString(1));
        }

        return ret;
    }

    public void addFriendPair(String username1,String username2) throws Exception {
        if(!this.isUser(username1) || !this.isUser(username2)) throw new NullPointerException();
        PreparedStatement check = connection.prepareStatement("select * from friendPairs where username1 = ? AND username2 = ?");
        check.setString(1,username1);
        check.setString(2,username2);
        ResultSet rs = check.executeQuery();
        if(rs.next()) return;

        PreparedStatement query = connection.prepareStatement("insert into friendPairs (username1,username2) values (?,?)");
        query.setString(1,username1);
        query.setString(2,username2);
        query.executeUpdate();

        PreparedStatement query1 = connection.prepareStatement("insert into friendPairs (username1,username2) values (?,?)");
        query1.setString(1,username2);
        query1.setString(2,username1);
        query1.executeUpdate();
    }

    public void addRequestPair(String username1,String username2) throws Exception {
        if(!this.isUser(username1) || !this.isUser(username2)) throw new NullPointerException();
        PreparedStatement check = connection.prepareStatement("select * from requestPairs where username1 = ? AND username2 = ?");
        check.setString(1,username1);
        check.setString(2,username2);
        ResultSet rs = check.executeQuery();
        if(rs.next()) return;

        PreparedStatement query = connection.prepareStatement("insert into requestPairs (username1,username2) values (?,?)");
        query.setString(1,username1);
        query.setString(2,username2);
        query.executeUpdate();

        PreparedStatement query1 = connection.prepareStatement("insert into requestPairs (username1,username2) values (?,?)");
        query1.setString(1,username2);
        query1.setString(2,username1);
        query1.executeUpdate();
    }

    public void removeRequestPair(String username1,String username2) throws Exception {
        if(!this.isUser(username1) || !this.isUser(username2)) throw new NullPointerException();
        PreparedStatement statement = connection.prepareStatement("delete from requestPairs where username1 = ? AND username2 = ?");
        statement.setString(1,username1);
        statement.setString(2,username2);
    }

    public boolean isUser(String username) throws Exception {
        return accountsDao.userExists(username);
    }

    public void addInvitation(String lobby,String username, String inviter) throws Exception {
        if(!this.isUser(username)) throw new NullPointerException();
        PreparedStatement statement = connection.prepareStatement("insert into invitations (lobby, username, inviter)" +
                " values (?,?,?)");
        statement.setString(1,lobby);
        statement.setString(2,username);
        statement.setString(3, inviter);
        statement.executeUpdate();
    }

    public void removeInvitation(String lobby) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from invitations where lobby = ?");
        statement.setString(1, lobby);
        statement.execute();
    }
}
