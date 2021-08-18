package servlets;

import DAO.chatDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SendMessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String sender = (String) httpServletRequest.getSession().getAttribute("username");
        String receiver = (String) httpServletRequest.getSession().getAttribute("receiver");
        try {
            chatDAO dao = new chatDAO(sender, receiver);
            dao.sendMessage((String) httpServletRequest.getParameter("chatText"));
            httpServletRequest.getRequestDispatcher("/WEB-INF/displayChat.jsp").forward(httpServletRequest, httpServletResponse);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
