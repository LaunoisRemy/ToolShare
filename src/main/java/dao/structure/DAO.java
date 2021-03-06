package dao.structure;

public interface DAO<T> {
    /**
     * Method which communicate with DB for retrieve the data T with id
     * @param id id of the object T the system wants
     * @return a object T if he exist in the DB, else return null
     */
    T find(int id,int... others);

    /**
     * Method which communicate with DB for create an obj T
     * @param obj object to save in database
     * @return true if the object is save in database, else return null.
     */
    T create(T obj);

    /**
     * Method which communicate with DB for update an obj T
     * @param obj object to update in database
     * @return true if the object is update in database, else return null.
     */
    T update(T obj);

    /**
     * Method which communicate with DB for delete an obj T
     * @param obj object to delete in database
     * @return true if the object is delete in database, else return null.
     */
    boolean delete(T obj);

}
