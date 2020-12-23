package dao.factory_business;

import business.system.Comment;
import dao.structure.CommentDAO;

import java.sql.Connection;

public class CommentDaoMySQL extends CommentDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    protected CommentDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Comment find(int id,int... others) {
        return null;
    }

    @Override
    public Comment create(Comment obj) {
        return null;
    }

    @Override
    public Comment update(Comment obj) {
        return null;
    }

    @Override
    public boolean delete(Comment obj) {
        return false;
    }
}
