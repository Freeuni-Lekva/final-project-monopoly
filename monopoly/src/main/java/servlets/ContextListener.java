package servlets;

import DAO.CardsDAO;
import DAO.UserDAO;
import gamelogic.Room;
import invitations.Lobby;
import invitations.UserBuilder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            CardsDAO cDao = new CardsDAO();
            servletContextEvent.getServletContext().setAttribute("cDao", cDao);

            UserDAO userDAO = new UserDAO("jdbc:mysql://localhost:3306/accounts","root",
                    "rootroot");
            servletContextEvent.getServletContext().setAttribute("usersDAO",userDAO);
            UserBuilder userBuilder = new UserBuilder(userDAO);
            servletContextEvent.getServletContext().setAttribute("userBuilder",userBuilder);
            HashMap<String, Lobby> lobbies = new HashMap<>();
            servletContextEvent.getServletContext().setAttribute("lobbies",lobbies);
            HashMap<String, Room> rooms = new HashMap<>();
            servletContextEvent.getServletContext().setAttribute("rooms",rooms);

            // !!!!!! Enter the path of the project here !!!!!!
            File file = new File("/home/cking01201/Desktop/finalproject/final-project-monopoly/monopoly/src/rules.txt");
            Scanner scanner = new Scanner(file);
            String rules = "";
            while (scanner.hasNextLine()) rules += scanner.nextLine() + "<br>";
            rules += "<br>";
            servletContextEvent.getServletContext().setAttribute("rules", rules);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
