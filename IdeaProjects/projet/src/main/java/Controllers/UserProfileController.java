package Controllers;

import entites.Organisateur;
import entites.Prestataire;
import entites.Sessions;
import entites.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import services.OrganisateurServiceImpl;
import services.PrestataireServiceImpl;

import java.util.Optional;

public class UserProfileController {

  @FXML
  private Label userNameLabel;
  @FXML
  private Label userEmailLabel;
  @FXML
  private Label userRoleLabel;
  @FXML
  private Label userExtraInfoLabel;

  @FXML
  public void initialize() {
    loadUserInfo();
  }

  private void loadUserInfo() {
    User user = Sessions.getCurrentUser(); // Récupérer l'utilisateur connecté
    if (user != null) {
      userNameLabel.setText("Nom : " + user.getFirstName() + " " + user.getLastName());
      userEmailLabel.setText("Email : " + user.getEmail());
      userRoleLabel.setText("Rôle : " + user.getRoles());

      // Vérifier si c'est un organisateur ou un prestataire et récupérer les infos spécifiques
      if (user.getRoles().equalsIgnoreCase("organisateur")) {
        OrganisateurServiceImpl organisateurService = new OrganisateurServiceImpl();
        Optional<Organisateur> organisateur = organisateurService.getOrganisateurByUserId(user.getIdUser());
        organisateur.ifPresent(o -> userExtraInfoLabel.setText("Expérience : " + o.getYearsOfExperience() + " ans | Événements : " + o.getNumberOfEventsOrganized()));
      } else if (user.getRoles().equalsIgnoreCase("prestataire")) {
        PrestataireServiceImpl prestataireService = new PrestataireServiceImpl();
        Optional<Prestataire> prestataire = prestataireService.getPrestataireByUserId(user.getIdUser());
        prestataire.ifPresent(p -> userExtraInfoLabel.setText("Spécialité : " + p.getSpeciality()));
      } else {
        userExtraInfoLabel.setText("Aucune information supplémentaire.");
      }
    } else {
      userNameLabel.setText("Utilisateur inconnu");
      userEmailLabel.setText("Aucun email");
      userRoleLabel.setText("Rôle inconnu");
      userExtraInfoLabel.setText("Aucune information disponible.");
    }
  }
}
