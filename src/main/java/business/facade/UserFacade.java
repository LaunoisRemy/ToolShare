package business.facade;

import business.system.Session;
import business.system.user.User;
import dao.factory.AbstractFactoryDAO;
import dao.factory.TypeDB;
import dao.structure.UserDAO;

public class UserFacade {

    public UserFacade(){

    }

    public User login(String mail, String password) {

        UserDAO userDAO = AbstractFactoryDAO.getFactory(TypeDB.MySQL).getUserDAO();

        User user = userDAO.getUserByEmail(mail);
        if (user != null && user.getPassword().equals(password)) {
            Session session = Session.getInstance();
            session.setUser(user);
            return session.getUser();
        } else {
            return null;
        }

    }

    public User register(String mail, String firstName, String lastName, String city, String password) {

        return null;
    }
}
