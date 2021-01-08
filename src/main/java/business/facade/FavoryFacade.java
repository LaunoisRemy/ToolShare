package business.facade;

import business.system.Favory;
import business.system.offer.Offer;
import business.system.user.User;
import dao.structure.FavoryDAO;

import java.util.List;

/**
 * Facade of all actions on Favory
 */
public class FavoryFacade {
    private final User user = SessionFacade.getInstance().getUser();
    private static final FavoryFacade INSTANCE = new FavoryFacade();

    /**
     * Constructor
     */
    private FavoryFacade() {
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static FavoryFacade getInstance(){
        return INSTANCE;
    }

    /**
     * Delete the offer of the list of favory for the user
     * @param offer Offer we want to not have in favory
     * @return If the delete is a success
     */
    public boolean deleteFavory(Offer offer){
        FavoryDAO favoryDAO = FavoryDAO.getInstance();
        Favory fav = new Favory(user,offer);
        return favoryDAO.delete(fav);
    }

    /**
     * Add the offer of the list of favory for the user
     * @param offer Offer we want to have in favory
     * @return If the create is a success
     */
    public Favory addFavory(Offer offer){
        FavoryDAO favoryDAO = FavoryDAO.getInstance();
        Favory fav = new Favory(user,offer);
        return favoryDAO.create(fav);

    }

    /**
     * Find if the offer is a favory for the user
     * @param offer Offer we want know if is a favory for the user
     * @return the favory object if the user have it in favory, else null
     */
    public Favory findFavory(Offer offer){
        FavoryDAO favoryDAO = FavoryDAO.getInstance();
        return favoryDAO.find(user.getUser_id(),offer.getOffer_id());
    }

    /**
     * Get all favory of a user
     * @return List of offers
     */
    public List<Offer> getAllFavories(){
        FavoryDAO favoryDAO = FavoryDAO.getInstance();
        return favoryDAO.getAllFavories(user);
    }
}
