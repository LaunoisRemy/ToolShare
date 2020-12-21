package dao.structure;

import business.system.Category;
import dao.factory_business.AbstractFactoryDAO;

public abstract class CategoryDAO implements DAO<Category> {
    private static final CategoryDAO INSTANCE = AbstractFactoryDAO.getInstance().getCategoryDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static CategoryDAO getInstance(){
        return INSTANCE;
    }

    /**
     * Find a category thanks to its name
     * @param name the name of the category we research in th bdd
     * @return an instance of category that corresponds to the name given
     */
    public abstract Category getCategoryByName(String name);


}

