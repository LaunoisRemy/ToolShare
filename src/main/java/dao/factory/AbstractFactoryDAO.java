package dao.factory;

import dao.mysql.FactoryDAOMySQL;
import dao.structure.UserDAO;

public abstract class AbstractFactoryDAO {
    private static AbstractFactoryDAO instance = null;

    public static AbstractFactoryDAO getFactory(TypeDB type){
        switch (type){
            case MySQL:
                instance = new FactoryDAOMySQL();
                break;
            case PostgreSQL:
                System.out.println("Erreur");
            default:
                instance = new FactoryDAOMySQL();
        }
        return instance;
    }

    public static void setInstance(AbstractFactoryDAO instance) {
        AbstractFactoryDAO.instance = instance;
    }

    public abstract UserDAO getUserDAO();

}
