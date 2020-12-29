package business.facade;

import business.exceptions.BadInsertionInBDDException;
import business.exceptions.MissingParametersException;
import business.exceptions.ObjectNotFoundException;
import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import business.system.offer.ToolSate;
import business.system.scorable.Scorable;
import business.system.user.User;
import dao.structure.CategoryDAO;
import dao.structure.CommentDAO;
import dao.structure.OfferDAO;
import dao.structure.QuestionDAO;

import java.util.Date;
import java.util.List;

public class OfferFacade {
    private final User user = SessionFacade.getInstance().getUser();
    private static final OfferFacade INSTANCE = new OfferFacade();
    private final OfferDAO offerDAO = OfferDAO.getInstance();


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
        Offer offer = this.offerDAO.find(offerId);
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
        Offer offer = this.getOffer(offerId);
        return this.offerDAO.delete(offer);
    }

    /**
     * Method that allows to create a new offer thanks to information given by the user in the offer view
     * @param title the offer title
     * @param price the offer price per day
     * @param description the offer description
     * @param toolSate the offer toolState
     * @param isPriority true if the offer is priority, false otherwise
     * @param category the offer category name
     * @param dateStartPriority null if the offer is not priority, start date of the priority otherwise
     * @param dateEndPriority null if the offer is not priority, end date of the priority otherwise
     * @return the instance of the new offer
     */
    public Offer createOffer(String title, float price, String description, ToolSate toolSate, boolean isPriority, String category, Date dateStartPriority, Date dateEndPriority) throws MissingParametersException {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();

        Category new_category = categoryDAO.getCategoryByName(category);

        Offer offer = null;
        if(isPriority && dateStartPriority!=null && dateEndPriority!=null){
            offer = new PriorityOffer(title,description,price,toolSate,isPriority,this.user,new_category,dateStartPriority,dateEndPriority);
        } else if (!isPriority){
            offer = new Offer(title,price,description,toolSate,isPriority,this.user,new_category);
        } else {
            throw new MissingParametersException("Missing parameters dates for a priority offer (start and end of booking)");
        }

        Offer res = this.offerDAO.create(offer);

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
        CategoryDAO categoryDAO = CategoryDAO.getInstance();

        //check if the initial offer exists
        Offer offer = this.getOffer(offerId);

        offer.setTitle(title);
        offer.setPricePerDay(price);
        offer.setDescription(description);
        offer.setIsPriority(isPriority);

        Category new_category = categoryDAO.getCategoryByName(category);
        ToolSate toolSate = ToolSate.valueOf(state);

        offer.setToolSate(toolSate);
        offer.setCategory(new_category);
        offer.setUser(this.user);

        if (isPriority && dateStartPriority != null && dateEndPriority != null) {
            ((PriorityOffer) offer).setDateStartPriority(dateStartPriority);
            ((PriorityOffer) offer).setDateEndPriority(dateEndPriority);
        } else if (!isPriority) {
            ((PriorityOffer) offer).setDateStartPriority(null);
            ((PriorityOffer) offer).setDateEndPriority(null);
        } else {
            throw new MissingParametersException("Missing parameters dates for a priority offer (start and end of booking)");
        }

        offer = this.offerDAO.update(offer);
        return offer;
    }

    /**
     * Method that allows to return all the offers of the connected user
     * @return the list of the user offers
     */
    public List<Offer> getOffersFromUser(){
        return this.offerDAO.getOffersFromUser(this.user.getUser_id());
    }

    /**
     * Method that allows to find offers with a specified city
     * @param city city to filter by
     * @return the list of Offers located in the given city
     */
    public List<Offer> getOffersByCity(String city) {
        return this.offerDAO.getOffersByCity(city);
    }

    /**
     * Method that allows to search all the offers
     * @return the list of all the offers
     */
    public List<Offer> getAllOffers() {
        return this.offerDAO.getAllOffers();
    }

    /**
     * Method which finds offers with a specified category name
     * @param categoryName category name for the research
     * @return the list of Offers with the same category
     */
    public List<Offer> getOffersByCategory(String categoryName) {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        int idCategory = categoryDAO.getCategoryByName(categoryName).getCategoryId();
        return this.offerDAO.getOffersByCategory(idCategory);
    }

    /**
     * Method which finds priority offers
     * @return the list of all the priority offers order by the start of priority (the most recent)
     */
    public List<Offer> getPriorityOffer() {
        return this.offerDAO.getPriorityOffer();
    }

    public List<Scorable> getAllQuestions(Offer offer) {
        return QuestionDAO.getInstance().getAllQuestionsByIdOffer(offer.getOffer_id());
    }

    public List<Scorable> getAllComments(Offer offer) {
        return  CommentDAO.getInstance().getAllCommentsByIdOffer(offer.getOffer_id());
    }
}
