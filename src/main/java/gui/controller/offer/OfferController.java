package gui.controller.offer;

import business.exceptions.ObjectNotFoundException;
import business.facade.EvaluationFacade;
import business.facade.OfferFacade;
import business.facade.SessionFacade;
import business.system.Score;
import business.system.offer.Offer;
import business.system.scorable.Scorable;
import business.system.scorable.faq.Answer;
import business.system.scorable.faq.Question;
import business.system.user.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import gui.LoadView;
import gui.ViewPath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import util.AlertBox;
import util.MapRessourceBundle;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OfferController implements Initializable {

    @FXML
    private JFXTextArea desc;
    @FXML
    private ImageView imgOffer;
    @FXML
    private Label titleOffer,category, state,price,offerLabelDesc,offerLabelComment;
    @FXML
    private JFXButton setPriority,editButton,deleteButton,faqButton,offersCommentButton, bookButton, PostQuestion;

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
            try {
                this.offer=offerFacade.getOffer(offer.getOffer_id());
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            }
        }

        boolean isOwner =  u.getUser_id() == offer.getUser().getUser_id();

        MyOffersController myc = new MyOffersController();
        setPriority.setOnAction(event ->
                this.alertPriority(event, this.offer, myc)
        );

        deleteButton.setOnAction(event ->
                alertDelete(event,this.offer)
        );

        editButton.setOnAction(event ->
                myc.handleUpdateOffer(event,this.offer)
        );

        if( u.isAdmin() || isOwner){
            editButton.setDisable(false);
            editButton.setVisible(true);

            deleteButton.setVisible(true);
            deleteButton.setDisable(false);

            if(isOwner){
                setPriority.setDisable(false);
                setPriority.setVisible(true);

                bookButton.setDisable(true);
                bookButton.setVisible(true);
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
                if(isOwner){
                    replyButton.setOnAction(event -> replyQuestion(event, param.getTableView().getItems().get(getIndex())));
                }
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    Question q = (Question)param.getTableView().getItems().get(getIndex());
                    Answer a =  q.getAnswer();
                    if(isOwner && a == null){
                        setGraphic(replyButton);

                    }else{
                        Label tf = new Label();

                        if(a != null){
                            tf.setText(a.getAnswerContent());
                        }else{
                            tf.setText("No reponse");
                        }
                        setGraphic(tf);
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

                            scoreLabel.setTextFill(Color.web("#008000"));
                        }else{
                            voteScorable(event,s,0);

                            upVoteFAQButton.setStyle("-fx-background-color: white");
                            downVoteFAQButton.setStyle("-fx-background-color: white");
                            scoreLabel.setTextFill(Color.web("#000000"));

                        }

                    }else{
                        voteScorable(event,s  ,1);
                        upVoteFAQButton.setStyle("-fx-background-color: green");
                        downVoteFAQButton.setStyle("-fx-background-color: white");
                        scoreLabel.setTextFill(Color.web("#008000"));
                    }
                    scoreLabel.setText(String.valueOf(s.getScore()));
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

                    }else{
                        voteScorable(event, s,-1);
                        downVoteFAQButton.setStyle("-fx-background-color: red");
                        upVoteFAQButton.setStyle("-fx-background-color: white");

                        scoreLabel.setTextFill(Color.web("#FF0000"));
                    }
                    scoreLabel.setText(String.valueOf(s.getScore()));

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
        Optional<String> result = dialogFAQ("Reply dialog","Enter you reply below :");
        result.ifPresent(s -> EvaluationFacade.getInstance().reply(question, s));
    }



    private void voteScorable(ActionEvent event, Scorable scorable, int i) {
        EvaluationFacade.getInstance().vote(scorable,i);
    }

    private void alertDelete(ActionEvent event, Offer offer) {
        Alert alert = AlertBox.showAlertConfirmationDeletion();

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            handleDeleteOffer(event,offer);
        }
    }

    private void handleDeleteOffer(ActionEvent event,Offer offer) {
        boolean deleted = false;
        try {
            deleted = this.offerFacade.deleteOffer(offer.getOffer_id());
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }

        if(deleted){
            LoadView.changeScreen(event, ViewPath.MYOFFERS_VIEW);
        }
    }

    private void alertPriority(ActionEvent event, Offer offer, MyOffersController myc) {
        Dialog<Pair<Date, Date>> dialog = myc.dialogPriority();

        Optional<Pair<Date, Date>> result = dialog.showAndWait();

        result.ifPresent(dates -> {
            this.offer = myc.handleSetPriority(offer,dates.getKey(),dates.getValue());
            //refresh page to add change the priority
            LoadView.changeScreen(event, ViewPath.OFFER_VIEW,this.offer);
        });
    }

    public void handleEditOffer(ActionEvent actionEvent) {
        LoadView.changeScreen(actionEvent, ViewPath.POSTUPDATEOFFER_VIEW, this.offer, 1);
    }
    public void handlePostQuestion(ActionEvent actionEvent) {
        Optional<String> result = dialogFAQ("Question dialog","Enter you question below :");

        if(result.isPresent()){
            Question q = EvaluationFacade.getInstance().postQuestion(offer, result.get());
            populateFAQTable(offerFacade, SessionFacade.getInstance().getUser().getUser_id() == offer.getUser().getUser_id());
        }
    }

    private Optional<String> dialogFAQ(String title,String header){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        ButtonType validate = new ButtonType("Validate", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(validate, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        TextArea textArea = new TextArea();
        textArea.setEditable(true);
        grid.add(textArea, 0, 1);
        dialog.getDialogPane().setContent(grid);
//        dialog.showAndWait();
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == validate) {
                return textArea.getText();
            }
            return null;
        });
        return dialog.showAndWait();
    }

    public void handleBookOffer(ActionEvent actionEvent) {
        LoadView.changeScreen(actionEvent, ViewPath.POSTUPDATERESERVATION_VIEW, this.offer);
    }
}
