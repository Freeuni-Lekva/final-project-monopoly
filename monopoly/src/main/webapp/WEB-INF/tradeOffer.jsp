<%@ page import="gamelogic.Room" %>
<%@ page import="cardTypes.Card" %>
<%@ page import="java.util.Set" %>
<%@ page import="cardTypes.PropertyCard" %>
<%@ page import="gamelogic.Player" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 12/08/2021
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trade Offer</title>
    <link rel="stylesheet" type="text/css" href="../tradeStyle.css">
</head>
<body>
    <%
        Room room = ((Map<String, Room>) pageContext.getServletContext().getAttribute("rooms"))
                .get(request.getSession().getAttribute("room-code"));
        Player player = room.getPlayers()[room.getCurrentPlayerId()];
    %>
    <h1>Offer</h1>

    <form action="/your-turn" method="post">
        <button id="cancel" type="submit">Cancel Trade</button>
    </form>

    <form method="post" action="/demand">
        <div class="curr-player-cards">
            <%
                for (Card card : room.getPlayerCards(player.getPlayerId())) {
                    if (card.isMortgaged() || (card instanceof PropertyCard && ((PropertyCard)card)
                            .getNumHouses() != 0)) continue;
            %>
            <input type="hidden" value="not-selected" id=<%=card.getCardName()%> name=<%=card.getCardName()%>>
            <img src=<%="card-images/" + card.getCardImageName()%> card-name=<%=card.getCardName()%>>
            <%  } %>
        </div>


        <ol class="additional-info">
            <% for (Player pl : room.getPlayers()) {
                if (pl != null && pl.getPlayerId() != room.getCurrentPlayerId()) { %>
            <li>
                <input type="radio" name="trade-with" id=<%=pl.getPlayerId()%> value=<%=pl.getPlayerId()%> required>
                <label for=<%=pl.getUsername()%>><%=pl.getUsername()%></label>
            </li>
            <%
                    }
                }
            %>
            <li>
                <input name="get-out-of-jail-num" type="number" min="0" max=<%=player.getGetOutOfJailCards()%>>
                    required> Get Out Of Jail Cards
            </li>
            <li>
                <input name="money-offered" type="number" min="0" max=<%=player.getMoney()%> required> Money
            </li>
            <li> <button type="submit">Confirm Offer</button> </li>
        </ol>
    </form>

    <script src="../tradeScript.js"></script>
</body>
</html>
