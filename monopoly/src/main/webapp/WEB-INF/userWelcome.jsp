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
</head>
<body>
<%
    String name = (String) request.getSession().getAttribute("username");
%>
Hello <%=name%>
</body>
</html>
