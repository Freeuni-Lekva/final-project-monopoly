package servlets;

import invitations.UserBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sendRequest")
public class AddFriendServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username1 = req.getParameter("newFriend");
        String username2 = (String)req.getSession().getAttribute("username");
        try {
            ((UserBuilder)getServletContext().getAttribute("userBuilder")).getInstance(username1).newRequest(username2);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        resp.sendRedirect("/friends");
    }
}
