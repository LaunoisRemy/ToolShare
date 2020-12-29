package business.facade;

import business.system.Favory;
import business.system.offer.Offer;
import business.system.user.User;
import dao.structure.FavoryDAO;

import java.util.List;

public class FavoryFacade {
    private final User user = SessionFacade.getInstance().getUser();
    private static final FavoryFacade INSTANCE = new FavoryFacade();

    private FavoryFacade() {
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static FavoryFacade getInstance(){
        return INSTANCE;
    }

    public boolean deleteFavory(Offer offer){
        FavoryDAO favoryDAO = FavoryDAO.getInstance();
        Favory fav = new Favory(user,offer);
        return favoryDAO.delete(fav);
    }

    public Favory addFavory(Offer offer){
        FavoryDAO favoryDAO = FavoryDAO.getInstance();
        Favory fav = new Favory(user,offer);
        return favoryDAO.create(fav);

    }
    public Favory findFavory(Offer offer){
        FavoryDAO favoryDAO = FavoryDAO.getInstance();
        return favoryDAO.find(user.getUser_id(),offer.getOffer_id());
    }
    public List<Offer> getAllFavories(){
        FavoryDAO favoryDAO = FavoryDAO.getInstance();
        return favoryDAO.getAllFavories(user);
    }
}
