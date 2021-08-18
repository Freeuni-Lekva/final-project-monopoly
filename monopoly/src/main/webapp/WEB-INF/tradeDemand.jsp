<%@ page import="cardTypes.Card" %>
<%@ page import="gamelogic.Room" %>
<%@ page import="java.util.Set" %>
<%@ page import="cardTypes.PropertyCard" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Vector" %>
<%@ page import="gamelogic.Player" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 12/08/2021
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trade Demand</title>
    <link rel="stylesheet" type="text/css" href="../tradeStyle.css">
</head>
<body>
    <h1>Demand</h1>

    <%
        Room room = ((Map<String, Room>) pageContext.getServletContext().getAttribute("rooms"))
                .get(request.getSession().getAttribute("room-code"));
        Player player = room.getPlayers()[room.getTrade().getTradeReceiverId()];
        Vector<Card> cards = room.getPlayerCards(player.getPlayerId());
    %>

    <form action="/your-turn" method="post">
        <button id="cancel" type="submit">Cancel Trade</button>
    </form>

    <form method="post" action="/trade-created">
        <div class="curr-player-cards">
            <%
                for (Card card : cards) {
                    if (card.isMortgaged() || (card instanceof PropertyCard &&
                            ((PropertyCard)card).getNumHouses() != 0)) continue;
            %>
            <input type="hidden" value="not-selected" id=<%=card.getCardName()%> name=<%=card.getCardName()%>>
            <img src=<%="card-images/" + card.getCardImageName()%> card-name=<%=card.getCardName()%>>
            <%  } %>
        </div>


        <ul class="additional-info">
            <li>
                <input name="get-out-of-jail-num" type="number" min="0" max=<%=player.getGetOutOfJailCards()%>
                        required> Get Out Of Jail Cards
            </li>
            <li>
                <input name="money-offered" type="number" min="0" max=<%=player.getMoney()%> required> Money
            </li>
            <li> <button type="submit">Confirm Demand</button> </li>
        </ul>
    </form>

    <script src="../tradeScript.js"></script>
</body>
</html>
