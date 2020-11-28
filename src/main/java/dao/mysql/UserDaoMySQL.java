package dao.mysql;

import business.system.User;
import dao.UserDAO;

import java.sql.SQLException;

public class UserDaoMySQL implements UserDAO {
    @Override
    public User getUserByID(int idUser) throws SQLException {
        return null;
    }

    @Override
    public User getUserByEmail(String mail) throws SQLException {
        return null;
    }

    @Override
    public void registerUser(String firstName, String lastName, String mail, String password) throws SQLException {

    }

    @Override
    public boolean userExists(String mail) throws SQLException {
        return false;
    }
}
