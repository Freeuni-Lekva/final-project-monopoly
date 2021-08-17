package servlets;

import gamelogic.Room;
import invitations.Lobby;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class StartGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/otherPlayersTurn.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomCode = (String)req.getSession().getAttribute("room-code");
        Lobby lobby = (Lobby)((HashMap)getServletContext().getAttribute("lobbies")).get(roomCode);
        if(!((HashMap)getServletContext().getAttribute("rooms")).containsKey(roomCode)){
            Vector<String> players = new Vector<>();
            for (String user: lobby.getCurPlayers()) {
                players.add(user);
            }
            Room room = new Room(players);
        }
    }
}
