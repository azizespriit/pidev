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
    private final publicationService pubService = new publicationService();

    // Initialisation avec la publication sélectionnée
    public void setPublication(publication selectedPub) {
        if (selectedPub != null) {
            this.selectedPublication = selectedPub;
            System.out.println("Publication sélectionnée : ID = " + selectedPub.getId());
            contenuField.setText(selectedPub.getContenu());
            descriptionField.setText(selectedPub.getDescription());
            imageUrlField.setText(selectedPub.getImagePath());
        } else {
            System.out.println(" Erreur : Aucune publication sélectionnée.");
        }
    }


    // Méthode pour enregistrer les modifications

    @FXML
    private void handleSave() {
        if (selectedPublication != null) {
            System.out.println("🔍 Avant mise à jour : " + selectedPublication.getId());
            System.out.println("Contenu : " + contenuField.getText());
            System.out.println("Description : " + descriptionField.getText());
            System.out.println("Image path: " + imageUrlField.getText());

            // Mettre à jour les attributs de la publication
            selectedPublication.setContenu(contenuField.getText());
            selectedPublication.setDescription(descriptionField.getText());
            selectedPublication.setImagePath(imageUrlField.getText());

            try {
                System.out.println("🚀 Tentative de mise à jour...");
                pubService.update(selectedPublication);  // Appel au service pour mise à jour
                System.out.println("✅ Mise à jour réussie !");
                showInfo("Succès", "Publication modifiée avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
                showError("Erreur", "Impossible de modifier la publication : " + e.getMessage());
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
