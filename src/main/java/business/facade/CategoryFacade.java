package business.facade;

import business.exceptions.ObjectNotFoundException;
import business.system.Category;
import dao.structure.CategoryDAO;
import java.util.List;

/**
 * Facade of all actions on Category
 * Category is about the type of offer
 */
public class CategoryFacade {

    private static final CategoryFacade INSTANCE = new CategoryFacade();
    private final CategoryDAO categoryDAO = CategoryDAO.getInstance();

    /**
     * getInstance will return the same correctly initialized INSTANCE
     * @return instance of the class
     */
    public static CategoryFacade getInstance(){
        return INSTANCE;
    }

    /**
     * Constructor
     */
    private CategoryFacade(){ }

    /**
     * getCategory will find a category with the specified id
     * @param idCategory the id category
     * @return the category founded
     * @throws ObjectNotFoundException Exception that is raised if the object is not found
     */
    public Category getCategory(int idCategory) throws ObjectNotFoundException {
        Category category = this.categoryDAO.find(idCategory);
        if(category != null){
            return category;
        } else {
            throw new ObjectNotFoundException("Category not found");
        }
    }

    /**
     * createCategory will create a new instance of category in the db
     * @param categoryName the name of the new category
     * @param validated true if the category is validated, false otherwise
     * @return the new Category
     */
    public Category createCategory(String categoryName, boolean validated) {
        Category category = new Category(categoryName,validated);
        return this.categoryDAO.create(category);
    }

    /**
     * getAllCategories will return the list of all the categories present in the db
     * @return the list of all the categories
     */
    public List<Category> getAllCategories() {
        return this.categoryDAO.getAllCategories();
    }

    /**
     * getAllValidatedCategories will return the list of all the validated categories present in the db
     * @return the list of all the validated categories
     */
    public List<Category> getAllValidatedCategories() {
        return this.categoryDAO.getAllValidatedCategories();
    }

    /**
     * deleteCategory will delete the category given by its id
     * @param category the category to delete
     * @return true if the category is well-deleted, false otherwise
     */
    public boolean deleteCategory(Category category) {
        return this.categoryDAO.delete(category);
    }

    /**
     * updateCategory will update the category identified by its given id and the new values of its attributes
     * @param idCategory th category id to update
     * @param categoryName the new value of the category name
     * @param validated the new value of the category validity
     * @return the updated category
     * @throws ObjectNotFoundException Exception that is raised if the object is not found
     */
    public Category updateCategory(int idCategory, String categoryName, boolean validated) throws ObjectNotFoundException {
        //check if the initial category exists
        Category category = this.getCategory(idCategory);

        category.setCategoryName(categoryName);
        category.setIsValidated(validated);

        return this.categoryDAO.update(category);
    }
}
