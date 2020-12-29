package gui.controller.offer;

import business.facade.EvaluationFacade;
import business.facade.OfferFacade;
import business.facade.SessionFacade;
import business.system.Score;
import business.system.offer.Offer;
import business.system.scorable.Scorable;
import business.system.user.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import util.MapRessourceBundle;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OfferController implements Initializable {


    @FXML
    private JFXTextArea desc;
    @FXML
    private ImageView imgOffer;
    @FXML
    private Label titleOffer,category, state,price,offerLabelDesc,offerLabelComment;
    @FXML
    private JFXButton setPriority,editButton,deleteButton,faqButton,offersCommentButton;

    @FXML
    private TableView<Scorable> faqTable;
    @FXML
    private TableColumn<Scorable,String> contentScorable;
    @FXML
    private TableColumn<Scorable,Integer> replyCol, voteCol;
    @FXML
    private ScrollPane scrollPane;


    private Offer offer;
    private OfferFacade offerFacade;

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
        offerFacade = OfferFacade.getInstance();
        SessionFacade sessionFacade = SessionFacade.getInstance();
        User u = sessionFacade.getUser();


        if(((MapRessourceBundle)resources).size()!=0){
            this.offer=(Offer) resources.getObject("0");
        }
        boolean isOwner =  u.getUser_id() == offer.getUser().getUser_id();

        if( u.isAdmin() || isOwner){
            editButton.setDisable(false);
            editButton.setVisible(true);

            deleteButton.setVisible(true);
            deleteButton.setDisable(false);
            if(isOwner){
                setPriority.setVisible(true);
                setPriority.setDisable(false);
            }
        }else{
            editButton.setDisable(true);
            editButton.setVisible(false);

            setPriority.setVisible(false);
            setPriority.setDisable(true);

            deleteButton.setVisible(false);
            deleteButton.setDisable(true);
        }

        titleOffer.setText(offer.getTitle());
        desc.setText(offer.getDescription());
        category.setText(offer.getCategory().getCategoryName());
        state.setText(offer.getToolSate().getString());
        price.setText(String.valueOf(offer.getPricePerDay()));
        desc.setEditable(false);

        populateFAQTable(offerFacade,isOwner);

    }


    /**
     * Method to populate table of faq
     * @param offerFacade facade offer
     * @param isOwner if user is owner
     */
    private void populateFAQTable(OfferFacade offerFacade,boolean isOwner) {
        List<Scorable> questions = offerFacade.getAllQuestions(offer);
        final ObservableList<Scorable> data = FXCollections.observableArrayList(questions);

        contentScorable.setCellValueFactory(new PropertyValueFactory<>("questionContent") );
        contentScorable.setCellFactory(TextFieldTableCell.forTableColumn());

        replyCol.setCellFactory(param -> new TableCell<>() {
            private final Button replyButton = new Button("Reply");
            {
                replyButton.setOnAction(event -> replyQuestion(event, param.getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    if(isOwner){
                        setGraphic(replyButton);

                    }else{
                        replyButton.setVisible(false);
                    }
                }
            }
        });

        voteButton(voteCol);


        faqTable.setItems(data);
    }
    /**
     * Method to populate table of comments
     * @param offerFacade
     */
    private void populateCommentTable(OfferFacade offerFacade) {
        List<Scorable> comments = offerFacade.getAllComments(offer);
        final ObservableList<Scorable> data = FXCollections.observableArrayList(comments);

        contentScorable.setCellValueFactory(new PropertyValueFactory<>("comment") );
        contentScorable.setCellFactory(TextFieldTableCell.forTableColumn());

        voteButton(voteCol);

        faqTable.setItems(data);
    }

    private void voteButton(TableColumn<Scorable,Integer> upVoteCol){
        upVoteCol.setCellFactory(param -> new TableCell<>() {

            private final Button upVoteFAQButton = new Button("Up vote");
            private final Button downVoteFAQButton = new Button("Down vote");
            private final Label scoreLabel = new Label("");

            private final HBox pane = new HBox(upVoteFAQButton,scoreLabel, downVoteFAQButton );

            {

                upVoteFAQButton.setOnAction(event -> {
                    Scorable s = param.getTableView().getItems().get(getIndex());
                    Score score = EvaluationFacade.getInstance().findScorable(param.getTableView().getItems().get(getIndex()));
                    if(score != null){
                        if(score.getScoreValue() != 1){
                            voteScorable(event,s  ,1);

                            upVoteFAQButton.setStyle("-fx-background-color: green");
                            downVoteFAQButton.setStyle("-fx-background-color: white");

                            scoreLabel.setText(String.valueOf(s.getScore()));
                            scoreLabel.setTextFill(Color.web("#008000"));
                        }else{
                            voteScorable(event,s,0);

                            upVoteFAQButton.setStyle("-fx-background-color: white");
                            downVoteFAQButton.setStyle("-fx-background-color: white");
                            scoreLabel.setTextFill(Color.web("#000000"));
                        }
                        scoreLabel.setText(String.valueOf(s.getScore()));

                    }

                });
                downVoteFAQButton.setOnAction(event -> {
                    Scorable s = param.getTableView().getItems().get(getIndex());
                    Score score = EvaluationFacade.getInstance().findScorable(param.getTableView().getItems().get(getIndex()));
                    scoreLabel.setText(String.valueOf(s.getScore()));

                    if(score != null){
                        if(score.getScoreValue() != -1){
                            voteScorable(event, s,-1);
                            downVoteFAQButton.setStyle("-fx-background-color: red");
                            upVoteFAQButton.setStyle("-fx-background-color: white");

                            scoreLabel.setTextFill(Color.web("#FF0000"));
                        }else{
                            voteScorable(event,s,0);

                            upVoteFAQButton.setStyle("-fx-background-color: white");
                            downVoteFAQButton.setStyle("-fx-background-color: white");

                            scoreLabel.setTextFill(Color.web("#000000"));
                        }
                        scoreLabel.setText(String.valueOf(s.getScore()));

                    }

                });
            }


            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty ) {
                    setGraphic(null);
                }
                else {
                    setGraphic(pane);
                    Score score = EvaluationFacade.getInstance().findScorable(param.getTableView().getItems().get(getIndex()));
                    scoreLabel.setText(String.valueOf(param.getTableView().getItems().get(getIndex()).getScore()));
                    if( score != null){
                        if(score.getScoreValue() == 1){
                            upVoteFAQButton.setStyle("-fx-background-color: green");
                            downVoteFAQButton.setStyle("-fx-background-color: white");
                            scoreLabel.setTextFill(Color.web("#008000"));

                        }else if(score.getScoreValue() == -1){
                            downVoteFAQButton.setStyle("-fx-background-color: red");
                            upVoteFAQButton.setStyle("-fx-background-color: white");
                            scoreLabel.setTextFill(Color.web("#FF0000"));
                        }
                    }else{
                        upVoteFAQButton.setDisable(true);
                    }
                }

            }
        });
    }


    public void showFAQTable(ActionEvent actionEvent){
        SessionFacade sessionFacade = SessionFacade.getInstance();
        User u = sessionFacade.getUser();
        boolean isOwner =  u.getUser_id() == offer.getUser().getUser_id();
        replyCol.setVisible(true);
        populateFAQTable(offerFacade,isOwner);

    }

    public void showCommentsTable(ActionEvent actionEvent){
        replyCol.setVisible(false);
        populateCommentTable(offerFacade);

    }


    private void replyQuestion(ActionEvent event, Scorable question) {
        System.out.println("Reply");
    }



    private void voteScorable(ActionEvent event, Scorable scorable, int i) {
        EvaluationFacade.getInstance().vote(scorable,i);
    }

}
