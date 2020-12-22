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
              new Offer(1,"Test",15,"ReTest Tool",ToolSate.Ok,false,3,3),
              new Offer(2,"ReTest",25,"Test Tool",ToolSate.Ok,false,3,3),
              new Offer(3,"RERETest",35,"RERETest Tool",ToolSate.Ok,false,3,3),
              new Offer(4,"RERERERETest",45,"RERERERETest Tool",ToolSate.Ok,false,3,3),
              new Offer(5,"RERERERERERETest",55,"RERERERERERETest Tool",ToolSate.Ok,false,3,3)
        );

        title.setCellValueFactory(new PropertyValueFactory<>("title") );
        category.setCellValueFactory(new PropertyValueFactory<>("category_id") );
        price.setCellValueFactory(new PropertyValueFactory<>("pricePerDay") );
        offerButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        favoryButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));

        offerButton.setCellFactory(param -> new TableCell<>() {

            private final JFXButton seeOfferButton = new JFXButton();
            {
                seeOfferButton.setOnAction(event -> {
                    seeOfferPage(event, param.getTableView().getItems().get(getIndex()));
                });
            }
        });

        favoryButton.setCellFactory(param -> new TableCell<>() {

            private final JFXButton favoryButton = new JFXButton();
            {
                favoryButton.setOnAction(event -> {
                    addToFavory(event, param.getTableView().getItems().get(getIndex()));
                });
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
