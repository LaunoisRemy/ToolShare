package main;

import gui.LoadView;
import gui.ViewPath;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Tool Share");
        primaryStage.setScene(new Scene(LoadView.load(ViewPath.LOGIN_VIEW), 1280, 900));
        primaryStage.getIcons().add(new Image("img/logo.png"));
//        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


}

