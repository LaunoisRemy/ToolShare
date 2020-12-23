package dao.factory_business;

import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import business.system.offer.ToolSate;
import business.system.user.User;
import dao.structure.OfferDAO;
import dao.structure.UserDAO;

import java.sql.*;
import java.util.ArrayList;

public class OfferDaoMySQL extends OfferDAO {
    static final String TITLE_COL = "title";
    static final String PRICE_PER_DAY_COL = "pricePerDay";
    static final String DESCRPTION_COL = "description";
    static final String ISPRIORITY = "isPriority";
    static final String DATE_START_PRIORITY_COL = "dateStartPriority";
    static final String DATE_END_PRIORITY_COL = "dateEndPriority";
    static final String TOOL_STATE_COL = "toolState";
    static final String USER_ID_COL = "user_id";
    static final String CATEGORY_ID_COL = "category_id";
    static final String OFFER_ID_COL = "offer_id";
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected OfferDaoMySQL(Connection connection) {
        this.connection = connection;
    }
    /**
     * method which communicate with the db in order to find an offer with the specified id
     * @param id id of the Offer the system wants
     * @return the Offer founded
     */
    @Override
    public Offer find(int id,int... others) {
        Offer offer = null;
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o."+USER_ID_COL+" = u."+UserDaoMySQL.ID_COL+
                            " LEFT JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE "+OFFER_ID_COL+" = ?"
            );
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                if(rs.getInt(OFFER_ID_COL) == (id)){
                    offer = this.createOfferFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return offer;
    }

