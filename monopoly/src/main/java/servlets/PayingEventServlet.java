package servlets;

import gamelogic.Player;
import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class PayingEventServlet extends HttpServlet {
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
        if (player.getTile() == 7 || player.getTile() == 22 || player.getTile() == 36) {
            room.performChanceAction();
        } else if (player.getTile() == 2 || player.getTile() == 33 || player.getTile() == 17) {
            room.performCommunityChestAction();
        } else if (player.getTile() == 4) {
            player.addMoney(-player.getEventCost());
            room.getLog().addEntry(player.getUsername() + " payed income tax");
        } else if (player.getTile() == 38) {
            player.addMoney(-player.getEventCost());
            room.getLog().addEntry(player.getUsername() + " payed luxury tax");
        } else {
            player.addMoney(-player.getEventCost());
            Player receiver = room.getPlayers()[room.getPayingEventMoneyReceiverId()];
            receiver.addMoney(player.getEventCost());
            room.getLog().addEntry(player.getUsername() + " payed " + receiver.getUsername() + " a rent of " +
                    player.getEventCost());
        }
        player.setEvent("none");
        httpServletRequest.getRequestDispatcher("WEB-INF/playersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
