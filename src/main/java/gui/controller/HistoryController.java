package gui.controller;

import business.facade.HistoryFacade;
import business.system.offer.Offer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    @FXML
    private TableView<Offer> table;
    @FXML
    private TableColumn<Offer,String> title,category,city;
    @FXML
    private TableColumn<Offer,Integer> offerButton,rateButton;
    @FXML
    private TableColumn<Offer,Float> price;


    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HistoryFacade historyFacade = HistoryFacade.getInstance();
        ArrayList<Offer> offers = new ArrayList<>();
        offers.addAll(historyFacade.getAllOffers());

        final ObservableList<Offer> data = FXCollections.observableArrayList(offers);

        title.setCellValueFactory(new PropertyValueFactory<>("title") );
        title.setCellFactory(TextFieldTableCell.forTableColumn());

        category.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"category","categoryName"));
        category.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        city.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"user","role","userCity"));
        city.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        price.setCellValueFactory(new PropertyValueFactory<>("pricePerDay") );
        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        offerButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        rateButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));


        offerButton.setCellFactory(param -> new TableCell<>() {

            private final Button seeOfferButton = new Button("See Offer");
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

    }

    public void seeOfferPage(Event event, Offer offer){

    }
}