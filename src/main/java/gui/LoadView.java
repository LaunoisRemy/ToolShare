package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class LoadView  extends Parent{

    public static Parent load(String nameFxml) throws IOException {
        URL urlPath = LoadView.class.getClassLoader().getResource("view/"+nameFxml+".fxml");
        if(urlPath == null){
            throw new IOException("Problem of path for fxml");
        }
        FXMLLoader loader = new FXMLLoader(urlPath);
        return (Parent)loader.load();
    }

    public static void changeScreen(ActionEvent actionEvent, String view) {
        try{
            Parent parent = LoadView.load(view);
            Scene newScene = new Scene(parent);
            Stage window =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(newScene);
            window.show();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
