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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }


}