    /**
     * method which create a new offer in the db
     * @param obj Offer to save in database
     * @return the Offer created
     */
    @Override
    public Offer create(Offer obj) {
        try {
            String sql = "INSERT INTO offer ("+TITLE_COL+","+
                            PRICE_PER_DAY_COL+","+
                            DESCRPTION_COL+","+
                            ISPRIORITY+","+
                            DATE_START_PRIORITY_COL+","+
                            DATE_END_PRIORITY_COL+","+
                            TOOL_STATE_COL+","+
                            USER_ID_COL+","+
                            CATEGORY_ID_COL+") " +
                            "VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            prep.setString(1,obj.getTitle());
            prep.setFloat(2,obj.getPricePerDay());
            prep.setString(3,obj.getDescription());
            prep.setBoolean(4,obj.getIsPriority());

            if(obj.getIsPriority()){
                PriorityOffer o = (PriorityOffer) obj;
                prep.setDate(5, (Date) o.getDateStartPriority());
                prep.setDate(6, (Date) o.getDateEndPriority());
            } else {
                prep.setDate(5,null);
                prep.setDate(6,null);
            }

            prep.setString(7,obj.getToolSate().name());
            prep.setInt(8,obj.getUser().getUser_id());
            prep.setInt(9, obj.getCategory().getCategoryId());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setOffer_id(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * method which update an offer in the db
     * @param obj offer to update in database
     * @return the updated offer
     */
    @Override
    public Offer update(Offer obj) {
        try {
            String sql =
                    "UPDATE offer " +
                            "SET "+TITLE_COL+" = ?, "+PRICE_PER_DAY_COL+" = ?, "+DESCRPTION_COL+" = ?, "+ISPRIORITY+" = ?, "+DATE_START_PRIORITY_COL+" = ?, "+DATE_END_PRIORITY_COL+" = ?, "+TOOL_STATE_COL+" = ?, "+USER_ID_COL+" = ?, "+CATEGORY_ID_COL+" = ? " +
                            "WHERE "+OFFER_ID_COL+" = ?";

            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            prep.setString(1,obj.getTitle());
            prep.setFloat(2,obj.getPricePerDay());
            prep.setString(3,obj.getDescription());
            prep.setBoolean(4,obj.getIsPriority());

            if(obj.getIsPriority()){
                PriorityOffer o = (PriorityOffer) obj;
                prep.setDate(5, (Date) o.getDateStartPriority());
                prep.setDate(6, (Date) o.getDateEndPriority());
            } else {
                prep.setDate(5,null);
                prep.setDate(6,null);
            }

            prep.setString(7,obj.getToolSate().name());
            prep.setInt(8,obj.getUser().getUser_id());
            prep.setInt(9, obj.getCategory().getCategoryId());
            prep.setInt(10,obj.getOffer_id());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * method which delete an offer from the db
     * @param obj offer to delete in database
     * @return true if the offer is deleted, else false
     */
    @Override
    public boolean delete(Offer obj) {
        try {
            String sql =
                    "DELETE FROM offer WHERE "+OFFER_ID_COL+" = ?";

            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1,obj.getOffer_id());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * method which communicate with the db in order to find offers with a specified user id
     * @param idUser id of the user the system wants
     * @return the list of Offers founded
     */
    @Override
    public ArrayList getOffersFromUser(int idUser) {
        ArrayList res = new ArrayList<Offer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o."+USER_ID_COL+" = u."+UserDaoMySQL.ID_COL+
                            " LEFT JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE "+USER_ID_COL+" = ?"
            );
            prep.setInt(1,idUser);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                if(rs.getInt(USER_ID_COL) == (idUser)){
                    res.add(this.createOfferFromRs(rs));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /**
     * method which communicate with the db in order to find offers with a specified city
     * @param city city of the user the system wants
     * @return the list of Offers founded
     */
    @Override
    public ArrayList getOffersByCity(String city) {
        ArrayList res = new ArrayList<Offer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o."+USER_ID_COL+" = u."+UserDaoMySQL.ID_COL+
                            " LEFT JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE "+UserDaoMySQL.USERCITY_COL+" = ?"
            );

            prep.setString(1,city);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                if(rs.getString(4).equals(city)){
                    res.add(this.createOfferFromRs(rs));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /*@Override
    public ArrayList getLatestOffers() {
        ArrayList res = new ArrayList<Offer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer ORDER BY ?"
            );

            prep.setString(1,DATE_START_PRIORITY_COL);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(this.createOfferFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }*/

    /**
     * Method that allows to search all the offers in the db and returns them
     * @return the list of all the offers
     */
    @Override
    public ArrayList getAllOffers() {
        ArrayList res = new ArrayList<Offer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o."+USER_ID_COL+" = u."+UserDaoMySQL.ID_COL+
                            " LEFT JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL
            );

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(this.createOfferFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /**
     * method which communicate with the db in order to find offers with a specified category id
     * @param category_id category id of the offer the system wants
     * @return the list of Offers founded
     */
    @Override
    public ArrayList getOffersByCategory(int category_id) {
        ArrayList res = new ArrayList<Offer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o."+USER_ID_COL+" = u."+UserDaoMySQL.ID_COL+
                            " LEFT JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE "+CATEGORY_ID_COL+" = ?"
            );

            prep.setInt(1,category_id);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                if(rs.getInt(CATEGORY_ID_COL) == (category_id)){
                    res.add(this.createOfferFromRs(rs));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /**
     * method which communicate with the db in order to find priority offers
     * @return the list of Offers founded
     */
    @Override
    public ArrayList getPriorityOffer() {
        ArrayList res = new ArrayList<PriorityOffer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o."+USER_ID_COL+" = u."+UserDaoMySQL.ID_COL+
                            " LEFT JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE "+ISPRIORITY+" = 1 ORDER BY "+DATE_START_PRIORITY_COL+" DESC"
            );

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(this.createOfferFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /**
     * Creates and returns an offer from a query ResultSet
     * @param rs the ResultSet that contains offer information
     * @return new Offer or new PriorityOffer if isPriority=True
     * @throws SQLException
     */
    public static Offer createOfferFromRs(ResultSet rs) throws SQLException {
        String ts = rs.getString(TOOL_STATE_COL);
        ToolSate toolState = ToolSate.valueOf(ts);
        User user = UserDaoMySQL.createUserFromRs(rs);
        Category category = CategoryDaoMySQL.createCategoryFromRs(rs);

        if (rs.getBoolean(ISPRIORITY)){
            return new PriorityOffer(rs.getInt(OFFER_ID_COL),rs.getString(TITLE_COL),rs.getFloat(PRICE_PER_DAY_COL),rs.getString(DESCRPTION_COL),toolState,rs.getBoolean(ISPRIORITY),user,category,rs.getDate(DATE_START_PRIORITY_COL),rs.getDate(DATE_END_PRIORITY_COL));
        } else {
            return new Offer(rs.getInt(OFFER_ID_COL),rs.getString(TITLE_COL),rs.getFloat(PRICE_PER_DAY_COL),rs.getString(DESCRPTION_COL),toolState,rs.getBoolean(ISPRIORITY),user,category);
        }

    }
}
