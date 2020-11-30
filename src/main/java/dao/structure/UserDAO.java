package dao.structure;

import business.system.user.User;

public abstract class UserDAO extends DAO<User> {

    public abstract User getUserByEmail(String email);

    public abstract boolean userExists(String email);

    public abstract String getSalt(String email);
}
