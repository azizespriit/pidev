package Controllers;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.publication;

import java.io.File;

public class publicationCell extends ListCell<publication> {
    private final VBox content;
    private final ImageView imageView;
    private final Text contenuText;
    private final Text descriptionText;

    // 📌 Définir le chemin absolu de l’image par défaut
    private static final String DEFAULT_IMAGE_PATH = System.getProperty("user.home") + "/SportifyImages/default.jpg";

    public publicationCell() {
        imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        contenuText = new Text();
        contenuText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        descriptionText = new Text();
        descriptionText.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        content = new VBox(10, imageView, contenuText, descriptionText);
        content.setPadding(new Insets(10));
    }

    @Override
    protected void updateItem(publication pub, boolean empty) {
        super.updateItem(pub, empty);

        if (empty || pub == null) {
            setGraphic(null);
        } else {
            String imagePath = pub.getImageUrl();
            Image image = null;

            System.out.println(" Chargement de l'image : " + imagePath); // Debugging

            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    System.out.println(" Image trouvée : " + file.toURI().toString());
                    image = new Image(file.toURI().toString());
                } else {
                    System.out.println(" Image non trouvée, chargement de l'image par défaut.");
                    File defaultImageFile = new File(DEFAULT_IMAGE_PATH);
                    if (defaultImageFile.exists()) {
                        image = new Image(defaultImageFile.toURI().toString());
                    } else {
                        System.out.println(" Image par défaut introuvable !");
                    }
                }
            } else {
                System.out.println(" Aucun chemin d’image fourni. Utilisation de l’image par défaut.");
                File defaultImageFile = new File(DEFAULT_IMAGE_PATH);
                if (defaultImageFile.exists()) {
                    image = new Image(defaultImageFile.toURI().toString());
                } else {
                    System.out.println(" Image par défaut introuvable !");
                }
            }

            imageView.setImage(image);
            contenuText.setText("📝 " + pub.getContenu());
            descriptionText.setText("📜 " + pub.getDescription());

            setGraphic(content);
        }
    }
}
