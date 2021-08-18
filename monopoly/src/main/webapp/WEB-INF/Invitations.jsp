<%@ page import="invitations.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jdk.internal.net.http.common.Pair" %>
<%@ page import="invitations.UserBuilder" %><%--
  Created by IntelliJ IDEA.
  User: Aernd
  Date: 16.08.2021
  Time: 09:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="../CreateLobbyStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <ul>
    <%
        UserBuilder userBuilder = ((UserBuilder)getServletConfig().getServletContext().getAttribute("userBuilder"));
        User user = userBuilder.getInstance((String)session.getAttribute("username"));
        ArrayList<String> invCodes = user.getInvitationCodes();
        ArrayList<String> inviters = user.getInviters();
        for (int i = 0; i < invCodes.size(); i++) {
            %>
        <form method="post" action="/lobby">
            <li><button type="submit" name="invitation" value=<%=invCodes.get(i)%>><%=inviters.get(i)%></button></li>
        </form>
    <%
        }
    %>
    </ul>
</body>
</html>
