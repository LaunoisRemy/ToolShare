package dao.factory;

import dao.mysql.FactoryDAOMySQL;
import dao.structure.UserDAO;

public abstract class AbstractFactoryDAO {
     public static AbstractFactoryDAO getInstance(){
        return new FactoryDAOMySQL();
    }
    public abstract UserDAO getUserDAO();

}
