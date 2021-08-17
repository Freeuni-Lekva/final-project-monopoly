package servlets;

import invitations.Lobby;
import invitations.UserBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/lobby")
public class LobbyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomCode = req.getParameter("invitation");
        req.getSession().setAttribute("room-code", roomCode);
        try {
            ((UserBuilder)getServletContext().getAttribute("userBuilder")).
                    getInstance((String) req.getSession().getAttribute("username")).removeInvitation(roomCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("WEB-INF/Lobby.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/Lobby.jsp").forward(req, resp);
    }
}
