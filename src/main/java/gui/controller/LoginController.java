package gui.controller;


import business.exceptions.UserBannedException;
import business.exceptions.UserNotFoundException;
import business.exceptions.WrongPasswordException;
import business.system.user.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import business.facade.SessionFacade;
import javafx.scene.control.Label;


public class LoginController {
    private SessionFacade facade=SessionFacade.getInstance();
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
//        System.out.println(mail.getText());
//        System.out.println(password.getText());

        try {
            User user = facade.login(mail.getText(), password.getText());
            LoadView.changeScreen(actionEvent,"offers");
            System.out.println("OK");
        } catch (UserNotFoundException | WrongPasswordException e1){
            banned_msg.setVisible(false);
            error_msg.setVisible(true);
            e1.toString();
        } catch (UserBannedException e2){
            error_msg.setVisible(false);
            banned_msg.setVisible(true);
            e2.toString();
        }

    }

    public void registerPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"register");
    }
}
