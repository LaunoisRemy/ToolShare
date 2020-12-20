package gui.controller.user;

import business.exceptions.BadInsertionInBDDException;
import business.facade.SessionFacade;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

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

    @FXML
    private Label error_msg;


    private final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final String NAME_REGEX = "^(([A-Za-z]+[,.]?[ ]?|[a-z]+['-]?)+)$";
    private final String PHONE_REGEX = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";

    public void handleRegister(ActionEvent actionEvent){
        try {

            boolean checked = true;
            if(!email.getText().matches(EMAIL_REGEX)){
                checked = false;
                email.setUnFocusColor(Paint.valueOf("red"));
            }
            else{
                email.setUnFocusColor(Paint.valueOf("black"));
            }
            if(!firstname.getText().matches(NAME_REGEX)){
                checked = false;
                firstname.setUnFocusColor(Paint.valueOf("red"));
            }
            else{
                firstname.setUnFocusColor(Paint.valueOf("black"));
            }
            if(!lastname.getText().matches(NAME_REGEX)){
                checked = false;
                lastname.setUnFocusColor(Paint.valueOf("red"));
            }
            else{
                lastname.setUnFocusColor(Paint.valueOf("black"));
            }
            if(!city.getText().matches(NAME_REGEX)) {
                checked = false;
                city.setUnFocusColor(Paint.valueOf("red"));
            }
            else{
                city.setUnFocusColor(Paint.valueOf("black"));
            }
            if(!phone.getText().matches(PHONE_REGEX)){
                checked = false;
                phone.setUnFocusColor(Paint.valueOf("red"));
            }
            else{
                phone.setUnFocusColor(Paint.valueOf("black"));
            }
            if(checked){
                facade.register(email.getText(), firstname.getText(), lastname.getText(), city.getText(), phone.getText(), password.getText(), false);
                LoadView.changeScreen(actionEvent, "login");
            }else{
                error_msg.setVisible(true);
            }

        } catch (BadInsertionInBDDException e){
            System.out.println(e.toString());
        }
    }
    public void loginPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"login");
    }


}
