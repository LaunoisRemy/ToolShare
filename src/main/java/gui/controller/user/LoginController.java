package gui.controller.user;


import business.exceptions.UserBannedException;
import business.exceptions.ObjectNotFoundException;
import business.exceptions.WrongPasswordException;
import business.system.user.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import gui.ViewPath;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import business.facade.SessionFacade;
import javafx.scene.control.Label;


public class LoginController {
    private final SessionFacade facade = SessionFacade.getInstance();
    @FXML
    private JFXTextField mail;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label error_msg;
    @FXML
    private Label banned_msg;

    /**
     * Handle the login from an user
     * @param actionEvent ActionEvent
     * Displays the corresponding view whether the user had valid credentials or not
     */
    public void handleLogin(ActionEvent actionEvent) {

        try {
            User user = facade.login(mail.getText(), password.getText());
            LoadView.changeScreen(actionEvent, ViewPath.HOMEPAGE_VIEW);
        } catch (ObjectNotFoundException | WrongPasswordException e1){
            banned_msg.setVisible(false);
            error_msg.setVisible(true);
        } catch (UserBannedException e2){
            error_msg.setVisible(false);
            banned_msg.setVisible(true);
        }

    }


    public void registerPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,ViewPath.REGISTERUPDATEUSER_VIEW);
    }
    public void forgotPasswordPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,ViewPath.FORGOTPASSWORD_VIEW);
    }
}
