package servlets;
import register.AccountsDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import register.Security;


public class CreateAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("createAccount.html").forward(httpServletRequest,httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            AccountsDao accDao = new AccountsDao();
            String username = (String)httpServletRequest.getParameter("username");
            String password = (String)httpServletRequest.getParameter("password");
            String securedPassword = Security.securedPassword(password);
            httpServletRequest.getSession().setAttribute("username", username);
            if(accDao.userExists(username)) {
                httpServletRequest.getRequestDispatcher("WEB-INF/userAlreadyExists.html").forward(httpServletRequest,httpServletResponse);
            } else {
                accDao.addAccount(username, securedPassword);
                httpServletRequest.getRequestDispatcher("WEB-INF/userWelcome.jsp").forward(httpServletRequest,httpServletResponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
