<%@ page import="cardTypes.Card" %>
<%@ page import="gamelogic.Room" %>
<%@ page import="cardTypes.PropertyCard"%>
<%@ page import="java.util.Map" %>
<%@ page import="gamelogic.Player" %>
<%@ page import="java.util.Vector" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 11/08/2021
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Turn</title>
    <link rel="stylesheet" type="text/css" href="../playersTurnStyle.css">
</head>
<body>
    <%
        Room room = ((Map<String, Room>) pageContext.getServletContext().getAttribute("rooms"))
                .get(request.getSession().getAttribute("room-code"));
        Player[] players = room.getPlayers();
        Player player = players[room.getCurrentPlayerId()];
        String event = player.getEvent();
        String eventMessage = player.getEventMessage();
        String eventImagePath = player.getEventImagePath();
        int eventCost = player.getEventCost();
        int money = player.getMoney();
        if (!event.equals("awaiting-response") && !event.equals("trade-decided") && money < 0) {
            event = "negative-money";
            eventMessage = "You have a negative amount of money.<br> Manage your property, " +
                "make trades or choose to declare bankruptcy";
        } else if (room.getTradeDecided()) {
            event = "trade-decided";
            eventMessage = room.getTradeMessage();
        } else if (player.getWaiting()) {
            event = "awaiting-response";
        }
        boolean canMove = player.playerCanMove();
        int turnsArrested = player.getTurnsArrested();
        int getOutOfJailCards = player.getGetOutOfJailCards();
        String[] dieImages = room.getDieImages();
        int firstDie = room.getFirstDie();
        int secondDie = room.getSecondDie();
        String[] circleImages = room.getCircleImages();
    %>

    <input type="hidden" id="event" value=<%=event%>>
    <input type="hidden" id="event-cost" value=<%=eventCost%>>
    <input type="hidden" id="turns-arrested" value=<%=turnsArrested%>>
    <input type="hidden" id="can-move" value=<%=canMove%>>

    <img src="../board.png" id="board" style="width: 100%">

    <div class="circles">
        <%
            for (int i = 0; i < room.getPlayers().length; i++) {
                if (players[i] != null) {
        %>
        <img src=<%= "player-circles/" + circleImages[i] %> width="2.5%" tile=<%=players[i].getTile()%>>
        <%      }
            } %>
    </div>

    <div class="left-panel">
        <div class="dice">
            <img src=<%="../dice-images/" + dieImages[firstDie - 1]%>>
            <img src=<%="../dice-images/" + dieImages[secondDie - 1]%>>
        </div>
        <p>Your Wealth: <%=money + "$"%></p>
        <p>Get Out of Jail Cards: <%=getOutOfJailCards%></p>
        <div class="buttons">
            <button onclick="displayRules()">Rules</button><br>
            <button onclick="rollDice('regular')">Roll Dice</button><br>
            <button onclick="addCardListeners()">My Cards</button><br>
            <form method="post" action="/offer">
                <button type="submit">Offer Trade</button><br>
            </form>
            <form action="/moved" method="post">
                <input name="first-die" id="first-die" type="hidden" value="1">
                <input name="second-die" id="second-die" type="hidden" value="1">
                <button type="submit" disabled>Move</button>
            </form>
            <form method="post" action="/wait-for-your-turn">
                <button type="submit" disabled>End Turn</button>
            </form>
            <button onclick="doubleCheck()">Declare Bankruptcy</button>
        </div>
    </div>



    <form action="/cards-updated" method="post">
        <div class="image-container">
            <div class="back-button-container">
                <button type="submit">Back</button>
            </div>
            <%
                Vector<Card> cards = room.getPlayerCards(player.getPlayerId());
                String type;
                int level;
                int houseCost;
                int hotelCost;
                int mortgageValue;
                int mortgageLiftCost;
                for (Card card : cards) {
                    type = "other";
                    level = 0;
                    houseCost = 0;
                    hotelCost = 0;
                    if (card instanceof PropertyCard) {
                        type = "property";
                        level = ((PropertyCard)card).getNumHouses();
                        houseCost = ((PropertyCard)card).getHouseCost();
                        hotelCost = ((PropertyCard)card).getHotelCost();
                    }
                    if (card.isMortgaged()) {
                        level = -1;
                    }
                    mortgageValue = card.getMortgageValue();
                    mortgageLiftCost = card.getMortgageLiftCost();
            %>
            <input type="hidden" id=<%=card.getCardName()%> name=<%=card.getCardName()%> value=<%=level%>>
            <img src=<%="card-images/" + card.getCardImageName()%> house-cost=<%=houseCost%>
                hotel-cost=<%=hotelCost%> card-type=<%=type%> id=<%=card.getCardName()%> level=<%=level%>
                mortgage-value=<%=mortgageValue%> mortgage-lift-cost=<%=mortgageLiftCost%>>
            <%  } %>
            <input name="money" id="money" type="hidden" value=<%=money%>>
        </div>
    </form>

    <div class="card-display">
        <button id="return-button" onclick="ret()">Return</button>
        <img src="">
        <p id="description">Not Mortgaged</p>
        <div class="card-buttons">
            <button onclick="increaseLevel()">Lift Mortgage</button><br>
            <button onclick="decreaseLevel()">Mortgage</button>
        </div>
        <p id="card-display-money"><%=money + "$"%></p>
    </div>

    <div class="rules">
        <button onclick="removeRules()">Back</button>
        <p><%=getServletConfig().getServletContext().getAttribute("rules")%></p>
    </div>

    <div class="confirm-bankruptcy">
        <p>Are You Sure You Want To Concede?</p>
        <div class="yes-no-buttons">
            <form method="post" action="/conceded">
                <button type="submit">Yes</button>
            </form>
            <button onclick="declineBankruptcy()">No</button>
        </div>
    </div>

    <% if (turnsArrested > 0) { %>
    <div class="arrested">
        <div class="jail-dice">
            <img src="../dice-images/die-one.png">
            <img src="../dice-images/die-one.png">
        </div>
        <p>You Are Arrested!</p>
        <p>Your Wealth: <%=money + "$"%></p>
        <p>Get Out of Jail Cards: <%=getOutOfJailCards%></p>
        <div class="jail-buttons">
            <button onclick="displayRules()">Rules</button><br>
            <button onclick="getOutByRolling()">Roll a Pair to Escape</button><br>
            <button onclick="getOutByCard()">Use Get Out of Jail Card</button><br>
            <button onclick="getOutByPaying()">Pay 50$</button>
            <form method="post" action="/free-from-jail">
                <input type="hidden" id="get-out-of-jail-cards"
                       value=<%=getOutOfJailCards%>>
                <input type="hidden" id="money-jail" value=<%=getOutOfJailCards%>>
                <button type="submit" name="get-out-button" value="pair-rolled" disabled>Get Out of Jail</button>
            </form>
            <button onclick="addCardListeners()">My Cards</button><br>
            <form method="post" action="/offer">
                <button type="submit">Offer Trade</button><br>
            </form>
            <form method="post" action="/wait-for-your-turn">
                <button type="submit" disabled>End Turn</button>
            </form>
            <button onclick="doubleCheck()">Declare Bankruptcy</button>
        </div>
    </div>
    <% } %>

