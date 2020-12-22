package gui;

public enum ViewPath {
    LOGIN_VIEW("view/login.fxml"),
    FORGOTPASSWORD_VIEW("view/forgot_password.fxml"),
    REGISTERUPDATEUSER_VIEW("view/registerUpdateUser.fxml"),
    HOMEPAGE_VIEW("view/offers.fxml");


    private String url;

    ViewPath(String envUrl){
        this.url=envUrl;
    }
    public String getUrl() {
        return url;
    }
}
