package gamelogic;

import DAO.CardsDAO;
import cardTypes.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static java.lang.Math.max;


public class Room {
    private Map<String, Integer> cardOwners;
    private Player[] players;
    private String chanceCardStr;
    private static final String[] TILES = {
        "GO", "P MEDITERRANEAN AVENUE", "COMMUNITY CHEST", "P BALTIC AVENUE", "INCOME TAX", "R READING RAILROAD",
            "P ORIENTAL AVENUE",
            "CHANCE", "P VERMONT AVENUE", "P CONNECTICUT AVENUE", "JAIL", "P ST. CHARLES PLACE", "U ELECTRIC COMPANY",
            "P STATES AVENUE", "P VIRGINIA AVENUE", "R PENNSYLVANIA RAILROAD", "P ST. JAMES PLACE", "COMMUNITY CHEST",
            "P TENNESSEE AVENUE", "P NEW YORK AVENUE", "FREE PARKING", "P KENTUCKY AVENUE", "CHANCE", "P INDIANA AVENUE",
            "P ILLINOIS AVENUE", "R B. & O. RAILROAD", "P ATLANTIC AVENUE", "P VENTNOR AVENUE", "U WATER WORKS",
            "P MARVIN GARDENS", "GO TO JAIL", "P PACIFIC AVENUE", "P NORTH CAROLINA AVENUE", "COMMUNITY CHEST",
            "P PENNSYLVANIA AVENUE", "R SHORT LINE", "CHANCE", "P PARK PLACE", "LUXURY TAX", "P BOARDWALK"
    };
    private Map<String, Card> cards;
    private String room_id;
    private RandomEventCardPile chance;
    private RandomEventCardPile communityChest;
    private static final int MAX_TILE_NUM = 40;
    private int current_player_id;
    private int firstDie;
    private int secondDie;

