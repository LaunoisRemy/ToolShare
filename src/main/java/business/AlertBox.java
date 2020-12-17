package business;

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
}
