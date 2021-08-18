package servlets;

import invitations.Lobby;
import invitations.UserBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/lobbyMade")
public class lobbyMadeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<String> friends = ((UserBuilder)getServletContext().getAttribute("userBuilder")).getInstance((String)req.getSession().getAttribute("username")).getFriends();
            ArrayList<String> invitedUsers = new ArrayList<>();
            for (String friend : friends) {
                if(req.getParameter(friend)!=null){
                    invitedUsers.add(friend);
                }
            }
            Lobby lobby = new Lobby((String) req.getSession().getAttribute("username"),invitedUsers,((UserBuilder) getServletContext().getAttribute("userBuilder")));
            ((HashMap)getServletContext().getAttribute("lobbies")).put(lobby.getKey(),lobby);
            req.getSession().setAttribute("room-code", lobby.getKey());
            req.getRequestDispatcher("WEB-INF/Lobby.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/Lobby.jsp").forward(req, resp);
    }
}
