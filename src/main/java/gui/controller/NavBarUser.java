package gui.controller;

import business.facade.SessionFacade;
import business.system.user.User;
import gui.LoadView;
import gui.router.UserRouter;
import javafx.event.ActionEvent;

public class NavBarUser {
    UserRouter userRouter = new UserRouter();
    User user = SessionFacade.getInstance().getUser();

    public void profilePage(ActionEvent actionEvent){
        userRouter.updateUser(actionEvent,"registerUpdateUser",user);
    }
    public void historyPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"register");
    }
    public void favoryPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"register");
    }
    public void homePage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"offers");
    }
    public void postOfferPage(ActionEvent actionEvent){
        LoadView.changeScreen(actionEvent,"registerUpdateUser");
    }

}
