package dao.structure;

import business.system.User;

import java.sql.SQLException;

public abstract class UserDAO extends DAO<User> {
    public abstract User getUserByEmail(String email);
    public abstract boolean userExists(String email);
}
