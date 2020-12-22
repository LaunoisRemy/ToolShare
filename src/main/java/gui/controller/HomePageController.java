package gui.controller;

import business.system.offer.Offer;
import business.system.offer.ToolSate;
import com.jfoenix.controls.JFXButton;
import gui.LoadView;
import gui.ViewPath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private TableView<Offer> table;
    @FXML
    private TableColumn<Offer,String> title;
    @FXML
    private TableColumn<Offer,Integer> category,offerButton,favoryButton;
    @FXML
    private TableColumn<Offer,Float> price;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ObservableList<Offer> data = FXCollections.observableArrayList(
              new Offer(1,"Test",15,"ReTest Tool",ToolSate.GOOD,false,3,3),
              new Offer(2,"ReTest",25,"Test Tool",ToolSate.GOOD,false,3,3),
              new Offer(3,"RERETest",35,"RERETest Tool",ToolSate.GOOD,false,3,3),
              new Offer(4,"RERERERETest",45,"RERERERETest Tool",ToolSate.GOOD,false,3,3),
              new Offer(5,"RERERERERERETest",55,"RERERERERERETest Tool",ToolSate.GOOD,false,3,3)
        );

        title.setCellValueFactory(new PropertyValueFactory<>("title") );
        title.setCellFactory(TextFieldTableCell.forTableColumn());

        category.setCellValueFactory(new PropertyValueFactory<>("category_id") );
        category.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        price.setCellValueFactory(new PropertyValueFactory<>("pricePerDay") );
        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        offerButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        favoryButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));


        offerButton.setCellFactory(param -> new TableCell<>() {

            private final JFXButton seeOfferButton = new JFXButton("See Offer");
            {
                seeOfferButton.setOnAction(event -> {
                    seeOfferPage(event, param.getTableView().getItems().get(getIndex()));
                });
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {

                    setGraphic(seeOfferButton);

                }
            }
        });

        favoryButton.setCellFactory(param -> new TableCell<>() {
            Image img = new Image("img/favory.png") ;
            ImageView iv = new ImageView(img);

            private final JFXButton favoryButton = new JFXButton();
            {
                favoryButton.setOnAction(event -> {
                    addToFavory(event, param.getTableView().getItems().get(getIndex()));
                });
                iv.setFitHeight(35);
                iv.setFitWidth(35);
                favoryButton.setGraphic(iv);
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(favoryButton);
                }
            }
        });
        table.setItems(data);
    }
    public void seeOfferPage(ActionEvent actionEvent,Offer offer){
        System.out.println(offer);
        //LoadView.changeScreen(actionEvent, ViewPath.LOGIN_VIEW,offer);
    }
    public void addToFavory(ActionEvent actionEvent,Offer offer){
        System.out.println(offer);
    }
}
