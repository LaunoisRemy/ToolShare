package business.facade;

import business.exceptions.NotInsertedUser;
import business.exceptions.UserBannedException;
import business.exceptions.UserNotFoundException;
import business.management.UserManagement;
import business.system.user.User;
import dao.factory.AbstractFactoryDAO;
import dao.structure.UserDAO;

public class SessionFacade {

    private User user;
    private UserManagement userManagement = new UserManagement();

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


    public User login(String email, String password) throws UserNotFoundException, UserBannedException {

        //Create UserDAO
        UserDAO userDAO = AbstractFactoryDAO.getInstance().getUserDAO();

        //GET user
        User user = userDAO.getUserByEmail(email);

        if(user==null){
            throw new UserNotFoundException("User is not found");
        } else if (user.isBanned()){
            throw new UserBannedException("User is banned");
        } else {
            //GET user salt
            String salt = user.getSalt();
            this.setUser(userManagement.comparePassword(user, password, salt));
        }

        //return session user
        return this.getUser();

    }

    public void register(String email, String firstName, String lastName, String city, String phoneNumber, String password) throws NotInsertedUser {

        //Create UserDAO
        UserDAO userDAO = AbstractFactoryDAO.getInstance().getUserDAO();

        //GET random salt
        String salt = userManagement.getRandomSalt();

        //GET hashed password
        String hashedPassword = userManagement.getHashedPassword(password, salt);

        //CREATE a new user
        User registeredUser = new User(firstName, lastName, email, hashedPassword, city, phoneNumber, salt);

        //true if the user is well inserted in the bdd, false otherwise
        if(!userDAO.create(registeredUser)) {
            throw new NotInsertedUser("The user is not registered in the app");
        }
    }
}
