package dao.structure;

import business.system.user.User;

public interface UserDAO extends DAO<User> {

    User getUserByEmail(String email);

    boolean userExists(String email);

    String getSalt(String email);
}
