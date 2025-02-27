package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.commentaire; // Assurez-vous que votre modèle de commentaire est bien importé

public class CommentaireItemC {



    @FXML
    private Label lblContenu;

    public void setCommentaire(commentaire commentaire) {
        lblContenu.setText(commentaire.getContenu());
    }
}