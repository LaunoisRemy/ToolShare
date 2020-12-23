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
    public Offer find(int id) {
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
            PreparedStatement prep = connection.prepareStatement(
                    "INSERT INTO offer (?,?,?,?,?,?,?,?,?) " +
                            "VALUES (?,?,?,?,?,?,?,?,?)"
            );

            prep.setString(1,TITLE_COL);
            prep.setString(2,PRICE_PER_DAY_COL);
            prep.setString(3,DESCRPTION_COL);
            prep.setString(4,ISPRIORITY);
            prep.setString(5,DATE_START_PRIORITY_COL);
            prep.setString(6,DATE_END_PRIORITY_COL);
            prep.setString(7,TOOL_STATE_COL);
            prep.setString(8,USER_ID_COL);
            prep.setString(9,CATEGORY_ID_COL);

            prep.setString(10,obj.getTitle());
            prep.setFloat(11,obj.getPricePerDay());
            prep.setString(12,obj.getDescription());
            prep.setBoolean(13,obj.getIsPriority());

            if(obj.getIsPriority()){
                PriorityOffer o = (PriorityOffer) obj;
                prep.setDate(14, (Date) o.getDateStartPriority());
                prep.setDate(15, (Date) o.getDateEndPriority());
            } else {
                prep.setDate(14,null);
                prep.setDate(15,null);
            }

            prep.setString(16,obj.getToolSate().name());
            prep.setInt(17,obj.getUser().getUser_id());
            prep.setInt(18, obj.getCategory().getCategoryId());

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
            PreparedStatement prep = connection.prepareStatement(
                    "UPDATE offer " +
                            "SET ? = ?, ? = ?, ? = ?, ? = ?, ? = ?, ? = ?, ? = ?, ? = ?, ? = ?" +
                            "WHERE ? = ?"
            );

            prep.setString(1,TITLE_COL);
            prep.setString(2,obj.getTitle());

            prep.setString(3,PRICE_PER_DAY_COL);
            prep.setFloat(4,obj.getPricePerDay());

            prep.setString(5,DESCRPTION_COL);
            prep.setString(6,obj.getDescription());

            prep.setString(7,ISPRIORITY);
            prep.setBoolean(8,obj.getIsPriority());

            prep.setString(9,DATE_START_PRIORITY_COL);
            prep.setString(11,DATE_END_PRIORITY_COL);
            if(obj.getIsPriority()){
                PriorityOffer o = (PriorityOffer) obj;
                prep.setDate(10, (Date) o.getDateStartPriority());
                prep.setDate(12, (Date) o.getDateEndPriority());
            } else {
                prep.setDate(10,null);
                prep.setDate(12,null);
            }

            prep.setString(13,TOOL_STATE_COL);
            prep.setString(14,obj.getToolSate().name());

            prep.setString(15,USER_ID_COL);
            prep.setInt(16,obj.getUser().getUser_id());

            prep.setString(17,CATEGORY_ID_COL);
            prep.setInt(18, obj.getCategory().getCategoryId());

            prep.setString(19,OFFER_ID_COL);
            prep.setInt(20,obj.getOffer_id());

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
     * method which delete an offer from the db
     * @param obj offer to delete in database
     * @return true if the offer is deleted, else false
     */
    @Override
    public boolean delete(Offer obj) {
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "DELETE FROM offer WHERE ? = ?"
            );
            prep.setString(1,OFFER_ID_COL);
            prep.setInt(2,obj.getOffer_id());
            ResultSet rs = prep.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public ArrayList getOffersFromUser(int user_id) {
        ArrayList res = new ArrayList<Offer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o."+USER_ID_COL+" = u."+UserDaoMySQL.ID_COL+
                            " JOIN category c ON o."+CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE "+USER_ID_COL+" = ?"
            );
            prep.setInt(1,user_id);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                if(rs.getInt(USER_ID_COL) == (user_id)){
                    res.add(this.createOfferFromRs(rs));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public ArrayList getOffersByCity(String city) {
        ArrayList res = new ArrayList<Offer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o.? = u.? " +
                            "JOIN category c ON o.? = c.? " +
                            "WHERE ? = ?"
            );

            prep.setString(1,USER_ID_COL);
            prep.setString(2,UserDaoMySQL.ID_COL);
            prep.setString(3,CATEGORY_ID_COL);
            prep.setString(4,CategoryDaoMySQL.CATEGORY_ID_COL);
            prep.setString(5,UserDaoMySQL.USERCITY_COL);
            prep.setString(6,city);
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

    @Override
    public ArrayList getOffersByCategory(int category_id) {
        ArrayList res = new ArrayList<Offer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o.? = u.? " +
                            "JOIN category c ON o.? = c.? " +
                            "WHERE ? = ?"
            );

            prep.setString(1,USER_ID_COL);
            prep.setString(2,UserDaoMySQL.ID_COL);
            prep.setString(3,CATEGORY_ID_COL);
            prep.setString(4,CategoryDaoMySQL.CATEGORY_ID_COL);
            prep.setString(5,CATEGORY_ID_COL);
            prep.setInt(6,category_id);
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

    @Override
    public ArrayList getPriorityOffer() {
        ArrayList res = new ArrayList<PriorityOffer>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM offer o " +
                            "JOIN user u ON o.? = u.? " +
                            "JOIN category c ON o.? = c.? " +
                            "WHERE ? = 1 ORDER BY ? DESC"
            );

            prep.setString(1,USER_ID_COL);
            prep.setString(2,UserDaoMySQL.ID_COL);
            prep.setString(3,CATEGORY_ID_COL);
            prep.setString(4,CategoryDaoMySQL.CATEGORY_ID_COL);
            prep.setString(5,ISPRIORITY);
            prep.setString(6,DATE_START_PRIORITY_COL);
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
        User user = new User(rs.getInt(USER_ID_COL),rs.getString(UserDaoMySQL.FIRST_NAME_COL),rs.getString(UserDaoMySQL.LAST_NAME_COL),rs.getString(UserDaoMySQL.EMAIL_COL),rs.getString(UserDaoMySQL.PASSWORD_COL),rs.getString(UserDaoMySQL.USERCITY_COL),rs.getString(UserDaoMySQL.PHONENUMBER_COL),rs.getBoolean(UserDaoMySQL.ISADMIN),rs.getBoolean(UserDaoMySQL.ISBANNED),rs.getString(UserDaoMySQL.SALT_COL));
        Category category = new Category(rs.getInt(CategoryDaoMySQL.CATEGORY_ID_COL),rs.getString(CategoryDaoMySQL.CATEGORY_NAME_COL),rs.getBoolean(CategoryDaoMySQL.ISVALIDATED));

        if (rs.getBoolean(ISPRIORITY)){
            return new PriorityOffer(rs.getInt(OFFER_ID_COL),rs.getString(TITLE_COL),rs.getFloat(PRICE_PER_DAY_COL),rs.getString(DESCRPTION_COL),toolState,rs.getBoolean(ISPRIORITY),user,category,rs.getDate(DATE_START_PRIORITY_COL),rs.getDate(DATE_END_PRIORITY_COL));
        } else {
            return new Offer(rs.getInt(OFFER_ID_COL),rs.getString(TITLE_COL),rs.getFloat(PRICE_PER_DAY_COL),rs.getString(DESCRPTION_COL),toolState,rs.getBoolean(ISPRIORITY),user,category);
        }

    }
}
