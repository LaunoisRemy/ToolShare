package business.facade;

import business.exceptions.MissingParametersException;
import business.exceptions.ObjectNotFoundException;
import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import business.system.offer.ToolSate;
import business.system.scorable.Scorable;
import business.system.user.User;
import dao.structure.CommentDAO;
import dao.structure.OfferDAO;
import dao.structure.CategoryDAO;
import dao.structure.QuestionDAO;

import java.util.Date;
import java.util.List;


/**
 * Facade of all actions on Offers
 */
public class OfferFacade {

    private static final OfferFacade INSTANCE = new OfferFacade();
    private final OfferDAO offerDAO = OfferDAO.getInstance();

    /**
     * Constructor
     */
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
     * @throws ObjectNotFoundException Exception that is raised if the object is not found
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
     * @throws ObjectNotFoundException Exception that is raised if the object that we want to delete is not found
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

        Offer offer;
        if(isPriority && dateStartPriority!=null && dateEndPriority!=null){
            offer = new PriorityOffer(title,description,price,toolSate, true,SessionFacade.getInstance().getUser(),new_category,dateStartPriority,dateEndPriority);
        } else if (!isPriority){
            offer = new Offer(title,price,description,toolSate, false,SessionFacade.getInstance().getUser(),new_category);
        } else {
            throw new MissingParametersException("Missing parameters dates for a priority offer (start and end of booking)");
        }

        return this.offerDAO.create(offer);

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
     * @throws ObjectNotFoundException Exception that is raised if the object is not found
     * @throws MissingParametersException Exception that is raised if the user didn't enter all the information needed
     */
    public Offer updateOffer(int offerId, String title, float price, String description, ToolSate state, boolean isPriority, String category, Date dateStartPriority, Date dateEndPriority) throws ObjectNotFoundException, MissingParametersException {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();

        Category new_category = categoryDAO.getCategoryByName(category);


        Offer offer = this.getOffer(offerId);

        if(isPriority && dateStartPriority!=null && dateEndPriority!=null) {
            offer = new PriorityOffer(offerId, title, price, description, state, true, SessionFacade.getInstance().getUser(), new_category, dateStartPriority, dateEndPriority);
        } else {
            offer.setTitle(title);
            offer.setPricePerDay(price);
            offer.setDescription(description);
            offer.setIsPriority(false);
            offer.setToolSate(state);
            offer.setCategory(new_category);
            offer.setUser(SessionFacade.getInstance().getUser());
        }

        return this.offerDAO.update(offer);
    }

    /**
     * Method that allows to update an existing offer
     * @param offer the updated offer
     * @return the offer newly updated
     */
    public Offer updateOfferFromObj(Offer offer){
        return this.offerDAO.update(offer);
    }

    /**
     * Method that allows to return all the offers of the connected user
     * @return the list of the user offers
     */
    public List<Offer> getOffersFromUser(){
        return this.offerDAO.getOffersFromUser(SessionFacade.getInstance().getUser().getUser_id());
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

    /**
     * Method which finds all questions about one offer
     * @param offer The offer we want to have questions
     * @return  A list of {@link business.system.scorable.faq.Question}
     */
    public List<Scorable> getAllQuestions(Offer offer) {
        return QuestionDAO.getInstance().getAllQuestionsByIdOffer(offer.getOffer_id());
    }

    /**
     * Method which finds all comments about one offer
     * @param offer The offer we want to have comments
     * @return  A list of {@link business.system.scorable.Comment}
     */
    public List<Scorable> getAllComments(Offer offer) {
        return  CommentDAO.getInstance().getAllCommentsByIdOffer(offer.getOffer_id());
    }
}
