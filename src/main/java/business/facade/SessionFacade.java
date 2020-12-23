package business.facade;

import business.exceptions.*;
import business.management.UserManagement;
import business.system.user.OrdinaryUser;
import business.system.user.User;
import dao.factory_business.AbstractFactoryDAO;
import dao.structure.UserDAO;

public class SessionFacade {

    private User user;
    private UserManagement userManagement = new UserManagement();
    private static final SessionFacade INSTANCE = new SessionFacade();

    private SessionFacade() {

    }



    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static SessionFacade getInstance(){
        return INSTANCE;
    }

    /**
     * Method return user connected
     * @return user
     */
    public User getUser(){
        return user;
    }

    /**
     * setter of the session user
     * @param user the user of the session
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * The login method called by the LoginController, it will set the user session as well
     * @param email the email of the user as a String
     * @param password the password of the user as a String
     * @return User
     * @throws ObjectNotFoundException if user is not found
     * @throws UserBannedException if user is banned
     */
    public User login(String email, String password) throws ObjectNotFoundException, UserBannedException, WrongPasswordException {

        //Create UserDAO
        UserDAO userDAO = UserDAO.getInstance();

        //GET user
        User user = userDAO.getUserByEmail(email);

        if(user==null){
            throw new ObjectNotFoundException("User is not found");
        } else if (user.isBanned()){
            throw new UserBannedException("User is banned");
        } else {
            //GET user salt
            String salt = userDAO.getSalt(email);
            //COMPARE user password
            this.setUser(userManagement.comparePassword(user, password, salt));
            if(this.user==null){
                throw new WrongPasswordException("Wrong password");
            }
        }

        //return session user
        return this.getUser();

    }

    /**
     * The register method called by the RegisterController, it will create a new user and login the user
     * @param email the email of the user as a String
     * @param firstName the first name of the user as a String
     * @param lastName the last name of the user as a String
     * @param city the city of the user as a String
     * @param phoneNumber the phone number of the user as a String
     * @param password the password of the user as a String
     * @param isAdmin true if the user is an admin, else false
     * @throws BadInsertionInBDDException if the user is not inserted
     */
    public void register(String email, String firstName, String lastName, String city, String phoneNumber, String password, boolean isAdmin) throws BadInsertionInBDDException {

        //Create UserDAO
        UserDAO userDAO = AbstractFactoryDAO.getInstance().getUserDAO();

        //GET random salt
        String salt = userManagement.getRandomSalt();

        //GET hashed password
        String hashedPassword = userManagement.getHashedPassword(password, salt);

        //CREATE a new user
        User registeredUser = new User(firstName, lastName, email, hashedPassword, city, phoneNumber, salt, isAdmin);

        //true if the user is well inserted in the bdd, false otherwise
        registeredUser = userDAO.create(registeredUser);
        if(registeredUser == null) {
            throw new BadInsertionInBDDException("The user is not registered in the app");
        }
    }

    public void sendMail(String mail) throws ObjectNotFoundException {
        User user = UserDAO.getInstance().getUserByEmail(mail);
        System.out.println(user);
        if(user == null){
            throw new ObjectNotFoundException("User not found");
        }
    }
    public void updateProfile(String email, String firstName, String lastName, String city, String phoneNumber, String password){
        //Get UserDAO
        UserDAO userDAO = UserDAO.getInstance();
        //Get the user
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        ((OrdinaryUser)user.getRole()).setUserCity(city);
        ((OrdinaryUser)user.getRole()).setPhoneNumber(phoneNumber);
        user.setPassword(password);

        userDAO.update(user);

    }

    public boolean checkCode(String code,String mail) {
        User user =  UserDAO.getInstance().getUserByEmail(mail);
        return code.equals(user.getRecoveryCode());
    }

    public void changePassword(String password,String mail) throws BadInsertionInBDDException {
        User user = UserDAO.getInstance().getUserByEmail(mail);

        String salt = userManagement.getRandomSalt();
        String hashedPassword = userManagement.getHashedPassword(password, salt);
        user.setSalt(salt);
        user.setPassword(hashedPassword);
        //User registeredUser = new User(firstName, lastName, email, hashedPassword, city, phoneNumber, salt, isAdmin);
        user = UserDAO.getInstance().update(user);
        if(user == null) {
            throw new BadInsertionInBDDException("The user is not registered in the app");
        }
    }


}
