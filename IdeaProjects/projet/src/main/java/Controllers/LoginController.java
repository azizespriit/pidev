package Controllers;

import entites.Sessions;
import entites.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.UserService;

import services.UserServiceImpl;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
  @FXML private TextField emailField;
  @FXML private PasswordField passwordField;
  @FXML private Label errorLabel;

  private final UserService userService = new UserServiceImpl();

  @FXML
  private void handleLogin(ActionEvent event) throws IOException {
    String email = emailField.getText();
    String password = passwordField.getText();

    // Vérifier si le compte est bloqué avant de tenter la connexion
    if (userService.isAccountLocked(email)) {
      showAlert("Compte bloqué", "Votre compte est actuellement bloqué. Veuillez réessayer dans 15 minutes.");
      return;
    }

    Optional<User> user = userService.login(email, password);

    if (user.isPresent()) {
      User loggedUser = user.get();
      // Définir l'utilisateur connecté dans la session
      Sessions.setCurrentUser(loggedUser);
      if (loggedUser.getRoles().contains("organisateur")) {
        System.out.println("Redirection vers l'interface Organisateur...");
        loadFXML("/DashboardFront/DashboardFront.fxml", event);
      } else if (loggedUser.getRoles().contains("prestataire")) {
        System.out.println("Redirection vers l'interface Prestataire...");
        loadFXML("/DashboardFront/DashboardFront.fxml", event);
      }
    } else {
      errorLabel.setText("Email ou mot de passe incorrect.");
    }
  }

  private void loadFXML(String fxmlFile, ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    Parent root = loader.load();

    // Obtenir la scène actuelle et la modifier
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    stage.show();
  }

  // Méthode pour afficher une alerte
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

//*************************************
public void openRegisterForm() throws IOException {

  FXMLLoader loader = new FXMLLoader(getClass().getResource("/register_user.fxml"));
  Parent registerForm = loader.load();
  Stage stage = (Stage) emailField.getScene().getWindow();
  stage.setScene(new Scene(registerForm));
  stage.show();
}
  public void openForgetForm() throws IOException {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/forgot_password.fxml"));
      Parent root = loader.load();

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.setTitle("Mot de passe oublié");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
