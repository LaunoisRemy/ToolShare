package dao.factory.dao;

import business.system.offer.Offer;
import business.system.reservation.Reservation;
import business.system.reservation.ReturnOffer;
import business.system.scorable.Comment;
import business.system.scorable.Scorable;
import business.system.user.User;
import dao.structure.ReservationDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoMySQL extends ReservationDAO {
    private final Connection connection;
    public static final String RESERVATION_ID="reservation_id";
    public static final String START_DATE="dateStartBooking";
    public static final String END_DATE="dateEndBoorking";
    public static final String USER_ID="user_id";
    public static final String OFFER_ID="offer_id";
    public static final String RETURN_ID="return_id";

    /**
     * Constructor of OfferDaoMySQL
     */
    protected ReservationDaoMySQL() {
        this.connection = AbstractFactoryDAO.connection;
    }

    @Override
    public Reservation find(int id,int... others) {
        Reservation reservation = null;
        try {
            String sql = "SELECT *  FROM reservation " +
                    "JOIN user u on u."+ UserDaoMySQL.USER_ID +" = reservation."+ USER_ID+" " +
                    " JOIN offer o on o."+OfferDaoMySQL.OFFER_ID_COL+" = reservation."+OFFER_ID+" " +
                    " JOIN return_offer ro on ro."+ReturnOfferDaoMySQL.RETURN_ID+" = reservation."+RETURN_ID+" " +
                    " WHERE reservation."+ RESERVATION_ID +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                reservation = createReservationFromRs(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reservation;
    }

    @Override
    public Reservation create(Reservation obj) {
        try {
            String sql = "INSERT INTO reservation ("+
                    START_DATE +","+
                    END_DATE+","+
                    OFFER_ID+","+
                    USER_ID+" , "+
                    RETURN_ID+" ) "+
                    "VALUES (?,?,?,?,?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setDate(1, (Date) obj.getDateStartBooking());
            prep.setDate(2, (Date) obj.getDateEndBooking());
            prep.setInt(3,obj.getOffer().getOffer_id());
            prep.setInt(4,obj.getUser().getUser_id());
            ReturnOffer returnOffer = obj.getReturnOffer();
            if(returnOffer == null){
                prep.setNull(5, java.sql.Types.INTEGER);
            }else{
                prep.setInt(5,returnOffer.getReturnId());
            }

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setReservationId(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public Reservation update(Reservation obj) {
        try {
            String sql ="UPDATE reservation " +
                    "SET "+START_DATE + " = ?, " +
                    END_DATE + " = ?, " +
                    OFFER_ID + " = ?, " +
                    USER_ID + " = ?, " +
                    RETURN_ID + " = ? " +
                    "WHERE "+ RESERVATION_ID + " = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setDate(1, (Date) obj.getDateStartBooking());
            prep.setDate(2, (Date) obj.getDateEndBooking());
            prep.setInt(3,obj.getOffer().getOffer_id());
            prep.setInt(4,obj.getUser().getUser_id());
            ReturnOffer returnOffer = obj.getReturnOffer();
            if(returnOffer == null){
                prep.setNull(5, java.sql.Types.INTEGER);
            }else{
                prep.setInt(5,returnOffer.getReturnId());
            }
            prep.setInt(6,obj.getReservationId());
            int rs = prep.executeUpdate();
            return obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(Reservation obj) {
        try {
            String sql ="DELETE FROM reservation WHERE "+RESERVATION_ID+" = ?";

            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,obj.getReservationId());
            ResultSet rs = prep.executeQuery();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Reservation> getReservationsByOffer(int id_offer) {
        return getReservations(id_offer, OFFER_ID);
    }

    @Override
    public List<Reservation> getReservationsByUser(int id_user) {
        return getReservations(id_user, USER_ID);
    }

    private List<Reservation> getReservations(int id, String clause) {
        ArrayList<Reservation> res = new ArrayList<>();

        try {
            String sql = "SELECT *  FROM reservation " +
                    "JOIN user u on u."+ UserDaoMySQL.USER_ID +" = reservation."+ USER_ID+" " +
                    " JOIN offer o on o."+OfferDaoMySQL.OFFER_ID_COL+" = reservation."+OFFER_ID+" " +
                    " JOIN category c on o."+OfferDaoMySQL.CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+" " +
                    " JOIN return_offer ro on ro."+ReturnOfferDaoMySQL.RETURN_ID+" = reservation."+RETURN_ID+" " +
                    " WHERE reservation."+ clause +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                res.add(createReservationFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Reservation> getReservationsByUserNotReturned(int id_user) {
        ArrayList<Reservation> res = new ArrayList<>();

        try {
            String sql = "SELECT *  FROM reservation " +
                    "JOIN user u on u."+ UserDaoMySQL.USER_ID +" = reservation."+ USER_ID+" " +
                    " JOIN offer o on o."+OfferDaoMySQL.OFFER_ID_COL+" = reservation."+OFFER_ID+" " +
                    " JOIN category c on o."+OfferDaoMySQL.CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+" " +
                    " LEFT JOIN return_offer ro on ro."+ReturnOfferDaoMySQL.RETURN_ID+" = reservation."+RETURN_ID+" " +
                    " WHERE reservation."+ USER_ID +" = ? AND reservation."+RETURN_ID+" IS NULL";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id_user);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(createReservationFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public int nbDaysToReturn(Reservation reservation) {
        int nbJours = 0;

        try {
            String sql = "SELECT DATEDIFF("+END_DATE+", NOW()) FROM reservation WHERE "+RESERVATION_ID+"=?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,reservation.getReservationId());
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                nbJours = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nbJours;
    }

    @Override
    public int nbDaysOfReservation(Reservation reservation) {
        int nbJours = 0;

        try {
            String sql = "SELECT DATEDIFF("+END_DATE+", "+START_DATE+") FROM reservation WHERE "+RESERVATION_ID+"=?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,reservation.getReservationId());
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                nbJours = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nbJours;
    }

    public static Reservation createReservationFromRs(ResultSet rs) throws SQLException {
        Offer offer = OfferDaoMySQL.createOfferFromRs(rs);
        User user = UserDaoMySQL.createUserFromRs(rs);
        ReturnOffer returnOffer = null;
        rs.getInt(RETURN_ID);
        if( !rs.wasNull() ){
            returnOffer = ReturnOfferDaoMySQL.createReturnOfferFromRs(rs);
        }
        return  new Reservation(
                rs.getInt(RESERVATION_ID),
                rs.getDate(START_DATE),
                rs.getDate(END_DATE),
                offer,
                user,
                returnOffer
        );
    }
}
