package gui.controller.offer;

import business.facade.OfferFacade;
import business.system.offer.Offer;
import business.system.scorable.Comment;
import business.system.scorable.faq.Question;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        if(((MapRessourceBundle)resources).size()!=0){
            this.offer=(Offer) resources.getObject("0");
        }
        titleOffer.setText(offer.getTitle());
        desc.setText(offer.getDescription());
        category.setText(offer.getCategory().getCategoryName());
        state.setText(offer.getToolSate().getString());
        price.setText(String.valueOf(offer.getPricePerDay()));
        desc.setEditable(false);

        List<Question> questions = offerFacade.getAllQuestions(offer);
        final ObservableList<Question> data = FXCollections.observableArrayList(questions);

        questionFAQ.setCellValueFactory(new PropertyValueFactory<>("questionContent") );
        questionFAQ.setCellFactory(TextFieldTableCell.forTableColumn());

        faqTable.setItems(data);
    }
}
