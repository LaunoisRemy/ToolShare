package dao.structure;

import business.system.Category;
import business.system.user.User;
import dao.factory.dao.AbstractFactoryDAO;

import java.util.ArrayList;
import java.util.List;

public abstract class UserDAO implements DAO<User> {
    private static final UserDAO INSTANCE = AbstractFactoryDAO.getInstance().getUserDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static UserDAO getInstance(){
        return INSTANCE;
    }

    public abstract User getUserByEmail(String mail);

    public abstract boolean userExists(String mail);

    public abstract String getSalt(String mail);

    /**
     * Get recovery code of user by his mail
     * @param mail of user
     * @return recovery code
     */
    public abstract String getRecoveryCodeByMail(String mail);

    public abstract List<User> getAllUsers();
}

