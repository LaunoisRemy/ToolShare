package gui.controller;

import business.facade.SessionFacade;
import business.system.user.Admin;
import business.system.user.User;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class NavBarUser implements Initializable {
    final User user = SessionFacade.getInstance().getUser();

    @FXML
    private VBox search,history,favory,categories,userList,myOffers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(user.getRole().getNameRole().equals(Admin.ADMIN)){
            favory.setVisible(false);
            favory.setManaged(false);
            history.setVisible(false);
            history.setManaged(false);
            myOffers.setVisible(false);
            myOffers.setManaged(false);
        } else { //Ordinary User
            /*categories.setVisible(false);
            categories.setManaged(false);
            userList.setVisible(false);
            userList.setManaged(false);*/
        }

    }

    public void profilePage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent, ViewPath.REGISTERUPDATEUSER_VIEW,user,1);
    }
    public void historyPage(ActionEvent event){
        LoadView.changeScreen(event, ViewPath.HISTORY_VIEW);
    }
    public void favoryPage(ActionEvent event){
        LoadView.changeScreen(event,ViewPath.HOMEPAGE_VIEW,1);
    }
    public void homePage(ActionEvent event){
        LoadView.changeScreen(event,ViewPath.HOMEPAGE_VIEW);
    }
    public void postOfferPage(ActionEvent event){
        LoadView.changeScreen(event,ViewPath.POSTUPDATEOFFER_VIEW);
    }
    public void categoryPage(ActionEvent event){
        LoadView.changeScreen(event,ViewPath.CATEGORY_VIEW);
    }
    public void userListPage(ActionEvent event) {
        LoadView.changeScreen(event,ViewPath.USERLIST_VIEW);
    }
    public void myOffersPage(ActionEvent event){
        LoadView.changeScreen(event,ViewPath.MYOFFERS_VIEW);
    }
    public void myReservationsPage(ActionEvent event){
        LoadView.changeScreen(event,ViewPath.MYRESERVATIONS);
    }


}
