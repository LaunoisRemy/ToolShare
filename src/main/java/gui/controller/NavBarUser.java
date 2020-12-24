package gui.controller;

import business.facade.SessionFacade;
import business.system.user.OrdinaryUser;
import business.system.user.Role;
import business.system.user.User;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class NavBarUser implements Initializable {
    User user = SessionFacade.getInstance().getUser();

    @FXML
    private VBox search,history,favory,categories;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(user.getRole().getNameRole().equals(OrdinaryUser.ORDINARY_USER)){
            //categories.setVisible(false);
            //categories.setManaged(false);
        } else { //Admin
            favory.setVisible(false);
            favory.setManaged(false);
            history.setVisible(false);
            history.setManaged(false);
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
        LoadView.changeScreen(event,ViewPath.POSTOFFER_VIEW);
    }
    public void categoryPage(ActionEvent event){
        LoadView.changeScreen(event,ViewPath.CATEGORY_VIEW);
    }


}
