package gamelogic;

import DAO.CardsDAO;
import DAO.LeaderboardDAO;
import cardTypes.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import static java.lang.Math.max;


public class Room {
    private Map<String, Integer> cardOwners;
    private Player[] players;
    private String chanceCardStr;
    private String communityChestCardStr;
    private static final String[] dieImages = {"die-one.png", "die-two.png", "die-three.png", "die-four.png",
            "die-five.png", "die-six.png"};
    private static final String[] circleImages = {"yellow-circle.png", "blue-circle.png", "light-blue-circle.png",
            "green-circle.png", "white-circle.png", "red-circle.png", "purple-circle.png", "orange-circle.png"};
    private static final String[] TILES = {
        "GO", "P MEDITERRANEAN-AVENUE", "COMMUNITY CHEST", "P BALTIC-AVENUE", "INCOME TAX", "R READING-RAILROAD",
            "P ORIENTAL-AVENUE",
            "CHANCE", "P VERMONT-AVENUE", "P CONNECTICUT-AVENUE", "JAIL", "P ST.-CHARLES-PLACE", "U ELECTRIC-COMPANY",
            "P STATES-AVENUE", "P VIRGINIA-AVENUE", "R PENNSYLVANIA-RAILROAD", "P ST.-JAMES-PLACE", "COMMUNITY CHEST",
            "P TENNESSEE-AVENUE", "P NEW-YORK-AVENUE", "FREE PARKING", "P KENTUCKY-AVENUE", "CHANCE", "P INDIANA-AVENUE",
            "P ILLINOIS-AVENUE", "R B.-&-O.-RAILROAD", "P ATLANTIC-AVENUE", "P VENTNOR-AVENUE", "U WATER-WORKS",
            "P MARVIN-GARDENS", "GO TO JAIL", "P PACIFIC-AVENUE", "P NORTH-CAROLINA-AVENUE", "COMMUNITY CHEST",
            "P PENNSYLVANIA-AVENUE", "R SHORT-LINE", "CHANCE", "P PARK-PLACE", "LUXURY TAX", "P BOARDWALK"
    };
    private Map<String, Card> cards;
    private RandomEventCardPile chance;
    private RandomEventCardPile communityChest;
    private static final int MAX_TILE_NUM = 40;
    private int current_player_id;
    private int firstDie;
    private int secondDie;
    private boolean tradeDecided;
    private Log log;
    private int playersLeft;
    private Trade trade;
    private int payingEventMoneyReceiverId;
    private String propertyToPurchase;
    private String winnerUsername;
    private String tradeMessage;
    private LeaderboardDAO leaderboardDAO;

