package dao.structure;

import business.system.Comment;
import business.system.user.User;
import dao.factory.AbstractFactoryDAO;

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

