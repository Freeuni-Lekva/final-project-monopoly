package servlets;

import DAO.CardsDAO;
import DAO.UserDAO;
import gamelogic.Room;
import invitations.Lobby;
import invitations.UserBuilder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.HashMap;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            CardsDAO cDao = new CardsDAO();
            servletContextEvent.getServletContext().setAttribute("cDao", cDao);

            UserDAO userDAO = new UserDAO("jdbc:mysql://localhost:3306/monopoly","root","makeitsecure");
            servletContextEvent.getServletContext().setAttribute("usersDAO",userDAO);
            UserBuilder userBuilder = new UserBuilder(userDAO);
            servletContextEvent.getServletContext().setAttribute("userBuilder",userBuilder);
            HashMap<String, Lobby> lobbies = new HashMap<>();
            servletContextEvent.getServletContext().setAttribute("lobbies",lobbies);
            HashMap<String, Room> rooms = new HashMap<>();
            servletContextEvent.getServletContext().setAttribute("rooms",rooms);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