    public Room(Vector<String> usernames) {
        payingEventMoneyReceiverId = -1;
        propertyToPurchase = "";
        tradeMessage = "";
        winnerUsername = "";
        log = new Log();
        cardOwners = new HashMap<>();
        current_player_id = 0;
        players = new Player[usernames.size()];
        cards = new HashMap<>();
        firstDie = 1;
        secondDie = 1;
        tradeDecided = false;
        Random random = new Random();
        for(int i = 0; usernames.size() > 0; i++) {
            int randomPlayerNum = random.nextInt(usernames.size());
            Player newPlayer = new Player(i, usernames.get(randomPlayerNum));
            usernames.remove(randomPlayerNum);
            players[i] = newPlayer;
        }
        playersLeft = players.length;
        try {
            leaderboardDAO = new LeaderboardDAO();
            CardsDAO cDao = new CardsDAO();
            String[][] propertyCardsData = cDao.getPropertyCardsData();
            for(int i = 0; i < propertyCardsData.length; i++) {
                PropertyCard prCard = new PropertyCard(propertyCardsData[i]);
                cardOwners.put(prCard.getCardName(), -1);
                cards.put(prCard.getCardName(), prCard);
            }
            String[][] railroadCardsData = cDao.getRailroadCardsData();
            for(int i = 0; i < railroadCardsData.length; i++) {
                RailroadCard rCard = new RailroadCard(railroadCardsData[i]);
                cards.put(rCard.getCardName(), rCard);
                cardOwners.put(rCard.getCardName(), -1);
            }
            String[][] utilityCardsData = cDao.getUtilityCardsData();
            for(int i = 0; i < utilityCardsData.length; i++) {
                UtilityCard uCard = new UtilityCard(utilityCardsData[i]);
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

    public String[] getDieImages() {
        return dieImages;
    }

    public String[] getCircleImages() {
        return circleImages;
    }

    public String getWinnerName() {
        return winnerUsername;
    }

    public int getPayingEventMoneyReceiverId() {
        return payingEventMoneyReceiverId;
    }

    public void setTradeMessage(String message) {
        tradeMessage = message;
    }

    public String getTradeMessage() {
        return tradeMessage;
    }

    public Vector<Card> getPlayerCards(int player_id) {
        Vector<Card> playerCards = new Vector<>();
        for (String cardName : cardOwners.keySet())
            if (cardOwners.get(cardName) == player_id) playerCards.add(cards.get(cardName));
        return playerCards;
    }

    public int getCurrentPlayerId() {
        return current_player_id;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Log getLog() {
        return log;
    }

    public void createTrade(Vector<Card> offeredCards, int trade_receiver_id, int offeredGetOutOfJailCards,
                            int offeredMoney) {
        trade = new Trade(offeredCards, trade_receiver_id, offeredGetOutOfJailCards, offeredMoney);
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTradeDecided(boolean decided) {
        tradeDecided = decided;
    }

    public boolean getTradeDecided() {
        return tradeDecided;
    }

    public void performTrade() {
        for (Card card : trade.offeredCards)
            cardOwners.put(card.getCardName(), trade.getTradeReceiverId());
        for (Card card : trade.demandedCards)
            cardOwners.put(card.getCardName(), current_player_id);
        Player trader = players[current_player_id];
        Player receiver = players[trade.getTradeReceiverId()];
        trader.addMoney(-trade.getOfferedMoney());
        trader.addMoney(trade.getDemandedMoney());
        trader.addMoney(trade.getDemandedMoney());
        receiver.addMoney(trade.getOfferedMoney());
        receiver.addMoney(-trade.getDemandedMoney());
        for (int i = 0; i < trade.getOfferedGetOutOfJailCards(); i++) {
            trader.takeGetOutOfJailCard();
            receiver.addGetOutOfJailCard();
        }
        for (int i = 0; i < trade.getDemandedGetOutOfJailCards(); i++) {
            trader.addGetOutOfJailCard();
            receiver.takeGetOutOfJailCard();
        }
    }

    public void declareBankruptcy() {
        Player player = players[current_player_id];
        playersLeft--;
        for (int i = 0; i < player.getGetOutOfJailCards(); i++) returnGetOutOfJailCard();
        for (String cardName : cardOwners.keySet())
            if (cardOwners.get(cardName) == current_player_id) cardOwners.put(cardName, -1);
        try {
            leaderboardDAO.moneyWon(player.getUsername(), player.getMaxMoney());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        players[current_player_id] = null;
        nextPlayer();
        if (playersLeft <= 1) {
            for (int i = 0; i < players.length; i++) {
                if (players[i] != null) {
                    winnerUsername = players[i].getUsername();
                    try {
                        leaderboardDAO.userWon(winnerUsername);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return;
                }
            }
        }
    }

    public void returnGetOutOfJailCard() {
        if (chance.containsGetOutOfJailCard()) {
            communityChest.addGetOutOfJail();
        } else {
            chance.addGetOutOfJail();
        }
    }

    public void nextPlayer() {
        while (true) {
            if (current_player_id < players.length - 1) {
                current_player_id++;
            } else {
                current_player_id = 0;
            }
            if (players[current_player_id] != null) break;
        }
        Player player = players[current_player_id];
        player.setEvent("none");
        player.setCanMove(true);
        player.setPairsRolledToZero();
    }

    public void purchaseProperty() {
        cardOwners.put(propertyToPurchase, current_player_id);
        Player player = players[current_player_id];
        player.addMoney(-player.getEventCost());
    }

    public String getPropertyToPurchase() {
        return propertyToPurchase;
    }

    public void setDice(int first, int second) {
        firstDie = first;
        secondDie = second;
    }

    public int getFirstDie() {
        return firstDie;
    }

    public int getSecondDie() {
        return secondDie;
    }

    public void move(boolean passGo, int tileNum, int railroadMultiplier, int utilityMultiplier) {
        Player player = players[current_player_id];
        if(passGo && tileNum%MAX_TILE_NUM <= player.getTile()) {
            player.addMoney(200);
        }
        player.setTile(tileNum%MAX_TILE_NUM);
        if(firstDie != secondDie) {
            player.setCanMove(false);
        } else {
            player.incrementPairsRolled();
        }
        if (player.getPairsRolled() < 3) {
            performTileAction(player.getTile(), railroadMultiplier, utilityMultiplier);
        } else {
            move(false, 10, 1, 1);
        }
    }

    private void performTileAction(int tile, int railroadMultiplier, int utilityMultiplier) {
        Player player = players[current_player_id];
        if(TILES[tile].equals("CHANCE")) {
            setupChanceEvent();
        } else if(TILES[tile].equals("COMMUNITY CHEST")) {
            setUpCommunityChestEvent();
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
                propertyToPurchase = cardName;
                player.setEvent("unoccupied-property");
                player.setEventMessage("You can purchase this property");
                player.setEventCost(prCard.getCost());
                player.setEventImagePath("../property-cards/" + prCard.getCardImageName());
            } else if(cardOwners.get(cardName) != current_player_id && !prCard.isMortgaged()) {
                int owner_id = cardOwners.get(cardName);
                payingEventMoneyReceiverId = owner_id;
                player.setEvent("paying-event");
                int sameColorCount = 0;
                String cardColor = prCard.getCardColor();
                for(String card: cardOwners.keySet()) {
                    if(cardOwners.get(card) == owner_id && cards.get(card) instanceof PropertyCard &&
                            ((PropertyCard)cards.get(card)).getCardColor().equals(cardColor)) {
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
                propertyToPurchase = cardName;
                player.setEvent("unoccupied-property");
                player.setEventMessage("You can purchase this railroad");
                player.setEventCost(rCard.getCost());
                player.setEventImagePath("../railroad-cards/" + rCard.getCardImageName());
            } else if(cardOwners.get(cardName) != current_player_id && !rCard.isMortgaged()) {
                int owner_id = cardOwners.get(cardName);
                payingEventMoneyReceiverId = owner_id;
                player.setEvent("paying-event");
                int railroadCount = 0;
                for(String card: cardOwners.keySet()) {
                    if(cardOwners.get(card) == owner_id && cards.get(card) instanceof RailroadCard) {
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
                propertyToPurchase = cardName;
                player.setEvent("unoccupied-property");
                player.setEventMessage("You can purchase this utility");
                player.setEventCost(uCard.getCost());
                player.setEventImagePath("../utility-cards/" + uCard.getCardImageName());
            } else if(cardOwners.get(cardName) != current_player_id && !uCard.isMortgaged()) {
                int owner_id = cardOwners.get(cardName);
                payingEventMoneyReceiverId = owner_id;
                player.setEvent("paying-event");
                int utilityCount = 0;
                for(String card: cardOwners.keySet()) {
                    if(cardOwners.get(card) == owner_id && cards.get(card) instanceof UtilityCard) {
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
        log.addEntry(player.getUsername() + " has advanced to the nearest railroad");
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
        log.addEntry(player.getUsername() + " has advanced to the nearest utility");
    }

    private void advanceToGo() {
        move(true, 0, 1, 1);
        Player player = players[current_player_id];
        log.addEntry(player.getUsername() + " has advanced to GO");
    }

    private void advanceToBoardwalk() {
        move(true, 39, 1, 1);
        Player player = players[current_player_id];
        log.addEntry(player.getUsername() + " has advanced to Boardwalk");
    }

    private void advanceToIllinois() {
        move(true, 24, 1, 1);
        Player player = players[current_player_id];
        log.addEntry(player.getUsername() + " has advanced to Illinois");
    }

    private void advanceToStCharles() {
        move(true,11, 1, 1);
        Player player = players[current_player_id];
        log.addEntry(player.getUsername() + " has advanced to St. Charles");
    }

    private void bankPays() {
        Player player = players[current_player_id];
        player.addMoney(50);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " received 50$ from the Bank");
    }

    private void repairs() {
        Player player = players[current_player_id];
        player.addMoney(-player.getEventCost());
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has made repairs on their property for " +
                player.getEventCost() + "$");
    }

    private void speedingFine() {
        Player player = players[current_player_id];
        player.addMoney(-15);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has paid a speeding fine of 15$");
    }

    private void buildingLoanMatures() {
        Player player = players[current_player_id];
        player.addMoney(150);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has received a payment of 150$");
    }

    private void chairman() {
        Player player = players[current_player_id];
        for(int i = 0; i < players.length; i++) {
            if(players[i] != null && i != current_player_id) {
                player.addMoney(-50);
                players[i].addMoney(50);
            }
        }
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has given each player 50$");
    }

    private void getOutOfJail() {
        Player player = players[current_player_id];
        player.addGetOutOfJailCard();
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has received a get out of jail for free card");
    }

    private void goBack() {
        move(false, TILES.length-3+players[current_player_id].getTile(),
                1, 1);
        log.addEntry(players[current_player_id].getUsername() + " has moved back 3 spaces");
    }

    private void goToJail() {
        move(false, 10, 1, 1);
        players[current_player_id].incrementTurnsArrested();
        log.addEntry(players[current_player_id].getUsername() + " has gone to jail");
    }

    private void goToReadingRailroad() {
        move(true, 5, 1, 1);
        log.addEntry(players[current_player_id].getUsername() + " has advanced to Reading Railroad");
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

    private void bankError() {
        Player player = players[current_player_id];
        player.addMoney(200);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has received 200$ due to a bank error");
    }

    private void beautyContest() {
        Player player = players[current_player_id];
        player.addMoney(10);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has won 10$ in a beauty contest");
    }

    private void birthday() {
        Player player = players[current_player_id];
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null && i != current_player_id) {
                players[i].addMoney(-10);
                player.addMoney(10);
            }
        }
        player.setEvent("none");
        log.addEntry(player.getUsername() + " was given a birthday gift of 10$ from everyone");
    }

    private void consultancyFee() {
        Player player = players[current_player_id];
        player.addMoney(25);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has paid a consultancy fee of 25");
    }

    private void doctorsFee() {
        Player player = players[current_player_id];
        player.addMoney(-50);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has paid a doctor's fee of 50$");
    }

    private void holidayFundMatures() {
        Player player = players[current_player_id];
        player.addMoney(100);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has received a payment of 100$");
    }

    private void hospitalFees() {
        Player player = players[current_player_id];
        player.addMoney(-100);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has paid a hospital fee of 100$");
    }

    private void incomeTaxRefund() {
        Player player = players[current_player_id];
        player.addMoney(200);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has received a payment of 200$");
    }

    private void inherit() {
        Player player = players[current_player_id];
        player.addMoney(100);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has inherited 100$");
    }

    private void lifeInsuranceMatures() {
        Player player = players[current_player_id];
        player.addMoney(200);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has received a payment of 200$");
    }

    private void saleOfStock() {
        Player player = players[current_player_id];
        player.addMoney(50);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has received a payment of 50$");
    }

    private void schoolFees() {
        Player player = players[current_player_id];
        player.addMoney(-50);
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has paid school fees of 50$");
    }

    private void streetRepairs() {
        Player player = players[current_player_id];
        player.addMoney(-player.getEventCost());
        player.setEvent("none");
        log.addEntry(player.getUsername() + " has paid " + player.getEventCost() + " due to street repairs");
    }

    public void performCommunityChestAction() {
        switch (communityChestCardStr) {
            case "chest-advance-to-go.png":
                advanceToGo();
                break;
            case "chest-bank-error.png":
                bankError();
                break;
            case "chest-beauty-contest.png":
                beautyContest();
                break;
            case "chest-birthday.png":
                birthday();
                break;
            case "chest-consultancy-fee.png":
                consultancyFee();
                break;
            case "chest-doctors-fee.png":
                doctorsFee();
                break;
            case "chest-get-out-of-jail.png":
                getOutOfJail();
                break;
            case "chest-go-to-jail.png":
                goToJail();
                break;
            case "chest-holiday-fund-matures.png":
                holidayFundMatures();
                break;
            case "chest-hospital-fees.png":
                hospitalFees();
                break;
            case "chest-income-tax-refund.png":
                incomeTaxRefund();
                break;
            case "chest-inherit.png":
                inherit();
                break;
            case "chest-life-insurance-matures.png":
                lifeInsuranceMatures();
                break;
            case "chest-sale-of-stock.png":
                saleOfStock();
                break;
            case "chest-school-fees.png":
                schoolFees();
                break;
            case "chest-street-repairs":
                streetRepairs();
                break;
        }
    }

    private void setUpCommunityChestEvent() {
        Player player = players[current_player_id];
        communityChestCardStr = communityChest.getRandomCard();
        player.setEventMessage("You got a community chest card!");
        player.setEventImagePath("../community-chest-cards/" + communityChestCardStr);
        switch (communityChestCardStr) {
            case "chest-advance-to-go.png":
            case "chest-bank-error.png":
            case "chest-beauty-contest.png":
            case "chest-birthday.png":
            case "chest-get-out-of-jail.png":
            case "chest-go-to-jail.png":
            case "chest-consultancy-fee.png":
            case "chest-holiday-fund-matures.png":
            case "chest-income-tax-refund.png":
            case "chest-inherit.png":
            case "chest-life-insurance-matures.png":
            case "chest-sale-of-stock.png":
                player.setEvent("non-interactive-event");
                break;
            case "chest-doctors-fee.png":
            case "chest-school-fees.png":
                player.setEvent("paying-event");
                player.setEventCost(50);
                break;
            case "chest-hospital-fees.png":
                player.setEvent("paying-event");
                player.setEventCost(100);
                break;
            case "chest-street-repairs":
                player.setEvent("paying-event");
                int payment = 0;
                for (String cardName : cards.keySet()) {
                    if (cardOwners.get(cardName) == current_player_id &&
                            cards.get(cardName) instanceof PropertyCard) {
                        if (((PropertyCard)cards.get(cardName)).getNumHouses() >= 5) {
                            payment += 115;
                        } else if (((PropertyCard)cards.get(cardName)).getNumHouses() >= 1) {
                            payment += 40;
                        }
                    }
                }
                player.setEventCost(payment);
                break;
        }
    }



    public class Trade {
        private Vector<Card> offeredCards;
        private Vector<Card> demandedCards;
        private int demandedMoney;
        private int offeredMoney;
        private int offeredGetOutOfJailCards;
        private int demandedGetOutOfJailCards;
        private int trade_receiver_id;

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

        public void setDemandedCards(Vector<Card> crd) {
            demandedCards = crd;
        }

        public Vector<Card> getDemandedCards() {
            return demandedCards;
        }

        public Vector<Card> getOfferedCards() {
            return offeredCards;
        }

        public int getTradeReceiverId() {
            return trade_receiver_id;
        }

        public int getOfferedMoney() {
            return offeredMoney;
        }

        public int getOfferedGetOutOfJailCards() {
            return offeredGetOutOfJailCards;
        }

        public Trade(Vector<Card> offeredCards, int trade_receiver_id, int offeredGetOutOfJailCards, int offeredMoney) {
            this.offeredCards = offeredCards;
            this.trade_receiver_id = trade_receiver_id;
            this.offeredGetOutOfJailCards = offeredGetOutOfJailCards;
            this.offeredMoney = offeredMoney;
        }



    }


}
