package gui.controller.user;

import business.AlertBox;
import business.facade.SessionFacade;
import business.system.user.User;
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
import java.util.regex.Pattern;

public class ForgotPasswordController implements Initializable {
    private SessionFacade facade = SessionFacade.getInstance();
    @FXML
    private URL location;


    @FXML
    private Hyperlink cancel;

    @FXML
    private JFXTextField mail;
    @FXML
    private Label mailError;

    private String stringMail;
    @FXML
    private JFXTextField code;
    @FXML
    private Label errorCode;
    @FXML
    private Label errorFormat;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField confirmPassword;
    @FXML
    private Label errorPassword;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(((MapRessourceBundle)resources).size()!=0){
            if(ConstantsRegex.match(Pattern.compile("forgot_password_check_code"),location.getFile())){ //Optimiser
                this.stringMail=(String)resources.getObject("0");
            }
        }
    }

    /***
     * Method which handle button send recovery mail
     * @param actionEvent
     */
    public void sendMail(ActionEvent actionEvent){
        try {
            stringMail = mail.getText();

            if(!ConstantsRegex.matchEmail(stringMail)){
                mail.setUnFocusColor(Paint.valueOf("red"));
                mailError.setVisible(true);
            }else{
                facade.sendMail(stringMail);
                LoadView.changeScreen(actionEvent, ViewPath.FORGOTPASSWORDCHECKCODE_VIEW,stringMail);
            }
        } catch (Exception e) {
            AlertBox.showAlert("Not yet Implemented","Not yet implemented");
        }
    }
    /***
     * Method which handle button validate
     * @param actionEvent
     */
    public void checkCode(ActionEvent actionEvent){
        try {
            String stringCode = code.getText();
            if(!ConstantsRegex.matchCodeRegex(stringCode)){
                code.setUnFocusColor(Paint.valueOf("red"));
                errorCode.setVisible(false);
                errorFormat.setVisible(true);
            }else{
                boolean check = facade.checkCode(code.getText(),stringMail);
                if(check){
                    LoadView.changeScreen(actionEvent, ViewPath.CHANGEPASSWORD_VIEW);
                }else{
                    errorFormat.setVisible(false);
                    errorCode.setVisible(true);
                }
            }
        } catch (Exception e) {
            AlertBox.showAlert("Error",e.toString());
        }
    }

    /***
     * Method which handle changePassword action
     * @param actionEvent
     */
    public void changePassword(ActionEvent actionEvent){
        try {
            String stringPassword = password.getText();
            String stringConfirmPassword = confirmPassword.getText();

            if(stringPassword.equals(stringConfirmPassword)){
                facade.changePassword(stringConfirmPassword);
                AlertBox.showAlert("Success","Password has been changed");
                LoadView.changeScreen(actionEvent, ViewPath.LOGIN_VIEW);
            }else{
                errorPassword.setVisible(true);
            }
        } catch (Exception e) {
            AlertBox.showAlert("Error",e.toString());
        }
    }

    public void loginPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent, ViewPath.LOGIN_VIEW);
    }

}
