package servlets;

import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class TradeConfirmServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Room room = ((Map<String, Room>) getServletContext().getAttribute("rooms"))
                .get(httpServletRequest.getSession().getAttribute("room-code"));
        room.getLog().addEntry(httpServletRequest.getSession().getAttribute("username") + " " +
                        httpServletRequest.getParameter("trade-button") + " " +
                        room.getPlayers()[room.getCurrentPlayerId()].getUsername() + "'s trade offer");
        if (httpServletRequest.getParameter("trade-button").equals("accepted")) {
            room.performTrade();
            room.setTradeMessage("Your trade offer has been accepted");
        } else {
            room.setTradeMessage("Your trade offer has been declined");
        }
        room.setTradeDecided(true);
        room.getPlayers()[room.getCurrentPlayerId()].setWaiting(false);
        room.getPlayers()[room.getTrade().getTradeReceiverId()].setEvent("none");
        httpServletRequest.getRequestDispatcher("WEB-INF/otherPlayersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("WEB-INF/otherPlayersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
