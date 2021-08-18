package servlets;

import gamelogic.Player;
import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UnoccupiedPropertyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("WEB-INF/playersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }


    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Room room = ((Map<String, Room>) getServletContext().getAttribute("rooms"))
                .get(httpServletRequest.getSession().getAttribute("room-code"));
        Player player = room.getPlayers()[room.getCurrentPlayerId()];
        room.purchaseProperty();
        player.setEvent("none");
        room.getLog().addEntry(player.getUsername() + " purchased " + room.getPropertyToPurchase());
        httpServletRequest.getRequestDispatcher("WEB-INF/playersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
