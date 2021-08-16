package servlets;

import invitations.Lobby;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Lobby lobby = (Lobby) ((HashMap)getServletContext().getAttribute("lobbies")).get(req.getQueryString());
        if(req.getSession().getAttribute("username") == null){
            resp.sendRedirect("/");
        }
        else if(!lobby.containsUser((String) req.getSession().getAttribute("username"))){
            resp.sendRedirect("/");
        }
        else{
            req.getRequestDispatcher("/WEB-INF/Lobby.jsp").forward(req, resp);
        }
        System.out.println(req.getQueryString());
    }
}
