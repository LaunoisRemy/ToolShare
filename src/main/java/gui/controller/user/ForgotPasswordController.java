package gui.controller.user;

import business.exceptions.NotYetImplementedException;
import business.facade.SessionFacade;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ForgotPasswordController {
    private SessionFacade facade = SessionFacade.getInstance();

    @FXML
    private JFXTextField mail;

    public void sendMail(ActionEvent actionEvent){
        try {
            facade.sendMail(mail.getText());
        } catch (NotYetImplementedException e) {
            e.printStackTrace();
        }
    }
}
