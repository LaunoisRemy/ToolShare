package business.system;

import business.system.offer.Offer;
import business.system.user.User;

public class Favory {
    private User user;
    private Offer offer;

    public Favory(User user,Offer offer){
        this.user = user;
        this.offer = offer;
    }

    public User getUser() {
        return user;
    }

    public Offer getOffer() {
        return offer;
    }
}
