package dao.mysql;

import business.system.User;
import dao.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoMySQL implements UserDAO {
    private Connection connection;
    private final String LAST_NAME_COL = "lastname";
    private final String FIRST_NAME_COL = "firstname";
    private final String EMAIL_COL = "email";

    /**
     * Method which communicate with DB for retrieve an User with one id
     * @param idUser id of the user the system wants
     * @return a User if he exist in the DB, else return null
     */
    @Override
    public User getUserByID(int idUser){
        User user = null;
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "SELECT *  FROM user WHERE id =?"
            );
            prep.setInt(1,idUser);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                if(rs.getInt(1) == (idUser)){
                    user = new User(rs.getString(FIRST_NAME_COL),rs.getString(LAST_NAME_COL),rs.getString(EMAIL_COL));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByEmail(String mail)  {
        User user = null;
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "SELECT *  FROM user WHERE email =?"
            );
            prep.setString(1,mail);
            ResultSet rs = prep.executeQuery();
            if(rs.next()){
                if(rs.getString(3).equals(mail)){
                    user = new User(rs.getString(FIRST_NAME_COL),rs.getString(LAST_NAME_COL),mail);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public User connectUser(String mail, String password) {
        return null;
    }

    @Override
    public void registerUser(String firstName, String lastName, String mail, String password){

    }

    @Override
    public boolean userExists(String mail){
        return false;
    }
}
