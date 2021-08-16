<%--
  Created by IntelliJ IDEA.
  User: cking01201
  Date: 13.08.21
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello Sir</title>
    <link rel="stylesheet" type="text/css" href="loginStyle.css">
</head>
<body>
    <%
        String name = (String) request.getSession().getAttribute("username");
    %>

    <img src="monopolian.png" alt="monopolyman" height="100%" class="monopolianman">
    <img src="money.png" alt="moneycolor" height=300px width=400px class="money">

    <div class="middle-class">
        <h2 id=begolden> Good luck <%=name%> <h2>
        <form action="/join-room" method="post">
            <button type="submit" name="Join" id="joinButton">Join room</button>
        </form>

        <form action="/create-room" method="post">
            <button type = "submit" name="Create room" id="createButton">Create room</button>
        </form>

        <form action="/leaderboards" method="post">
            <button type = "submit" name="leaderboards" id="leaderboards">Leaderboards</button>
        </form>

        <form action="/chat" method="post">
            <button type = "submit" name="chatButton" id="leaderBoards"> Chat someone </button>
        </form>
    </div>

</body>
</html>