    public Room(int playerNum, String[] usernames){
        cardOwners = new HashMap<>();
        current_player_id = 0;
        players = new Player[playerNum];
        cards = new HashMap<>();
        for(int i = 0; i < playerNum; i++) {
            Player newPlayer = new Player(i, usernames[i]);
            players[i] = newPlayer;
        }
        try {
            CardsDAO cDao = new CardsDAO();
            String[][] propertyCardsData = cDao.getPropertyCardsData();
            for(int i = 0; i < propertyCardsData.length; i++) {
                PropertyCard prCard = new PropertyCard(propertyCardsData[i], 10);
                cardOwners.put(prCard.getCardName(), -1);
                cards.put(prCard.getCardName(), prCard);
            }
            String[][] railroadCardsData = cDao.getRailroadCardsData();
            for(int i = 0; i < railroadCardsData.length; i++) {
                RailroadCard rCard = new RailroadCard(railroadCardsData[i], 10);
                cards.put(rCard.getCardName(), rCard);
                cardOwners.put(rCard.getCardName(), -1);
            }
            String[][] utilityCardsData = cDao.getUtilityCardsData();
            for(int i = 0; i < utilityCardsData.length; i++) {
                UtilityCard uCard = new UtilityCard(utilityCardsData[i], 10);
                cards.put(uCard.getCardName(), uCard);
                cardOwners.put(uCard.getCardName(), -1);
            }
            Vector<String> chanceCards = cDao.getChanceCards();
            Vector<String> communityChestCards = cDao.getCommunityChestCards();
            chance = new RandomEventCardPile(chanceCards, "chance-get-out-of-jail.png");
            communityChest = new RandomEventCardPile(communityChestCards, "chest-get-out-of-jail.png");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void move(boolean passGo, int tileNum, int railroadMultiplier, int utilityMultiplier) {
        Player player = players[current_player_id];
        if(passGo && (tileNum + player.getTile()) >= MAX_TILE_NUM) {
            player.addMoney(200);
        }
        player.setTile((tileNum+player.getTile())%MAX_TILE_NUM);
        if(firstDie != secondDie) player.setCanMove(false);
        performTileAction(player.getTile(), railroadMultiplier, utilityMultiplier);
    }

    private void performTileAction(int tile, int railroadMultiplier, int utilityMultiplier) {
        Player player = players[current_player_id];
        if(TILES[tile].equals("CHANCE")) {
            //your code here
        } else if(TILES[tile].equals("COMMUNITY CHEST")) {
            //your code here
        } else if(TILES[tile].equals("GO TO JAIL")) {
            player.setTile(10);
            player.incrementTurnsArrested();
        } else if(TILES[tile].equals("INCOME TAX")) {
            player.setEvent("paying-event");
            player.setEventMessage("Income tax, pay 200$");
            player.setEventCost(200);
            player.setEventImagePath("none");
        } else if(TILES[tile].equals("LUXURY TAX")) {
            player.setEvent("paying-event");
            player.setEventMessage("Luxury tax, pay 100$");
            player.setEventCost(100);
            player.setEventImagePath("none");
        } else if(TILES[tile].substring(0,2).equals("P ")) {
            String cardName = TILES[tile].substring(2);
            PropertyCard prCard = (PropertyCard)cards.get(cardName);
            if(cardOwners.get(cardName) == -1) {
                player.setEvent("unoccupied-property");
                player.setEventMessage("You can purchase this property");
                player.setEventCost(prCard.getCost());
                player.setEventImagePath("../property-cards/" + prCard.getCardImageName());
            }
            if(cardOwners.get(cardName) != current_player_id && !prCard.isMortgaged()) {
                int owner_id = cardOwners.get(cardName);
                player.setEvent("paying-event");
                int sameColorCount = 0;
                String cardColor = prCard.getCardColor();
                for(String card: cardOwners.keySet()) {
                    if(cardOwners.get(card) == owner_id && cards.get(cardOwners.get(card)) instanceof PropertyCard &&
                            ((PropertyCard)cards.get(cardOwners.get(card))).getCardColor().equals(cardColor)) {
                        sameColorCount++;
                    }
                }
                player.setEventMessage(players[owner_id].getUsername() + " owns this property." +
                        " pay " + prCard.getRent(sameColorCount));
                player.setEventImagePath("../property-cards/" + prCard.getCardImageName());
                player.setEventCost(prCard.getRent(sameColorCount));
            }
        }  else if(TILES[tile].substring(0, 2).equals("R ")) {
            String cardName = TILES[tile].substring(2);
            RailroadCard rCard = (RailroadCard)cards.get(cardName);
            if(cardOwners.get(cardName) == -1) {
                player.setEvent("unoccupied-property");
                player.setEventMessage("You can purchase this railroad");
                player.setEventCost(rCard.getCost());
                player.setEventImagePath("../railroad-cards/" + rCard.getCardImageName());
            }
            if(cardOwners.get(cardName) != current_player_id && !rCard.isMortgaged()) {
                int owner_id = cardOwners.get(cardName);
                player.setEvent("paying-event");
                int railroadCount = 0;
                for(String card: cardOwners.keySet()) {
                    if(cardOwners.get(card) == owner_id && cards.get(cardOwners.get(card)) instanceof RailroadCard) {
                        railroadCount++;
                    }
                }
                player.setEventMessage(players[owner_id].getUsername() + " owns this railroad." +
                        " pay " + rCard.getRent(railroadCount));
                player.setEventImagePath("../railroad-cards/" + rCard.getCardImageName());
                player.setEventCost(rCard.getRent(railroadCount)*railroadMultiplier);
            }
        } else if(TILES[tile].substring(0,2).equals("U ")) {
            String cardName = TILES[tile].substring(2);
            UtilityCard uCard = (UtilityCard)cards.get(cardName);
            if(cardOwners.get(cardName) == -1) {
                player.setEvent("unoccupied-property");
                player.setEventMessage("You can purchase this utility");
                player.setEventCost(uCard.getCost());
                player.setEventImagePath("../utility-cards/" + uCard.getCardImageName());
            }
            if(cardOwners.get(cardName) != current_player_id && !uCard.isMortgaged()) {
                int owner_id = cardOwners.get(cardName);
                player.setEvent("paying-event");
                int utilityCount = 0;
                for(String card: cardOwners.keySet()) {
                    if(cardOwners.get(card) == owner_id && cards.get(cardOwners.get(card)) instanceof UtilityCard) {
                        utilityCount++;
                    }
                }
                player.setEventMessage(players[owner_id].getUsername() + " owns this railroad." +
                        " pay " + uCard.getRent(utilityCount, firstDie + secondDie));
                player.setEventImagePath("../railroad-cards/" + uCard.getCardImageName());
                player.setEventCost(uCard.getRent(max(utilityCount,utilityMultiplier), firstDie + secondDie));
            }
        }
    }

    private void nearestRailroad(){
        Player player = players[current_player_id];
        int tile = player.getTile();
        int nearestRailroadTile;
        if(tile >= 35) {
            nearestRailroadTile = 5;
        } else if(tile >= 25) {
            nearestRailroadTile = 35;
        } else if(tile >= 15) {
            nearestRailroadTile = 25;
        } else if(tile >= 5) {
            nearestRailroadTile = 15;
        } else {
            nearestRailroadTile = 5;
        }
        move(true, nearestRailroadTile, 2, 1);
    }

    private void nearestUtility(){
        Player player = players[current_player_id];
        int tile = player.getTile();
        int nearestUtilityTile;
        if(tile >= 28) {
            nearestUtilityTile = 12;
        } else if(tile >= 12) {
            nearestUtilityTile = 28;
        } else nearestUtilityTile = 12;
        move(true, nearestUtilityTile, 1, 10);
    }

    private void advanceToGo() {
        move(true, 0, 1, 1);
    }

    private void advanceToBoardwalk() {
        move(true, 39, 1, 1);
    }

    private void advanceToIllinois() {
        move(true, 24, 1, 1);
    }

    private void advanceToStCharles() {
        move(true,11, 1, 1);
    }

    private void bankPays() {
        Player player = players[current_player_id];
        player.addMoney(50);
    }

    private void repairs() {
        Player player = players[current_player_id];
        player.addMoney(-player.getEventCost());
    }

    private void speedingFine() {
        Player player = players[current_player_id];
        player.addMoney(-15);
    }

    private void buildingLoanMatures() {
        Player player = players[current_player_id];
        player.addMoney(150);
    }

    private void chairman() {
        Player player = players[current_player_id];
        for(int i = 0; i < players.length; i++) {
            if(players[i] != null && i != current_player_id) {
                player.addMoney(-50);
                players[i].addMoney(50);
            }
        }
    }

    private void getOutOfJail() {
        Player player = players[current_player_id];
        player.addGetOutOfJailCard();
    }

    private void goBack() {
        move(false, TILES.length-3+players[current_player_id].getTile(), 1, 1);
    }

    private void goToJail() {
        move(false, 10, 1, 1);
    }

    private void goToReadingRailroad() {
        move(true, 5, 1, 1);
    }



    public void performChanceAction() {
        switch(chanceCardStr) {
            case "chance-advance-to-boardwalk.png":
                advanceToBoardwalk();
                break;
            case "chance-advance-to-go.png":
                advanceToGo();
                break;
            case "chance-advance-to-illinois.png":
                advanceToIllinois();
                break;
            case "chance-advance-to-st-charles.png":
                advanceToStCharles();
                break;
            case "chance-bank-pays-50.png":
                bankPays();
                break;
            case "chance-building-loan-matures.png":
                buildingLoanMatures();
                break;
            case "chance-get-out-of-jail.png":
                getOutOfJail();
                break;
            case "chance-go-back-3-spaces.png":
                goBack();
                break;
            case "chance-go-to-jail.png":
                goToJail();
                break;
            case "chance-nearest-railroad.png":
                nearestRailroad();
                break;
            case "chance-nearest-utility.png":
                nearestUtility();
                break;
            case "chance-go-to-reading-railroad.png":
                goToReadingRailroad();
                break;
            case "chance-repairs.png":
                repairs();
                break;
            case "chance-speeding-fine.png":
                speedingFine();
                break;
            case "chance-chairman.png":
                chairman();
                break;
        }
    }

    public void setupChanceEvent(){
        Player player = players[current_player_id];
        chanceCardStr = chance.getRandomCard();
        player.setEventMessage("You got a chance card!");
        player.setEventImagePath("../chance-cards/" + chanceCardStr);
        switch(chanceCardStr) {
            case "chance-advance-to-boardwalk.png":
            case "chance-advance-to-go.png":
            case "chance-advance-to-illinois.png":
            case "chance-advance-to-st-charles.png":
            case "chance-bank-pays-50.png":
            case "chance-building-loan-matures.png":
            case "chance-get-out-of-jail.png":
            case "chance-go-back-3-spaces.png":
            case "chance-go-to-jail.png":
            case "chance-nearest-railroad.png":
            case "chance-nearest-utility.png":
            case "chance-go-to-reading-railroad.png":
                player.setEvent("non-interactive-event");
                break;
            case "chance-repairs.png":
                player.setEvent("paying-event");
                int payment = 0;
                for (String cardName : cards.keySet()) {
                    if (cardOwners.get(cardName) == current_player_id &&
                            cards.get(cardName) instanceof PropertyCard) {
                        if (((PropertyCard)cards.get(cardName)).getNumHouses() >= 5) {
                            payment += 100;
                        } else if (((PropertyCard)cards.get(cardName)).getNumHouses() >= 1) {
                            payment += 25;
                        }
                    }
                }
                player.setEventCost(payment);
                break;
            case "chance-speeding-fine.png":
                player.setEvent("paying-event");
                player.setEventCost(15);
                break;
            case "chance-chairman.png":
                int pMent = 0;
                for(int i = 0; i < players.length; i++) {
                    if(players[i] != null && i != current_player_id) {
                        pMent += 50;
                    }
                }
                player.setEvent("paying-event");
                player.setEventCost(pMent);
        }
    }


    private class Trade {
        private String[] offeredCards;
        private String[] demandedCards;
        private int demandedMoney;
        private int offeredMoney;
        private int offeredGetOutOfJailCards;
        private int demandedGetOutOfJailCards;
        private int trader_id;

        public void setDemandedMoney(int moneyCount) {
            demandedMoney = moneyCount;
        }

        public int getDemandedMoney() {
            return demandedMoney;
        }

        public void setDemandedGetOutOfJailCards(int cardsCount) {
            demandedGetOutOfJailCards = cardsCount;
        }

        public int getDemandedGetOutOfJailCards() {
            return demandedGetOutOfJailCards;
        }

        public void setDemandedCards(String[] crd) {
            demandedCards = crd;
        }

        public String[] getDemandedCards() {
            return demandedCards;
        }

        public String[] getOfferedCards() {
            return offeredCards;
        }

        public int getTraderId() {
            return trader_id;
        }

        public int getOfferedMoney() {
            return offeredMoney;
        }

        public int getOfferedGetOutOfJailCards() {
            return getOfferedGetOutOfJailCards();
        }

        public Trade(String[] offeredCards, int trader_id, int offeredGetOutOfJailCards, int offeredMoney) {
            this.offeredCards = offeredCards;
            this.trader_id = trader_id;
            this.offeredGetOutOfJailCards = offeredGetOutOfJailCards;
            this.offeredMoney = offeredMoney;
        }



    }


}
