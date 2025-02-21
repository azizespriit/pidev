package Controllers;

import entites.EmailSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button sendLinkButton;

    @FXML
    private Button backButton;

    private EmailSender emailSender = new EmailSender(); // Initialize email sender

    @FXML
    private void handleSendResetLink() {
        String email = emailField.getText().trim();

        // ✅ Validate Email Format
        if (!isValidEmail(email)) {
            messageLabel.setText("Veuillez entrer un email valide.");
            messageLabel.setVisible(true);
            return;
        }

        // ✅ Prepare Email Content
        String subject = "Réinitialisation de votre mot de passe";
        String messageText = "Cliquez sur le lien suivant pour réinitialiser votre mot de passe : [lien]";

        // ✅ Send Email
        boolean sent = emailSender.sendEmail(email, subject, messageText);
        if (sent) {
            messageLabel.setText("Lien de réinitialisation envoyé avec succès !");
        } else {
            messageLabel.setText("Échec de l'envoi du lien de réinitialisation.");
        }
        messageLabel.setVisible(true);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w-]+\\.[a-zA-Z]{2,6}$");
    }
}
