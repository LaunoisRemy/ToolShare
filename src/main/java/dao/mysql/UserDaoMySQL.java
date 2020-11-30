package dao.mysql;

import business.system.user.User;
import dao.structure.UserDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoMySQL extends UserDAO {
    private static final String LAST_NAME_COL = "lastname";
    private static final String FIRST_NAME_COL = "firstname";
    private static final String EMAIL_COL = "email";
    private static final String PASSWORD_COL = "password";
    private static final String ISBANNED = "isBanned";
    private static final String USERCITY_COL = "userCity";
    private static final String PHONENUMBER_COL = "phoneNumber";
    private static final String ISADMIN = "isAdmin";
    private static final String ID_COL = "user_id";


    /**
     * Method which communicate with DB for retrieve an User with id
     * @param id id of the user the system wants
     * @return a User if he exist in the DB, else return null
     */
    @Override
    public User find(int id) {
        User user = null;
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "SELECT *  FROM user WHERE user_id =?"
            );
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                if(rs.getInt(1) == (id)){
                    user = user = this.createUser(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean create(User obj) {
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "INSERT INTO user (lastname,firstname,email,password,userCity,phoneNumber,isAdmin,isBanned) " +
                            "VALUES (?,?,?,?,?,?,?,?)"
            );
            prep.setString(1,obj.getLastName());
            prep.setString(2,obj.getFirstName());
            prep.setString(3,obj.getEmail());
            prep.setString(4,obj.getPassword());
            prep.setString(5,obj.getUserCity());
            prep.setString(6,obj.getPhoneNumber());
            prep.setBoolean(7,obj.isAdmin());
            prep.setBoolean(8,obj.isBanned());

            prep.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean update(User obj) {
        return false;
    }

    @Override
    public boolean delete(User obj) {
        return false;
    }

    /**
     * Method which communicate with DB for retrieve an User with email
     * @param email email of the user the system wants
     * @return a User if he exist in the DB, else return null
     */
    @Override
    public User getUserByEmail(String email)  {
        User user = null;
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "SELECT *  FROM user WHERE email =?"
            );
            prep.setString(1,email);
            ResultSet rs = prep.executeQuery();
            if(rs.next()){
                if(rs.getString(3).equals(email)){
                    user = this.createUser(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
    private User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt(ID_COL),rs.getString(FIRST_NAME_COL),rs.getString(LAST_NAME_COL),rs.getString(EMAIL_COL),rs.getString(PASSWORD_COL),rs.getString(USERCITY_COL),rs.getString(PHONENUMBER_COL),rs.getBoolean(ISADMIN),rs.getBoolean(ISBANNED));
    }

    @Override
    public boolean userExists(String email){
        return false;
    }
}
