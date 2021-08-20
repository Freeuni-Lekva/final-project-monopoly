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
    <img src="money.png" alt="moneycolor" height=500px width=400px class="money">

    <div class="middle-class">
        
        <h2 id=begolden> Good luck <%=name%> </h2>

	    <form method="post" action="/friends">
		<button type="submit" >See friends</button>
	    </form>
	    <br>
	    <form method="post" action="/friendRequests">
		<button type="submit" >See friend requests</button>
	    </form>
	    <br>
	    <form method="post" action="/invitations">
		<button type="submit">See invitations</button>
	    </form>
	    <br>
	    <form action="/leaderboards" method="post">
		<button type = "submit" name="leaderboards" id="leaderboards">Leaderboards</button>
	    </form>
	    <br>
	    <form method="post" action="lobbyCreation">
		<button type="submit">Create Lobby</button>
	    </form>
	    <br>
	    <form method="post" action="/sendRequest">
		<input type="text" name="newFriend"><br/>
		<button type="submit" >Add a friend</button>
	    </form>
        

        <form action="/chat" method="post">
            <button type = "submit" name="chatButton" id="leaderBoards"> Chat someone </button>
        </form>
    </div>

</body>
</html>
