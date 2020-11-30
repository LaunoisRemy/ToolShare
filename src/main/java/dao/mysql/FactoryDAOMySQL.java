package dao.mysql;

import dao.factory.AbstractFactoryDAO;
import dao.structure.UserDAO;

public class FactoryDAOMySQL extends AbstractFactoryDAO {
    @Override
    public UserDAO getUserDAO() {
        return new UserDaoMySQL();
    }
}
