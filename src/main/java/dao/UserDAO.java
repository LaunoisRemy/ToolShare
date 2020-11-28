package dao;

import business.system.User;

import java.sql.SQLException;

public interface UserDAO {
    User getUserByID(int idUser);
    User getUserByEmail(String mail);
    User connectUser(String mail,String password);

    void registerUser(String firstName, String lastName,String mail, String password);

    boolean userExists(String mail);

}
