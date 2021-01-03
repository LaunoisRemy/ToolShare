package gui.controller.offer;

import business.exceptions.MissingParametersException;
import business.facade.CategoryFacade;
import business.facade.OfferFacade;
import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.ToolSate;
import com.jfoenix.controls.*;
import gui.LoadView;
import gui.ViewPath;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import util.AlertBox;
import util.ConstantsRegex;
import util.MapRessourceBundle;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateOfferController implements Initializable {

    private final OfferFacade offerFacade = OfferFacade.getInstance();
    private final CategoryFacade categoryFacade = CategoryFacade.getInstance();
    private Offer offer = null;
    private int action = 0;

    @FXML
    private JFXButton cancel;

    @FXML
    private Label head;

    @FXML
    private JFXButton submit;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXComboBox<ToolSate> state;

    @FXML
    private JFXTextField price;

    @FXML
    private JFXComboBox<Category> category;

    @FXML
    private Label error_msg;

    @FXML
    private Label cast_msg;

    @FXML
    private JFXCheckBox isPriority;

    @FXML
    private Label dateStartPriorityLabel;

    @FXML
    private Label dateEndPriorityLabel;

    @FXML
    private Label priority_msg;

    @FXML
    private DatePicker dateStart;

    @FXML
    private DatePicker dateEnd;

    /**
     *
     * @param location
     * @param resources got two resources
     *                 - the Offer : Offer
     *                 - the Action : Int = 0 to register a new offer or 1 to update an offer
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        state.getItems().clear();
        state.getItems().addAll(ToolSate.values());

        category.getItems().clear();
        category.getItems().addAll(categoryFacade.getAllValidatedCategories());

        if(((MapRessourceBundle)resources).size()!=0){
            this.offer =(Offer)resources.getObject("0");
            this.action = (Integer) resources.getObject("1");
        }
        if(this.action == 0) {
            head.setText("Post an Offer");
            submit.setOnAction(this::handleNewOffer);
            submit.setText("Create");
            cancel.setOnAction(this::cancel);
        } else {
            head.setText("Update Offer");
            submit.setText("Update");
            submit.setOnAction(this::handleUpdateOffer);
            cancel.setOnAction(this::cancelUpdate);
        }
    }

    public void handleNewOffer(ActionEvent actionEvent) {
        error_msg.setVisible(false);
        cast_msg.setVisible(false);
        try {
            Offer offer;
            if(!ConstantsRegex.matchFloatRegex(price.getText())) throw new NumberFormatException();
            if(!isPriority.isSelected()) {
                offer = offerFacade.createOffer(
                        title.getText(),
                        Float.parseFloat(price.getText()),
                        description.getText(),
                        state.getValue(),
                        isPriority.isSelected(),
                        category.getValue().getCategoryName(),
                        null,
                        null
                );
            } else {
                if(Date.from(Instant.from(dateStart.getValue().atStartOfDay(ZoneId.systemDefault()))).compareTo(Date.from(Instant.from(dateEnd.getValue().atStartOfDay(ZoneId.systemDefault())))) > 0) throw new MissingParametersException("Wrong date format");
                offer = offerFacade.createOffer(
                        title.getText(),
                        Float.parseFloat(price.getText()),
                        description.getText(),
                        state.getValue(),
                        isPriority.isSelected(),
                        category.getValue().getCategoryName(),
                        Date.from(Instant.from(dateStart.getValue().atStartOfDay(ZoneId.systemDefault()))),
                        Date.from(Instant.from(dateEnd.getValue().atStartOfDay(ZoneId.systemDefault())))
                );
            }
            LoadView.changeScreen(actionEvent, ViewPath.OFFER_VIEW, offer);
        } catch (NumberFormatException e) {
            System.err.println(e.toString());
            cast_msg.setVisible(true);
        } catch (MissingParametersException e) {
            error_msg.setVisible(true);
            e.printStackTrace();
        }
    }

    public void handleUpdateOffer(ActionEvent actionEvent) {

    }

    public void handleNewCategory(ActionEvent actionEvent) {
        LoadView.changeScreen(actionEvent, ViewPath.POSTCATEGORY_VIEW);
    }

    public void handleIsPriority(ActionEvent actionEvent) {
        if(isPriority.isSelected()) {
            priority_msg.setVisible(true);
            dateStartPriorityLabel.setVisible(true);
            dateEndPriorityLabel.setVisible(true);
            dateStart.setVisible(true);
            dateEnd.setVisible(true);
        } else {
            priority_msg.setVisible(false);
            dateStartPriorityLabel.setVisible(false);
            dateEndPriorityLabel.setVisible(false);
            dateStart.setVisible(false);
            dateEnd.setVisible(false);
            dateStart.setValue(null);
            dateEnd.setValue(null);
        }
    }

    public void cancel(ActionEvent actionEvent) {
        LoadView.changeScreen(actionEvent, ViewPath.HOMEPAGE_VIEW);
    }

    public void cancelUpdate(ActionEvent actionEvent) {
        LoadView.changeScreen(actionEvent, ViewPath.OFFER_VIEW, offer);
    }

    public void handleAddPicture(ActionEvent actionEvent) {
        AlertBox.showAlert("Add Picture","This functionality is not implemented yet.\nWe are sorry!");
    }
}
