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
    private Label titleOffer,category, state,price,commentLabel,faqLabel,offerLabelDesc,offerLabelComment;
    @FXML
    private JFXButton setPriority,editButton,deleteButton;

    @FXML
    private TableView<Scorable> faqTable;
    @FXML
    private TableColumn<Scorable,String> questionFAQ;
    @FXML
    private TableColumn<Scorable,Integer> replyCol,upVoteFAQ,downVoteFAQ;

    @FXML
    private TableView<Scorable> commentTable;
    @FXML
    private TableColumn<Scorable,String> commentCol;
    @FXML
    private TableColumn<Scorable,Integer> upVoteComment,downVoteComment;


    private Offer offer;
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
        OfferFacade offerFacade = OfferFacade.getInstance();
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
        populateCommentTable(offerFacade);

    }


    /**
     * Method to populate table of faq
     * @param offerFacade facade offer
     * @param isOwner if user is owner
     */
    private void populateFAQTable(OfferFacade offerFacade,boolean isOwner) {
        List<Scorable> questions = offerFacade.getAllQuestions(offer);
        final ObservableList<Scorable> data = FXCollections.observableArrayList(questions);

        questionFAQ.setCellValueFactory(new PropertyValueFactory<>("questionContent") );
        questionFAQ.setCellFactory(TextFieldTableCell.forTableColumn());

        replyCol.setCellFactory(param -> new TableCell<>() {
            private final Button replyButton = new Button("Reply");
            {
                replyButton.setOnAction(event -> {
                    replyQuestion(event, param.getTableView().getItems().get(getIndex()));
                });
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

        upVoteButton(upVoteFAQ);
        downVoteButton(downVoteFAQ);


        faqTable.setItems(data);
    }

    private void upVoteButton(TableColumn<Scorable,Integer> upVoteCol){
        upVoteCol.setCellFactory(param -> new TableCell<>() {

            private final Button upVoteFAQButton = new Button("Up vote");
            {
                upVoteFAQButton.setOnAction(event -> {
                    voteScorable(event, param.getTableView().getItems().get(getIndex()) ,1);
                });
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty ) {
                    setGraphic(null);
                }
                else {
                    setGraphic(upVoteFAQButton);
                    Score score = EvaluationFacade.getInstance().findScorable(param.getTableView().getItems().get(getIndex()));
                    if( score != null){
                        if(score.getScoreValue() == 1){
                            upVoteFAQButton.setDisable(true);
                        }
                    }else{
                        upVoteFAQButton.setDisable(true);
                    }
                }

            }
        });
    }

    private void downVoteButton(TableColumn<Scorable,Integer> downVoteCol){
        downVoteCol.setCellFactory(param -> new TableCell<>() {

            private final Button downVoteFAQButton = new Button("Down vote");
            {
                downVoteFAQButton.setOnAction(event -> {
                    voteScorable(event, param.getTableView().getItems().get(getIndex()),-1);
                });
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(downVoteFAQButton);
                    Score score = EvaluationFacade.getInstance().findScorable(param.getTableView().getItems().get(getIndex()));
                    if( score != null){
                        if(score.getScoreValue() == -1){
                            downVoteFAQButton.setDisable(true);
                        }
                    }else{
                        downVoteFAQButton.setDisable(true);
                    }
                }
            }
        });
    }


    private void replyQuestion(ActionEvent event, Scorable question) {
        System.out.println("Reply");
    }

    /**
     * Method to populate table of comments
     * @param offerFacade
     */
    private void populateCommentTable(OfferFacade offerFacade) {
        List<Scorable> comments = offerFacade.getAllComments(offer);
        final ObservableList<Scorable> data = FXCollections.observableArrayList(comments);

        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment") );
        commentCol.setCellFactory(TextFieldTableCell.forTableColumn());

        upVoteButton(upVoteComment);
        downVoteButton(downVoteComment);

        commentTable.setItems(data);
    }

    private void voteScorable(ActionEvent event, Scorable scorable, int i) {
        EvaluationFacade.getInstance().vote(scorable,i);
    }

}
