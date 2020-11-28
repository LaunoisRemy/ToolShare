package main;

import gui.LoadView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Tool Share");
        primaryStage.setScene(new Scene(new LoadView().load("login"), 1280, 900));
        primaryStage.show();
    }


}
