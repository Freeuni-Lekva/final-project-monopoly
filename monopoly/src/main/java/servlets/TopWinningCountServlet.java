package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TopWinningCountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getSession().setAttribute("type", "top winning");
        httpServletRequest.getRequestDispatcher("/WEB-INF/mainLeaderboard.jsp").forward(httpServletRequest,httpServletResponse);
    }
}
