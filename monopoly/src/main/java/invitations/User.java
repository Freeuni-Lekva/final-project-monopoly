package invitations;

import DAO.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class User {
    public  String username;
    public UserDAO userDAO;
    private UserBuilder userBuilder;
    private ArrayList<String> friends;
    private ArrayList<String> friendRequests;

    public User(String username,UserBuilder userBuilder,UserDAO userDAO){
        this.username = username;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.userBuilder = userBuilder;
        this.userDAO=userDAO;
    }

    public ArrayList<String> getFriends(){
        return friends;
    }

    public void addFriend(String user){
        try {
            if (userDAO.isUser(user)) {
                userDAO.addFriendPair(this.username, user);
                if (!friends.contains(user)) {
                    friends.add(user);
                }
                if (!userBuilder.getInstance(user).friends.contains(this.username)) {
                    userBuilder.getInstance(user).friends.add(this.username);
                }
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

    public void removeFriend(String user){
        try {
            friends.remove(user);
            userBuilder.getInstance(user).friends.remove(this.username);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void newRequest(String user){
        try {
            friendRequests.add(user);
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
            friendRequests.remove(user);
            userDAO.removeRequestPair(this.username,user);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void declineRequest(String user){
        try {
            friendRequests.remove(user);
            userDAO.removeRequestPair(this.username,user);
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

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }
}
