package gui.controller;

import business.exceptions.ObjectNotFoundException;
import business.facade.FavoryFacade;
import business.facade.OfferFacade;
import business.system.Favory;
import business.system.offer.Offer;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FavoryController {
    public FavoryController() {}

    public void addToFavory(ActionEvent actionEvent, int offerID, JFXButton button){
        FavoryFacade favoryFacade = FavoryFacade.getInstance();
        Offer offer = null;
        try {
            offer = OfferFacade.getInstance().getOffer(offerID);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
        Favory favAdded = favoryFacade.addFavory(offer);
        if(favAdded!=null){
            Image img= new Image("img/favoryFilled.png") ;
            ImageView iv= new ImageView(img);
            button.setOnAction(event -> deleteFromFavory(event, offerID,button));
            iv.setFitHeight(35);
            iv.setFitWidth(35);
            button.setGraphic(iv);
        }
    }

    
    public void deleteFromFavory(ActionEvent actionEvent, int offerID,JFXButton button){
        FavoryFacade favoryFacade = FavoryFacade.getInstance();
        Offer offer = null;
        try {
            offer = OfferFacade.getInstance().getOffer(offerID);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
        boolean deleted = favoryFacade.deleteFavory(offer);
        if(deleted){
            Image img= new Image("img/favory.png") ;
            ImageView iv= new ImageView(img);
            button.setOnAction(event -> addToFavory(event, offerID,button));
            iv.setFitHeight(35);
            iv.setFitWidth(35);
            button.setGraphic(iv);
        }
    }

    public void findAllFavories(ActionEvent actionEvent){
        FavoryFacade favoryFacade = FavoryFacade.getInstance();

    }
}
