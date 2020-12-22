package gui.controller;

import business.exceptions.ObjectNotFoundException;
import business.facade.FavoryFacade;
import business.facade.OfferFacade;
import business.system.Favory;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    private FavoryController favoryController = new FavoryController();

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
        FavoryFacade favoryFacade = FavoryFacade.getInstance();
    //TODO Ici recupérer toutes les offres et non la 1ere
        ArrayList<Offer> offerArrayList=new ArrayList<>();
        Offer offer =null;
        try {
            offer = OfferFacade.getInstance().getOffer(1);
            offerArrayList.add(offer);

        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }

        final ObservableList<Offer> data = FXCollections.observableArrayList(offerArrayList);

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
                    System.out.println(item);
                    try {
                       offer= OfferFacade.getInstance().getOffer((int)item);
                    } catch (ObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println(offer);
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


    public void seeOfferPage(ActionEvent actionEvent,Offer offer){
        //System.out.println(offer);
        //LoadView.changeScreen(actionEvent, ViewPath.LOGIN_VIEW,offer);
    }
    public void addToFavory(ActionEvent actionEvent,int offerID,JFXButton button){
       favoryController.addToFavory(actionEvent,offerID,button);
    }
    public void deleteFromFavory(ActionEvent actionEvent, int offerID,JFXButton button){
        favoryController.deleteFromFavory(actionEvent,offerID,button);
    }
}
