<%@ page import="invitations.User" %>
<%@ page import="java.util.ArrayList" %>
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
        User user = userBuilder.getInstance((String)session.getAttribute("user"));
        ArrayList<String> invs = user.getInvitations();
        for (String invitation : invs) {
            String url = "/lobby?" + invitation ;
            %>
        <li><a href = <%out.print(url);%>><%out.print(invitation);%></a></li>
    <%
        }
    %>
    </ul>
</body>
</html>
