package business.facade;

import business.system.ScoreOffer;
import business.system.offer.Offer;
import business.system.scorable.Comment;
import dao.factory_business.CommentDaoMySQL;
import dao.structure.CommentDAO;
import dao.structure.ScoreOfferDAO;

public class EvaluationFacade {
    public static EvaluationFacade getInstance(){
        return INSTANCE;
    }

    public ScoreOffer rate(Offer offer, int rate){
        ScoreOfferDAO scoreOfferDAO = ScoreOfferDAO.getInstance();
        SessionFacade sessionFacade = SessionFacade.getInstance();
        ScoreOffer scoreOffer = new ScoreOffer(rate,offer,null,sessionFacade.getUser());
        scoreOfferDAO.create(scoreOffer);
        return scoreOffer;
    }
    public void comment(ScoreOffer scoreOffer, String commentText){
        ScoreOfferDAO scoreOfferDAO = ScoreOfferDAO.getInstance();
        CommentDAO commentDaoMySQL = CommentDaoMySQL.getInstance();
        SessionFacade sessionFacade = SessionFacade.getInstance();
        Comment comment = new Comment(commentText);
        comment = commentDaoMySQL.create(comment);
        scoreOffer.setComment(comment);
        scoreOfferDAO.update(scoreOffer);

    }
    public void deleteComment(int commentId){}
    public void deleteRate(int idRate){}
    public void updateComment(int commentId, int idRate, int userID){}
    private static final EvaluationFacade INSTANCE = new EvaluationFacade();

    public ScoreOffer findRate(int offerId,int userId){
        return ScoreOfferDAO.getInstance().find(userId,offerId);
    }



}
