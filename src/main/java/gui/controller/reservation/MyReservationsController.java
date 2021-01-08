package gui.controller.reservation;

import business.facade.ReservationFacade;
import business.system.offer.ToolSate;
import business.system.reservation.Reservation;
import business.system.reservation.ReturnOffer;
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
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class MyReservationsController implements Initializable {
    private final ReservationFacade reservationFacade = ReservationFacade.getInstance();

    @FXML
    private TableView<Reservation> table;
    @FXML
    private TableColumn<Reservation,String> title, dates, days;
    @FXML
    private TableColumn<Reservation,Integer> offerButton,returnButton;
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

        days.setCellValueFactory(param -> {
            int nbDays = this.reservationFacade.nbDaysOfReservation(param.getValue())+1;
            String res = "";
            if(nbDays == 1){
                res = nbDays + " day";
            } else {
                res =  nbDays + " days";
            }

            return new SimpleObjectProperty<>(res);
        });
        days.setCellFactory(TextFieldTableCell.forTableColumn());

        price.setCellValueFactory(param -> {
            Float res = param.getValue().getOffer().getPricePerDay()*(this.reservationFacade.nbDaysOfReservation(param.getValue())+1);

            return new SimpleObjectProperty<>(res);
        });
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

        returnButton.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        returnButton.setCellFactory(param -> new TableCell<>() {

            private final Button returnButton = new Button("Return the offer");
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
        List<String> choices = new ArrayList<>();
        choices.add("EXCELLENT");
        choices.add("GOOD");
        choices.add("USED");
        choices.add("BAD");
        choices.add("DAMAGED");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(reservation.getOffer().getToolSate().getString().toUpperCase(), choices);
        dialog.setTitle("Return the offer");

        String s2 = "Don't forget to confirm this return by clicking 'OK'";
        int nbDay = this.reservationFacade.nbDaysToReturn(reservation);
        String s = null;
        if(nbDay < 0){
            s = "You return the offer late : " + (nbDay*-1) + " days.\n"+"You will have to pay 1 euro/per day late.\n";
        } else if (nbDay == 0){
            s="You return the offer at time.\n Don't forget to confirm this return by clicking 'OK'";
        } else {
            s="You have "+ nbDay +" days left to return the offer. \n Don't forget to confirm this return by clicking 'OK'";
        }

        dialog.setHeaderText(s2);
        dialog.setContentText(s+"\nTool state :");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(str -> {
            handleSetReturn(event,reservation,str);
        });
    }

    private void handleSetReturn(ActionEvent event, Reservation reservation, String s) {
        Date returnDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        ReturnOffer ro = new ReturnOffer(ToolSate.valueOf(s),returnDate,reservation);
        reservation.setReturnOffer(ro);
        this.reservationFacade.updateReservationFromObj(reservation,ro);
        LoadView.changeScreen(event, ViewPath.MYRESERVATIONS_VIEW);
    }
}
