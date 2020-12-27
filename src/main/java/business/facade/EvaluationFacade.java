package business.facade;

import business.system.ScoreOffer;
import business.system.offer.Offer;
import dao.structure.ScoreOfferDAO;

public class EvaluationFacade {
    public static EvaluationFacade getInstance(){
        return INSTANCE;
    }

    public void rate(Offer offer, int rate){
        ScoreOfferDAO scoreOfferDAO = ScoreOfferDAO.getInstance();
        SessionFacade sessionFacade = SessionFacade.getInstance();
        ScoreOffer scoreOffer = new ScoreOffer(rate,offer,null,sessionFacade.getUser());
        scoreOfferDAO.create(scoreOffer);
        System.out.println(offer);

    }
    public void comment(int offerId, String comment){}
    public void deleteComment(int commentId){}
    public void deleteRate(int idRate){}
    public void updateComment(int commentId, int idRate, int userID){}
    private static final EvaluationFacade INSTANCE = new EvaluationFacade();

    public ScoreOffer findRate(int offerId,int userId){
        return ScoreOfferDAO.getInstance().find(userId,offerId);
    }



}
