package business.management;

import business.system.user.User;
import util.Cryptor;
import java.security.spec.InvalidKeySpecException;

/**
 * UserManagement is a set of user methods
 */
public class UserManagement {

    /**
     * Constructor
     */
    public UserManagement() {

    }

    /**
     * hash the password thanks to the salt
     * @param password the password of the user as a String
     * @param salt the salt of the user as a String
     * @return the hashed password if successful, else return null
     */
    public String getHashedPassword(String password, String salt) {
        String encryptedPassword;
        try {
            encryptedPassword = Cryptor.generateHash(password, salt);
        } catch (InvalidKeySpecException throwable) {
            throwable.printStackTrace();
            return null;
        }
        return encryptedPassword;
    }

    /**
     *
     * @param user the user you want to check the password
     * @param password the potential password of the user as a string, it should be hashed
     * @param salt the salt of the user
     * @return the user if the password matched, else return null
     */
    public boolean comparePassword(User user, String password, String salt) {
        return user != null && user.getPassword().equals(this.getHashedPassword(password, salt));
    }

    /**
     * generate a random salt
     * @return a Salt as a String
     */
    public String getRandomSalt() {
        return Cryptor.getSaltRandom();
    }

}
