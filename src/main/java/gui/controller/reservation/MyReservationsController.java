package gui.controller.reservation;

import business.facade.ReservationFacade;
import business.system.reservation.Reservation;
import gui.LoadView;
import gui.ViewPath;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyReservationsController implements Initializable {
    private final ReservationFacade reservationFacade = ReservationFacade.getInstance();

    @FXML
    private TableView<Reservation> table;
    @FXML
    private TableColumn<Reservation,String> title, dates;
    @FXML
    private TableColumn<Reservation,Integer> returnButton,offerButton;
    @FXML
    private TableColumn<Reservation,Float> price;


    /**
     * Initialize the view corresponding to what the user wanted
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Reservation> resArrayList = new ArrayList<>(this.reservationFacade.getReservationsByUserNotReturned());

        final ObservableList<Reservation> data = FXCollections.observableArrayList(resArrayList);

        title.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"offer","title"));
        title.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        price.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"offer","pricePerDay"));
        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        dates.setCellValueFactory(param -> {
            String start = param.getValue().getDateStartBooking().toString();
            String end = param.getValue().getDateEndBooking().toString();

            String res = "Start : " + start + "\n End : " + end;

            return new SimpleObjectProperty<>(res);
        });
        dates.setCellFactory(TextFieldTableCell.forTableColumn());

        offerButton.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"offer","offer_id"));
        offerButton.setCellFactory(param -> new TableCell<>() {

            private final Button seeOfferButton = new Button("See Offer");
            {
                seeOfferButton.setOnAction(event -> seeOfferPage(event, param.getTableView().getItems().get(getIndex())));
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

        returnButton.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"offer","offer_id"));
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

    private void seeOfferPage(ActionEvent event, Reservation reservation) {
        LoadView.changeScreen(event, ViewPath.OFFER_VIEW,reservation.getOffer());
    }

    private void alertReturnOffer(ActionEvent event, Reservation reservation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return the offer");
        alert.setHeaderText(null);
        int nbJour = this.reservationFacade.nbJoursForReturn(reservation);
        if(nbJour < 0){
            alert.setContentText("EN RETARD");
        } else if (nbJour == 0){
            alert.setContentText("A L'HEURE");
        } else {
            alert.setContentText("EN AVANCE");
        }

        Optional<ButtonType> result = alert.showAndWait();
    }
}
