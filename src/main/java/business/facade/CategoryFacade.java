package business.facade;

import business.exceptions.ObjectNotFoundException;
import business.system.Category;
import dao.structure.CategoryDAO;

import java.util.List;

public class CategoryFacade {

    private static final CategoryFacade INSTANCE = new CategoryFacade();

    public CategoryFacade(){ }

    /**
     * getCategory will find a category with the specified id
     * @param idCategory the id category
     * @return the category founded
     * @throws ObjectNotFoundException
     */
    public Category getCategory(int idCategory) throws ObjectNotFoundException {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        Category category = categoryDAO.find(idCategory);
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
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        Category category = new Category(categoryName,validated);
        return categoryDAO.create(category);
    }

    /**
     * getAllCategories will return the list of all the categories present in the db
     * @return the list of all the categories
     */
    public List getAllCategories() {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        return categoryDAO.getAllCategories();

    }

    /**
     * deleteCategory will delete the category given by its id
     * @param idCategory the id category to delete
     * @return true if the category is well-deleted, false otherwise
     * @throws ObjectNotFoundException
     */
    public boolean deleteCategory(int idCategory) throws ObjectNotFoundException {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        Category category = this.getCategory(idCategory);
        return categoryDAO.delete(category);
    }

    /**
     * updateCategory will update the category identified by its given id and the new values of its attributes
     * @param idCategory th category id to update
     * @param categoryName the new value of the category name
     * @param validated the new value of the category validity
     * @return the updated category
     * @throws ObjectNotFoundException
     */
    public Category updateCategory(int idCategory, String categoryName, boolean validated) throws ObjectNotFoundException {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();

        //check if the initial category exists
        Category category = this.getCategory(idCategory);

        category.setCategoryName(categoryName);
        category.setIsValidated(validated);

        return categoryDAO.update(category);
    }
}
