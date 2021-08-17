package servlets;

import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class BankruptcyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Room room = ((Map<String, Room>) getServletContext().getAttribute("rooms"))
                .get(httpServletRequest.getSession().getAttribute("room-code"));
        room.getLog().addEntry(room.getPlayers()[room.getCurrentPlayerId()].getUsername() + " declared bankruptcy");
        room.declareBankruptcy();
        httpServletRequest.getRequestDispatcher("WEB-INF/otherPlayersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);

    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("WEB-INF/otherPlayersTurn.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}
