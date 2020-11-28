package dao;

import business.system.User;

import java.sql.SQLException;

public interface UserDAO {
    User getUserByID(int idUser) throws SQLException;
    User getUserByEmail(String mail) throws SQLException;

    void registerUser(String firstName, String lastName,String mail, String password) throws SQLException;

    boolean userExists(String mail) throws SQLException;

}
