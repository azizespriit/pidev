package Controllers;

import entites.Organisateur;
import entites.Prestataire;
import entites.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import services.OrganisateurServiceImpl;
import services.PrestataireServiceImpl;
import services.UserServiceImpl;

public class RegisterController {

  @FXML
  private TextField CinTextFiled;
  @FXML
  private CheckBox FemmeCheckBox;
  @FXML
  private CheckBox HommeCheckBox;
  @FXML
  private TextField NomTextFiled;
  @FXML
  private CheckBox OrganisateurCheckBox;
  @FXML
  private TextField PasswordTextFiled;

  @FXML
  private TextField PhoneTextFiled;

  @FXML
  private TextField PrenomTextFiled;

  @FXML
  private TextField emailTextFiled;

  @FXML
  private Label YearsOfExperienceLabel;

  @FXML
  private TextField YearsOfExperienceTextField;

  @FXML
  private Label EventsOrganizedLabel;

  @FXML
  private TextField EventsOrganizedTextField;

  @FXML
  private Button OrganisateurButton;

  @FXML
  private Button PrestataireButton;


  @FXML
  private CheckBox PrestataireCheckBox;

  @FXML
  private Label SpecialtyLabel;

  @FXML
  private TextField SpecialtyTextField;

  @FXML
  private Label experienceLabel;

  @FXML
  private TextField experienceTextField;


  // Méthode d'inscription pour l'utilisateur (et l'organisateur si sélectionné)
  @FXML
  void register(ActionEvent event) {
    // Récupérer les valeurs des champs
    String cin = CinTextFiled.getText().trim();
    String lastName = NomTextFiled.getText().trim();
    String firstName = PrenomTextFiled.getText().trim();
    String gender = getGender();
    String phone = PhoneTextFiled.getText().trim();
    String roles = getRoles(); // Ici on suppose que les rôles sont gérés avec une valeur "organisateur" ou autre

    String email = emailTextFiled.getText().trim();
    String password = PasswordTextFiled.getText().trim();

    // Vérification des champs
    if (cin.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || gender.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
      showAlert("Erreur", "Tous les champs doivent être remplis.", AlertType.ERROR);
      return;
    }

    // Créer l'objet User
    User user = new User(cin, lastName, firstName, gender, phone, roles, email, password);

    // Appel au service User pour enregistrer l'utilisateur commun
    UserServiceImpl userService = new UserServiceImpl();
    try {
      userService.register(user);

      // Si l'utilisateur est un organisateur, on enregistre les informations spécifiques
      if (OrganisateurCheckBox.isSelected()) {
        registerOrganisateur(user);
      }

      // Si l'utilisateur est un prestataire, on enregistre les informations spécifiques
      if (PrestataireCheckBox.isSelected()) {
        registerPrestataire(user);
      }

      showAlert("Succès", "Utilisateur enregistré avec succès !", AlertType.INFORMATION);
    } catch (Exception e) {
      System.err.println("Erreur lors de l'enregistrement : " + e.getMessage());
      showAlert("Erreur", "Une erreur est survenue lors de l'enregistrement. Veuillez réessayer.", AlertType.ERROR);
    }
  }

  // Méthode pour récupérer le genre sélectionné
  private String getGender() {
    if (FemmeCheckBox.isSelected()) {
      return "Femme";
    } else if (HommeCheckBox.isSelected()) {
      return "Homme";
    }
    return ""; // Si aucun genre n'est sélectionné, retourner une chaîne vide
  }

  // Méthode pour récupérer le rôle sélectionné (seulement "organisateur" dans ce cas)
  private String getRoles() {
    if (OrganisateurCheckBox.isSelected()) {
      return "organisateur";
    }
    if (PrestataireCheckBox.isSelected()) {
      return "prestataire";
    }
    return ""; // Si l'option n'est pas cochée, le rôle est vide ou "utilisateur" par défaut
  }

