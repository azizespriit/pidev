package Controllers;

import javafx.scene.Parent;
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
import services.reactionService;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

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

    @FXML
    private TextField chatbotInput; // ✅ Champ de texte pour la question du chatbot

    @FXML
    private Button chatbotSendButton; // ✅ Bouton pour envoyer la requête au chatbot

    @FXML
    private TextArea chatbotOutput; // ✅ Zone d'affichage des réponses du chatbot

    private String imagePath = null; // Stocke le chemin de l'image sélectionnée
    private publication publicationCourante;
    private LocalDateTime date_pub;


    private reactionService reactionService = new reactionService();
    private int idUtilisateur = 1; // Remplace par l'ID de l'utilisateur connecté


    @FXML
    public void initialize() {
        // ✅ Message d'accueil du chatbot
        if (chatbotOutput != null) {
            chatbotOutput.setText("💬 Chatbot: Bonjour ! Posez une question sur les publications.\n");
        }
    }


    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // 🔹 Dossier de destination
                String userDir = System.getProperty("user.home") + "/SportifyImages";
                File directory = new File(userDir);
                if (!directory.exists()) {
                    directory.mkdirs(); // Créer le dossier s'il n'existe pas
                }

                // 🔹 Copier l'image sélectionnée
                File destinationFile = new File(directory, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // 🔹 Stocker le chemin absolu de l'image
                imagePath = destinationFile.getAbsolutePath();
                System.out.println("📸 Image copiée dans : " + imagePath);

                // 🔹 Afficher l'image si imageView n'est pas null
                if (imageView != null) {
                    Image image = new Image(destinationFile.toURI().toString());
                    imageView.setImage(image);
                    System.out.println("✅ Image affichée.");
                } else {
                    System.out.println("⚠️ Erreur : imageView est null !");
                }

            } catch (IOException e) {
                System.out.println("❌ Erreur lors de la copie de l'image : " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Aucune image sélectionnée.");
        }
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        String contenu = contenuField.getText();
        String description = descriptionField.getText();

        if (contenu == null || contenu.trim().isEmpty()) {
            System.out.println("⚠️ Erreur : Le champ 'Contenu' est vide !");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            System.out.println("⚠️ Erreur : Le champ 'Description' est vide !");
            return;
        }

        if (imagePath == null || imagePath.isEmpty()) {
            System.out.println("⚠️ Aucune image sélectionnée ! Utilisation de l'image par défaut.");
            String defaultImagePath = System.getProperty("user.home") + "/SportifyImages/default.jpg";
            File defaultImage = new File(defaultImagePath);
            if (defaultImage.exists()) {
                imagePath = defaultImagePath;
            } else {
                System.out.println("⚠️ Attention : L'image par défaut n'existe pas.");
                imagePath = ""; // Laisser vide si aucune image n'est disponible
            }
        }

        publicationService publicationService = new publicationService();

        try {
            if (publicationCourante == null) {
                // 🔹 Création d'une nouvelle publication
                publication newPub = new publication(contenu, description, imagePath);
                publicationService.create(newPub);
                System.out.println("✅ Nouvelle publication ajoutée !");
            } else {
                // 🔹 Modification d'une publication existante
                publicationCourante.setContenu(contenu);
                publicationCourante.setDescription(description);
                publicationCourante.setImagePath(imagePath); // ✅ Correction ici

                publicationService.update(publicationCourante);
                System.out.println("✅ Publication modifiée !");
            }

            // 🔹 Retourner à la page d'affichage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affiche.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'opération : " + e.getMessage());
        }
    }

    @FXML
    public void setPublication(publication pub) {
        this.publicationCourante = pub;

        if (pub != null) {
            contenuField.setText(pub.getContenu());
            descriptionField.setText(pub.getDescription());

            if (pub.getImagePath() != null && !pub.getImagePath().isEmpty()) {
                imagePath = pub.getImagePath();
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image);
                } else {
                    System.out.println("⚠️ L'image de la publication n'existe pas.");
                }
            } else {
                System.out.println("⚠️ Aucune image enregistrée pour cette publication.");
            }
        }
    }

    @FXML
    private void openChatbotWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chatbot.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Chatbot");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
