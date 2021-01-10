package business.facade;

import business.exceptions.*;
import business.management.UserManagement;
import business.system.user.OrdinaryUser;
import business.system.user.User;
import dao.factory.dao.AbstractFactoryDAO;
import dao.structure.UserDAO;
import util.Mail;

import java.security.SecureRandom;
import java.util.List;

/**
 * Facade of all actions on Session or User
 */
public class SessionFacade {

    private User user;
    private final UserManagement userManagement = new UserManagement();
    private static final SessionFacade INSTANCE = new SessionFacade();

    /**
     * Constructor
     */
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
        } else if (user.getIsBanned()){
            throw new UserBannedException("User is banned");
        } else {
            //GET user salt
            String salt = userDAO.getSalt(email);
            //COMPARE user password
            boolean goodPassword = userManagement.comparePassword(user, password, salt);
            if(!goodPassword){
                throw new WrongPasswordException("Wrong password");
            }else{
                this.setUser(user);
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

    /**
     * Method which update information of the user
     * @param email the new email of the user
     * @param firstName the new firstname of the user
     * @param lastName the new lastname of the user
     * @param city the new city of the user
     * @param phoneNumber the new phone number of the user
     * @param password the new password of the user
     */
    public void updateProfile(String email, String firstName, String lastName, String city, String phoneNumber, String password){
        UserDAO userDAO = UserDAO.getInstance();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        ((OrdinaryUser)user.getRole()).setUserCity(city);
        ((OrdinaryUser)user.getRole()).setPhoneNumber(phoneNumber);
        user.setPassword(password);

        userDAO.update(user);

    }

    /**
     * Method which update information of the user
     * @param user the updated user
     */
    public void updateUser(User user){
        UserDAO.getInstance().update(user);
    }

    /**
     * Method which create a code of size 6 with number and characters
     * @return code with a length of six
     */
    private String generateCode(){
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int sizeAlpha = alphabet.length();
        for(int i = 0; i < 6; i++)
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return sb.toString();

    }

    /**
     * Method to send an email to the user
     * @param mail the mail of the user
     * @throws ObjectNotFoundException Exception that is raised if the object is not found
     */
    public void sendMail(String mail) throws ObjectNotFoundException {
        User user = UserDAO.getInstance().getUserByEmail(mail);
        if(user == null){
            throw new ObjectNotFoundException("User not found");
        }else{
            user.setRecoveryCode(generateCode());
            UserDAO.getInstance().update(user);
            Mail.sendMail("New password",
                    "Here your code to change password :  " + user.getRecoveryCode(),
                    user.getEmail()
            );
        }
    }

    /**
     * Method which check if the recovery code of the user is ok
     * @param code the recovery code of the user
     * @param mail the mail of the user
     * @return true if the code is validated, else false
     */
    public boolean checkCode(String code,String mail) {
        User user =  UserDAO.getInstance().getUserByEmail(mail);
        return code.equals(user.getRecoveryCode());
    }

    /**
     * Method for the user to change his password
     * @param password the new password of the user
     * @param mail the mail of the user
     */
    public void changePassword(String password,String mail){
        User user = UserDAO.getInstance().getUserByEmail(mail);
        String salt = userManagement.getRandomSalt();
        String hashedPassword = userManagement.getHashedPassword(password, salt);
        user.setSalt(salt);
        user.setPassword(hashedPassword);
        UserDAO.getInstance().update(user);
    }

    /**
     * Method which list all the users of the system
     * @return a List of User
     */
    public List<User> getAllUsers(){
        return UserDAO.getInstance().getAllUsers();
    }

    /**
     * Method which delete a user
     * @param u the user to delete
     * @return true if the user is correctly deleted, else false
     */
    public boolean deleteUser(User u){
        return UserDAO.getInstance().delete(u);
    }

    public void logOut(){
        setUser(null);
    }

}
