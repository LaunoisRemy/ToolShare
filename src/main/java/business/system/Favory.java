package business.system;

import business.system.offer.Offer;
import business.system.user.User;

/**
 * Represent the Favory in the application
 */
public class Favory {
    private final User user;
    private final Offer offer;

    /**
     * Constructor
     */
    public Favory(User user,Offer offer){
        this.user = user;
        this.offer = offer;
    }

    /**
     * Getter of the user
     * @return the user of the favory
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter of the offer
     * @return the offer of the favory
     */
    public Offer getOffer() {
        return offer;
    }

    /**
     * method that allows to format the object category
     * @return String as a description of the favory
     */
    @Override
    public String toString() {
        return "Favory{" +
                "user=" + user +
                ", offer=" + offer +
                '}';
    }
}
