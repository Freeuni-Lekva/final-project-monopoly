package register;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;

public class AccountsDao {

    private Connection con;

    public AccountsDao() throws SQLException{
        BasicDataSource dSource = new BasicDataSource();
        dSource.setUrl("jdbc:mysql://localhost:3306/database_name_here");
        dSource.setUsername("user_name_here");
        dSource.setPassword("password_here");
        con = dSource.getConnection();
    }

    public boolean userExists(String user) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select * from Accounts where username = ?");
        statement.setString(1, user);
        ResultSet res = statement.executeQuery();
        if(res.next()) return true;
        return false;
    }

    public boolean accountCorrect(String user, String pass) throws SQLException{
        if(!userExists(user)) return false;
        PreparedStatement statement = con.prepareStatement("select password from Accounts where username = ?");
        statement.setString(1, user);
        ResultSet res = statement.executeQuery();
        if(res.next()) {
            String p = res.getString(1);
            if(p.equals(pass)) return true;
        }
        return false;
    }

    public boolean addAccount(String user, String password) throws SQLException {
        if(userExists(user)) return false;
        PreparedStatement statement = con.prepareStatement("insert into Accounts values(?, ?)");
        statement.setString(1, user);
        statement.setString(2, password);
        statement.execute();
        return true;
    }

}
