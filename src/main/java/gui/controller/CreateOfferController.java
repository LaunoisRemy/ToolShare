package gui.controller;

import business.exceptions.BadInsertionInBDDException;
import business.facade.OfferFacade;
import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.ToolSate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreateOfferController {

    private OfferFacade facade = new OfferFacade();

    @FXML
    private JFXButton createOffer;

    @FXML
    private JFXButton addNewCategory;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton createPriorityOffer;

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

    public void handleNewOffer(javafx.event.ActionEvent actionEvent) {
        try {
            Offer offer = facade.createOffer(title.getText(), Integer.parseInt(price.getText()), description.getText(), state.getValue(), false, Integer.parseInt(category.getId()));
        } catch (NumberFormatException e) {
            System.err.println(e.toString());
            cast_msg.setVisible(true);
        } catch (BadInsertionInBDDException e) {
            error_msg.setVisible(true);
            System.err.println(e.toString());
        }
    }

    public void handleNewCategory(javafx.event.ActionEvent actionEvent) {
    }

    public void handleNewPriorityOffer(ActionEvent actionEvent) {
    }
}
