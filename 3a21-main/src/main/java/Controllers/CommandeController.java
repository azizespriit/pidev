package Controllers;

import models.Commande;
import models.Panier;
import services.CommandeService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import utils.DataSource;


public class CommandeController {

    @FXML
    private ListView<Commande> listViewCommandes;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLocalisation;

    private ObservableList<Commande> commandesList = FXCollections.observableArrayList();

    Connection conn = DataSource.getInstance().getConnection();

    CommandeService commandeService = new CommandeService(conn);

    @FXML
    public void initialize() {
        chargerCommandes();
    }

    private void chargerCommandes() {
        try {
            List<Commande> commandes = commandeService.getCommandes(); // Utilisation de getCommandes()

            if (commandes != null) {
                commandesList.setAll(commandes);
                listViewCommandes.setItems(commandesList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afficherErreur("Erreur lors du chargement des commandes.");
        }
    }


    @FXML
    void validerCommande() {
        try {
            if (txtEmail.getText().isEmpty() || txtLocalisation.getText().isEmpty()) {
                afficherErreur("Veuillez remplir tous les champs !");
                return;
            }

            Commande commande = new Commande(0, new Panier(1), txtEmail.getText(), txtLocalisation.getText());
            commandeService.ajouterCommande(commande);

            chargerCommandes(); // Mise à jour de la liste après ajout

            afficherMessage("Commande validée et email envoyé !");
        } catch (SQLException e) {
            e.printStackTrace();
            afficherErreur("Erreur lors de la validation de la commande.");
        }
    }


    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }


    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.show();
    }
}
