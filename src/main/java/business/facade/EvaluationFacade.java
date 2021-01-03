package business.facade;

import business.system.Score;
import business.system.ScoreOffer;
import business.system.offer.Offer;
import business.system.scorable.Comment;
import business.system.scorable.Scorable;
import business.system.scorable.faq.Answer;
import business.system.scorable.faq.Question;
import business.system.user.User;
import dao.factory.dao.CommentDaoMySQL;
import dao.structure.*;

/**
 * Facade of all actions on evaluations
 * Evaluation is about : rate an offer, comment, faq and rate the last two
 */
public class EvaluationFacade {
    public static EvaluationFacade getInstance(){
        return INSTANCE;
    }

    private static final EvaluationFacade INSTANCE = new EvaluationFacade();

    /**
     * Method which deal with create rate
     * @param offer offer that receives a rating
     * @param rate rating of the offer
     * @return scoreOffer create to pass if the user need to {@link #comment(ScoreOffer, String)}comment
     */
    public ScoreOffer rate(Offer offer, int rate){
        ScoreOfferDAO scoreOfferDAO = ScoreOfferDAO.getInstance();
        SessionFacade sessionFacade = SessionFacade.getInstance();
        ScoreOffer scoreOffer = new ScoreOffer(rate,offer,null,sessionFacade.getUser());
        scoreOfferDAO.create(scoreOffer);
        return scoreOffer;
    }

    /**
     * Method which deal with create comme,t
     * @param scoreOffer score offer that receives a comment
     * @param commentText comment of the score
     */
    public void comment(ScoreOffer scoreOffer, String commentText){
        ScoreOfferDAO scoreOfferDAO = ScoreOfferDAO.getInstance();
        CommentDAO commentDaoMySQL = CommentDaoMySQL.getInstance();
        SessionFacade sessionFacade = SessionFacade.getInstance();
        Comment comment = new Comment(commentText);
        comment = commentDaoMySQL.create(comment);
        scoreOffer.setComment(comment);
        scoreOfferDAO.update(scoreOffer);

    }

    /**
     * Method to find a rate of an offer
     * @param offerId offer we need to find the rate
     * @param userId The id of the user who have rate the offer of the first param
     * @return {@link business.system.ScoreOffer}
     */
    public ScoreOffer findRate(int offerId,int userId){
        return ScoreOfferDAO.getInstance().find(userId,offerId);
    }

    /**
     * Create an object scorable({@link business.system.scorable.Scorable}
     * @param scorable the scorable we want to save in database
     * @param i the score of the scorable
     */
    public void vote(Scorable scorable, int i) {

        SessionFacade sessionFacade = SessionFacade.getInstance();
        Score score = findScorable(scorable);

        if( score == null){
            score = new Score(sessionFacade.getUser(),scorable, scorable.getScoreType(),i);
            score = ScoreDAO.getInstance().create(score);
            scorable.setScore(scorable.getScore()+score.getScoreValue());
        }else {
            int value = score.getScoreValue();
            score.setScoreValue(i);
            ScoreDAO.getInstance().update(score);
            if( i == 0 ){
                scorable.setScore(scorable.getScore()-value);
            }else{
                scorable.setScore(scorable.getScore()+score.getScoreValue());
            }

        }

        if(scorable instanceof  Comment){
            CommentDAO.getInstance().update((Comment) scorable);
        }else if(scorable instanceof Question){
            QuestionDAO.getInstance().update((Question) scorable);
        }else if(scorable instanceof Answer){
            AnswerDAO.getInstance().update((Answer) scorable);
        }
    }

    /**
     * get the Score in the database
     * @param scorable The scorable we want the score
     * @return Score
     */
    public Score findScorable(Scorable scorable) {
        User user = SessionFacade.getInstance().getUser();
        return ScoreDAO.getInstance().find(user.getUser_id(), scorable.getId(),scorable.getScoreType().getIntByType());
    }

    public void delScore(Score score) {
        ScoreDAO.getInstance().delete(score);
    }
}
