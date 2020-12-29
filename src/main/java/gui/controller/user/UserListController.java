package gui.controller.user;

import business.facade.SessionFacade;
import business.system.user.Admin;
import business.system.user.User;
import com.jfoenix.controls.JFXButton;
import gui.LoadView;
import gui.ViewPath;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserListController implements Initializable {

    private final SessionFacade sessionFacade = SessionFacade.getInstance();

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User,String> name,email,role;
    @FXML
    private TableColumn<User,Boolean> isBanned;
    @FXML
    private TableColumn<User,Integer> delete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<User> usersArrayList = new ArrayList<>(this.sessionFacade.getAllUsers());

        final ObservableList<User> data = FXCollections.observableArrayList(usersArrayList);

        name.setCellValueFactory(param -> {
            String fistName = param.getValue().getFirstName();
            String lastName = param.getValue().getLastName();

            String res = fistName + " " + lastName;

            return new SimpleObjectProperty<>(res);
        });
        name.setCellFactory(TextFieldTableCell.forTableColumn());

        email.setCellValueFactory(new PropertyValueFactory<>("email") );
        email.setCellFactory(TextFieldTableCell.forTableColumn());

        role.setCellValueFactory(param -> {
            String role = param.getValue().getRole().getNameRole();
            return new SimpleObjectProperty<>(role);
        });
        role.setCellFactory(TextFieldTableCell.forTableColumn());

        isBanned.setCellValueFactory(new PropertyValueFactory<>("isBanned"));

        isBanned.setCellFactory(param -> new TableCell<>() {
            private final Button banButton = new Button();

            {
                banButton.setOnAction(event -> alertBanned(event, param.getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                }
                else {
                    if(item){
                        banButton.setText("Unban account");
                    } else {
                        banButton.setText("Ban account");
                    }
                    setGraphic(banButton);
                }
            }
        });

        delete.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        delete.setCellFactory(param -> new TableCell<>() {
            Image img;
            ImageView iv;

            private final JFXButton deleteButton = new JFXButton();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }
                else {
                    User user = getTableView().getItems().get(getIndex());
                    Image img= new Image("img/red_trash.png") ;
                    ImageView iv= new ImageView(img);
                    if(!user.getRole().getNameRole().equals(Admin.ADMIN)){
                        deleteButton.setOnAction(event -> alertDelete(event, user ,data));
                    } else {
                        deleteButton.setDisable(true);
                    }
                    iv.setFitHeight(25);
                    iv.setFitWidth(25);
                    deleteButton.setGraphic(iv);

                    setGraphic(deleteButton);
                }
            }
        });

        table.setItems(data);
    }

    private void alertDelete(ActionEvent event, User user, ObservableList<User> data) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete this user ?");

        Optional<ButtonType> result = alert.showAndWait();
        if ( result.isPresent() && result.get() == ButtonType.OK){
            handleDeleteUser(user,data);
        }
    }

    private void handleDeleteUser(User user, ObservableList<User> data) {
        boolean deleted = this.sessionFacade.deleteUser(user);

        if(deleted){
            data.removeAll(user);
        }
    }

    private void alertBanned(ActionEvent event, User u) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if(u.getIsBanned()){
            alert.setTitle("Confirmation Unban");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to unban this user ?");
        } else {
            alert.setTitle("Confirmation Ban");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to ban this user ?");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            handleSetBanned(u);
            //refresh page to see changes
            LoadView.changeScreen(event, ViewPath.USERLIST_VIEW);
        }
    }

    private void handleSetBanned(User u) {
        u.setIsBanned(!u.getIsBanned());
        this.sessionFacade.updateUser(u);
    }

    public void addAdminPage(ActionEvent event) {
        LoadView.changeScreen(event,ViewPath.ADDADMIN_VIEW);
    }
}
