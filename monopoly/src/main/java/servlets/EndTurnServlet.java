package servlets;

import gamelogic.Player;
import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class EndTurnServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Room room = ((Map<String, Room>) getServletContext().getAttribute("rooms"))
                .get(httpServletRequest.getSession().getAttribute("room-code"));
        Player player = room.getPlayers()[room.getCurrentPlayerId()];
        player.setEvent("none");
        if (player.getTurnsArrested() > 0) player.incrementTurnsArrested();
        player.setPairsRolledToZero();
        room.nextPlayer();
        httpServletRequest.getRequestDispatcher("WEB-INF/otherPlayersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("WEB-INF/otherPlayersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
