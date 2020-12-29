package gui.controller;

import business.facade.EvaluationFacade;
import business.facade.HistoryFacade;
import business.facade.SessionFacade;
import business.system.ScoreOffer;
import business.system.offer.Offer;
import gui.LoadView;
import gui.ViewPath;
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
import java.util.List;
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
     * Initialize the view by getting all the offers the user has reserved
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HistoryFacade historyFacade = HistoryFacade.getInstance();
        List<Offer> offers = new ArrayList<>(historyFacade.getAllOffers());

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
        rateButton.setCellFactory(param -> new TableCell<>() {

            private final Button seeOfferButton = new Button("Rate");
            {
                //Offer offer= param.getTableView().getItems().get(getIndex());
                seeOfferButton.setOnAction(event -> {
                    rateOffer(event,param.getTableView().getItems().get(getIndex()) );
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
                    Offer offer= param.getTableView().getItems().get(getIndex());
                    EvaluationFacade evaluationFacade = EvaluationFacade.getInstance();
                    ScoreOffer scoreOffer = evaluationFacade.findRate(offer.getOffer_id(), SessionFacade.getInstance().getUser().getUser_id());
                    if(scoreOffer != null){
                        if(scoreOffer.getComment() == null){
                            seeOfferButton.setOnAction(event -> {
                                commentOffer(event,scoreOffer);
                            });
                            seeOfferButton.setText("Comment");
                        }else{
                            this.setDisable(true);
                        }
                    }
                }
            }

        });
        table.setItems(data);

    }

    /**
     * Display the view of the offer
     * @param event
     * @param offer
     */
    public void seeOfferPage(Event event, Offer offer){
        LoadView.changeScreen(event, ViewPath.OFFER_VIEW,offer);

    }

    /**
     * Display a view to rate the selected offer
     * @param event
     * @param offer
     */
    public void rateOffer(Event event, Offer offer){
        LoadView.changeScreen(event, ViewPath.RATE_VIEW,offer);

    }
    /**
     * Display a view to rate the selected offer
     * @param event
     * @param scoreOffer
     */
    public void commentOffer(Event event, ScoreOffer scoreOffer){
        LoadView.changeScreen(event, ViewPath.COMMENT_VIEW,scoreOffer);

    }
}
