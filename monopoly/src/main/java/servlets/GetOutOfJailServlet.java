package servlets;

import gamelogic.Player;
import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class GetOutOfJailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("/WEB-INF/playersTurn.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Room room = ((Map<String, Room>) getServletContext().getAttribute("rooms"))
                .get(httpServletRequest.getSession().getAttribute("room-code"));
        Player player = room.getPlayers()[room.getCurrentPlayerId()];
        String getOutOfJailWay = httpServletRequest.getParameter("get-out-button");
        if (getOutOfJailWay.equals("used-card")) {
            player.takeGetOutOfJailCard();
            room.returnGetOutOfJailCard();
        } else if (getOutOfJailWay.equals("paid-money")) {
            player.addMoney(-50);
        }
        player.freeFromJail();
        httpServletRequest.getRequestDispatcher("WEB-INF/playersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
