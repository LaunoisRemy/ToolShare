package dao.structure;

import business.system.Favory;
import dao.factory_business.AbstractFactoryDAO;

public abstract class FavoryDAO{
    private static final FavoryDAO INSTANCE = AbstractFactoryDAO.getInstance().getFavoryDAO();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static FavoryDAO getInstance(){
        return INSTANCE;
    }

    /**
     * Method which communicate with DB for retrieve the data Favory with id
     * @param user_id id of the user the system wants
     * @param offer_id id of the offer
     * @return a object T if he exist in the DB, else return null
     */
    public abstract Favory find(int user_id,int offer_id);

    /**
     * Method which communicate with DB for create an obj Favory
     * @param obj object to save in database
     * @return true if the object is save in database, else return null.
     */
    public abstract Favory create(Favory obj);



    /**
     * Method which communicate with DB for delete an obj Favory
     * @param obj object to delete in database
     * @return true if the object is delete in database, else return null.
     */
    public abstract boolean delete(Favory obj);

}

