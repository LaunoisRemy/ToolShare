package gui.router;

import business.system.user.User;
import gui.LoadView;
import gui.controller.user.AddUpdateUserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserRouter {

    public void updateUser(ActionEvent actionEvent, String view, User user){
        Parent parent = null;
        try {

            FXMLLoader loader = LoadView.loader(view);
            AddUpdateUserController addUpdateUserController = new AddUpdateUserController(user,1);
            loader.setController(addUpdateUserController);
            parent = loader.load();
            Scene newScene = new Scene(parent);
            Stage window =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(newScene);
            window.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(ActionEvent actionEvent, String view){
        try {

            FXMLLoader loader = LoadView.loader(view);
            AddUpdateUserController addUpdateUserController = new AddUpdateUserController(null,0);
            loader.setController(addUpdateUserController);
            Parent parent = loader.load();
            Scene newScene = new Scene(parent);
            Stage window =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(newScene);
            window.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
