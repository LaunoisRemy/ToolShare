package gui.controller;


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
    public void handleLogin(ActionEvent actionEvent){
        System.out.println(mail.getText());
        System.out.println(password.getText());
        User user=facade.login(mail.getText(),password.getText());
        if (user == null) {
            banned_msg.setVisible(false);
            error_msg.setVisible(true);
        }
        else{
            if(user.isBanned()){
                error_msg.setVisible(false);
                banned_msg.setVisible(true);
            }
            LoadView.changeScreen(actionEvent,"offers");
            System.out.println("OK");
        }
    }

    public void registerPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"register");
    }
}
