package dao.factory.dao;

import business.system.History;
import business.system.offer.Offer;
import business.system.user.User;
import dao.structure.HistoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HistoryDaoMySQL extends HistoryDAO {
    private final Connection connection;




    /**
     * Constructor of OfferDaoMySQL
     */
    protected HistoryDaoMySQL() {
        this.connection = AbstractFactoryDAO.connection;
    }


    @Override
    public History find(int idUser,int... idOffer) {
        History history = null;
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM reservation " +
                            "JOIN user u ON reservation."+UserDaoMySQL.USER_ID+" = u."+UserDaoMySQL.USER_ID+
                            " JOIN offer o on o.offer_id = reservation.offer_id"+
                            " LEFT JOIN category c ON o."+CategoryDaoMySQL.CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE o."+OfferDaoMySQL.OFFER_ID_COL+" = ? AND u."+ UserDaoMySQL.USER_ID+"=? AND reservation."+ReservationDaoMySQL.RETURN_ID+" is not null"
            );
            prep.setInt(1,idUser);
            prep.setInt(2,idOffer[0]);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                history = createHistoryFromRs(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return history;
    }

    @Override
    public History create(History obj) {
        return null;
    }

    @Override
    public History update(History obj) {
        return null;
    }

    @Override
    public boolean delete(History obj) {
        return false;
    }


    @Override
    public List<Offer> getHistory(User user) {
        List<Offer> offers = new ArrayList<>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM reservation " +
                            "JOIN user u ON reservation."+ReservationDaoMySQL.USER_ID+" = u."+UserDaoMySQL.USER_ID+
                            " JOIN offer o on o.offer_id = reservation.offer_id"+
                            " LEFT JOIN category c ON o."+CategoryDaoMySQL.CATEGORY_ID_COL+" = c."+CategoryDaoMySQL.CATEGORY_ID_COL+
                            " WHERE u."+ ReservationDaoMySQL.USER_ID+"=? AND reservation."+ReservationDaoMySQL.RETURN_ID+" is not null"
            );
            prep.setInt(1,user.getUser_id());
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                offers.add(OfferDaoMySQL.createOfferFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return offers;
    }


    public static History createHistoryFromRs(ResultSet rs) throws SQLException {
        User u = UserDaoMySQL.createUserFromRs(rs);
        Offer o = OfferDaoMySQL.createOfferFromRs(rs);
        Date startDate = rs.getDate(ReservationDaoMySQL.START_DATE);
        Date endDate = rs.getDate(ReservationDaoMySQL.END_DATE);
        return new History(u,o,startDate,endDate);
    }
}
