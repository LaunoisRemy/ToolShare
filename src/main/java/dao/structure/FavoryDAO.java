package dao.structure;

import business.system.Favory;
import business.system.user.User;
import dao.factory.AbstractFactoryDAO;

public abstract class FavoryDAO implements DAO<Favory> {
    private static final FavoryDAO INSTANCE = AbstractFactoryDAO.getInstance().getFavoryDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static FavoryDAO getInstance(){
        return INSTANCE;
    }


}

