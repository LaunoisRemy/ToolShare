package gui;

public enum ViewPath {
    LOGIN_VIEW("view/user/login.fxml"),
    FORGOTPASSWORD_VIEW("view/user/forgot_password/forgot_password.fxml"),
    FORGOTPASSWORDCHECKCODE_VIEW("view/user/forgot_password/forgot_password_check_code.fxml"),
    CHANGEPASSWORD_VIEW("view/user/forgot_password/change_password.fxml"),
    REGISTERUPDATEUSER_VIEW("view/user/registerUpdateUser.fxml"),

    POSTOFFER_VIEW("view/offer/createOffer.fxml"),
    CATEGORY_VIEW("view/category/category.fxml"),
    POSTCATEGORY_VIEW("view/offer/createCategory.fxml"),
    OFFER_VIEW("view/offer/offerPage.fxml"),

    HOMEPAGE_VIEW("view/homePage.fxml"),
    FAVORY_VIEW("view/favoryPage.fxml"),
    HISTORY_VIEW("view/historyPage.fxml"),

    RATE_VIEW("view/rate/rateOffer.fxml"),
    COMMENT_VIEW("view/rate/comment.fxml")
    ;


    private String url;

    ViewPath(String envUrl){
        this.url=envUrl;
    }
    public String getUrl() {
        return url;
    }
}
