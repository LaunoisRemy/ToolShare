package gui.controller;


import business.system.User;
import com.jfoenix.controls.JFXTextField;
import dao.mysql.UserDaoMySQL;
import gui.LoadView;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import business.facade.UserFacade;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    private UserFacade facade=new UserFacade();

    @FXML
    private JFXTextField mail;
    @FXML
    private JFXTextField password;
    @FXML
    private Label error_msg;

    public void handleLogin(ActionEvent actionEvent){
        System.out.println(mail.getText());
        System.out.println(password.getText());
        User user=facade.login(mail.getText(),password.getText());
        if (user == null) {
            error_msg.setVisible(true);
        }
        else{
            LoadView.changeScreen(actionEvent,"offers");
            System.out.println("OK");
        }
    }
}
