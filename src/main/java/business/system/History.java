package business.system;

import business.system.offer.Offer;
import business.system.user.User;

import java.time.LocalDate;
import java.util.Date;

public class History {
    private User user;
    private Offer offer;
    private Date startDate;
    private Date endDate;

    public History( User user, Offer offer, Date startDate, Date endDate) {
        this.user = user;
        this.offer = offer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