<%--    The event-window will have different objects inside it depending on the current players event   --%>
    <div class="event-window">
    <% if (event.equals("unoccupied-property")) { %>
        <p id="purchase-message"><%=eventMessage%></p>
        <img id="purchase-image" src=<%=eventImagePath%>>
        <form method="post" action="/unoccupied-property-purchased">
            <button id="purchase-button" type="submit">Purchase Property (<%=eventCost%>$)</button>
        </form>
        <form method="post" action="/event-done">
            <button id="purchase-cancel-button" type="submit">Cancel</button>
        </form>
    <% } else if (event.equals("paying-event")) { %>
        <p id="paying-event-message"><%=eventMessage%></p>
        <% if (!eventImagePath.equals("none")) { %>
        <img id="paying-event-image" src=<%=eventImagePath%>>
        <% } %>
        <form method="post" action="/paying-event-completed">
            <% if (money >= eventCost) { %>
            <button id="pay-button" type="submit">Pay <%=eventCost%>$</button>
            <% } else { %>
            <button id="pay-button" type="submit" disabled>Pay <%=eventCost%>$</button>
            <% } %>
        </form>
    <% } else if (event.equals("awaiting-response")) { %>
        <p id="awaiting-message">Awaiting Response</p>
        <img id="waiting-image" src="../waiting.png">
    <% } else if (event.equals("trade-decided")) { %>
        <p id="trade-decided-message"><%=eventMessage%></p>
        <form action="/event-done" method="post">
            <button id="trade-decided-button" type="submit">OK</button>
        </form>
    <% } else if (event.equals("non-interactive-event")) { %>
        <p id="non-interactive-event-message"><%=eventMessage%></p>
        <img id="non-interactive-event-image" src=<%=eventImagePath%>>
        <form method="post" action="/event-done">
            <button id="non-interactive-event-button" type="submit">OK</button>
        </form>
    <% } else if (event.equals("negative-money")) { %>
        <p id="negative-money-message"><%=eventMessage%></p>
    <% } else if (event.equals("nearest-utility-owned")) { %>
        <p id="utility-owned-message"><%=eventMessage%></p>
        <img id="utility-owned-img" src=<%=eventImagePath%>>
        <form method="post" action="/nearest-utility-is-owned">
            <input type="hidden" name="utility-owned-payment" id="utility-owned-payment" value=<%=eventCost%>>
            <button id="utility-owned-pay-button" type="submit" disabled>Pay <%=eventCost%>$</button>
        </form>
    <% } %>
    </div>
    <script src="../playersTurnScript.js"></script>
</body>
</html>
