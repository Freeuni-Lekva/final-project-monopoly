package servlets;

import gamelogic.Player;
import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MoveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Room room = ((Map<String, Room>) getServletContext().getAttribute("rooms"))
                .get(httpServletRequest.getSession().getAttribute("room-code"));
        Player player = room.getPlayers()[room.getCurrentPlayerId()];
        int firstDie = Integer.parseInt(httpServletRequest.getParameter("first-die"));
        int secondDie = Integer.parseInt(httpServletRequest.getParameter("second-die"));
        room.setDice(firstDie, secondDie);
        room.move(true, player.getTile() + firstDie + secondDie, 1, 1);
        httpServletRequest.getRequestDispatcher("WEB-INF/playersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }

}
