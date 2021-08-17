<%@ page import="invitations.UserBuilder" %>
<%@ page import="invitations.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Aernd
  Date: 15.08.2021
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Monopoly Friends</title>
    <link href="../CreateLobbyStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <p>This are your friends</p>
    <ul>
    <%
        UserBuilder userBuilder = (UserBuilder) getServletConfig().getServletContext().getAttribute("userBuilder");
        User user = userBuilder.getInstance((String)request.getSession().getAttribute("user"));
        ArrayList<String> friends = user.getFriends();
        for (String friend : friends) {%>
            <li>
                <p><%=friend%></p>
            </li>
        <%}%>
    </ul>
</body>
</html>
