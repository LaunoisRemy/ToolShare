package gui.controller;

import business.facade.OfferFacade;
import business.system.Category;
import business.system.offer.Offer;
import business.system.offer.ToolSate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CreateOfferController {

    private OfferFacade facade = new OfferFacade();

    @FXML
    public JFXButton createOffer;

    @FXML
    public JFXButton addNewCategory;

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

    @FXML JFXComboBox<Category> category;

    public void handleNewOffer(javafx.event.ActionEvent actionEvent) {
        try {
            Offer offer = facade.createOffer(title.getText(), Integer.parseInt(price.getText()), description.getText(), state.getValue(), false, Integer.parseInt(category.getId()));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void handleNewCategory(javafx.event.ActionEvent actionEvent) {
    }

    public void handleNewPriorityOffer(ActionEvent actionEvent) {
    }
}
