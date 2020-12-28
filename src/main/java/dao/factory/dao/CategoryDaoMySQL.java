package dao.factory.dao;

import business.system.Category;
import dao.structure.CategoryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoMySQL extends CategoryDAO {
    static final String ISVALIDATED = "isValidated";
    static final String CATEGORY_NAME_COL = "categoryName";
    static final String CATEGORY_ID_COL = "category_id";
    private final Connection connection;

    /**
     * Constructor of CategoryDaoMySQL
     * @param connection to have a link of the connection
     */
    protected CategoryDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    /**
     * method which communicate with the db in order to find a category with the specified id
     * @param id id of the object T the system wants
     * @return the founded category
     */
    @Override
    public Category find(int id,int... others) {
        Category category = null;
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM category c " +
                            "WHERE "+CATEGORY_ID_COL+" = ?"
            );
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();

            if(rs.next()){
                if(rs.getInt(CATEGORY_ID_COL) == (id)){
                    category = createCategoryFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    /**
     * method which create a new category in the db
     * @param obj object to save in database
     * @return the new created category
     */
    @Override
    public Category create(Category obj) {
        try {
            String sql =
                    "INSERT INTO category ("+CATEGORY_NAME_COL+","+
                            ISVALIDATED+") " +
                            "VALUES (?,?)";

            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            prep.setString(1,obj.getCategoryName());
            prep.setBoolean(2,obj.getIsValidated());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setCategoryId(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * method which update a category in the db
     * @param obj object to update in database
     * @return the updated category
     */
    @Override
    public Category update(Category obj) {
        try {
            String sql =
                    "UPDATE category " +
                            "SET "+CATEGORY_NAME_COL+" = ?, "+ISVALIDATED+" = ? " +
                            "WHERE "+CATEGORY_ID_COL+" = ?";

            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            prep.setString(1,obj.getCategoryName());
            prep.setBoolean(2,obj.getIsValidated());
            prep.setInt(3,obj.getCategoryId());

            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            obj.setCategoryId(generatedKey);
            return obj;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * method which delete a category from the db
     * @param obj object to delete in database
     * @return true if the category is well-deleted, false otherwise
     */
    @Override
    public boolean delete(Category obj) {
        try {
            String sql =
                    "DELETE FROM category WHERE "+CATEGORY_ID_COL+" = ?";
            PreparedStatement prep = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1,obj.getCategoryId());
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * method which find a category from the db by its name
     * @param name the name of the category we research in th bdd
     * @return the founded category
     */
    public Category getCategoryByName(String name){
        Category category = null;
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM category WHERE UPPER("+CATEGORY_NAME_COL+") = UPPER(?)"
            );
            prep.setString(1,name);
            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                if(rs.getString(2).equals(name)){
                    category=this.createCategoryFromRs(rs);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    /**
     * method which find all categories from the db
     * @return the list of all the categories founded
     */
    public List<Category> getAllCategories(){
        List<Category> res = new ArrayList<>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM category"
            );

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(CategoryDaoMySQL.createCategoryFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /**
     * method which find all the validated categories from the db
     * @return the list of all the validated categories founded
     */
    public List<Category> getAllValidatedCategories(){
        List<Category> res = new ArrayList<>();
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM category WHERE isValidated = 1"
            );

            ResultSet rs = prep.executeQuery();

            while(rs.next()){
                res.add(CategoryDaoMySQL.createCategoryFromRs(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    /**
     * Creates and returns a category from a query ResultSet
     * @param rs the ResultSet that contains category information
     * @return new Category
     * @throws SQLException
     */
    public static Category createCategoryFromRs(ResultSet rs) throws SQLException {
        return new Category(rs.getInt(CATEGORY_ID_COL),rs.getString(CATEGORY_NAME_COL),rs.getBoolean(ISVALIDATED));
    }
}
