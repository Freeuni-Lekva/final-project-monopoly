
<%@ page import="DAO.LeaderboardDAO" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: cking01201
  Date: 16.08.21
  Time: 03:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String type = (String) request.getSession().getAttribute("type");
%>
<html>
<head>
    <title><%=type%></title>
    <link rel="stylesheet" type="text/css" href="loginStyle.css">
</head>
<body>
<img src="frame.png" alt="leaderboardframe" height="80%" class="monopolianman">
<%
    LeaderboardDAO dao = new LeaderboardDAO();
    Vector<String> keys = new Vector<String>();
    Map<String, Integer> map = null;
    if(type == "top money") {
        try {
            map = dao.topTenMoney(keys);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } else {
        try {
            map = dao.topTenWins(keys);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
%>
<h1 id="begolden"> Leaderboard of <%=type%> </h1>
<div class="middle-class">
    <% for(int i = 0; i < 10; i++) { %>
    <h1 id="begolden"> <%=keys.get(i)%> is number <%=i+1%> with <%=map.get(keys.get(i))%></h1>
    <% } %>
</div>
</body>
</html>
