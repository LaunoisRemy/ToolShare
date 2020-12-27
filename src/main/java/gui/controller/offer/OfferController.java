package gui.controller.offer;

import business.facade.OfferFacade;
import business.facade.SessionFacade;
import business.system.offer.Offer;
import business.system.scorable.Comment;
import business.system.scorable.faq.Question;
import business.system.user.Admin;
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
import java.util.function.Consumer;

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
    private TableView<Question> faqTable;
    @FXML
    private TableColumn<Question,String> questionFAQ;
    @FXML
    private TableColumn<Question,Integer> replyCol,upVoteFAQ,downVoteFAQ;

    @FXML
    private TableView<Comment> commentTable;
    @FXML
    private TableColumn<Comment,String> commentCol;
    @FXML
    private TableColumn<Comment,Integer> upVoteComment,downVoteComment;


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
            if(isOwner){
                setPriority.setVisible(true);
                setPriority.setDisable(false);
            }
        }else{
            editButton.setDisable(true);
            editButton.setVisible(false);
            setPriority.setVisible(false);
            setPriority.setDisable(true);
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


//    private <A> TableCell createTableCell(String nameButton, TableColumn<A,Integer> param, Consumer<ActionEvent,A> function){
//        return new TableCell<>() {
//
//            private final Button downVoteFAQButton = new Button("Up vote");
//            {
//                downVoteFAQButton.setOnAction(event -> {
//                    function.accept(event,param.getTableView().getItems().get(getIndex()));
//                    downVoteComment(event, );
//                });
//            }
//            @Override
//            protected void updateItem(Integer item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (empty) {
//                    setGraphic(null);
//                }
//                else {
//                    setGraphic(downVoteFAQButton);
//                }
//            }
//        }
//
//    }

    /**
     * Method to populate table of faq
     * @param offerFacade
     */
    private void populateFAQTable(OfferFacade offerFacade,boolean isOwner) {
        List<Question> questions = offerFacade.getAllQuestions(offer);
        final ObservableList<Question> data = FXCollections.observableArrayList(questions);

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
        upVoteFAQ.setCellFactory(param -> new TableCell<>() {

            private final Button upVoteFAQButton = new Button("Up vote");
            {
                upVoteFAQButton.setOnAction(event -> {
                    upVoteQuestion(event, param.getTableView().getItems().get(getIndex()));
                });
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(upVoteFAQButton);
                }
            }
        });
        downVoteFAQ.setCellFactory(param -> new TableCell<>() {

            private final Button downVoteFAQButton = new Button("Up vote");
            {
                downVoteFAQButton.setOnAction(event -> {
                    downVoteQuestion(event, param.getTableView().getItems().get(getIndex()));
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
                }
            }
        });


        faqTable.setItems(data);
    }

    private void replyQuestion(ActionEvent event, Question question) {
        System.out.println("Reply");
    }

    /**
     * Method to populate table of comments
     * @param offerFacade
     */
    private void populateCommentTable(OfferFacade offerFacade) {
        List<Comment> questions = offerFacade.getAllComments(offer);
        final ObservableList<Comment> data = FXCollections.observableArrayList(questions);

        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment") );
        commentCol.setCellFactory(TextFieldTableCell.forTableColumn());

        upVoteComment.setCellFactory(param -> new TableCell<>() {

            private final Button upVoteFAQButton = new Button("Up vote");
            {
                upVoteFAQButton.setOnAction(event -> {
                    upVoteComment(event, param.getTableView().getItems().get(getIndex()));
                });
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(upVoteFAQButton);
                }
            }
        });
        downVoteComment.setCellFactory(param -> new TableCell<>() {

            private final Button downVoteFAQButton = new Button("Up vote");
            {
                downVoteFAQButton.setOnAction(event -> {
                    downVoteComment(event, param.getTableView().getItems().get(getIndex()));
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
                }
            }
        });

        commentTable.setItems(data);
    }

    private void upVoteComment(ActionEvent event, Comment comment) {
        System.out.println(comment.getCommentScore()-1);
    }
    private void downVoteComment(ActionEvent event, Comment comment) {
        System.out.println(comment.getCommentScore()+1);
    }
    private void upVoteQuestion(ActionEvent event, Question question) {
        System.out.println(question.getQuestionScore()-1);
    }
    private void downVoteQuestion(ActionEvent event, Question question) {
        System.out.println(question.getQuestionScore()+1);
    }
}
