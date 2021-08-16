package invitations;

import DAO.UserDAO;

import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class User {
    public  String username;
    public UserDAO userDAO;
    private UserBuilder userBuilder;
    private ArrayList<String> friends;
    private ArrayList<String> friendRequests;
    private ArrayList<String> invitations;

    public User(String username, Collection<String> friends, Collection<String> friendRequests,Collection<String> invitations,UserBuilder userBuilder,UserDAO userDAO){
        this.username = username;
        this.friends = new ArrayList<>(friends);
        this.friendRequests = new ArrayList<>(friendRequests);
        this.invitations = new ArrayList<>(invitations);
        this.userBuilder = userBuilder;
        this.userDAO=userDAO;
    }

    public User(String username,UserBuilder userBuilder,UserDAO userDAO){
        this.username = username;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.invitations = new ArrayList<>();
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

    public void  addInvitation(String lobby){
       invitations.add(lobby);
    }

    public void  removeInvitation(String lobby){
        invitations.remove(lobby);
    }

    public ArrayList<String> getInvitations(){
        return invitations;
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
