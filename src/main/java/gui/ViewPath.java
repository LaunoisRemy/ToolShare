package gui;

public enum ViewPath {
    LOGIN_VIEW("view/user/login.fxml"),
    FORGOTPASSWORD_VIEW("view/user/forgot_password/forgot_password.fxml"),
    FORGOTPASSWORDCHECKCODE_VIEW("view/user/forgot_password/forgot_password_check_code.fxml"),
    CHANGEPASSWORD_VIEW("view/user/forgot_password/change_password.fxml"),
    REGISTERUPDATEUSER_VIEW("view/user/registerUpdateUser.fxml"),
    POSTOFFER_VIEW("view/createOffer.fxml"),
    HOMEPAGE_VIEW("view/homePage.fxml");


    private String url;

    ViewPath(String envUrl){
        this.url=envUrl;
    }
    public String getUrl() {
        return url;
    }
}
