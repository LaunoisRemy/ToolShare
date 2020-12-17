package gui.controller;

import gui.LoadView;
import javafx.event.ActionEvent;

public class NavBarUser {
    public void profilePage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"register");
    }
    public void historyPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"register");
    }
    public void favoryPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"register");
    }
    public void postOfferPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"register");
    }

}