  // Méthode pour enregistrer un organisateur dans la base de données
  private void registerOrganisateur(User user) {
    try {
      // Assurez-vous que les champs spécifiques à l'organisateur sont remplis
      int yearsOfExperience = Integer.parseInt(YearsOfExperienceTextField.getText().trim());
      int eventsOrganized = Integer.parseInt(EventsOrganizedTextField.getText().trim());

      Organisateur organisateur = new Organisateur(
        user.getCin(), user.getLastName(), user.getFirstName(),
        user.getGender(), user.getPhone(), user.getRoles(),
        user.getEmail(), user.getPassword(),
        yearsOfExperience, eventsOrganized
      );

      // Appeler le service Organisateur pour enregistrer l'organisateur
      OrganisateurServiceImpl organisateurService = new OrganisateurServiceImpl();
      organisateurService.registerOrganisateur(organisateur);
    } catch (Exception e) {
      showAlert("Erreur", "Erreur lors de l'enregistrement de l'organisateur.", AlertType.ERROR);
      e.printStackTrace();
    }
  }

  // Méthode pour enregistrer un prestataire dans la base de données
  private void registerPrestataire(User user) {
    try {
      // Assurez-vous que les champs spécifiques au prestataire sont remplis
      String specialty = SpecialtyTextField.getText().trim();
      String serviceDescription = experienceTextField.getText().trim();

      Prestataire prestataire = new Prestataire(
        user.getCin(), user.getLastName(), user.getFirstName(),
        user.getGender(), user.getPhone(), user.getRoles(),
        user.getEmail(), user.getPassword(),
        specialty, serviceDescription
      );

      // Appeler le service Prestataire pour enregistrer le prestataire
      PrestataireServiceImpl prestataireService = new PrestataireServiceImpl();
      prestataireService.registerPrestataire(prestataire);
    } catch (Exception e) {
      showAlert("Erreur", "Erreur lors de l'enregistrement du prestataire.", AlertType.ERROR);
      e.printStackTrace();
    }
  }

