package gui.controller.user;

import business.exceptions.BadInsertionInBDDException;
import business.facade.SessionFacade;
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
        try {
            facade.register(email.getText(), firstname.getText(), lastname.getText(), city.getText(), phone.getText(), password.getText(), false);
            LoadView.changeScreen(actionEvent, "login");
        } catch (BadInsertionInBDDException e){
            System.out.println(e.toString());
        }
    }
    public void loginPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"login");
    }


}
