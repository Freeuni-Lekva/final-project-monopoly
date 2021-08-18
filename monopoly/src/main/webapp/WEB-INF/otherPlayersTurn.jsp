<%@ page import="gamelogic.Room" %>
<%@ page import="cardTypes.Card" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Map" %>
<%@ page import="gamelogic.Player" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 12/08/2021
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wait For Your Turn</title>
    <link rel="stylesheet" type="text/css" href="../otherPlayersTurnStyle.css">
</head>
<body>
  <%
    Room room = ((Map<String, Room>) pageContext.getServletContext().getAttribute("rooms"))
            .get(request.getSession().getAttribute("room-code"));
    Player[] players = room.getPlayers();
    Player currPlayer = players[room.getCurrentPlayerId()];
    Player player = null;
    String playerName = (String) request.getSession().getAttribute("username");
    for (int i = 0; i < players.length; i++) {
      if (players[i] != null && players[i].getUsername().equals(playerName)) {
        player = players[i];
        break;
      }
    }
    String event = "";
    String eventMessage = "";
    String victoryMessage = room.getWinnerName() + " WON!!!";
    if (room.getWinnerName().length() > 0) {
      event = "victory";
      if (room.getWinnerName().equals(playerName))
        victoryMessage = "YOU WON<br> CONGRATULATIONS!!!";
    } else if (player == null) {
      event = "none";
    } else if (currPlayer.getUsername().equals(player.getUsername())) {
      event = "your-turn";
    } else {
      event = player.getEvent();
      eventMessage = player.getEventMessage();
    }

    String[] circles = room.getCircleImages();
    String firstDie = room.getDieImages()[room.getFirstDie() - 1];
    String secondDie = room.getDieImages()[room.getSecondDie() - 1];
    Vector<String> log = room.getLog().getRecordings();
  %>

  <p id="curr-player"><%=currPlayer.getUsername()%>'s Turn</p>
  <input type="hidden" id="event" value=<%=event%>>

  <div class="circles" style="height: 100%">
    <img src="../board.png" id="board" height="100%">
    <%
      for (int i = 0; i < players.length; i++) {
        if (players[i] != null) {
    %>
    <img class="circle" src=<%= "../player-circles/" + circles[i] %> tile=<%=players[i].getTile()%>>
    <%
        }
      }
    %>
  </div>

  <div class="dice">
    <img id="first-die" src=<%="../dice-images/" + firstDie%>>
    <img id="second-die" src=<%="../dice-images/" + secondDie%>>
  </div>

  <ol class="log">
    <% for (int i = 0; i < log.size(); i++) { %>
    <li><p><%=log.get(i)%></p></li>
    <% } %>
  </ol>

  <% if (event.equals("your-turn")) { %>
  <div class="next-turn">
    <form method="post" action="/your-turn">
      <button type="submit">Begin Your Turn</button>
    </form>
  </div>
  <% } else if (event.equals("victory")) { %>
    <div class="victory-screen">
      <p id="victory-message"><%=victoryMessage%></p>
      <form action="/returned-to-menu" method="post">
        <button id="return-to-menu" type="submit">Return To Main Menu</button>
      </form>
    </div>
  <% } else if (event.equals("trade")) { %>
    <div class="confirm-trade">
      <p id="trade-with"><%=eventMessage%></p>
      <p id="offer">Offer</p>
      <p id="demand">Demand</p>
      <div class="cards">
        <div>
          <% for (Card card : room.getTrade().getOfferedCards()) { %>
          <img src=<%="../card-images/" + card.getCardImageName()%>>
          <% } %>
        </div>
        <div>
          <% for (Card card : room.getTrade().getDemandedCards()) { %>
          <img src=<%="../card-images/" + card.getCardImageName()%>>
          <% } %>
        </div>
      </div>
      <p id="other-offerings">
        Get Out of Jail Cards: <%=room.getTrade().getOfferedGetOutOfJailCards()%><br>Money:
        <%=room.getTrade().getOfferedMoney()%></p>
      <form method="post" action="/trade-decided">
        <button name="trade-button" type="submit" value="accepted">Accept Trade</button>
      </form>
      <form method="post" action="/trade-decided">
        <button name="trade-button" type="submit" value="declined">Decline Trade</button>
      </form>
      <p id="other-demands">
        Get Out of Jail Cards: <%=room.getTrade().getDemandedGetOutOfJailCards()%><br>Money: <%=
      room.getTrade().getDemandedMoney()%></p>
    </div>
  <% } %>

  <script src="../otherPlayersTurnScript.js"></script>

</body>
</html>
