package dao.structure;

import business.system.user.User;
import dao.factory_business.AbstractFactoryDAO;

public abstract class UserDAO implements DAO<User> {
    private static final UserDAO INSTANCE = AbstractFactoryDAO.getInstance().getUserDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static UserDAO getInstance(){
        return INSTANCE;
    }

    public abstract User getUserByEmail(String email);

    public abstract boolean userExists(String email);

    public abstract String getSalt(String email);
}

