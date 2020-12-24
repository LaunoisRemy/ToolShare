package gui.controller.category;

import business.exceptions.ObjectNotFoundException;
import business.facade.CategoryFacade;
import business.system.Category;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
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
        ArrayList<Category> categoryArrayList = new ArrayList<Category>();

        categoryArrayList.addAll(this.categoryFacade.getAllCategories());

        final ObservableList<Category> data = FXCollections.observableArrayList(categoryArrayList);

        categoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName") );
        categoryName.setCellFactory(TextFieldTableCell.forTableColumn());

        deleteButton.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        updateButton.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        isValidated.setCellValueFactory(new PropertyValueFactory<>("isValidated"));

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

        isValidated.setCellFactory(column -> new CheckBoxTableCell());

        deleteButton.setCellFactory(param -> new TableCell<Category,Integer>() {
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
                        handleDeleteCategory(event, category,data);
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

    private void setValidated(ActionEvent event, Category category) {
    }

    public void handleDeleteCategory(ActionEvent actionEvent, Category category,ObservableList data){
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
        Category category = this.categoryFacade.createCategory(categoryName.getText(),false);
    }

    public void alertUpdate(ActionEvent actionEvent, Category category){

    }

    public void updateCategory(ActionEvent actionEvent, int idCategory){
        try {
            Category category = this.categoryFacade.updateCategory(idCategory,newCategoryName.getText(),false);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
