<%@ page import="DAO.chatDAO" %>
<%@ page import="java.util.Vector" %><%--
  Created by IntelliJ IDEA.
  User: cking01201
  Date: 16.08.21
  Time: 05:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="loginStyle.css">
    <title>Chat</title>
</head>
<body>
<%
    chatDAO dao = new chatDAO((String) request.getSession().getAttribute("username"),
            (String) request.getSession().getAttribute("receiver"));
    Vector<String> senders = new Vector<String>();
    Vector<String> messages = new Vector<String>();
    dao.getMessages(senders, messages);
%>




<div class="middle-class">
    <% for(int i = senders.size() - 1; i >= 0; i--) { %>
    <h1 id="beRed"> <%=senders.get(i)%> sent: <%=messages.get(i)%></h1>
    <% } %>
</div>

<form action="/sendMessage" method="post">
    <div class = "bottom-class">
        <input type="text" name="chatText" id="chatText" required pattern={1,30}></input>
        <button type="submit" name="send" id="createButton"> Send </button>
    </div>
</form>

<br>
<br>

<form action="/refresh" method="post">
    <div class = "some-class">
        <button type="submit" name="refresh" id="refreshButton"> Refresh </button>
    </div>
</form>

</body>
</html>
