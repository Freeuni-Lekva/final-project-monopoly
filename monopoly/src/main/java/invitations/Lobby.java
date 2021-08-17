package invitations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Lobby {
    private UserBuilder userBuilder;
    private String host;
    private String  key;
    private ArrayList<String> curPlayers;
    private ArrayList<String> invitedPlayers;
    public Lobby(String host, Collection<String> invitedPlayers,UserBuilder userBuilder){
        this.host = host;
        this.userBuilder = userBuilder;
        this.invitedPlayers = new ArrayList<>(invitedPlayers);
        this.curPlayers = new ArrayList<>();
        key = UUID.randomUUID().toString();
        for (String user : invitedPlayers) {
            try {
                System.out.println(userBuilder.getInstance(user) + "user iiisss" + user);
                userBuilder.getInstance(user).addInvitation(key);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
    public int getNumPlayers(){
        return curPlayers.size();
    }

    public boolean isHost(String username){
        return username.equals(host);
    }
    public void addPlayer(String username){
        curPlayers.add(username);
    }

    public ArrayList<String> getCurPlayers(){
        return curPlayers;
    }

    public void removePlayer(String username){
        curPlayers.remove(username);
    }

    public boolean containsUser(String username){
        if(!host.equals(username) && !invitedPlayers.contains(username)) return false;
        return true;
    }

    public String getKey(){
        return key;
    }

}
