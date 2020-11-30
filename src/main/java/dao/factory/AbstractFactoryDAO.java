package dao.factory;

import dao.mysql.FactoryDAOMySQL;
import dao.structure.UserDAO;

public abstract class AbstractFactoryDAO {
    private static AbstractFactoryDAO instance = null;

    public static AbstractFactoryDAO getInstance(){
        return instance;
    }

    public static void setInstance(AbstractFactoryDAO instance) {
        AbstractFactoryDAO.instance = instance;
    }

    public abstract UserDAO getUserDAO();

}
