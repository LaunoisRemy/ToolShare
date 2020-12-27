package dao.structure;

import business.system.scorable.Comment;
import dao.factory.dao.AbstractFactoryDAO;

import java.util.List;

public abstract class CommentDAO implements DAO<Comment> {
    private static final CommentDAO INSTANCE = AbstractFactoryDAO.getInstance().getCommentDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static CommentDAO getInstance(){
        return INSTANCE;
    }
    public abstract List<Comment> getAllCommentsByIdOffer(int idOffer);
    public abstract List<Comment> getAllCommentsByIdUser(int idUser);

}

