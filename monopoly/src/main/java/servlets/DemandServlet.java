package servlets;

import cardTypes.Card;
import gamelogic.Room;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class DemandServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Room room = ((Map<String, Room>) getServletContext().getAttribute("rooms"))
                .get(httpServletRequest.getSession().getAttribute("room-code"));
        Vector<Card> currPlayerCards = room.getPlayerCards(room.getCurrentPlayerId());
        Vector<Card> selectedCards = new Vector<>();
        for (Card card : currPlayerCards)
            if (httpServletRequest.getParameter(card.getCardName()) != null &&
                    httpServletRequest.getParameter(card.getCardName()).equals("selected"))
                selectedCards.add(card);
        room.createTrade(selectedCards, Integer.parseInt(httpServletRequest.getParameter("trade-with")),
                Integer.parseInt(httpServletRequest.getParameter("get-out-of-jail-num")),
                Integer.parseInt(httpServletRequest.getParameter("money-offered")));
        httpServletRequest.getRequestDispatcher("WEB-INF/tradeDemand.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
