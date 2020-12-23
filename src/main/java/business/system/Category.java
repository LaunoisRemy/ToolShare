package business.system;

public class Category {
    private int categoryId;
    private String categoryName;
    private boolean isValidated;

    public Category(int categoryId,String categoryName,boolean isValidated){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.isValidated = isValidated;
    }

    public Category(String categoryName,boolean isValidated){
        this.categoryName = categoryName;
        this.isValidated = isValidated;
    }

    /**
     * getter of the category id
     * @return the id of the category
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * setter of category id
     * @param categoryId the new id
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * getter of category name
     * @return name of the category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * setter of category name
     * @param categoryName the new category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * getter of category validity
     * @return true if the category is validated, false otherwise
     */
    public boolean getIsValidated() {
        return isValidated;
    }

    /**
     * setter of the category validity
     * @param validated the new validity of the category
     */
    public void setIsValidated(boolean validated) {
        isValidated = validated;
    }

    /**
     * method that allows to format the object category
     * @return
     */
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", isValidated=" + isValidated +
                '}';
    }
}
