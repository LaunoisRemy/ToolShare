package gui.controller.offer;

import business.facade.CategoryFacade;
import business.system.Category;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.AlertBox;

public class CreateCategoryController {

    private final CategoryFacade categoryFacade = CategoryFacade.getInstance();

    @FXML
    public JFXTextField categoryName;

    @FXML
    public Label error_msg;

    public void cancel(ActionEvent actionEvent) {
        LoadView.changeScreen(actionEvent, ViewPath.POSTOFFER_VIEW);
    }

    public void handleNewCategory(ActionEvent actionEvent) {
        Category category = categoryFacade.createCategory(categoryName.getText(), false);
        AlertBox.showAlert("Add Category","Thanks for your submission!\nAn admin will add your category soon.");
        LoadView.changeScreen(actionEvent, ViewPath.POSTOFFER_VIEW);
    }
}
