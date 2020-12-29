package gui.controller;

import business.facade.EvaluationFacade;
import business.system.ScoreOffer;
import business.system.offer.Offer;
import com.jfoenix.controls.*;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.Rating;
import util.ConstantsRegex;
import util.MapRessourceBundle;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Controller which deals with rating an offer and comment it
 */
public class EvaluationController implements Initializable {

    @FXML
    private JFXTextArea commentArea;
    @FXML
    private Rating rateArea;
    @FXML
    private JFXComboBox<Integer> star;
    @FXML
    private JFXButton submit, submitComment;
    @FXML
    private JFXRadioButton yesButton, noButton;
    @FXML
    private ToggleGroup choiceComment;
    @FXML
    private Hyperlink cancel;
    @FXML
    private JFXTextField titleOffer;
    @FXML
    private JFXTextArea desc;

    private Offer offer;
    private ScoreOffer scoreOffer;
    private String commentText;


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

        if(ConstantsRegex.match(Pattern.compile(ViewPath.RATE_VIEW.getUrl()),location.getFile())){
            if(((MapRessourceBundle)resources).size()!=0){
                this.offer=(Offer) resources.getObject("0");
            }

            star.getItems().clear();
            star.getItems().addAll((new ArrayList<>(Arrays.asList(0,1, 2, 3, 4, 5))));
            star.getSelectionModel().select(0);
        }else if(ConstantsRegex.match(Pattern.compile(ViewPath.COMMENT_VIEW.getUrl()),location.getFile())){
            if(((MapRessourceBundle)resources).size()!=0){
                this.scoreOffer=(ScoreOffer) resources.getObject("0");
                this.offer = scoreOffer.getOffer();
            }
            noButton.setSelected(true);
            yesButton.setSelected(false);
            commentArea.setEditable(false);
            commentArea.setVisible(false);
        }

        titleOffer.setText(offer.getTitle());
        desc.setEditable(false);
        desc.setText(offer.getDescription());

    }


    /**
     * Handle text area depending of choice of user
     * Yes -> textarea visible, editable with text
     * No -> textArea not accessible
     * @param actionEvent
     */
    public void handleChoiceComment(javafx.event.ActionEvent actionEvent){
        if(yesButton.isSelected()){
            commentArea.setEditable(true);
            commentArea.setText(commentText);
            commentArea.setVisible(true);
        }else{
            commentArea.setEditable(false);
            commentText = commentArea.getText();
            commentArea.clear();
            commentArea.setVisible(false);

        }
    }
    public void historyPage(Event actionEvent){
        LoadView.changeScreen(actionEvent, ViewPath.HISTORY_VIEW);
    }

    /**
     * Handle action if the user click on submit for rate Page
     * @param actionEvent
     */
    public void handleRate(Event actionEvent){
        int rate = star.getValue();
        EvaluationFacade evaluationFacade = EvaluationFacade.getInstance();
        ScoreOffer scoreOffer = evaluationFacade.rate(offer,rate);
        LoadView.changeScreen(actionEvent, ViewPath.COMMENT_VIEW,scoreOffer);
    }

    /**
     * Handle action if the user click on submit for comment Page
     * @param actionEvent
     */
    public void handleComment(Event actionEvent){

        if(yesButton.isSelected()){
            EvaluationFacade evaluationFacade = EvaluationFacade.getInstance();
            evaluationFacade.comment(scoreOffer,commentArea.getText());

        }
        LoadView.changeScreen(actionEvent, ViewPath.HISTORY_VIEW);
    }

}
