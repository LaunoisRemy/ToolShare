package gui.controller.offer;

import business.exceptions.ObjectNotFoundException;
import business.facade.OfferFacade;
import business.system.offer.Offer;
import business.system.offer.PriorityOffer;
import com.jfoenix.controls.JFXButton;
import gui.LoadView;
import gui.ViewPath;
import gui.controller.HomePageController;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import util.AlertBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class MyOffersController implements Initializable {
    private final OfferFacade offerFacade = OfferFacade.getInstance();

    @FXML
    private TableView<Offer> table;
    @FXML
    private TableColumn<Offer,String> title,category;
    @FXML
    private TableColumn<Offer,Integer> deleteButton,updateButton,offerButton;
    @FXML
    private TableColumn<Offer,Boolean> priority;
    @FXML
    private TableColumn<Offer,Float> price;


    /**
     * Initialize the view corresponding to what the user wanted
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Offer> offerArrayList = new ArrayList<>(this.offerFacade.getOffersFromUser());

        final ObservableList<Offer> data = FXCollections.observableArrayList(offerArrayList);

        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        title.setCellFactory(TextFieldTableCell.forTableColumn());

        category.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"category","categoryName"));
        category.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        price.setCellValueFactory(new PropertyValueFactory<>("pricePerDay") );
        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        deleteButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        updateButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        offerButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        priority.setCellValueFactory(new PropertyValueFactory<>("isPriority"));

        priority.setCellFactory(param -> new TableCell<>() {

            private final Button priorityButton = new Button("Put it in priority");
            {
                priorityButton.setOnAction(event ->
                    alertPriority(event, param.getTableView().getItems().get(getIndex()))
                );
            }
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    if(item){
                        priorityButton.setDisable(true);
                    }
                    setGraphic(priorityButton);
                }
            }
        });

        HomePageController.buttonSeeOffer(offerButton);


        updateButton.setCellFactory(param -> new TableCell<>() {

            private final Button updateButton = new Button("Update");
            {
                updateButton.setOnAction(event ->
                        handleUpdateOffer(event, param.getTableView().getItems().get(getIndex()))
                );
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
                    Offer offer = getTableView().getItems().get(getIndex());
                    Image img= new Image("img/red_trash.png") ;
                    ImageView iv= new ImageView(img);
                    deleteButton.setOnAction(event -> alertDelete(event, offer,data));
                    iv.setFitHeight(25);
                    iv.setFitWidth(25);
                    deleteButton.setGraphic(iv);

                    setGraphic(deleteButton);
                }
            }
        });
        table.setItems(data);

    }

    public void handleUpdateOffer(ActionEvent event, Offer offer) {
        LoadView.changeScreen(event, ViewPath.POSTUPDATEOFFER_VIEW, offer, 1);
    }

    public  Dialog<Pair<Date, Date>> dialogPriority(){
        Dialog<Pair<Date, Date>> dialog = new Dialog<>();
        dialog.setTitle("Priority");
        dialog.setHeaderText(null);

        // Set the button types.
        ButtonType okButton = ButtonType.OK;
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker startDate = new DatePicker(LocalDate.now());
        DatePicker endDate = new DatePicker(LocalDate.now().plus(1, ChronoUnit.DAYS));

        grid.add(new Label("Start date:"), 0, 0);
        grid.add(startDate, 1, 0);
        grid.add(new Label("End date:"), 0, 1);
        grid.add(endDate, 1, 1);

        Label error = new Label("Wrong dates : if you click on OK, nothing will be done");
        error.setTextFill(Color.RED);
        grid.add(error,1,2);
        error.setVisible(false);

        this.listenerDatePicker(dialog, grid, startDate, error, startDate.getValue(), endDate.getValue());
        this.listenerDatePicker(dialog, grid, endDate, error, startDate.getValue(), endDate.getValue());

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a date-date-pair when the ok button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                boolean res = this.checkDates(startDate.getValue(), endDate.getValue());
                if(res){
                    Date start = Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date end = Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    return new Pair<>(start, end);
                }
            }
            return null;
        });
        return dialog;
    }


    private void alertPriority(ActionEvent event, Offer offer) {
        Dialog<Pair<Date, Date>> dialog = dialogPriority();

        Optional<Pair<Date, Date>> result = dialog.showAndWait();

        result.ifPresent(dates -> {
            handleSetPriority(offer,dates.getKey(),dates.getValue());
            //refresh page to add change the priority
            LoadView.changeScreen(event, ViewPath.MYOFFERS_VIEW);
        });
    }

    public void listenerDatePicker(Dialog<Pair<Date, Date>> dialog, GridPane grid, DatePicker value, Label error,LocalDate start, LocalDate end) {
        value.setOnAction((EventHandler<ActionEvent>) t -> {
            boolean res = checkDates(start, end);
            error.setVisible(!res);
            dialog.getDialogPane().setContent(grid);
        });
    }

    public Offer handleSetPriority(Offer offer, Date start, Date end) {
        PriorityOffer priorityOffer = new PriorityOffer(offer.getOffer_id(),offer.getTitle(),offer.getPricePerDay(),offer.getDescription(),offer.getToolSate(),true,offer.getUser(),offer.getCategory(),start,end);
        return this.offerFacade.updateOfferFromObj(priorityOffer);
    }

    public boolean checkDates(LocalDate start, LocalDate end) {
        return start != null && end != null && !start.isBefore(LocalDate.now()) && !end.isBefore(start);
    }

    /**
     * Display the view of the offer when clicked on "see Offer"
     * @param actionEvent
     * @param offer
     */
    private void seeOfferPage(ActionEvent actionEvent,Offer offer){
        LoadView.changeScreen(actionEvent, ViewPath.OFFER_VIEW,offer);
    }

    private void alertDelete(ActionEvent event, Offer offer, ObservableList<Offer> data) {
        Alert alert = AlertBox.showAlertConfirmationDeletion();
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            handleDeleteOffer(offer,data);
        }
    }

    private void handleDeleteOffer(Offer offer, ObservableList<Offer> data) {
        boolean deleted = false;
        try {
            deleted = this.offerFacade.deleteOffer(offer.getOffer_id());
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }

        if(deleted){
            data.removeAll(offer);
        }
    }

}

