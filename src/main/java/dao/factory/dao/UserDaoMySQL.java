package dao.factory.dao;

import business.system.user.OrdinaryUser;
import business.system.user.User;
import dao.structure.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    static final String USER_ID = "user_id";
    static final String SALT_COL = "salt";
    static final String RECOVERYCODE_COL = "recoveryCode";
    private final Connection connection;

    /**
     * Constructor of UserDaoMySQL
     */
    protected UserDaoMySQL() {
        this.connection = AbstractFactoryDAO.connection;
    }


    /**
     * Method which communicate with DB to retrieve an User with id
     * @param id id of the user the system wants
     * @return a User if he exist in the DB, else return null
     */
    @Override
    public User find(int id,int... others) {
        User user = null;
        try {
            String sql = "SELECT *  FROM user WHERE "+ USER_ID +" =?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                user = createUserFromRs(rs);
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
            String sql = "INSERT INTO user ("+LAST_NAME_COL +","+
                    FIRST_NAME_COL+","+
                    EMAIL_COL+","+
                    PASSWORD_COL+","+
                    USERCITY_COL+","+
                    PHONENUMBER_COL+","+
                    ISADMIN+","+
                    ISBANNED+","
                    + SALT_COL +") "+
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
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
            prep.setBoolean(8,obj.getIsBanned());
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
            String sql ="UPDATE user " +
                    "SET "+EMAIL_COL + " = ?, " +
                    FIRST_NAME_COL + " = ?, " +
                    LAST_NAME_COL + " = ?, " +
                    PASSWORD_COL + " = ?, " +
                    USERCITY_COL + " = ?, " +
                    PHONENUMBER_COL + " = ?, " +
                    ISADMIN + " = ?, " +
                    ISBANNED + " = ?, " +
                    SALT_COL + " = ?, " +
                    RECOVERYCODE_COL + " = ? " +
                    "WHERE "+ USER_ID + " = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
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
            prep.setBoolean(8,obj.getIsBanned());
            prep.setString(9,obj.getSalt());
            prep.setString(10,obj.getRecoveryCode());
            prep.setInt(11,obj.getUser_id());
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
        try {
            String sql =
                    "DELETE FROM user WHERE "+USER_ID+" = ?";
            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1,obj.getUser_id());
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
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
                user = createUserFromRs(rs);
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


    @Override
    public List<User> getAllUsers() {
        List<User> res = new ArrayList<>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT * FROM user"
            );

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(UserDaoMySQL.createUserFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /**
     * Creates and returns an user from a query ResultSet
     * @param rs the ResultSet that contains user information
     * @return new User with rs information
     * @throws SQLException
     */
    public static User createUserFromRs(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt(USER_ID),
                rs.getString(FIRST_NAME_COL),
                rs.getString(LAST_NAME_COL),
                rs.getString(EMAIL_COL),
                rs.getString(PASSWORD_COL),
                rs.getString(USERCITY_COL),
                rs.getString(PHONENUMBER_COL),
                rs.getBoolean(ISADMIN),
                rs.getBoolean(ISBANNED),
                rs.getString(SALT_COL),
                rs.getString(RECOVERYCODE_COL));
     }

}
