package business.system.reservation;

import business.system.offer.ToolSate;

import java.util.Date;

/**
 * Class used to instantiate ReturnOffer object
 * ReturnOffer refer to a booked offer which have to return to his/her owner
 */
public class ReturnOffer {
    private int returnId;
    private ToolSate toolSate;
    private Date dateReturnBooking;
    private Reservation reservation;

    /**
     * Constructor
     */
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

    public ReturnOffer(ToolSate toolSate, Date dateReturnBooking) {
        this.toolSate = toolSate;
        this.dateReturnBooking = dateReturnBooking;
    }

    /**
     * Get the id of the ReturnOffer
     * @return the id
     */
    public int getReturnId() {
        return returnId;
    }

    /**
     * Set the id of the ReturnOffer
     * @param returnId the id 
     */
    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    /**
     * Get the state of the tool of the ReturnOffer
     * @return the toolState at the end of the booking
     */
    public ToolSate getToolSate() {
        return toolSate;
    }

    /**
     * Set the state of the tool of the ReturnOffer
     * @param toolSate the toolState at the end of the booking
     */
    public void setToolSate(ToolSate toolSate) {
        this.toolSate = toolSate;
    }

    /**
     * Get the date of return of the ReturnOffer
     * @return the date of return of the reservation
     */
    public Date getDateReturnBooking() {
        return dateReturnBooking;
    }

    /**
     * Set the date of return of the ReturnOffer
     * @param dateReturnBooking the date of return of the reservation
     */
    public void setDateReturnBooking(Date dateReturnBooking) {
        this.dateReturnBooking = dateReturnBooking;
    }

    /**
     * Get the reservation of the ReturnOffer
     * @return the reservation
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Set the reservation of the ReturnOffer
     * @param reservation the reservation
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
