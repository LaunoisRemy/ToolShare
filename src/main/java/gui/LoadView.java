package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class LoadView {
    public static Parent load(String nameFxml) throws IOException {
        URL urlPath = LoadView.class.getClassLoader().getResource("view/"+nameFxml);
        if(urlPath == null){
            throw new IOException("Problem of path for fxml");
        }
        FXMLLoader loader = new FXMLLoader(urlPath);
        return (Parent)loader.load();
    }
}
