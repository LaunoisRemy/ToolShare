package gui.controller.reservation;

import business.exceptions.MissingParametersException;
import business.facade.ReservationFacade;
import business.system.offer.Offer;
import business.system.reservation.Reservation;
import com.jfoenix.controls.JFXButton;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Callback;
import util.MapRessourceBundle;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CreateUpdateReservationController implements Initializable {

    private final ReservationFacade reservationFacade = ReservationFacade.getInstance();
    private Offer offer;

    @FXML
    private JFXButton cancel, submit;

    @FXML
    private Label error_msg;

    @FXML
    private DatePicker dateStart, dateEnd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(((MapRessourceBundle)resources).size()!=0) {
            this.offer = (Offer) resources.getObject("0");
        }

        List<Reservation> reservations = new ArrayList<>(this.reservationFacade.getReservationsByOffer(this.offer.getOffer_id()));
        for (Reservation res : reservations) System.out.println(res.getReservationId());
        Callback<DatePicker, DateCell> dayCellFactory = this.getDayCellFactory(reservations);
        dateStart.setDayCellFactory(dayCellFactory);
    }

    // Factory to create Cell of DatePicker
    private Callback<DatePicker, DateCell> getDayCellFactory(List<Reservation> reservations) {

        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate now = LocalDate.now();

        return new Callback<>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (date.compareTo(now) < 0) {
                            this.setDisable(true);
                            this.setStyle("-fx-background-color: #ffc0cb;");
                        } else {
                            for (Reservation reservation : reservations) {
                                LocalDate dateS = LocalDate.parse(dtf.format(reservation.getDateStartBooking()));
                                LocalDate dateE = LocalDate.parse(dtf.format(reservation.getDateEndBooking()));
                                if (!(date.isBefore(dateS) || date.isAfter(dateE))) {
                                    this.setDisable(true);
                                    this.setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        }
                    }
                };
            }
        };
    }

    public void cancel(ActionEvent actionEvent) {
        LoadView.changeScreen(actionEvent, ViewPath.OFFER_VIEW, this.offer);
    }

    public void handleNewReservation(ActionEvent actionEvent) {
        error_msg.setVisible(false);
        try {
            if(dateStart.getValue() == null || dateEnd.getValue() == null) throw new MissingParametersException("No date have been entered");
            Reservation reservation = this.reservationFacade.createReservation(
                    Date.from(Instant.from(this.dateStart.getValue().atStartOfDay(ZoneId.systemDefault()))),
                    Date.from(Instant.from(this.dateEnd.getValue().atStartOfDay(ZoneId.systemDefault()))),
                    this.offer.getOffer_id()
            );
        } catch (MissingParametersException e) {
            error_msg.setVisible(true);
        }
        LoadView.changeScreen(actionEvent, ViewPath.MYRESERVATIONS_VIEW);
    }

}
