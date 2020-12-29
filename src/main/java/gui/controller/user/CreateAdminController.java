package gui.controller.user;

import business.exceptions.BadInsertionInBDDException;
import business.facade.SessionFacade;
import business.system.user.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import gui.ViewPath;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import util.ConstantsRegex;

public class CreateAdminController {
    private final SessionFacade sessionFacade = SessionFacade.getInstance();

    @FXML
    private JFXTextField email,firstname,lastname;
    @FXML
    private JFXPasswordField password;
    @FXML
    private Label error_msg;

    /**
     * Method that allows to check every input of the admin register form
     * @return true if every inputs are valid; else false
     */
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
        return checked;
    }

    public void handleNewAdmin(ActionEvent event) {
        if(checkInputs()){
            try {
                this.sessionFacade.register(email.getText(),firstname.getText(),lastname.getText(),null,null,password.getText(),true);
            } catch (BadInsertionInBDDException e) {
                e.printStackTrace();
            }
            this.userListPage(event);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Admin Created");
            alert.setHeaderText(null);
            alert.setContentText("The new admin is well created !");

            alert.showAndWait();
        } else {
            error_msg.setVisible(true);
        }
    }

    public void userListPage(ActionEvent event){
        LoadView.changeScreen(event, ViewPath.USERLIST_VIEW);
    }
}
