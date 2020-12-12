package dao.mysql;

import business.system.offer.Offer;
import dao.structure.OfferDAO;

public class OfferDaoMySQL extends OfferDAO {

    /**
     * method which communicate with the db in order to find an offer with the specified id
     * @param id id of the Offer the system wants
     * @return the Offer founded
     */
    @Override
    public Offer find(int id) {
        //TODO
        return null;
    }

    /**
     * method which create a new offer in the db
     * @param obj Offer to save in database
     * @return the Offer created
     */
    @Override
    public Offer create(Offer obj) {
        //TODO
        return null;
    }

    /**
     * method which update an offer in the db
     * @param obj offer to update in database
     * @return the updated offer
     */
    @Override
    public Offer update(Offer obj) {
        //TODO
        return null;
    }

    /**
     * method which delete an offer from the db
     * @param obj offer to delete in database
     * @return true if the offer is deleted, else false
     */
    @Override
    public boolean delete(Offer obj) {
        //TODO
        return false;
    }
}
