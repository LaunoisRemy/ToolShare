package util;

import javafx.scene.control.Alert;

public class AlertBox {
    public static void showAlert(String title,String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    public static Alert showAlertConfirmationDeletion(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete this offer ?");
        return alert;
    }
}
