<%@ page import="invitations.UserBuilder" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Aernd
  Date: 16.08.2021
  Time: 04:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Lobby</title>
    <link href="../CreateLobbyStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <form method="post" action="lobbyMade">
        <ul class = "classList">
        <%
        UserBuilder ub = (UserBuilder) getServletConfig().getServletContext().getAttribute("userBuilder");
        ArrayList<String> friends = ub.getInstance((String)request.getSession().getAttribute("user")).getFriends();
        for (String friend : friends) {%>
        <li><input type="checkbox" name="<%out.print(friend);%>"> <%out.print(friend);%></li>

        <%}%></ul>
        <input id="create" type="submit" value="Create!">
    </form>
</body>
</html>
