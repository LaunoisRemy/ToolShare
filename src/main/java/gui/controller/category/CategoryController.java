package gui.controller.category;

import business.exceptions.ObjectNotFoundException;
import business.facade.CategoryFacade;
import business.system.Category;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gui.LoadView;
import gui.ViewPath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    private CategoryFacade categoryFacade = CategoryFacade.getInstance();

    @FXML
    private TableView<Category> table;
    @FXML
    private TableColumn<Category,String> categoryName;
    @FXML
    private TableColumn<Category,Integer> deleteButton,updateButton;
    @FXML
    private TableColumn<Category,Boolean> isValidated;
    @FXML
    private JFXTextField newCategoryName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Category> categoryArrayList = new ArrayList<>();

        categoryArrayList.addAll(this.categoryFacade.getAllCategories());

        final ObservableList<Category> data = FXCollections.observableArrayList(categoryArrayList);

        categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName") );
        categoryName.setCellFactory(TextFieldTableCell.forTableColumn());

        deleteButton.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        updateButton.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

        isValidated.setCellValueFactory(new PropertyValueFactory<>("isValidated"));
        isValidated.setCellFactory(param -> new TableCell<>() {

            private final CheckBox checkBox = new CheckBox();
            {
                checkBox.setOnAction(event -> {
                    Category c = param.getTableView().getItems().get(getIndex());
                    if(checkBox.isSelected()){
                        c.setIsValidated(true);
                    } else {
                        c.setIsValidated(false);
                    }
                    handleSetValidated(event, c);
                });
            }
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(checkBox);
                    checkBox.setSelected(item);
                }
            }
        });

        updateButton.setCellFactory(param -> new TableCell<>() {

            private final Button updateButton = new Button("Update");
            {
                updateButton.setOnAction(event -> {
                    alertUpdate(event, param.getTableView().getItems().get(getIndex()));
                });
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(updateButton);
                }
            }
        });

        deleteButton.setCellFactory(param -> new TableCell<>() {
            Image img;
            ImageView iv;

            private final JFXButton deleteButton = new JFXButton();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
                else {
                    Category category= getTableView().getItems().get(getIndex());
                    Image img= new Image("img/red_trash.png") ;
                    ImageView iv= new ImageView(img);
                    deleteButton.setOnAction(event -> {
                        alertDelete(event, category,data);
                    });
                    iv.setFitHeight(25);
                    iv.setFitWidth(25);
                    deleteButton.setGraphic(iv);

                    setGraphic(deleteButton);
                }
            }
        });
        table.setItems(data);

    }

    private void handleSetValidated(ActionEvent event, Category category) {
        try {
            Category c = this.categoryFacade.updateCategory(category.getCategoryId(),category.getCategoryName(),category.getIsValidated());
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteCategory(Category category,ObservableList data){
        boolean deleted = false;
        try {
            deleted = this.categoryFacade.deleteCategory(category);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }

        if(deleted){
            data.removeAll(category);
        }
    }

    public void handleNewCategory(ActionEvent actionEvent){
        if(!newCategoryName.getText().isEmpty()){
            this.categoryFacade.createCategory(newCategoryName.getText(),false);
            //refresh page to add the new category
            LoadView.changeScreen(actionEvent, ViewPath.CATEGORY_VIEW);
        }
    }

    private void alertUpdate(ActionEvent actionEvent, Category category){
        TextInputDialog dialog = new TextInputDialog(category.getCategoryName());
        dialog.setTitle("Update Category");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter the new category name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> category.setCategoryName(name));
        updateCategory(category);

        //refresh page to update the category
        LoadView.changeScreen(actionEvent, ViewPath.CATEGORY_VIEW);
    }

    private void alertDelete(ActionEvent actionEvent, Category category,ObservableList data){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete this category ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            handleDeleteCategory(category,data);
        }
    }

    private void updateCategory(Category category){
        try {
            this.categoryFacade.updateCategory(category.getCategoryId(),category.getCategoryName(),category.getIsValidated());
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
