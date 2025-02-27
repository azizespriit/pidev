package Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.commentaire;
import models.publication;
import services.commentaireService;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;



import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class commentaireC implements Initializable {
    public Button btnAjouter;
    public TextField txtCommentaire;
    public Button btnModifier;
    public Button btnSupprimer;
    public ListView listViewCommentaires;
    private publication publicationSelectionnee;


    @FXML
    private HBox commentContainer;

    @FXML
    private Label publicationLabel;

    @FXML

    private final commentaireService comService = new commentaireService();
    private publication selectedPublication;



    public void setPublicationC(publication pub) {
        if (pub == null) {
            System.out.println("⚠️ ERREUR: Aucune publication reçue !");
            return;
        }
        this.selectedPublication = pub; // ✅ Stocker la publication sélectionnée
        System.out.println("✅ Publication reçue : " + selectedPublication.getContenu());
    }


    public void setTxtCommentaire(TextField txtCommentaire) {
        this.txtCommentaire = txtCommentaire;
        System.out.println("✅ descriptionField reçu dans commentaireC : " + (txtCommentaire!= null));
    }

    @FXML
    public void initialize() {
        if (txtCommentaire == null) {
            System.out.println("⚠️ txtCommentaire est NULL !");
        }
    }


    public void afficherCommentaires(int idPub) {
        listViewCommentaires.getItems().clear(); // ✅ Vider la liste avant de recharger

        try {
            List<commentaire> commentaires = comService.getCommentairesByPublication(idPub);

            if (commentaires.isEmpty()) {
                System.out.println("📌 Aucun commentaire trouvé.");
            }

            listViewCommentaires.getItems().addAll(commentaires); // ✅ Ajouter à la liste
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void ajouterCommentaire(ActionEvent event) {
        if (selectedPublication == null || selectedPublication.getId() == 0) {
            showError("Erreur", "Aucune publication sélectionnée ou ID invalide.");
            return;
        }

        if (txtCommentaire == null) {
            showError("Erreur", "Le champ commentaire n'est pas initialisé.");
            return;
        }

        String description = txtCommentaire.getText().trim();
        if (description.isEmpty()) {
            showError("Erreur", "Veuillez entrer un commentaire.");
            return;
        }

        commentaire newComment = new commentaire(
                0,
                selectedPublication.getId(),
                description,  // ✅ Utiliser le vrai texte saisi
                LocalDateTime.now()
        );

        try {
            comService.create(newComment); // ✅ Ajout à la base de données
            txtCommentaire.clear();

            // ✅ Recharger la liste des commentaires
            afficherCommentaires(selectedPublication.getId());

            showSuccess("Succès", "Commentaire ajouté avec succès.");
        } catch (Exception e) {
            showError("Erreur", "Impossible d'ajouter le commentaire : " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    public void modifierCommentaire(ActionEvent event) {
        if (selectedPublication == null) {
            showError("Erreur", "Aucune publication sélectionnée.");
            return;
        }

        String newDescription = txtCommentaire.getText().trim();
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

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        listViewCommentaires.setCellFactory(lv -> new ListCell<commentaire>() {
            @Override
            protected void updateItem(commentaire commentaire, boolean empty) {
                super.updateItem(commentaire, empty);

                if (empty || commentaire == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/commentaire_item.fxml"));
                        Parent root = loader.load();

                        CommentaireItemC controller = loader.getController();
                        controller.setCommentaire(commentaire); // Passe le commentaire à la cellule

                        setGraphic(root); // Affiche l'élément FXML dans la liste
                    } catch (IOException e) {
                        e.printStackTrace();
                        setText("Erreur de chargement");
                    }
                }
            }
        });

        listViewCommentaires.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                commentaire selectedComment = (commentaire) newValue;
                txtCommentaire.setText(selectedComment.getContenu()); // Afficher le texte du commentaire sélectionné
                System.out.println("Commentaire sélectionné : " + selectedComment.getContenu());
            }
        });

        btnModifier.setOnAction(event -> {
            commentaire selectedComment = (commentaire) listViewCommentaires.getSelectionModel().getSelectedItem();

            if (selectedComment != null) {
                String nouveauContenu = txtCommentaire.getText().trim();

                if (!nouveauContenu.isEmpty()) {
                    try {
                        selectedComment.setContenu(nouveauContenu);
                        comService.update(selectedComment); // ✅ Mettre à jour dans la base de données
                        listViewCommentaires.refresh(); // ✅ Mettre à jour l'affichage
                        txtCommentaire.clear();
                        showSuccess("Succès", "Commentaire modifié avec succès.");
                    } catch (Exception e) {  // ✅ Capture l'exception et affiche l'erreur
                        showError("Erreur", "Impossible de modifier le commentaire : " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    showError("Erreur", "Le commentaire ne peut pas être vide.");
                }
            } else {
                showError("Erreur", "Veuillez sélectionner un commentaire à modifier.");
            }
        });


        btnSupprimer.setOnAction(event -> {
            commentaire selectedComment = (commentaire) listViewCommentaires.getSelectionModel().getSelectedItem();

            if (selectedComment != null) {
                try {
                    // ✅ Supprimer de la base de données
                    comService.delete(selectedComment);

                    // ✅ Supprimer de la liste
                    listViewCommentaires.getItems().remove(selectedComment);

                    txtCommentaire.clear();
                    btnModifier.setDisable(true);
                    btnSupprimer.setDisable(true);

                    showSuccess("Succès", "Commentaire supprimé avec succès.");
                } catch (Exception e) {  // ✅ Capture l'exception et affiche l'erreur
                    showError("Erreur", "Impossible de supprimer le commentaire : " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                showError("Erreur", "Veuillez sélectionner un commentaire à supprimer.");
            }
        });

    }






}
