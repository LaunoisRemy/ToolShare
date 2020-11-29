package dao.structure;

import business.system.User;

import java.sql.SQLException;

public interface UserDAO {
    User getUserByEmail(String mail);
    boolean userExists(String mail);

}
