package dao.factory;

import dao.factory.AbstractFactoryDAO;
import dao.mysql.UserDaoMySQL;
import dao.structure.UserDAO;
import db.ConnectionDBMySQL;

import java.sql.Connection;

/**
 * Factory concrete using MySQL database
 */
public class FactoryDAOMySQL extends AbstractFactoryDAO {

    @Override
    public UserDAO getUserDAO() {
        return new UserDaoMySQL(connection);
    }

    @Override
    public UserDAO getOfferDAO() {
        return null;
    }

    @Override
    public UserDAO getScoreOfferDAO() {
        return null;
    }

    @Override
    public UserDAO getCommentDAO() {
        return null;
    }
}
