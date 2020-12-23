package dao.factory_business;

import business.system.user.OrdinaryUser;
import business.system.user.User;
import dao.structure.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Dao concrete of user using MySQL database
 */
public class UserDaoMySQL extends UserDAO {
    static final String LAST_NAME_COL = "lastname";
    static final String FIRST_NAME_COL = "firstname";
    static final String EMAIL_COL = "email";
    static final String PASSWORD_COL = "password";
    static final String ISBANNED = "isBanned";
    static final String USERCITY_COL = "userCity";
    static final String PHONENUMBER_COL = "phoneNumber";
    static final String ISADMIN = "isAdmin";
    static final String ID_COL = "user_id";
    static final String SALT_COL = "salt";
    static final String RECOVERYCODE_COL = "recoveryCode";
    private final Connection connection;

    /**
     * Constructor of UserDaoMySQL
     * @param connection to have a link of the connection
     */
    protected UserDaoMySQL(Connection connection) {
        this.connection = connection;
    }


    /**
     * Method which communicate with DB to retrieve an User with id
     * @param id id of the user the system wants
     * @return a User if he exist in the DB, else return null
     */
    @Override
    public User find(int id) {
        User user = null;
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM user WHERE user_id =?"
            );
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                if(rs.getInt(1) == (id)){
                    user = this.createUserFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    /**
     * Create a user in database by an object instance of User
     * @param obj object to save in database
     * @return
     */
    @Override
    public User create(User obj) {
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "INSERT INTO user (lastname,firstname,email,password,userCity,phoneNumber,isAdmin,isBanned, salt) " +
                            "VALUES (?,?,?,?,?,?,?,?,?)"
            );
            prep.setString(1,obj.getLastName());
            prep.setString(2,obj.getFirstName());
            prep.setString(3,obj.getEmail());
            prep.setString(4,obj.getPassword());
            if(obj.getRole().getNameRole().equals(OrdinaryUser.ORDINARY_USER)){
                OrdinaryUser role = (OrdinaryUser) obj.getRole();
                prep.setString(5,role.getUserCity());
                prep.setString(6,role.getPhoneNumber());
                prep.setBoolean(7,false);
            }else{
                prep.setString(5,null);
                prep.setString(6,null);
                prep.setBoolean(7,true);
            }
            prep.setBoolean(8,obj.isBanned());
            prep.setString(9, obj.getSalt());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setUser_id(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    /**
     * Update the user (instance of User) in database
     * @param obj object to update in database
     * @return user update or null if a problem has been raise
     */
    @Override
    public User update(User obj) {
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "UPDATE user " +
                        "SET email=?,firstname=?,lastname=?,password=?,userCity=?,phoneNumber=?,isAdmin=?,isBanned=?" +
                        "WHERE user_id=?"
            );
            prep.setString(1,obj.getEmail());
            prep.setString(2,obj.getFirstName());
            prep.setString(3,obj.getLastName());
            prep.setString(4,obj.getPassword());
            if(obj.getRole().getNameRole().equals(OrdinaryUser.ORDINARY_USER)){
                OrdinaryUser role = (OrdinaryUser) obj.getRole();
                prep.setString(5,role.getUserCity());
                prep.setString(6,role.getPhoneNumber());
                prep.setBoolean(7,false);
            }
            else{
                prep.setString(5,null);
                prep.setString(6,null);
                prep.setBoolean(7,true);
            }
            prep.setBoolean(8,obj.isBanned());
            prep.setInt(9,obj.getUser_id());

            int rs = prep.executeUpdate();
            return obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    /**
     * Delete the user (instance of User) in database
     * @param obj object to update in database
     * @return true for good delete
     */
    @Override
    public boolean delete(User obj) {
        return false;
    }

    /**
     * Method which communicate with DB to retrieve an User with email
     * @param email email of the user the system wants
     * @return a User if he exist in the DB, else return null
     */
    @Override
    public User getUserByEmail(String email)  {
        User user = null;
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "SELECT *  FROM user WHERE "+ EMAIL_COL +"  = ?"
            );
            prep.setString(1,email);
            ResultSet rs = prep.executeQuery();
            if(rs.next()){
                if(rs.getString(EMAIL_COL).equals(email)){
                    user = this.createUserFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    /**
     * Method which communicate with the DB to retrieve an User's salt thanks to his email
     * @param email email of the user the system wants the salt
     * @return the salt of the given user's email if it exists, else return null
     */
    @Override
    public String getSalt(String email) {
        try {
            String sql = "SELECT salt FROM user WHERE email =?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setString(1,email);
            ResultSet rs = prep.executeQuery();
            //String salt = rs.getString(1);
            String salt = null;
            if(rs.next()){
                salt = rs.getString(1);
            }
            return salt;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Get recovery code of user by his mail
     *
     * @param mail of user
     * @return recovery code
     */
    @Override
    public String getRecoveryCodeByMail(String mail) {
        String code="";
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "SELECT "+ RECOVERYCODE_COL +" FROM user WHERE " + EMAIL_COL  + " = ? "
            );
            prep.setString(1,mail);
            ResultSet rs = prep.executeQuery();
            if(rs.next()){
                code = rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return code;
    }

    /**
     * Creates and returns an user from a query ResultSet
     * @param rs the ResultSet that contains user information
     * @return new User with rs information
     * @throws SQLException
     */
    private User createUserFromRs(ResultSet rs) throws SQLException {
        return new User(rs.getInt(ID_COL),rs.getString(FIRST_NAME_COL),rs.getString(LAST_NAME_COL),rs.getString(EMAIL_COL),rs.getString(PASSWORD_COL),rs.getString(USERCITY_COL),rs.getString(PHONENUMBER_COL),rs.getBoolean(ISADMIN),rs.getBoolean(ISBANNED),rs.getString(SALT_COL));
     }

    @Override
    public boolean userExists(String email){
        return false;
    }
}
