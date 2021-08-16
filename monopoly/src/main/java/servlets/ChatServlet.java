package servlets;

import register.AccountsDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ChatServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String username = (String) httpServletRequest.getParameter("user");
        try {
            AccountsDao dao = new AccountsDao();
            if(!dao.userExists(username)) {
                httpServletRequest.getRequestDispatcher("/WEB-INF/chatEnterError.html").forward(httpServletRequest,httpServletResponse);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        httpServletRequest.getSession().setAttribute("receiver", username);
        httpServletRequest.getRequestDispatcher("/WEB-INF/displayChat.jsp").forward(httpServletRequest,httpServletResponse);
    }
}
