package gui;

public enum ViewPath {
    LOGIN_VIEW("view/user/login.fxml"),
    FORGOTPASSWORD_VIEW("view/user/forgot_password/forgot_password.fxml"),
    FORGOTPASSWORDCHECKCODE_VIEW("view/user/forgot_password/forgot_password_check_code.fxml"),
    CHANGEPASSWORD_VIEW("view/user/forgot_password/change_password.fxml"),
    REGISTERUPDATEUSER_VIEW("view/user/registerUpdateUser.fxml"),
    POSTOFFER_VIEW("view/createOffer.fxml"),
    POSTCATEGORY_VIEW("view/createCategory.fxml"),
    FAVORY_VIEW("view/favoryPage.fxml"),
    HISTORY_VIEW("view/historyPage.fxml"),
    CATEGORY_VIEW("view/category/category.fxml"),
    HOMEPAGE_VIEW("view/homePage.fxml"),
    RATE_VIEW("view/rate/rateOffer.fxml");


    private String url;

    ViewPath(String envUrl){
        this.url=envUrl;
    }
    public String getUrl() {
        return url;
    }
}
