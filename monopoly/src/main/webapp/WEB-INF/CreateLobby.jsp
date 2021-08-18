<%@ page import="invitations.UserBuilder" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Aernd
  Date: 16.08.2021
  Time: 04:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Lobby</title>
    <link href="../CreateLobbyStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <form method="post" action="lobbyMade">
        <ul class = "classList">
        <%
        UserBuilder ub = (UserBuilder) getServletConfig().getServletContext().getAttribute("userBuilder");
        ArrayList<String> friends = ub.getInstance((String)request.getSession().getAttribute("username")).getFriends();
        for (int i = 0; i < friends.size(); i++) {
        %>
        <li><input type="checkbox" class = "checkboxes" onclick="limitPlayers(<%=i%>)" name="<%=friends.get(i)%>"> <%=friends.get(i)%></li>

        <%}%></ul>
        <input id="create" type="submit" value="Create!" disabled>
    </form>
    <script>
        function limitPlayers(checkboxNum) {
            let checked = 0;
            let checkBoxes = document.querySelectorAll('.checkboxes');
            for(let i = 0; i < checkBoxes.length; i++) {
                if(checkBoxes[i].checked) {
                    checked++;
                }
            }
            if(checked > 7) {
                checkBoxes[checkboxNum].checked = false;
                alert("The maximum number of players is 8");
            } else if(checked == 0){
                document.getElementById('create').disabled = true;
            } else {
                document.getElementById('create').disabled = false;
            }
        }
    </script>
</body>
</html>
