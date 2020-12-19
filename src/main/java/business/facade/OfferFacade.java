package business.facade;

import business.exceptions.BadInsertionInBDDException;
import business.system.offer.Offer;
import business.system.offer.ToolSate;
import business.system.user.User;

public class OfferFacade {

    public Offer createOffer(String title, int pricePerDay, String description, ToolSate state, boolean isPriority, int category_id) throws BadInsertionInBDDException {

        // Get Session
        SessionFacade sessionFacade = SessionFacade.getInstance();

        // Get User
        User user = sessionFacade.getUser();

        // Create an Offer object
        Offer newOffer = new Offer(title, pricePerDay, description, state, isPriority, user.getUser_id(), category_id);

        // Post it to db


        return newOffer;
    }
}
