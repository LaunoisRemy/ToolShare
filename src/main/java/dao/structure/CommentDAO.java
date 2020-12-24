package dao.structure;

import business.system.scorable.Comment;
import dao.factory_business.AbstractFactoryDAO;

public abstract class CommentDAO implements DAO<Comment> {
    private static final CommentDAO INSTANCE = AbstractFactoryDAO.getInstance().getCommentDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static CommentDAO getInstance(){
        return INSTANCE;
    }


}

