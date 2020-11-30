package gui.controller;


import business.system.user.User;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import business.facade.UserFacade;
import javafx.scene.control.Label;


public class LoginController {
    private UserFacade facade=new UserFacade();

    @FXML
    private JFXTextField mail;
    @FXML
    private JFXTextField password;
    @FXML
    private Label error_msg;
    @FXML
    private Label banned_msg;

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
}
