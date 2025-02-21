package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterUserController {

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    void Ajouter(ActionEvent event) throws Exception {
        UserService sc = new UserService();
        User u1 = new User(Integer.parseInt(txtAge.getText()),txtNom.getText(),txtPrenom.getText());
        sc.create(u1);

    }
    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
        Parent root = loader.load();
        txtAge.getScene().setRoot(root);

    }

}