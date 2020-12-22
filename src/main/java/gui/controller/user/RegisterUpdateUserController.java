package gui.controller.user;

import business.exceptions.BadInsertionInBDDException;
import business.facade.SessionFacade;
import business.system.user.OrdinaryUser;
import business.system.user.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import util.MapRessourceBundle;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterUpdateUserController implements Initializable {

    private SessionFacade facade=SessionFacade.getInstance();;
    private User user=null;
    private int action=0;
    @FXML
    private JFXTextField email,firstname,lastname,city,phone;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label error_msg,profileLabel,registerLabel;
    @FXML
    private JFXButton submit;
    @FXML
    private Hyperlink cancel;

    /**
     *
     * @param location
     * @param resources got two resources
     *                 - the User : User
     *                 - the Action : Int = 0 to register or 1 to update
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(((MapRessourceBundle)resources).size()!=0){
            this.user=(User)resources.getObject("0");
            this.action = (Integer) resources.getObject("1");
        }
        if(action==0){
            registerLabel.setVisible(true);
            submit.setOnAction(this::handleRegister);
            cancel.setOnAction(this::loginPage);

        }else{
            profileLabel.setVisible(true);
            email.setText(user.getEmail());
            firstname.setText(user.getFirstName());
            lastname.setText(user.getLastName());
            city.setText(((OrdinaryUser)user.getRole()).getPhoneNumber());
            phone.setText(((OrdinaryUser)user.getRole()).getPhoneNumber());
            submit.setOnAction(this::handleUpdate);
            cancel.setOnAction(this::homePage);


        }
    }

    private final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final String NAME_REGEX = "^(([A-Za-z]+[,.]?[ ]?|[a-z]+['-]?)+)$";
    private final String PHONE_REGEX = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";


    private boolean checkInputs(){
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
        return checked;
    }

    public void handleRegister(ActionEvent actionEvent){


        try {
            if(checkInputs()){
                facade.register(email.getText(), firstname.getText(), lastname.getText(), city.getText(), phone.getText(), password.getText(), false);
                LoadView.changeScreen(actionEvent, ViewPath.LOGIN_VIEW);
            }else{
                error_msg.setVisible(true);
            }

        } catch (BadInsertionInBDDException e){
            System.out.println(e.toString());
        }
    }
    public void handleUpdate(ActionEvent actionEvent){
        if(checkInputs()){
            facade.updateProfile(email.getText(), firstname.getText(), lastname.getText(), city.getText(), phone.getText(),password.getText());
        }else{
            error_msg.setVisible(true);
        }

    }

    public void loginPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,ViewPath.LOGIN_VIEW);
    }
    public void homePage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,ViewPath.HOMEPAGE_VIEW);
    }



}
