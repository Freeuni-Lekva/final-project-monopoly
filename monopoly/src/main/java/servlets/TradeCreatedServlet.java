package servlets;

import cardTypes.Card;
import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class TradeCreatedServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Room room = ((Map<String, Room>) getServletContext().getAttribute("rooms"))
                .get(httpServletRequest.getSession().getAttribute("room-code"));
        room.getLog().addEntry(room.getPlayers()[room.getCurrentPlayerId()].getUsername() + " offered a trade to " +
                room.getPlayers()[room.getTrade().getTradeReceiverId()].getUsername());
        Vector<Card> playerCards = room.getPlayerCards(room.getTrade().getTradeReceiverId());
        Vector<Card> selectedCards = new Vector<>();
        for (Card card : playerCards)
            if (httpServletRequest.getParameter(card.getCardName()) != null &&
                    httpServletRequest.getParameter(card.getCardName()).equals("selected"))
                selectedCards.add(card);
        room.getTrade().setDemandedCards(selectedCards);
        room.getTrade().setDemandedGetOutOfJailCards(Integer
                        .parseInt(httpServletRequest.getParameter("get-out-of-jail-num")));
        room.getTrade().setDemandedMoney(Integer.parseInt(httpServletRequest.getParameter("money-offered")));
        room.getPlayers()[room.getTrade().getTradeReceiverId()].setEvent("trade");
        room.getPlayers()[room.getCurrentPlayerId()].setWaiting(true);
        httpServletRequest.getRequestDispatcher("WEB-INF/playersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("WEB-INF/playersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
