package gui.controller;

import business.facade.SessionFacade;
import business.system.user.User;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;


public class NavBarUser {
    User user = SessionFacade.getInstance().getUser();

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

}
