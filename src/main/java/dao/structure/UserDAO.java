package dao.structure;

import business.system.User;

import java.sql.SQLException;

public abstract class UserDAO extends DAO<User> {
    public abstract User getUserByEmail(String mail);
    public abstract boolean userExists(String mail);
}
