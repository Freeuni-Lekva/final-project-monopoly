package servlets;

import register.AccountsDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import register.Security;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            AccountsDao accDao = new AccountsDao();
            String username = (String) httpServletRequest.getParameter("username");
            String password = (String) httpServletRequest.getParameter("password");
            String securedPassword = Security.securedPassword(password);
            httpServletRequest.getSession().setAttribute("username", username);
            if(accDao.accountCorrect(username, securedPassword)){
                httpServletRequest.getRequestDispatcher("WEB-INF/userWelcome.jsp").forward(httpServletRequest,httpServletResponse);
            } else {
                httpServletRequest.getRequestDispatcher("WEB-INF/incorrectLogin.html").forward(httpServletRequest,httpServletResponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
