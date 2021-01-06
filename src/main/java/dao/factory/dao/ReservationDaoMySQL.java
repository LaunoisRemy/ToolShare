package dao.factory.dao;

import business.system.offer.Offer;
import business.system.reservation.Reservation;
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
                    " WHERE "+ RESERVATION_ID +" = ?";
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
                    USER_ID+" ) "+
                    "VALUES (?,?,?,?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setDate(1, new Date(obj.getDateStartBooking().getTime()) );
            prep.setDate(2, new Date(obj.getDateEndBooking().getTime()) );
            prep.setInt(3,obj.getOffer().getOffer_id());
            prep.setInt(4,obj.getUser().getUser_id());


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
                    USER_ID + " = ? " +
                    "WHERE "+ RESERVATION_ID + " = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setDate(1, (Date) obj.getDateStartBooking());
            prep.setDate(2, (Date) obj.getDateEndBooking());
            prep.setInt(3,obj.getOffer().getOffer_id());
            prep.setInt(4,obj.getUser().getUser_id());
            prep.setInt(5,obj.getReservationId());
            int     rs = prep.executeUpdate();
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
                    "JOIN offer o on o."+OfferDaoMySQL.OFFER_ID_COL+" = reservation."+OFFER_ID+" " +
                    "JOIN category c on c."+ CategoryDaoMySQL.CATEGORY_ID_COL+" = o."+ OfferDaoMySQL.CATEGORY_ID_COL+ " " +
                    " WHERE reservation."+ clause +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(createReservationFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    public static Reservation createReservationFromRs(ResultSet rs) throws SQLException {
        Offer offer = OfferDaoMySQL.createOfferFromRs(rs);
        User user = UserDaoMySQL.createUserFromRs(rs);
        return  new Reservation(
                rs.getDate(START_DATE),
                rs.getDate(END_DATE),
                offer,
                user
        );
    }
}
