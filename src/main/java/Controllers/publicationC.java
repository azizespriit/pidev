package Controllers;

import models.publication;
import services.publicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class publicationC {

    @FXML
    private TextField contenuField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button selectButton;

    @FXML
    private Button addButton;

    @FXML
    private ImageView imageView;

    private String imagePath = null; // Stocke le chemin de l'image sélectionnée

    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // Dossier de destination
                String userDir = System.getProperty("user.home") + "/SportifyImages";
                File directory = new File(userDir);
                if (!directory.exists()) {
                    directory.mkdirs(); // Créer le dossier s'il n'existe pas
                }

                // Copier l'image sélectionnée
                File destinationFile = new File(directory, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Stocker le chemin absolu de l'image
                imagePath = destinationFile.getAbsolutePath();
                System.out.println("📸 Image copiée dans : " + imagePath);

                // Afficher l'image
                if (imageView != null) {
                    Image image = new Image(destinationFile.toURI().toString());
                    imageView.setImage(image);
                    System.out.println(" Image affichée : " + imagePath);
                } else {
                    System.out.println(" Erreur : imageView est null !");
                }

            } catch (IOException e) {
                System.out.println("Erreur lors de la copie de l'image : " + e.getMessage());
            }
        } else {
            System.out.println("Aucune image sélectionnée.");
        }
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        String contenu = contenuField.getText();
        String description = descriptionField.getText();

        if (contenu == null || contenu.trim().isEmpty()) {
            System.out.println(" Erreur : Le champ 'Contenu' est vide !");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            System.out.println("Erreur : Le champ 'Description' est vide !");
            return;
        }

        // Vérification de l'image sélectionnée
        if (imagePath == null || imagePath.isEmpty()) {
            System.out.println(" Aucune image sélectionnée ! Utilisation de l'image par défaut.");
            imagePath = System.getProperty("user.home") + "/SportifyImages/default.jpg";
        }

        try {
            // Création de la publication
            publication publication = new publication(contenu, description, imagePath);
            publicationService publicationService = new publicationService();
            publicationService.create(publication);
            System.out.println(" Publication ajoutée avec succès !");
            System.out.println(" Image enregistrée en BDD : " + imagePath);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affiche.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(" Erreur lors de l'insertion : " + e.getMessage());
        }
    }
}
