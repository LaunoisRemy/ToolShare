package gui.controller;

import business.exceptions.BadInsertionInBDDException;
import business.exceptions.MissingParametersException;
import business.facade.CategoryFacade;
import business.facade.OfferFacade;
import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.ToolSate;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateOfferController implements Initializable {

    private final OfferFacade offerFacade = OfferFacade.getInstance();
    private final CategoryFacade categoryFacade = CategoryFacade.getInstance();

    @FXML
    private JFXButton createOffer;

    @FXML
    private JFXButton addNewCategory;

    @FXML
    private JFXButton cancel;

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
    private JFXDatePicker dateStart;

    @FXML
    private JFXDatePicker dateEnd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        state.getItems().clear();
        state.getItems().addAll(ToolSate.values());

        category.getItems().clear();
        category.getItems().addAll(categoryFacade.getAllCategories());
    }

    public void handleNewOffer(javafx.event.ActionEvent actionEvent) {
        try {
            Offer offer = offerFacade.createOffer(title.getText(), Float.parseFloat(price.getText()), description.getText(), state.getValue(), isPriority.isSelected(), category.getValue().getCategoryName(), Date.from(dateStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(dateEnd.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } catch (NumberFormatException e) {
            System.err.println(e.toString());
            cast_msg.setVisible(true);
        } catch (BadInsertionInBDDException e) {
            error_msg.setVisible(true);
            System.err.println(e.toString());
        } catch (MissingParametersException e) {
            e.printStackTrace();
        }
    }

    public void handleNewCategory(javafx.event.ActionEvent actionEvent) {
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
}
