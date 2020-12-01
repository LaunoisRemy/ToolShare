package business.facade;

import business.system.user.User;
import dao.factory.AbstractFactoryDAO;
import dao.structure.UserDAO;
import util.Cryptor;

import java.security.spec.InvalidKeySpecException;

public class SessionFacade {

    private User user;

    private SessionFacade() {
    }

    /**
     * The static class definition LazyHolder within it is not initialized until the JVM determines that LazyHolder must be executed
     */
    private static class LazyHolder {
        public static final SessionFacade INSTANCE= new SessionFacade();
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static SessionFacade getInstance(){
        return SessionFacade.LazyHolder.INSTANCE;
    }

    /**
     * Method return user connected
     * @return user
     */
    public User getUser(){
        return user;
    };

    /**
     * setter of the session user
     * @param user the user of the session
     */
    public void setUser(User user) {
        this.user = user;
    }


    public User login(String mail, String password) {

        UserDAO userDAO = AbstractFactoryDAO.getInstance().getUserDAO();

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
            SessionFacade session = SessionFacade.getInstance();
            session.setUser(user);
            return session.getUser();
        } else {
            return null;
        }

    }

    public User register(String email, String firstName, String lastName, String city, String phoneNumber, String password) {

        UserDAO userDAO = AbstractFactoryDAO.getInstance().getUserDAO();

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
