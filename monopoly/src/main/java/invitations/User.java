package invitations;

import DAO.UserDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class User {
    public  String username;
    public UserDAO userDAO;
    private UserBuilder userBuilder;

    public User(String username,UserBuilder userBuilder,UserDAO userDAO){
        this.username = username;
        this.userBuilder = userBuilder;
        this.userDAO=userDAO;
    }

    public ArrayList<String> getFriends() throws Exception{
        return (ArrayList<String>) userDAO.getUserData(username)[1];
    }

    public void addFriend(String user){
        try {
            if (userDAO.isUser(user) && !userDAO.friendsPairExists(username, user)) {
                userDAO.addFriendPair(this.username, user);
                userBuilder.getInstance(user).addFriend(username);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public void addFriend(Collection<String> users){
        for(String user: users) {
           this.addFriend(user);
        }
    }


    public void newRequest(String user){
        try {

            userDAO.addRequestPair(this.username,user);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void newRequest(Collection<String> users){
        for (String user : users) {
            newRequest(user);
        }
    }

    public void acceptRequest(String user){
        try {
            this.addFriend(user);
            userDAO.removeRequestPair(username, user);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    public void declineRequest(String user){
        try {
            userDAO.removeRequestPair(username, user);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void  addInvitation(String lobby, String inviter) throws Exception {
       userDAO.addInvitation(lobby, username, inviter);
    }

    public void  removeInvitation(String lobby) throws SQLException {
        userDAO.removeInvitation(lobby);
    }

    public ArrayList<String> getInvitationCodes() throws Exception {
        return (ArrayList<String>) userDAO.getUserData(username)[3];
    }

    public ArrayList<String> getInviters() throws Exception {
        return (ArrayList<String>) userDAO.getUserData(username)[4];
    }

    public ArrayList<String> getFriendRequests() throws Exception {
        return (ArrayList<String>) userDAO.getUserData(username)[2];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }
}
