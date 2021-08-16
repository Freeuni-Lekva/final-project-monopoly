package invitations;

import DAO.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserBuilder {
    private UserDAO userDAO;
    private HashMap<String,User> instantiatedUsers = new HashMap<>();
    public UserBuilder(UserDAO userDAO){
        this.userDAO=userDAO;
    }
    public User getInstance(String username) throws Exception {
        if(instantiatedUsers.containsKey(username)){
            return instantiatedUsers.get(username);
        }
        if(!userDAO.isUser(username)) throw new Exception();
        Object[] data = userDAO.getUserData(username);
        User user = new User((String)data[0],this,userDAO);
        instantiatedUsers.put(username,user);
        user.addFriend((ArrayList<String>)data[1]);
        user.newRequest((ArrayList<String>)data[2]);
        //user.
        return user;
    }

    public void removeInstance(String username) throws Exception {
        if(!userDAO.isUser(username)) throw new Exception();
        instantiatedUsers.remove(username);
    }


}
