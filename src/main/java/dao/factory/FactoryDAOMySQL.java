package dao.factory;

import dao.factory.AbstractFactoryDAO;
import dao.mysql.UserDaoMySQL;
import dao.structure.UserDAO;
import db.ConnectionDBMySQL;

import java.sql.Connection;

public class FactoryDAOMySQL extends AbstractFactoryDAO {
    @Override
    public UserDAO getUserDAO() {
        return new UserDaoMySQL();
    }
}
