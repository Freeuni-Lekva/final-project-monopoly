<%@ page import="DAO.chatDAO" %>
<%@ page import="java.util.Vector" %><%--
  Created by IntelliJ IDEA.
  User: cking01201
  Date: 16.08.21
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="loginStyle.css">
    <title>Inbox</title>
</head>
<body>
<img src="chatFrame.jpg" alt="chatFrame" height="100%" class="monopolianman">
<%
    chatDAO dao = new chatDAO((String) request.getSession().getAttribute("username"),
            (String) request.getSession().getAttribute("receiver"));
    Vector<Integer> seens = new Vector<Integer>();
    Vector<String> senders = dao.getInbox(seens);
%>


<div class="middle-class">
    <% for(int i = 0; i < senders.size(); i++) {
        String isSeen = "Not seen yet";
        if(seens.get(i) == 1) {
            isSeen = "Already seen";
        }
    %>
    <h1 id="beRed"> <%=senders.get(i)%> :   <%=isSeen%></h1>
    <% } %>
</div>




</body>
</html>
