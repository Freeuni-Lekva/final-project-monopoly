package servlets;

import DAO.CardsDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            CardsDAO cDao = new CardsDAO();
            servletContextEvent.getServletContext().setAttribute("cDao", cDao);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
