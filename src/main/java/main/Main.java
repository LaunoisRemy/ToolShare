package main;

import gui.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        URL test = getClass().getClassLoader().getResource("gui/login/login.fxml");
        FXMLLoader loader = new FXMLLoader(test);
        Parent root = null;
        try {
            root = (Parent)loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Tool Share");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


}
