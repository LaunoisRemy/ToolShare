package business.system.reservation;

import business.system.offer.Offer;
import business.system.user.User;

import java.util.Date;

/**
 * Class used to instantiate Reservation object
 */
public class Reservation {
    public int reservationId;
    private Date dateStartBooking;
    private Date dateEndBooking;
    private Offer offer;
    private User user;

    /**
     * Constructor
     */
    public Reservation(int reservationId, Date dateStartBooking, Date dateEndBooking, Offer offer, User user) {
        this.reservationId = reservationId;
        this.dateStartBooking = dateStartBooking;
        this.dateEndBooking = dateEndBooking;
        this.offer = offer;
        this.user = user;
    }

    public Reservation(Date dateStartBooking, Date dateEndBooking, Offer offer, User user) {
        this.dateStartBooking = dateStartBooking;
        this.dateEndBooking = dateEndBooking;
        this.offer = offer;
        this.user = user;
    }

    /**
     * Get reservation id
     * @return the reservation id
     */
    public int getReservationId() {
        return this.reservationId;
    }

    /**
     * Set the reservation id
     * @param reservationId the new reservation id
     */
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * Get the starting date of the reservation
     * @return the starting date
     */
    public Date getDateStartBooking() {
        return dateStartBooking;
    }

    /**
     * Set the starting date of the reservation
     * @param dateStartBooking the starting date
     */
    public void setDateStartBooking(Date dateStartBooking) {
        this.dateStartBooking = dateStartBooking;
    }

    /**
     * Get the ending date of the reservation
     * @return the ending date of the reservation
     */
    public Date getDateEndBooking() {
        return dateEndBooking;
    }

    /**
     * Set the ending date of the reservation
     * @param dateEndBooking the ending date
     */
    public void setDateEndBooking(Date dateEndBooking) {
        this.dateEndBooking = dateEndBooking;
    }

    /**
     * get the offer of the reservation
     * @return the offer
     */
    public Offer getOffer() {
        return offer;
    }

    /**
     * Set the offer of the reservation
     * @param offer the new offer booked
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    /**
     * Get the user who made the reservation
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user who made the reservation
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
