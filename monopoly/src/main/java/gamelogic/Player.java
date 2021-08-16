package gamelogic;


public class Player {
    private String username;
    private int player_id;
    private int money;
    private int getOutOfJailCards;
    private int turnsArrested;
    private int pairsRolled;
    private String event;
    private String eventMessage;
    private String eventImagePath;
    private int eventCost;
    private int tile_num;
    private boolean canMove;
    private boolean waiting;
    private int maxMoney;

    public Player(int player_id, String username) {
        this.username = username;
        this.player_id = player_id;
        money = 1500;
        maxMoney = money;
        getOutOfJailCards = 0;
        turnsArrested = 0;
        pairsRolled = 0;
        event = "none";
        eventMessage = "none";
        eventImagePath = "none";
        eventCost = 0;
        tile_num = 0;
        canMove = true;
        waiting = false;
    }

    public int getPlayerId() {
        return player_id;
    }

    public void incrementPairsRolled() {
        pairsRolled++;
        if (pairsRolled >= 3) turnsArrested++;
    }

    public int getMaxMoney() {
        return maxMoney;
    }

    public void setPairsRolledToZero() {
        pairsRolled = 0;
    }

    public int getPairsRolled() {
        return pairsRolled;
    }

    public void addGetOutOfJailCard() {
        getOutOfJailCards++;
    }

    public int getGetOutOfJailCards() {
        return getOutOfJailCards;
    }

    public void takeGetOutOfJailCard() {
        getOutOfJailCards--;
    }

    public void setWaiting(boolean g) {
        waiting = g;
    }

    public boolean getWaiting() {
        return waiting;
    }

    public void setCanMove(boolean g) {
        canMove = g;
    }

    public boolean playerCanMove(){
        return canMove;
    }

    public void setTile(int tile) {
        tile_num = tile;
    }

    public int getTile() {
        return tile_num;
    }

    public void addMoney(int money) {
        this.money += money;
        if (this.money > maxMoney) maxMoney = this.money;
    }

    public int getMoney() {
        return money;
    }

    public void incrementTurnsArrested() {
        turnsArrested++;
    }

    public void freeFromJail() {
        turnsArrested = 0;
    }

    public int getTurnsArrested() {
        return turnsArrested;
    }

    public void setEvent(String eventName) {
        event = eventName;
    }

    public String getEvent() {
        return event;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public String getEventMessage() {return eventMessage;}

    public void setEventImagePath(String path) {
        eventImagePath = path;
    }

    public String getEventImagePath() {
        return eventImagePath;
    }

    public String getUsername() {return username;}

    public void setEventCost(int eventCost) {
        this.eventCost = eventCost;
    }

    public int getEventCost() {
        return eventCost;
    }

}