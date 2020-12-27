package gui.controller;

import business.facade.EvaluationFacade;
import business.facade.HistoryFacade;
import business.system.Category;
import business.system.offer.Offer;
import business.system.user.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import gui.ViewPath;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import org.controlsfx.control.Rating;
import util.MapRessourceBundle;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EvaluationController implements Initializable {
    @FXML
    private JFXTextArea commentArea;
    @FXML
    private Rating rateArea;
    @FXML
    private JFXComboBox<Integer> star;
    @FXML
    private JFXButton submit;
    @FXML
    private Hyperlink cancel;
    @FXML
    private JFXTextField titleOffer;
    @FXML
    private JFXTextArea desc;
    private Offer offer;


    public void handleRate(ActionEvent actionEvent){}
    public void handleComment(ActionEvent actionEvent){}

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(((MapRessourceBundle)resources).size()!=0){
            this.offer=(Offer) resources.getObject("0");
        }
        System.out.println(offer.getTitle());
        titleOffer.setText(offer.getTitle());
        desc.setEditable(false);
        desc.setText(offer.getDescription());
        star.getItems().clear();
        star.getItems().addAll((new ArrayList<>(Arrays.asList(0,1, 2, 3, 4, 5))));
    }
    public void historyPage(javafx.event.ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent, ViewPath.HISTORY_VIEW);
    }
    public void handleRate(javafx.event.ActionEvent actionEvent){
        int rate = star.getValue();
        System.out.println(rate);
        EvaluationFacade evaluationFacade = EvaluationFacade.getInstance();
        evaluationFacade.rate(offer,rate);
        LoadView.changeScreen(actionEvent, ViewPath.HISTORY_VIEW);
    }


}
