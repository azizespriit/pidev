package Controllers;

import models.publication;
import services.publicationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Modification {

    @FXML
    private TextField contenuField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField imageUrlField;

    private publication selectedPublication;
    private publicationService pubService = new publicationService();

    // Initialisation avec la publication sélectionnée
    public void setPublication(publication selectedPub) {
        this.selectedPublication = selectedPub;
        // Pré-remplir les champs avec les données actuelles de la publication
        contenuField.setText(selectedPub.getContenu());
        descriptionField.setText(selectedPub.getDescription());
        imageUrlField.setText(selectedPub.getImageUrl());
    }

    // Méthode pour enregistrer les modifications
    @FXML
    private void handleSave() {
        if (selectedPublication != null) {
            // Mettre à jour les attributs de la publication
            selectedPublication.setContenu(contenuField.getText());
            selectedPublication.setDescription(descriptionField.getText());
            selectedPublication.setImageUrl(imageUrlField.getText());

            try {
                pubService.update(selectedPublication);  // Appel au service pour mettre à jour la publication dans la base de données
                showInfo("Succès", "Publication modifiée avec succès.");
            } catch (Exception e) {
                showError("Erreur", "Impossible de modifier la publication.");
            }
        } else {
            showError("Erreur", "Veuillez sélectionner une publication.");
        }
    }

    // Méthode pour annuler la modification
    @FXML
    private void handleCancel() {
        // Fermer ou réinitialiser les champs
        System.out.println("Modification annulée.");
    }

    // Méthode pour afficher une alerte d'erreur
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher une alerte d'information
    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
