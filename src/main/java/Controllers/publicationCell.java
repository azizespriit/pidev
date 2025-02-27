package Controllers;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.publication;
import services.reactionService;

import java.io.File;

public class publicationCell extends ListCell<publication> {
    private final VBox content;
    private final ImageView imageView;
    private final Text contenuText;
    private final Text descriptionText;
    private final Label dateLabel; // ✅ Ajout du label pour la date

    private final Button btnLike;
    private final Label lblLikes;
    private final Button btnDislike;
    private final Label lblDislikes;

    private final reactionService reactionService = new reactionService();
    private final int currentUserId = 1; // Simule un utilisateur connecté

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

        dateLabel = new Label(); // ✅ Initialisation de la date
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: grey;");

        btnLike = new Button("👍");
        lblLikes = new Label("0");
        btnDislike = new Button("👎");
        lblDislikes = new Label("0");

        HBox reactionBox = new HBox(10, btnLike, lblLikes, btnDislike, lblDislikes);
        reactionBox.setPadding(new Insets(5, 0, 0, 0));

        content = new VBox(10, imageView, contenuText, descriptionText, dateLabel, reactionBox); // ✅ Ajout de la date
        content.setPadding(new Insets(10));

        btnLike.setOnAction(event -> handleReaction("like"));
        btnDislike.setOnAction(event -> handleReaction("dislike"));
    }

    @Override
    protected void updateItem(publication pub, boolean empty) {
        super.updateItem(pub, empty);

        if (empty || pub == null) {
            setGraphic(null);
        } else {
            String imagePath = pub.getImagePath();
            Image image = null;
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    image = new Image(file.toURI().toString());
                } else {
                    image = new Image(new File(DEFAULT_IMAGE_PATH).toURI().toString());
                }
            } else {
                image = new Image(new File(DEFAULT_IMAGE_PATH).toURI().toString());
            }
            imageView.setImage(image);

            contenuText.setText("📝 " + pub.getContenu());
            descriptionText.setText("📜 " + pub.getDescription());

            dateLabel.setText("📅 " + pub.getdate_pub()); // ✅ Mise à jour de la date affichée

            updateReactions(pub.getId());

            setGraphic(content);
        }
    }

    private void handleReaction(String type) {
        if (getItem() != null) {
            reactionService.ajouterReaction(currentUserId, getItem().getId(), type);
            updateReactions(getItem().getId());
        }
    }

    private void updateReactions(int publicationId) {
        int likes = reactionService.countReactions(publicationId, "like");
        int dislikes = reactionService.countReactions(publicationId, "dislike");

        lblLikes.setText(String.valueOf(likes));
        lblDislikes.setText(String.valueOf(dislikes));
    }
}
