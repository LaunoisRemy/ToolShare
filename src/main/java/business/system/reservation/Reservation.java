package business.system.reservation;

import business.system.offer.Offer;
import business.system.user.User;

import java.util.Date;

public class Reservation {
    public int reservationId;
    private Date dateStartBooking;
    private Date dateEndBooking;
    private Offer offer;
    private User user;
    private ReturnOffer returnOffer;

    public Reservation(int reservationId, Date dateStartBooking, Date dateEndBooking, Offer offer, User user, ReturnOffer returnOffer) {
        this.reservationId = reservationId;
        this.dateStartBooking = dateStartBooking;
        this.dateEndBooking = dateEndBooking;
        this.offer = offer;
        this.user = user;
        this.returnOffer = returnOffer;
    }

    public Reservation(Date dateStartBooking, Date dateEndBooking, Offer offer, User user, ReturnOffer returnOffer) {
        this.dateStartBooking = dateStartBooking;
        this.dateEndBooking = dateEndBooking;
        this.offer = offer;
        this.user = user;
        this.returnOffer = returnOffer;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getDateStartBooking() {
        return dateStartBooking;
    }

    public void setDateStartBooking(Date dateStartBooking) {
        this.dateStartBooking = dateStartBooking;
    }

    public Date getDateEndBooking() {
        return dateEndBooking;
    }

    public void setDateEndBooking(Date dateEndBooking) {
        this.dateEndBooking = dateEndBooking;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReturnOffer getReturnOffer() {
        return returnOffer;
    }

    public void setReturnOffer(ReturnOffer returnOffer) {
        this.returnOffer = returnOffer;
    }
}
