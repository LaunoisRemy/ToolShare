package gui.controller;

import business.facade.SessionFacade;
import business.system.user.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RegisterController {
    private SessionFacade facade = SessionFacade.getInstance();
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField firstname;
    @FXML
    private JFXTextField lastname;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField city;
    @FXML
    private JFXTextField phone;

    public void handleRegister(ActionEvent actionEvent){
        User user=facade.register(email.getText(),firstname.getText(),lastname.getText(),city.getText(),phone.getText(),password.getText());
        if(user!=null){
            LoadView.changeScreen(actionEvent,"offers");
        }else{
            System.out.println("rip");
        }
    }
    public void loginPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"login");
    }


}
