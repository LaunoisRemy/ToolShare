package business.facade;

import business.system.offer.Offer;
import business.system.user.User;
import dao.structure.HistoryDAO;

import java.util.List;

public class HistoryFacade {
    private static final HistoryFacade INSTANCE = new HistoryFacade();
    private User user = SessionFacade.getInstance().getUser();

    private HistoryFacade() {
    }

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static HistoryFacade getInstance(){
        return INSTANCE;
    }

    public List<Offer> getAllOffers(){
        HistoryDAO historyDAO = HistoryDAO.getInstance();
        return historyDAO.getHistory(user);

    };
}
