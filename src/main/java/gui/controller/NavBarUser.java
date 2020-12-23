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
    public void historyPage(Event event){
        LoadView.changeScreen(event, ViewPath.LOGIN_VIEW);
    }
    public void favoryPage(Event event){
        LoadView.changeScreen(event,ViewPath.FAVORY_VIEW);
    }
    public void homePage(Event event){
        LoadView.changeScreen(event,ViewPath.HOMEPAGE_VIEW);
    }
    public void postOfferPage(Event event){
        LoadView.changeScreen(event,ViewPath.POSTOFFER_VIEW);
    }

}
