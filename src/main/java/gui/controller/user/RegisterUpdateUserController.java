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
import util.ConstantsRegex;
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

    private boolean checkInputs(){
        boolean checked = true;
        if(!ConstantsRegex.matchEmail((email.getText()))){
            checked = false;
            email.setUnFocusColor(Paint.valueOf("red"));
        }
        else{
            email.setUnFocusColor(Paint.valueOf("black"));
        }
        if(!ConstantsRegex.matchNameRegex(firstname.getText())){
            checked = false;
            firstname.setUnFocusColor(Paint.valueOf("red"));
        }
        else{
            firstname.setUnFocusColor(Paint.valueOf("black"));
        }
        if(!ConstantsRegex.matchNameRegex(lastname.getText())){
            checked = false;
            lastname.setUnFocusColor(Paint.valueOf("red"));
        }
        else{
            lastname.setUnFocusColor(Paint.valueOf("black"));
        }
        if(!ConstantsRegex.matchNameRegex((city.getText()))) {
            checked = false;
            city.setUnFocusColor(Paint.valueOf("red"));
        }
        else{
            city.setUnFocusColor(Paint.valueOf("black"));
        }
        if(!ConstantsRegex.matchPhoneRegex((phone.getText()))){
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
