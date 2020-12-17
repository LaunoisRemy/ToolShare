package dao.mysql;

import business.system.Category;
import business.system.offer.Offer;
import dao.structure.CategoryDAO;

import java.sql.Connection;

public class CategoryDaoMySQL extends CategoryDAO {
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    public CategoryDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Category find(int id) {
        return null;
    }

    @Override
    public Category create(Category obj) {
        return null;
    }

    @Override
    public Category update(Category obj) {
        return null;
    }

    @Override
    public boolean delete(Category obj) {
        return false;
    }
}
