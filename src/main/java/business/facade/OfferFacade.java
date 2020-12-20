package business.facade;

import business.exceptions.MissingParametersException;
import business.exceptions.ObjectNotFoundException;
import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import business.system.offer.ToolSate;
import business.system.user.User;
import dao.structure.CategoryDAO;
import dao.structure.OfferDAO;

import java.util.Date;

public class OfferFacade {
    private User user = SessionFacade.getInstance().getUser();
    private static final OfferFacade INSTANCE = new OfferFacade();

    private OfferFacade() {
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static OfferFacade getInstance(){
        return INSTANCE;
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
     * Method that allows to delete an offer thanks to its id given in parameter
     * @param offerId, the offer id to delete
     * @return boolean : True if the object is well-deleted, false otherwise
     * @throws ObjectNotFoundException throws if try to delete a not-existed object
     */
    public boolean deleteOffer(int offerId) throws ObjectNotFoundException {
        OfferDAO offerDAO = OfferDAO.getInstance();
        Offer offer = this.getOffer(offerId);
        return offerDAO.delete(offer);
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
    public Offer createOffer(String title, float price, String description, String state, boolean isPriority, String category, Date dateStartPriority, Date dateEndPriority) throws MissingParametersException {
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
            throw new MissingParametersException("Missing parameters dates for a priority offer (start and end of booking)");
        }

        Offer res = offerDAO.create(offer);

        return res;

    }

    /**
     * Method that allows to update an existing offer
     * @param offerId, the id of the existing offer
     * @param title, the new title to change
     * @param price, the new price to change
     * @param description, the new description to change
     * @param state, the new state to change
     * @param isPriority, the new isPriority to change
     * @param category, the new category to change
     * @param dateStartPriority, the new starting date to change
     * @param dateEndPriority, the new ending date to change
     * @return the new offer with the new values
     * @throws ObjectNotFoundException
     * @throws MissingParametersException
     */
    public Offer updateOffer(int offerId, String title, float price, String description, String state, boolean isPriority, String category, Date dateStartPriority, Date dateEndPriority) throws ObjectNotFoundException, MissingParametersException {
        OfferDAO offerDAO = OfferDAO.getInstance();
        CategoryDAO categoryDAO = CategoryDAO.getInstance();

        //check if the initial offer exists
        Offer offer = this.getOffer(offerId);

        offer.setTitle(title);
        offer.setPricePerDay(price);
        offer.setDescription(description);
        offer.setPriority(isPriority);

        int category_id = categoryDAO.getCategoryByName(category).getCategoryId();
        ToolSate toolSate = ToolSate.valueOf(state);

        offer.setToolSate(toolSate);
        offer.setCategory_id(category_id);
        offer.setUser_id(this.user.getUser_id());

        if(isPriority && dateStartPriority!=null && dateEndPriority!=null){
            ((PriorityOffer) offer).setDateStartPriority(dateStartPriority);
            ((PriorityOffer) offer).setDateEndPriority(dateEndPriority);
        } else if (!isPriority){
            ((PriorityOffer) offer).setDateStartPriority(null);
            ((PriorityOffer) offer).setDateEndPriority(null);
        } else {
            throw new MissingParametersException("Missing parameters dates for a priority offer (start and end of booking)");
        }

        offer = offerDAO.update(offer);
        return offer;
    }
}
