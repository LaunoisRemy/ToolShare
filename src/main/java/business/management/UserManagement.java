package business.management;

import business.facade.SessionFacade;
import business.system.user.User;
import dao.mysql.UserDaoMySQL;
import dao.structure.UserDAO;
import util.Cryptor;

import java.security.spec.InvalidKeySpecException;

public class UserManagement {

    public UserManagement() {

    }

    /**
     *
     * @param password
     * @param salt
     * @return
     */
    public String getHashedPassword(String password, String salt) {
        String encryptedPassword = null;
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
     * @param user
     * @param password
     * @param salt
     * @return
     */
    public User comparePassword(User user, String password, String salt) {
        if (user != null && user.getPassword().equals(this.getHashedPassword(password, salt))) {
            return user;
        } else {
            return null;
        }
    }

    public String getRandomSalt() {
        return Cryptor.getSaltRandom();
    }

}
