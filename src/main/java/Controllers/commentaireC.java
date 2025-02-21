package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.commentaire;
import models.publication;
import services.commentaireService;

import java.time.LocalDateTime;
import java.util.List;

public class commentaireC {
    public Button btnAjouter;
    public TextField txtCommentaire;
    public Button btnModifier;
    public Button btnSupprimer;
    public ListView listViewCommentaires;
    private publication publicationSelectionnee;
    @FXML
    private Label lblPublicationTitre;
    @FXML
    private VBox commentContainer;

    @FXML
    private Label publicationLabel;

    @FXML
    private TextArea descriptionField;
    private publication publicationCourante;
    private commentaireService comService = new commentaireService();
    private publication selectedPublication;

    public void setPublicationC(publication pub) {
        this.publicationCourante = pub;
        System.out.println("Publication sélectionnée : " + pub.getContenu()); // Vérification
    }

    @FXML
    public void initialize() {
    }

    public void afficherCommentaires(int idPub) {
        commentContainer.getChildren().clear();
        try {
            List<models.commentaire> commentaires = comService.getCommentairesByPublication(idPub);
            for (commentaire com : commentaires) {
                Label label = new Label(com.getContenu() + " - " + com.getDate_commentaire());
                label.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px;");
                commentContainer.getChildren().add(label);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ajouterCommentaire(ActionEvent event) {
        if (selectedPublication == null) {
            showError("Erreur", "Aucune publication sélectionnée.");
            return;
        }

        String description = descriptionField.getText().trim();
        if (description.isEmpty()) {
            showError("Erreur", "Veuillez entrer un commentaire.");
            return;
        }

        commentaire newComment = new commentaire(0, selectedPublication.getId(), description, LocalDateTime.now());

        try {
            comService.create(newComment);
            afficherCommentaires(selectedPublication.getId());
            descriptionField.clear();
            showSuccess("Succès", "Commentaire ajouté avec succès.");
        } catch (Exception e) {
            showError("Erreur", "Impossible d'ajouter le commentaire : " + e.getMessage());
        }
    }

    @FXML
    public void modifierCommentaire(ActionEvent event) {
        if (selectedPublication == null) {
            showError("Erreur", "Aucune publication sélectionnée.");
            return;
        }

        String newDescription = descriptionField.getText().trim();
        if (newDescription.isEmpty()) {
            showError("Erreur", "Veuillez entrer une nouvelle description.");
            return;
        }

        try {
            List<commentaire> commentaires = comService.getCommentairesByPublication(selectedPublication.getId());
            if (commentaires.isEmpty()) {
                showError("Erreur", "Aucun commentaire à modifier.");
                return;
            }

            commentaire dernierCommentaire = commentaires.get(commentaires.size() - 1);
            dernierCommentaire.setContenu(newDescription);

            comService.update(dernierCommentaire);
            afficherCommentaires(selectedPublication.getId());
            showSuccess("Succès", "Commentaire modifié avec succès.");
        } catch (Exception e) {
            showError("Erreur", "Impossible de modifier le commentaire : " + e.getMessage());
        }
    }

    @FXML
    public void supprimerCommentaire(ActionEvent event) {
        if (selectedPublication == null) {
            showError("Erreur", "Aucune publication sélectionnée.");
            return;
        }

        try {
            List<commentaire> commentaires = comService.getCommentairesByPublication(selectedPublication.getId());
            if (commentaires.isEmpty()) {
                showError("Erreur", "Aucun commentaire à supprimer.");
                return;
            }

            commentaire dernierCommentaire = commentaires.get(commentaires.size() - 1);
            comService.delete(dernierCommentaire);
            afficherCommentaires(selectedPublication.getId());
            showSuccess("Succès", "Commentaire supprimé avec succès.");
        } catch (Exception e) {
            showError("Erreur", "Impossible de supprimer le commentaire : " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}  // <-- Fin correcte du fichier
