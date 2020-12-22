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

public class ForgotPasswordController {
    private SessionFacade facade = SessionFacade.getInstance();
    @FXML
    private Hyperlink cancel;

    @FXML
    private JFXTextField mail;

    @FXML
    private JFXTextField code;
    @FXML
    private Label errorCode;

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
            System.out.println(mail.getText());

            facade.sendMail(mail.getText());
            //TODO check mail regex
            LoadView.changeScreen(actionEvent, ViewPath.FORGOTPASSWORDCHECKCODE_VIEW);
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
            boolean check = facade.checkCode(code.getText());
            //TODO check mail regex
            if(check){
                LoadView.changeScreen(actionEvent, ViewPath.CHANGEPASSWORD_VIEW);
            }else{
                errorCode.setVisible(true);
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
            System.out.println(password);
            System.out.println(confirmPassword);
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
