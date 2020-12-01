package business.facade;

import business.system.Session;
import business.system.user.User;
import dao.factory.AbstractFactoryDAO;
import dao.structure.UserDAO;
import util.Cryptor;

import java.security.spec.InvalidKeySpecException;

public class UserFacade {
    AbstractFactoryDAO factory;
    public UserFacade(){
        factory = AbstractFactoryDAO.getInstance();
    }

    public User login(String mail, String password) {
        ;//AbstractFactory : getInstance (new implementation : new MySQL )
        UserDAO userDAO = factory.getUserDAO();

        //TODO : uncomment when the register view is implemented & update password check

        /* GET SALT
        String salt = userDAO.getSalt(mail);
        if (salt == null) {
            return null;
        }

        GENERATE HASHED PASSWORD
        String encryptedPassword = null;
        try {
            encryptedPassword = Cryptor.generateHash(password, salt);
        } catch (InvalidKeySpecException throwable) {
            throwable.printStackTrace();
            return null;
        }
         */

        User user = userDAO.getUserByEmail(mail);
        if (user != null && user.getPassword().equals(password)) {
            Session session = Session.getInstance();
            session.setUser(user);
            return session.getUser();
        } else {
            return null;
        }

    }

    public User register(String email, String firstName, String lastName, String city, String phoneNumber, String password) {

        UserDAO userDAO = factory.getUserDAO();

        String salt = Cryptor.getSaltRandom();
        String hashedPassword = null;
        try {
            hashedPassword = Cryptor.generateHash(password, salt);
        } catch (InvalidKeySpecException throwable) {
            throwable.printStackTrace();
            return null;
        }

        User registeredUser = new User(firstName, lastName, email, hashedPassword, city, phoneNumber, salt);

        if(userDAO.create(registeredUser)) {
            return login(email, password);
        } else {
            return null;
        }
    }
}
