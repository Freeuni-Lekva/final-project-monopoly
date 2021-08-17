<%@ page import="invitations.UserBuilder" %>
<%@ page import="invitations.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Monopoly Friends</title>
    <link href="../CreateLobbyStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
<p>This are your friend requests</p>
<ul>
<%
    UserBuilder userBuilder = (UserBuilder) getServletConfig().getServletContext().getAttribute("userBuilder");
    User user = userBuilder.getInstance((String)request.getSession().getAttribute("user"));
    ArrayList<String> friendRequests = user.getFriendRequests();
    for (String friend : friendRequests) {%>
    <li>
        <p><%=friend%></p>
        <button onclick="var xmlHttp = new XMLHttpRequest(); xmlHttp.open( 'GET', '/acceptRequest', false ); xmlHttp.setRequestHeader('AcceptedUser','<%out.print(friend);%>'); xmlHttp.send(); location.reload();" >Accept</button>
        <button onclick="var xmlHttp = new XMLHttpRequest(); xmlHttp.open( 'GET', '/declineRequest', false ); xmlHttp.setRequestHeader('DeclinedUser','<%out.print(friend);%>'); xmlHttp.send(); location.reload();">Decline</button>
    </li>
<%}%>
</ul>
</body>
</html>
