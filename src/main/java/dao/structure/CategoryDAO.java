package dao.structure;

import business.system.Category;
import business.system.user.User;
import dao.factory.AbstractFactoryDAO;

public abstract class CategoryDAO implements DAO<Category> {
    private static final CategoryDAO INSTANCE = AbstractFactoryDAO.getInstance().getCategoryDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static CategoryDAO getInstance(){
        return INSTANCE;
    }


}

