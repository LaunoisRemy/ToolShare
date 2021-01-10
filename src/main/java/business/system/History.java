package business.system;

import business.system.offer.Offer;
import business.system.user.User;

import java.util.Date;

/**
 * Represent the History in the application
 */
public class History {
    private User user;
    private Offer offer;
    private Date startDate;
    private Date endDate;

    /**
     * Constructor
     */
    public History( User user, Offer offer, Date startDate, Date endDate) {
        this.user = user;
        this.offer = offer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Getter of the user
     * @return the user of the History
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter of the user
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter of the offer
     * @return the offer of the history
     */
    public Offer getOffer() {
        return offer;
    }

    /**
     * Setter of the offer
     * @param offer the new offer
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    /**
     * Getter of the Starting date
     * @return the starting date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter of the starting date
     * @param startDate the new date of start
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter of the ending date
     * @return the ending date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter of the ending date
     * @param endDate the new ending date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
