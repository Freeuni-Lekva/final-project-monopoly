package servlets;

import gamelogic.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReturnToMenuServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Map<String, Room> rooms = (HashMap<String, Room>) getServletContext().getAttribute("rooms");
        if (rooms.containsKey(httpServletRequest.getSession().getAttribute("room-code")))
            rooms.remove(httpServletRequest.getSession().getAttribute("room-code"));
        httpServletRequest.getRequestDispatcher("/WEB-INF/userWelcome.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
