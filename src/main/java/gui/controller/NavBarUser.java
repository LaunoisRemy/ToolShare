package gui.controller;

import business.facade.SessionFacade;
import business.system.user.User;
import gui.LoadView;
import gui.ViewPath;
import javafx.event.ActionEvent;

import java.util.ArrayList;

public class NavBarUser {
    User user = SessionFacade.getInstance().getUser();

    public void profilePage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent, ViewPath.REGISTERUPDATEUSER_VIEW,user,1);
    }
    public void historyPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent, ViewPath.LOGIN_VIEW);
    }
    public void favoryPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,ViewPath.LOGIN_VIEW);
    }
    public void homePage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,ViewPath.HOMEPAGE_VIEW);
    }
    public void postOfferPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,ViewPath.POSTOFFER_VIEW);
    }

}
