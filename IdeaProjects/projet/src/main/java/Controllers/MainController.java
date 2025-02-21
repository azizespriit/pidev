package Controllers;

import entites.Sessions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

  @FXML
  private AnchorPane mainContent;


  @FXML
  public void initialize() {
    loadUserProfile();
  }

  @FXML
  private void handleButtonAction(ActionEvent event) {
    Button clickedButton = (Button) event.getSource();
    String fxmlFile = "";

    switch (clickedButton.getText()) {
      case "Événements":
        fxmlFile = "/DashboardFront/Evenements.fxml";
        break;
      case "Services":
        fxmlFile = "/DashboardFront/Services.fxml";
        break;
      case "Produits":
        fxmlFile = "/DashboardFront/Produits.fxml";
        break;
      case " Équipements":
        fxmlFile = "/DashboardFront/Equipements.fxml";
        break;
      case "Reserver Équipements":
        fxmlFile = "/DashboardFront/Reservation.fxml";
        break;
      case "Panier":
        fxmlFile = "/DashboardFront/Panier.fxml";
        break;
      case "Réclamation":
        fxmlFile = "/DashboardFront/Reclamation.fxml";
        break;
      default:
        return;
    }

    loadContent(fxmlFile);
  }

  ///////////////////**********************************************/////////////
  private void loadContent(String fxmlFile) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent newContent = loader.load();
      mainContent.getChildren().clear();
      mainContent.getChildren().add(newContent);
      AnchorPane.setTopAnchor(newContent, 0.0);
      AnchorPane.setBottomAnchor(newContent, 0.0);
      AnchorPane.setLeftAnchor(newContent, 0.0);
      AnchorPane.setRightAnchor(newContent, 0.0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
///**************************************************************/////////////////////////////////////
  private void loadFXML(String fxmlFile, ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    Parent root = loader.load();
    // Obtenir la scène actuelle et la modifier
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }
  //////////////////////*************************************************************
  public void handleLogout(ActionEvent event) throws IOException {
    // Déconnexion de l'utilisateur
    String username = Sessions.getCurrentUser().getFirstName();
    String roles = Sessions.getCurrentUser().getRoles();
    Sessions.logout();  // Efface les informations de session de l'utilisateur connecté
    showAlert("Déconnexion", "L'utilisateur " + username + " a été déconnecté avec succès.");

    // Redirection vers la page de login
    loadFXML("/login.fxml", event);
  }
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
  private void loadUserProfile() {
    loadContent("/DashboardFront/UserProfile.fxml"); // Charge juste le fichier FXML
  }
}
