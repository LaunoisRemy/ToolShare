package gui.controller.reservation;

import business.facade.OfferFacade;
import business.facade.ReservationFacade;
import business.system.offer.Offer;
import business.system.reservation.Reservation;
import gui.controller.HomePageController;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.util.converter.FloatStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyReservationsController implements Initializable {
    private final ReservationFacade reservationFacade = ReservationFacade.getInstance();

    @FXML
    private TableView<Reservation> table;
    @FXML
    private TableColumn<Reservation,String> title, dates;
    @FXML
    private TableColumn<Reservation,Integer> returnButton;
    @FXML
    private TableColumn<Offer,Integer> offerButton;
    @FXML
    private TableColumn<Reservation,Float> price;


    /**
     * Initialize the view corresponding to what the user wanted
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Reservation> resArrayList = new ArrayList<>(this.reservationFacade.getReservationsByUser());

        final ObservableList<Reservation> data = FXCollections.observableArrayList(resArrayList);

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        title.setCellFactory(TextFieldTableCell.forTableColumn());

        price.setCellValueFactory(new PropertyValueFactory<>("pricePerDay") );
        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        dates.setCellValueFactory(param -> {
            String start = param.getValue().getDateStartBooking().toString();
            String end = param.getValue().getDateEndBooking().toString();

            String res = "Start : " + start + "\n End : " + end;

            return new SimpleObjectProperty<>(res);
        });
        dates.setCellFactory(TextFieldTableCell.forTableColumn());

        offerButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        HomePageController.buttonSeeOffer(offerButton);

        returnButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        returnButton.setCellFactory(param -> new TableCell<>() {

            private final Button returnButton = new Button("Return Offer");
            {
                returnButton.setOnAction(event ->
                        alertReturnOffer(event, param.getTableView().getItems().get(getIndex()))
                );
            }
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    setGraphic(returnButton);
                }
            }
        });

        table.setItems(data);

    }

    private void alertReturnOffer(ActionEvent event, Reservation reservation) {

    }
}
