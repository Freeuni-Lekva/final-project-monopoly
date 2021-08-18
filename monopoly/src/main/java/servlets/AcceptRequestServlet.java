package servlets;

import invitations.UserBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/acceptRequest")
public class AcceptRequestServlet extends HttpServlet {
    UserBuilder userBuilder;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username1 = (String)req.getSession().getAttribute("username");
        String username2 = req.getHeader("acceptedUser");
        try {
            userBuilder.getInstance(username1).acceptRequest(username2);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void init(){
        userBuilder = (UserBuilder) getServletContext().getAttribute("userBuilder");
    }
}
