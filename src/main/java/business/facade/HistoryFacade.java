package business.facade;

import business.system.offer.Offer;
import business.system.user.User;
import dao.structure.HistoryDAO;

import java.util.List;

/**
 * Facade of all action on History
 */
public class HistoryFacade {
    private static final HistoryFacade INSTANCE = new HistoryFacade();

    /**
     * Constructor
     */
    private HistoryFacade() {
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static HistoryFacade getInstance(){
        return INSTANCE;
    }

    /**
     * Get a list of all offer in History of the user
     * @return a List of Offers
     */
    public List<Offer> getAllOffers(){
        HistoryDAO historyDAO = HistoryDAO.getInstance();
        return historyDAO.getHistory(SessionFacade.getInstance().getUser());

    }
}
