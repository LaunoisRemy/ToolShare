package dao.mysql;

import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import business.system.offer.ToolSate;
import dao.structure.CategoryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDaoMySQL extends CategoryDAO {
    static final String ISVALIDATED = "isValidated";
    static final String CATEGORY_NAME_COL = "categoryName";
    static final String CATEGORY_ID_COL = "category_id";
    private final Connection connection;

    /**
     * Constructor of OfferDaoMySQL
     * @param connection to have a link of the connection
     */
    public CategoryDaoMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Category find(int id) {
        return null;
    }

    @Override
    public Category create(Category obj) {
        return null;
    }

    @Override
    public Category update(Category obj) {
        return null;
    }

    @Override
    public boolean delete(Category obj) {
        return false;
    }

    public Category getCategoryByName(String name){
        Category category = null;
        try {
            PreparedStatement prep = this.connection.prepareStatement(
                    "SELECT *  FROM category WHERE UPPER(?) = UPPER(?)"
            );
            prep.setString(1,CATEGORY_NAME_COL);
            prep.setString(2,name);
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

    private Category createCategoryFromRs(ResultSet rs) throws SQLException {
        return new Category(rs.getInt(CATEGORY_ID_COL),rs.getString(CATEGORY_NAME_COL),rs.getBoolean(ISVALIDATED));
    }
}
