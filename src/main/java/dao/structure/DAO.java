package dao.structure;

import db.ConnectionDBMySQL;

import java.sql.Connection;

public abstract class DAO<T> {
    protected Connection connection;

    public DAO() {
        this.connection = ConnectionDBMySQL.getInstance().getDb();
    }

    /**
     * Method which communicate with DB for retrieve the data T with id
     * @param id id of the object T the system wants
     * @return a object T if he exist in the DB, else return null
     */
    public abstract T find(int id);

    /**
     * Method which communicate with DB for create an obj T
     * @param obj object to save in database
     * @return true if the object is save in database, else return null.
     */
    public abstract boolean create(T obj);

    /**
     * Method which communicate with DB for update an obj T
     * @param obj object to update in database
     * @return true if the object is update in database, else return null.
     */
    public abstract boolean update(T obj);

    /**
     * Method which communicate with DB for delete an obj T
     * @param obj object to delete in database
     * @return true if the object is delete in database, else return null.
     */
    public abstract boolean delete(T obj);

}
