package dao.structure;

import business.system.user.User;
import dao.factory.AbstractFactoryDAO;
import dao.mysql.UserDaoMySQL;

public abstract class UserDAO implements DAO<User> {
    protected UserDAO() {
    }

    /**
     * The static class definition LazyHolder within it is not initialized until the JVM determines that LazyHolder must be executed
     */
    private static class LazyHolder {
        public static final UserDAO INSTANCE=AbstractFactoryDAO.getInstance().getUserDAO();
    }
    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static UserDAO getInstance(){

        return UserDAO.LazyHolder.INSTANCE;
    }

    public abstract User getUserByEmail(String email);

    public abstract boolean userExists(String email);

    public abstract String getSalt(String email);
}

