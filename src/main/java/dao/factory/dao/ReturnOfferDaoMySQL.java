package dao.factory.dao;

import business.system.offer.ToolSate;
import business.system.reservation.Reservation;
import business.system.reservation.ReturnOffer;
import dao.structure.ReturnOfferDAO;

import java.sql.*;

public class ReturnOfferDaoMySQL extends ReturnOfferDAO {
    private final Connection connection;
    public static final String RETURN_ID="return_id";
    public static final String TOOLSTATE_COL="finalToolState";
    public static final String RETURN_DATE="dateReturnBooking";
    public static final String RESERVATION_ID="reservation_id";

    /**
     * Constructor of OfferDaoMySQL
     */
    protected ReturnOfferDaoMySQL() {
        this.connection = AbstractFactoryDAO.connection;
    }

    @Override
    public ReturnOffer find(int id, int... others) {
        ReturnOffer returnOffer = null;
        try {
            String sql = "SELECT *  FROM return_offer " +
                    "JOIN reservation r on r."+ReservationDaoMySQL.RESERVATION_ID+" = return_offer."+RESERVATION_ID+" " +
                    " WHERE "+ RETURN_ID +" = ?";
            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                returnOffer = createReturnOfferFromRs(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return returnOffer;
    }

    @Override
    public ReturnOffer create(ReturnOffer obj) {
        try {
            String sql = "INSERT INTO return_offer ("+
                    TOOLSTATE_COL +","+
                    RETURN_DATE+","+
                    RESERVATION_ID+") "+
                    "VALUES (?,?,?)";
            PreparedStatement prep = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, obj.getToolSate().getString());
            prep.setDate(2, new Date(obj.getDateReturnBooking().getTime()));
            prep.setInt(3,obj.getReservation().getReservationId());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setReturnId(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ReturnOffer update(ReturnOffer obj) {
        try {
            String sql ="UPDATE return_offer " +
                    "SET "+TOOLSTATE_COL + " = ?, " +
                    RETURN_DATE + " = ?, " +
                    RESERVATION_ID + " = ? " +
                    "WHERE "+ RESERVATION_ID + " = ?";
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setString(1, obj.getToolSate().getString());
            prep.setDate(2, (Date) obj.getDateReturnBooking());
            prep.setInt(3,obj.getReservation().getReservationId());
            prep.setInt(4,obj.getReturnId());
            int rs = prep.executeUpdate();
            return obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(ReturnOffer obj) {
        try {
            String sql ="DELETE FROM return_offer WHERE "+RESERVATION_ID+" = ?";

            PreparedStatement prep = this.connection.prepareStatement(sql);
            prep.setInt(1,obj.getReturnId());
            ResultSet rs = prep.executeQuery();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static ReturnOffer createReturnOfferFromRs(ResultSet rs) throws SQLException {
        ToolSate toolSate = ToolSate.getType(rs.getString(TOOLSTATE_COL));
        return  new ReturnOffer(
               toolSate,
               rs.getDate(RETURN_DATE)
        );
    }
}
