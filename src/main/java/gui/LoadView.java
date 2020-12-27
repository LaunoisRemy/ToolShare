package gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import util.MapRessourceBundle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class LoadView  extends Parent{


    public static Parent load(ViewPath view,Object... objects) throws IOException {
        URL urlPath = LoadView.class.getClassLoader().getResource(view.getUrl());
        if(urlPath == null){
            throw new IOException("Problem of path for fxml");
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(urlPath);
        loader.setResources(new MapRessourceBundle(objects));
        return (Parent)loader.load();
    }

    public static void changeScreen(Event event, ViewPath view, Object... objects) {
        try{

            Parent parent = LoadView.load(view,objects);
            ScrollPane scrollPane = new ScrollPane(parent);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            Scene newScene = new Scene(scrollPane);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(newScene);
            window.show();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
