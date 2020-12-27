package dao.factory.object;

import business.system.user.User;
import dao.factory.dao.AbstractFactoryDAO;
import dao.factory.dao.FactoryDAOMySQL;
import dao.factory.dao.UserDaoMySQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to generalize create object from ResultSet
 */
public class FactoryObject {

    private FactoryObject(){};


    /**
     * Creates and returns an user from a query ResultSet
     * @param rs the ResultSet that contains user information
     * @return new User with rs information
     * @throws SQLException
     */
    public static User createUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                getValueByInt(rs,UserMySql.USER_ID),
                getValueByString(rs,UserMySql.FIRST_NAME_COL),
                getValueByString(rs,UserMySql.LAST_NAME_COL),
                getValueByString(rs,UserMySql.EMAIL_COL),
                getValueByString(rs,UserMySql.PASSWORD_COL),
                getValueByString(rs,UserMySql.USERCITY_COL),
                getValueByString(rs,UserMySql.PHONENUMBER_COL),
                getValueByBoolean(rs,UserMySql.ISADMIN),
                getValueByBoolean(rs,UserMySql.ISBANNED),
                getValueByString(rs,UserMySql.SALT_COL),
                getValueByString(rs,UserMySql.RECOVERYCODE_COL)
        );
    }


    private static <T> T getValue(ResultSet rs, String nameColumn, Class<T> type) {
        try {
            return rs.getObject(nameColumn, type);
        } catch (SQLException e) {
            return null;
        }
    }


    private static String getValueByString(ResultSet rs, String nameColumn) {
        return getValue(rs,nameColumn,String.class);
    }


    private static Boolean getValueByBoolean(ResultSet rs, String nameColumn) {
        return getValue(rs,nameColumn,Boolean.class);
    }


    private static int getValueByInt(ResultSet rs, String nameColumn) {
        try {
            return rs.getInt(nameColumn);
        } catch (SQLException e) {
            return 0;
        }
    }


}
