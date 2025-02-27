package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.publication;
import services.publicationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import utils.MyDb;


public class affiche {

    @FXML
    private TextField searchField;


    private publicationService servicePub = new publicationService();

    @FXML
    void handleRecherche(ActionEvent event) {
        String keyword = searchField.getText().trim();

        if (!keyword.isEmpty()) {
            List<publication> filteredList = servicePub.rechercherPublicationsParContenu(keyword);
           All.getItems().setAll(filteredList); // Mettre à jour l'affichage
        } else {
            try {
                All.getItems().setAll(servicePub.getAll()); // Réinitialiser la l
            }catch (Exception e) {
                System.out.println("Erreur lors du chargement des publications : " + e.getMessage());
            }


        }
    }

    @FXML
    private ListView<publication> All;


// ListView pour afficher les publications

    @FXML
    private Button mod, supp, commentButton; // Boutons Modifier, Supprimer, Commenter

    @FXML
    private Button ajouter;
    private final publicationService pubService = new publicationService(); // Service pour les publications
    private ObservableList<publication> publicationList; // Liste observable des publications
    private publication selectedPublication;


    public void setPublicationC(publication pub) {
        if (pub == null) {
            System.out.println("setPublicationC: pub est NULL !");
            return;
        }
        System.out.println("setPublicationC appelée avec : " + pub.getContenu());
    }






    public void initialize() {
        try {
            // Charger les publications depuis la base de données
            publicationList = FXCollections.observableArrayList(pubService.getAll());
            All.setItems(publicationList);
            All.setCellFactory(param -> new publicationCell());

        } catch (Exception e) {
            showError("Erreur", "Impossible de charger les publications : " + e.getMessage());
        }

        // Activer/désactiver les boutons selon la sélection
        All.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isDisabled = (newValue == null);
            mod.setDisable(isDisabled);
            supp.setDisable(isDisabled);
            commentButton.setDisable(isDisabled);
        });
    }

    @FXML
    private void handleModifier(ActionEvent event) {
        publication selectedPub = All.getSelectionModel().getSelectedItem(); // Récupérer la publication sélectionnée

        if (selectedPub != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/publication.fxml"));
                AnchorPane root = loader.load();

                // Récupérer le contrôleur et lui passer la publication
                publicationC controller = loader.getController();
                controller.setPublication(selectedPub);

                // Afficher la fenêtre
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("❌ Erreur : " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Aucune publication sélectionnée !");
        }
    }

    @FXML
    private void handleSupprimer(ActionEvent event) {
        publication selectedPub = All.getSelectionModel().getSelectedItem();
        if (selectedPub != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cette publication ?");
            alert.setContentText("Cette action est irréversible.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        pubService.delete(selectedPub);
                        publicationList.remove(selectedPub);
                        showInfo("Succès", "Publication supprimée avec succès.");
                    } catch (Exception e) {
                        showError("Erreur", "Impossible de supprimer la publication : " + e.getMessage());
                    }
                }
            });
        } else {
            showError("Erreur", "Veuillez sélectionner une publication à supprimer.");
        }
    }

    private void refreshListView() {
        try {
            publicationList.setAll(pubService.getAll());
        } catch (Exception e) {
            showError("Erreur", "Impossible de rafraîchir la liste des publications : " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void handleCommenter(ActionEvent event) {
        publication selectedPub = All.getSelectionModel().getSelectedItem();

        if (selectedPub != null) { // ✅ Vérification de la sélection
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/commentaire.fxml"));
                Parent root = loader.load();

                // Récupérer le contrôleur de la fenêtre des commentaires
                commentaireC controller = loader.getController();
                controller.setPublicationC(selectedPub); // ✅ Passer la publication sélectionnée

                // Ouvrir une nouvelle fenêtre pour les commentaires
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Ajouter un commentaire");
                stage.show();
            } catch (IOException e) {
                showError("Erreur", "Impossible d'ouvrir la page des commentaires.");
                e.printStackTrace();
            }
        } else {
            showError("Erreur", "Veuillez sélectionner une publication avant d'ajouter un commentaire.");
        }
    }




    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null); // Enlever le header pour un affichage propre
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publication.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ajouter.getScene().getWindow(); // Récupérer la fenêtre actuelle
            stage.setScene(new Scene(root)); // Charger la nouvelle scène
            stage.setTitle("Publications"); // Modifier le titre si nécessaire
            stage.show();
        } catch (IOException e) {
            showError("Impossible d'ouvrir la page des publications.");
        }
    }





}