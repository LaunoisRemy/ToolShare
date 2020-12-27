package gui.controller;

import business.exceptions.ObjectNotFoundException;
import business.facade.FavoryFacade;
import business.facade.OfferFacade;
import business.system.Favory;
import business.system.offer.Offer;
import com.jfoenix.controls.JFXButton;
import gui.ViewPath;
import javafx.beans.binding.Bindings;
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
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import util.ConstantsRegex;
import util.MapRessourceBundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class HomePageController implements Initializable {
    private FavoryController favoryController = new FavoryController();

    @FXML
    private TableView<Offer> table;
    @FXML
    private TableColumn<Offer,String> title,category,city;
    @FXML
    private TableColumn<Offer,Integer> offerButton,favoryButton;
    @FXML
    private TableColumn<Offer,Float> price;


    /**
     * Initialize the view corresponding to what the user wanted,
     * it can be the home page or only the favorties offers of the user
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FavoryFacade favoryFacade = FavoryFacade.getInstance();
        ArrayList<Offer> offerArrayList=new ArrayList<>();
        //Check if we are on the homepage or the favory page
        if(((MapRessourceBundle)resources).size() != 0){
                offerArrayList.addAll(favoryFacade.getAllFavories());
        }else{
            offerArrayList.addAll(OfferFacade.getInstance().getAllOffers());
        }


        final ObservableList<Offer> data = FXCollections.observableArrayList(offerArrayList);

        title.setCellValueFactory(new PropertyValueFactory<>("title") );
        title.setCellFactory(TextFieldTableCell.forTableColumn());

        category.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"category","categoryName"));
        category.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        city.setCellValueFactory(cellData -> Bindings.select(cellData.getValue(),"user","role","userCity"));
        city.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));

        price.setCellValueFactory(new PropertyValueFactory<>("pricePerDay") );
        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        offerButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));
        favoryButton.setCellValueFactory(new PropertyValueFactory<>("offer_id"));


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

        favoryButton.setCellFactory(param -> new TableCell<>() {
            Image img;
            ImageView iv;

            private final JFXButton favoryButton = new JFXButton();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
                else {
                    Offer offer=null;
                    try {
                       offer= OfferFacade.getInstance().getOffer(item);
                    } catch (ObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    Favory fav = favoryFacade.findFavory(offer);

                    if(fav==null){
                        Image img= new Image("img/favory.png") ;
                        ImageView iv= new ImageView(img);
                        favoryButton.setOnAction(event -> {
                            addToFavory(event, item,favoryButton);
                        });
                        iv.setFitHeight(35);
                        iv.setFitWidth(35);
                        favoryButton.setGraphic(iv);
                    }else{
                        Image img= new Image("img/favoryFilled.png") ;
                        ImageView iv= new ImageView(img);
                        favoryButton.setOnAction(event -> {
                            deleteFromFavory(event, item,favoryButton);
                        });
                        iv.setFitHeight(35);
                        iv.setFitWidth(35);
                        favoryButton.setGraphic(iv);
                    }

                    setGraphic(favoryButton);
                }
            }
        });
        table.setItems(data);
    }

    /**
     * Display the view of the offer when clicked on "see Offer"
     * @param actionEvent
     * @param offer
     */
    public void seeOfferPage(ActionEvent actionEvent,Offer offer){

        //LoadView.changeScreen(actionEvent, ViewPath.LOGIN_VIEW,offer);
    }

    /**
     * Add the Offer to the favory list
     * @param actionEvent
     * @param offerID
     * @param button
     */
    public void addToFavory(ActionEvent actionEvent,int offerID,JFXButton button){
       favoryController.addToFavory(actionEvent,offerID,button);
    }

    /**
     * Delete the offer from the favory list
     * @param actionEvent
     * @param offerID
     * @param button
     */
    public void deleteFromFavory(ActionEvent actionEvent, int offerID,JFXButton button){
        favoryController.deleteFromFavory(actionEvent,offerID,button);
    }
}
