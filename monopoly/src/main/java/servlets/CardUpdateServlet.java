package servlets;

import cardTypes.Card;
import cardTypes.PropertyCard;
import gamelogic.Player;
import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CardUpdateServlet extends HttpServlet {

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
        room.getLog().addEntry(player.getUsername() + " updated their properties");
        for (Card card : room.getPlayerCards(room.getCurrentPlayerId())) {
            int level = Integer.parseInt(httpServletRequest.getParameter(card.getCardName()));
            if (level <= -1) {
                card.setMortgage(true);
            } else if (level == 0) {
                card.setMortgage(false);
                if (card instanceof PropertyCard)
                    ((PropertyCard)card).setNumHouses(0);
            } else {
                ((PropertyCard)card).setNumHouses(level);
            }
        }
        player.addMoney(Integer.parseInt(httpServletRequest.getParameter("money")) - player.getMoney());
        httpServletRequest.getRequestDispatcher("WEB-INF/playersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
