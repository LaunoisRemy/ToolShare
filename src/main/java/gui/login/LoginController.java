package gui.login;


import business.system.User;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import business.facade.UserFacade;
import javafx.scene.control.Label;

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
            System.out.println("OK");
        }
    }
}
