package dao.mysql;

import business.system.Comment;
import business.system.offer.Offer;
import dao.structure.CommentDAO;

import java.sql.Connection;

public class CommentDaoMySQL extends CommentDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    public CommentDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Comment find(int id) {
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
