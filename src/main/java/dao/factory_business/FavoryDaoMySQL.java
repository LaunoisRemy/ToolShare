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
import java.util.ArrayList;
import java.util.List;

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
    public Favory find(int user_id,int... others) {
        int offer_id = others[0];
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
                    fav = createFavoryFromRs(rs);
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

    /**
     * Method which communicate with DB for update an obj T
     *
     * @param obj object to update in database
     * @return true if the object is update in database, else return null.
     */
    @Override
    public Favory update(Favory obj) {
        return null;
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

    public List<Offer> getAllFavories(User user){
        List<Offer> list = new ArrayList<>();
        try{
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT * FROM favory" +
                            " JOIN offer o on favory."+OFFER_ID_COL+" = o."+OFFER_ID_COL+
                            " JOIN user u ON o."+USER_ID_COL+" = u."+UserDaoMySQL.ID_COL+
                            " LEFT JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE favory."+UserDaoMySQL.ID_COL+" = ?"
            );
            prep.setInt(1,user.getUser_id());
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                list.add(OfferDaoMySQL.createOfferFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    /**
     * Creates and returns an offer from a query ResultSet
     * @param rs the ResultSet that contains offer information
     * @return new Offer or new PriorityOffer if isPriority=True
     * @throws SQLException
     */
    public static Favory createFavoryFromRs(ResultSet rs) throws SQLException {
        User u = UserDaoMySQL.createUserFromRs(rs);
        Offer o = OfferDaoMySQL.createOfferFromRs(rs);
        return new Favory(u,o);
    }


}
