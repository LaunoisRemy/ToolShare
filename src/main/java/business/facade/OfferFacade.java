package business.facade;

import business.exceptions.ObjectNotFoundException;
import business.management.UserManagement;
import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import business.system.offer.ToolSate;
import business.system.user.User;
import dao.structure.CategoryDAO;
import dao.structure.OfferDAO;
import dao.structure.UserDAO;

import java.util.Date;

public class OfferFacade {
    private User user = SessionFacade.getInstance().getUser();
    private static final OfferFacade INSTANCE = new OfferFacade();

    private OfferFacade() {
    }

    /**
     * getOffer will find an offer with the specified id
     * @param offerId id of the Offer the system wants
     * @return an instance of the found offer
     * @throws ObjectNotFoundException
     */
    public Offer getOffer(int offerId) throws ObjectNotFoundException {
        OfferDAO offerDAO = OfferDAO.getInstance();
        Offer offer = offerDAO.find(offerId);
        if(offer != null){
            return offer;
        } else {
            throw new ObjectNotFoundException("Offer not found");
        }
    }

    /**
     * Method that allows to create a new offer thanks to information given by the user in the offer view
     * @param title the offer title
     * @param price the offer price per day
     * @param description the offer description
     * @param state the offer toolState
     * @param isPriority true if the offer is priority, false otherwise
     * @param category the offer category name
     * @param dateStartPriority null if the offer is not priority, start date of the priority otherwise
     * @param dateEndPriority null if the offer is not priority, end date of the priority otherwise
     * @return the instance of the new offer
     */
    public Offer createOffer(String title, float price, String description, String state, boolean isPriority, String category, Date dateStartPriority, Date dateEndPriority){
        OfferDAO offerDAO = OfferDAO.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();

        int category_id = categoryDAO.getCategoryByName(category).getCategoryId();
        ToolSate toolSate = ToolSate.valueOf(state);

        Offer offer = null;
        if(isPriority && dateStartPriority!=null && dateEndPriority!=null){
            offer = new PriorityOffer(title,description,price,toolSate,isPriority,this.user.getUser_id(),category_id,dateStartPriority,dateEndPriority);
        } else if (!isPriority){
            offer = new Offer(title,price,description,toolSate,isPriority,this.user.getUser_id(),category_id);
        } else {

        }

        Offer res = offerDAO.create(offer);

        return res;

    }
}
