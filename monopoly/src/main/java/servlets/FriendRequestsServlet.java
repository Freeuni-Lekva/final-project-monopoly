package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/friendRequests")
public class FriendRequestsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("username") == null){
            resp.sendRedirect("/");
        }
        else {
            req.getRequestDispatcher("/WEB-INF/FriendRequests.jsp").forward(req, resp);
        }
    }
}
