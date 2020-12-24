package business.system.reservation;

import business.system.offer.Offer;
import business.system.offer.ToolSate;

import java.util.Date;

public class ReturnOffer {
    private int returnId;
    private ToolSate toolSate;
    private Date dateReturnBooking;
    private Reservation reservation;

    public ReturnOffer(int returnId, ToolSate toolSate, Date dateReturnBooking, Reservation reservation) {
        this.returnId = returnId;
        this.toolSate = toolSate;
        this.dateReturnBooking = dateReturnBooking;
        this.reservation = reservation;
    }

    public ReturnOffer(ToolSate toolSate, Date dateReturnBooking, Reservation reservation) {
        this.toolSate = toolSate;
        this.dateReturnBooking = dateReturnBooking;
        this.reservation = reservation;
    }

    public int getReturnId() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    public ToolSate getToolSate() {
        return toolSate;
    }

    public void setToolSate(ToolSate toolSate) {
        this.toolSate = toolSate;
    }

    public Date getDateReturnBooking() {
        return dateReturnBooking;
    }

    public void setDateReturnBooking(Date dateReturnBooking) {
        this.dateReturnBooking = dateReturnBooking;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
