package business.facade;

import business.exceptions.ObjectNotFoundException;
import business.system.offer.Offer;
import business.system.reservation.Reservation;
import business.system.reservation.ReturnOffer;
import business.system.user.User;
import dao.structure.OfferDAO;
import dao.structure.ReservationDAO;
import dao.structure.ReturnOfferDAO;

import java.util.Date;
import java.util.List;

/**
 * Facade of all actions on Reservation
 */
public class ReservationFacade {

    private static final ReservationFacade INSTANCE = new ReservationFacade();
    private final ReservationDAO ReservationDAO = dao.structure.ReservationDAO.getInstance();
    private final User user = SessionFacade.getInstance().getUser();

    /**
     * Constructor
     */
    private ReservationFacade() {}

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static ReservationFacade getInstance(){
        return INSTANCE;
    }

    /**
     * getReservationById find a reservation with the specified id
     * @param reservationId id of the reservation
     * @return an instance of the found reservation
     * @throws ObjectNotFoundException Exception that is raised if the object is not found
     */
    public Reservation getReservationById(int reservationId) throws ObjectNotFoundException {
        Reservation reservation = this.ReservationDAO.find(reservationId);
        if(reservation != null){
            return reservation;
        } else {
            throw new ObjectNotFoundException("Reservation not found");
        }
    }

    /**
     * getReservationsByOffer find all the reservation of a given offer
     * @param offerId id of the offer
     * @return a List of all reservations
     */
    public List<Reservation> getReservationsByOffer(int offerId) {
        return this.ReservationDAO.getReservationsByOffer(offerId);

    }

    /**
     * getReservationsByUser find all the reservation of the connected user
     * @return a List of all reservations
     */
    public List<Reservation> getReservationsByUser() {
        return this.ReservationDAO.getReservationsByUser(this.user.getUser_id());
    }

    /**
     * getReservationsByUser find all the reservation of the connected user
     * @return a List of all reservations
     */
    public List<Reservation> getReservationsByUserNotReturned() {
        return this.ReservationDAO.getReservationsByUserNotReturned(this.user.getUser_id());
    }

    /**
     * deleteResservationById delete a reservation with the specified id
     * @param reservationId id of the reservation
     * @return true if the reservation is deleted, else false
     * @throws ObjectNotFoundException Exception that is raised if the object is not found
     */
    public boolean deleteResservationById(int reservationId) throws ObjectNotFoundException {
        Reservation reservation = this.getReservationById(reservationId);
        return this.ReservationDAO.delete(reservation);
    }

    /**
     * createReservation create a new reservation with the specified params
     * @param dateStartBooking starting date of the reservation
     * @param dateEndBooking ending date of the reservation
     * @param offerId the id of the offer which is being booked
     * @return the newly created reservation
     */
    public Reservation createReservation(Date dateStartBooking, Date dateEndBooking, int offerId) {
        OfferDAO offerDAO = OfferDAO.getInstance();
        Offer offer = offerDAO.find(offerId);
        ReturnOffer returnOffer = null;
        Reservation reservation = new Reservation(dateStartBooking, dateEndBooking, offer, this.user, returnOffer);
        return this.ReservationDAO.create(reservation);
    }

    /**
     * updateReservation update a reservation with the specified id and params
     * @param reservationId id of the reservation
     * @param dateStartBooking starting date of the reservation
     * @param dateEndBooking ending date of the reservation
     * @return the updated reservation
     * @throws ObjectNotFoundException Exception that is raised if the object is not found
     */
    public Reservation updateReservation(int reservationId, Date dateStartBooking, Date dateEndBooking) throws ObjectNotFoundException {
        Reservation reservation = this.getReservationById(reservationId);
        reservation.setDateStartBooking(dateStartBooking);
        reservation.setDateEndBooking(dateEndBooking);
        return this.ReservationDAO.update(reservation);
    }

    public Reservation updateReservationFromObj(Reservation reservation, ReturnOffer ro){
        ReturnOfferDAO.getInstance().create(ro);
        return this.ReservationDAO.update(reservation);
    }

    public int nbDaysToReturn(Reservation reservation){
        return this.ReservationDAO.nbDaysToReturn(reservation);
    }

    public int nbDaysOfReservation(Reservation reservation){
        return this.ReservationDAO.nbDaysOfReservation(reservation);
    }
}
