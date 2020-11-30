package business.facade;

import business.system.Session;
import business.system.User;
import dao.factory.AbstractFactoryDAO;
import dao.structure.UserDAO;
import java.sql.SQLException;

public class UserFacade {

    public UserFacade(){

    }

    public User login(String mail, String password) throws SQLException {

        UserDAO userDAO = AbstractFactoryDAO.getInstance().getUserUserDAO();

        User user = userDAO.getUserByEmail(mail);

        if (user.getPassword().equals(password)) {
            Session session = new Session();
            session.setUser(user);
            return user;
        } else {
            return null;
        }

    }

    public User register(String mail, String firstName, String lastName, String city, String password) {

        return null;
    }
}
