package dao.factory_business;

import business.system.Category;
import business.system.Favory;
import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import business.system.offer.ToolSate;
import business.system.user.User;
import dao.structure.FavoryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.factory_business.OfferDaoMySQL.*;

public class FavoryDaoMySQL extends FavoryDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected FavoryDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Favory find(int user_id,int offer_id) {
        Favory fav = null;
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  " +
                        "FROM favory " +
                        "JOIN offer o on favory.offer_id = o.offer_id "+
                        "JOIN user u on favory.user_id = u.user_id "+
                        "LEFT JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL +
                        " WHERE u."+UserDaoMySQL.ID_COL+" = ? AND o."+OfferDaoMySQL.OFFER_ID_COL+" = ?"
            );
            prep.setInt(1,user_id);
            prep.setInt(2,offer_id);

            ResultSet rs = prep.executeQuery();
            if(rs.next()){
                if(rs.getInt(OfferDaoMySQL.OFFER_ID_COL) == (offer_id)){
                    fav = this.createFavoryFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return fav;
    }

    @Override
    public Favory create(Favory obj) {
        try {
            PreparedStatement prep = connection.prepareStatement(
                    "INSERT INTO favory (user_id,offer_id) "+
                    "VALUES (?,?) "
            );
            prep.setInt(1,obj.getUser().getUser_id());
            prep.setInt(2,obj.getOffer().getOffer_id());

            prep.executeUpdate();
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(Favory obj) {
        try{
            PreparedStatement prep = this.connection.prepareStatement(
                    "DELETE FROM favory WHERE "+UserDaoMySQL.ID_COL+" = ? AND "+OfferDaoMySQL.OFFER_ID_COL +" = ?"
            );
            prep.setInt(1,obj.getUser().getUser_id());
            prep.setInt(2,obj.getOffer().getOffer_id());
            prep.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * Creates and returns an offer from a query ResultSet
     * @param rs the ResultSet that contains offer information
     * @return new Offer or new PriorityOffer if isPriority=True
     * @throws SQLException
     */
    private Favory createFavoryFromRs(ResultSet rs) throws SQLException {
        User u = new User(
                rs.getInt(UserDaoMySQL.ID_COL),
                rs.getString(UserDaoMySQL.FIRST_NAME_COL),
                rs.getString(UserDaoMySQL.LAST_NAME_COL),
                rs.getString(UserDaoMySQL.EMAIL_COL),
                rs.getString(UserDaoMySQL.PASSWORD_COL),
                rs.getString(UserDaoMySQL.USERCITY_COL),
                rs.getString(UserDaoMySQL.PHONENUMBER_COL),
                rs.getBoolean(UserDaoMySQL.ISADMIN),
                rs.getBoolean(UserDaoMySQL.ISBANNED),
                rs.getString(UserDaoMySQL.SALT_COL)
        );
        Offer o = createOfferFromRs(rs);

        return new Favory(u,o);
    }

    private Offer createOfferFromRs(ResultSet rs) throws SQLException {
        String ts = rs.getString(TOOL_STATE_COL);
        ToolSate toolState = ToolSate.valueOf(ts);
        User user = new User(rs.getInt(USER_ID_COL),rs.getString(UserDaoMySQL.FIRST_NAME_COL),rs.getString(UserDaoMySQL.LAST_NAME_COL),rs.getString(UserDaoMySQL.EMAIL_COL),rs.getString(UserDaoMySQL.PASSWORD_COL),rs.getString(UserDaoMySQL.USERCITY_COL),rs.getString(UserDaoMySQL.PHONENUMBER_COL),rs.getBoolean(UserDaoMySQL.ISADMIN),rs.getBoolean(UserDaoMySQL.ISBANNED),rs.getString(UserDaoMySQL.SALT_COL));
        Category category = new Category(rs.getInt(CategoryDaoMySQL.CATEGORY_ID_COL),rs.getString(CategoryDaoMySQL.CATEGORY_NAME_COL),rs.getBoolean(CategoryDaoMySQL.ISVALIDATED));
        if (rs.getBoolean(ISPRIORITY)){
            return new PriorityOffer(rs.getInt(OFFER_ID_COL),rs.getString(TITLE_COL),rs.getFloat(PRICE_PER_DAY_COL),rs.getString(DESCRPTION_COL),toolState,rs.getBoolean(ISPRIORITY),user,category,rs.getDate(DATE_START_PRIORITY_COL),rs.getDate(DATE_END_PRIORITY_COL));
        } else {
            return new Offer(rs.getInt(OFFER_ID_COL),rs.getString(TITLE_COL),rs.getFloat(PRICE_PER_DAY_COL),rs.getString(DESCRPTION_COL),toolState,rs.getBoolean(ISPRIORITY),user,category);
        }

    }

}
