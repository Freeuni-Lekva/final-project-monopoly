package servlets;
import register.AccountsDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CreateAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            AccountsDao accDao = new AccountsDao();
            String username = (String)httpServletRequest.getParameter("username");
            String password = (String)httpServletRequest.getParameter("password");
            if(accDao.userExists(username)) {

            } else {
                accDao.addAccount(username, password);
                httpServletRequest.getSession().setAttribute("username", username);
                httpServletRequest.getRequestDispatcher("WEB-INF/userWelcome.jsp").forward(httpServletRequest,httpServletResponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
