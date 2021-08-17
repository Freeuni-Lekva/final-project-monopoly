<%@ page import="invitations.UserBuilder" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="invitations.Lobby" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Aernd
  Date: 16.08.2021
  Time: 05:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../CreateLobbyStyle.css">
</head>
<body>
    <ul>
    <%
        UserBuilder userBuilder = (UserBuilder) getServletConfig().getServletContext().getAttribute("userBuilder");
        String user = (String)session.getAttribute("username");
        Lobby lobby = (Lobby)((HashMap)getServletConfig().getServletContext().getAttribute("lobbies")).get(session.getAttribute("room-code"));
        if (!lobby.getCurPlayers().contains(user)) lobby.addPlayer(user);
        ArrayList<String> players = lobby.getCurPlayers();
        for (String player : players) {%>
            <li><%=player%></li>

    <%
        }
    %>
    </ul>
    <form action="/startGame" method="post">
        <%
        if(((HashMap)pageContext.getServletContext().getAttribute("rooms")).containsKey(lobby.getKey())
        || (lobby.isHost((String) request.getSession().getAttribute("username")) && lobby.getCurPlayers().size()
        > 1 && lobby.getCurPlayers().size() < 9)){
        %>
            <button type="submit" id="create">Start Game</button>
        <%} else{%>
            <button type="submit" id="create" disabled>Start Game</button>
        <%}%>
    </form>

    <script>setTimeout(function(){window.location.href = window.location.href},2000);</script>
</body>
</html>