  // Méthode pour afficher une alerte
  private void showAlert(String title, String message, AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  // Méthode pour gérer l'affichage des champs spécifiques à l'Organisateur
  @FXML
  public void handleOrganisateurCheckBox(ActionEvent event) {
    if (OrganisateurCheckBox.isSelected()) {
      PrestataireCheckBox.setSelected(false);
      // Afficher les champs spécifiques à l'organisateur
      YearsOfExperienceLabel.setVisible(true);
      YearsOfExperienceTextField.setVisible(true);
      EventsOrganizedLabel.setVisible(true);
      EventsOrganizedTextField.setVisible(true);
      OrganisateurButton.setVisible(true);

      // Cacher les champs spécifiques au prestataire
      SpecialtyLabel.setVisible(false);
      SpecialtyTextField.setVisible(false);
      experienceLabel.setVisible(false);
      experienceTextField.setVisible(false);
      PrestataireButton.setVisible(false);
    } else {
      // Cacher les champs spécifiques à l'organisateur
      YearsOfExperienceLabel.setVisible(false);
      YearsOfExperienceTextField.setVisible(false);
      EventsOrganizedLabel.setVisible(false);
      EventsOrganizedTextField.setVisible(false);
      OrganisateurButton.setVisible(false);
    }
  }

  // Méthode pour gérer l'action du bouton "S'inscrire en tant qu'Organisateur"
  @FXML
  private void handleOrganisateurButtonClick(ActionEvent event) {
    // Cette méthode est appelée lorsque l'utilisateur clique sur le bouton "S'inscrire en tant que Organisateur"

    // Vérifiez si tous les champs nécessaires sont remplis
    String yearsOfExperienceText = YearsOfExperienceTextField.getText().trim();
    String eventsOrganizedText = EventsOrganizedTextField.getText().trim();

    if (yearsOfExperienceText.isEmpty() || eventsOrganizedText.isEmpty()) {
      showAlert("Erreur", "Les champs spécifiques à l'organisateur doivent être remplis.", AlertType.ERROR);
      return;
    }

    try {
      // Récupérer les données spécifiques à l'organisateur
      int yearsOfExperience = Integer.parseInt(yearsOfExperienceText);
      int eventsOrganized = Integer.parseInt(eventsOrganizedText);

      // Appel à la méthode d'inscription pour enregistrer l'organisateur
      // Créer un objet Organisateur
      Organisateur organisateur = new Organisateur(
        CinTextFiled.getText().trim(),
        NomTextFiled.getText().trim(),
        PrenomTextFiled.getText().trim(),
        getGender(),
        PhoneTextFiled.getText().trim(),
        getRoles(),
        emailTextFiled.getText().trim(),
        PasswordTextFiled.getText().trim(),
        yearsOfExperience,
        eventsOrganized
      );

      // Appeler le service pour enregistrer l'organisateur
      OrganisateurServiceImpl organisateurService = new OrganisateurServiceImpl();
      organisateurService.registerOrganisateur(organisateur);

      // Afficher un message de succès
      showAlert("Succès", "Organisateur inscrit avec succès !", AlertType.INFORMATION);

    } catch (Exception e) {
      showAlert("Erreur", "Une erreur est survenue lors de l'inscription de l'organisateur.", AlertType.ERROR);
      e.printStackTrace();
    }
  }

  // Méthode pour gérer l'affichage des champs spécifiques au Prestataire
  @FXML
  public void handlePrestataireCheckBox(ActionEvent event) {
    if (PrestataireCheckBox.isSelected()) {
      OrganisateurCheckBox.setSelected(false);
      // Afficher les champs spécifiques au prestataire
      SpecialtyLabel.setVisible(true);
      SpecialtyTextField.setVisible(true);
      experienceLabel.setVisible(true);
      experienceTextField.setVisible(true);
      PrestataireButton.setVisible(true);

      // Cacher les champs spécifiques à l'organisateur
      YearsOfExperienceLabel.setVisible(false);
      YearsOfExperienceTextField.setVisible(false);
      EventsOrganizedLabel.setVisible(false);
      EventsOrganizedTextField.setVisible(false);
      OrganisateurButton.setVisible(false);
    } else {
      // Cacher les champs spécifiques au prestataire
      SpecialtyLabel.setVisible(false);
      SpecialtyTextField.setVisible(false);
      experienceLabel.setVisible(false);
      experienceTextField.setVisible(false);
      PrestataireButton.setVisible(false);
    }

  }

  // Méthode pour gérer l'action du bouton "S'inscrire en tant que Prestataire"
  @FXML
  private void handlePrestataireButtonClick(ActionEvent event) {
    // Cette méthode est appelée lorsque l'utilisateur clique sur le bouton "S'inscrire en tant que Prestataire"

    // Vérifiez si tous les champs nécessaires sont remplis
    String specialtyText = SpecialtyTextField.getText().trim();
    String serviceDescriptionText = experienceTextField.getText().trim();

    if (specialtyText.isEmpty() || serviceDescriptionText.isEmpty()) {
      showAlert("Erreur", "Les champs spécifiques au prestataire doivent être remplis.", AlertType.ERROR);
      return;
    }

    try {
      // Récupérer les données spécifiques au prestataire
      String specialty = specialtyText;
      String serviceDescription = serviceDescriptionText;

      // Créer un objet Prestataire
      Prestataire prestataire = new Prestataire(
        CinTextFiled.getText().trim(),
        NomTextFiled.getText().trim(),
        PrenomTextFiled.getText().trim(),
        getGender(),
        PhoneTextFiled.getText().trim(),
        getRoles(),
        emailTextFiled.getText().trim(),
        PasswordTextFiled.getText().trim(),
        specialty,
        serviceDescription
      );

      // Appeler le service pour enregistrer le prestataire
      PrestataireServiceImpl prestataireService = new PrestataireServiceImpl();
      prestataireService.registerPrestataire(prestataire);

      // Afficher un message de succès
      showAlert("Succès", "Prestataire inscrit avec succès !", AlertType.INFORMATION);

    } catch (Exception e) {
      showAlert("Erreur", "Une erreur est survenue lors de l'inscription du prestataire.", AlertType.ERROR);
      e.printStackTrace();
    }
  }
}
