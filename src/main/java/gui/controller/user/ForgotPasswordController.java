package gui.controller.user;

import business.AlertBox;
import business.facade.SessionFacade;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import util.ConstantsRegex;

public class ForgotPasswordController {
    private SessionFacade facade = SessionFacade.getInstance();
    @FXML
    private Hyperlink cancel;

    @FXML
    private JFXTextField mail;
    @FXML
    private Label mailError;

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


    /***
     * Method which handle button send recovery mail
     * @param actionEvent
     */
    public void sendMail(ActionEvent actionEvent){
        try {
            String stringMail = mail.getText();

            if(!ConstantsRegex.matchEmail(stringMail)){
                mail.setUnFocusColor(Paint.valueOf("red"));
                mailError.setVisible(true);
            }else{
                facade.sendMail(mail.getText());
                LoadView.changeScreen(actionEvent, ViewPath.FORGOTPASSWORDCHECKCODE_VIEW);
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
            String stringCode =code.getText();
            if(!ConstantsRegex.matchCodeRegex(stringCode)){
                code.setUnFocusColor(Paint.valueOf("red"));
                errorCode.setVisible(false);
                errorFormat.setVisible(true);
            }else{
                boolean check = facade.checkCode(code.getText());
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
