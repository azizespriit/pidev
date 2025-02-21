package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;

public class IndexDash {
  @FXML
  private Button dashButton;
  @FXML
  private void Dash() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dash.fxml"));
      Stage stage = new Stage();
      stage.setScene(new Scene(loader.load()));
      stage.setTitle("Dashboard");
      stage.show();
      closeWindow();
    } catch (Exception e) {
      showAlert("Error", "Failed to open the dashboard: " + e.getMessage());
    }
  }

  private void closeWindow() {
    Stage stage = (Stage) dashButton.getScene().getWindow();
    stage.close();
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
