package Controllers;

import models.Produit;
import services.ProduitService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DataSource;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ProduitController {
    @FXML
    private ImageView imgProduit;

    @FXML
    private ListView<Produit> listViewProduits;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrix;

    @FXML
    private TextField txtStock;
    private ObservableList<Produit> produitsList = FXCollections.observableArrayList();

    Connection conn = DataSource.getInstance().getConnection();

    ProduitService produitService = new ProduitService(conn);


    @FXML
    void ajouterProduit(ActionEvent event) {
        try {
            Produit produit = new Produit(0, txtNom.getText(), Double.parseDouble(txtPrix.getText()), "path/to/image.png", txtDescription.getText(), Integer.parseInt(txtStock.getText()));
            produitService.ajouterProduit(produit);
            chargerProduits();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void genererQRCode(ActionEvent event) {
        Produit produit = listViewProduits.getSelectionModel().getSelectedItem();
        if (produit != null) {
            try {
                // Chemin du dossier qr_codes en dehors du .jar
                File qrCodeDir = new File(System.getProperty("user.dir") + "/qr_codes/");

                if (!qrCodeDir.exists() && !qrCodeDir.mkdirs()) {
                    System.out.println("Impossible de créer le dossier 'qr_codes'.");
                    return;
                }

                String chemin = qrCodeDir.getAbsolutePath() + "/" + produit.getNom() + ".png";
                produitService.genererQRCode(produit, chemin);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "QR Code généré avec succès !");
                alert.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    void modifierProduit(ActionEvent event) {
        Produit produit = listViewProduits.getSelectionModel().getSelectedItem();
        if (produit != null) {
            try {
                produit.setNom(txtNom.getText());
                produit.setPrix(Double.parseDouble(txtPrix.getText()));
                produit.setStock(Integer.parseInt(txtStock.getText()));
                produit.setDescription(txtDescription.getText());
                produitService.modifierProduit(produit);
                chargerProduits();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void supprimerProduit(ActionEvent event) {
        Produit produit = listViewProduits.getSelectionModel().getSelectedItem();
        if (produit != null) {
            try {
                produitService.supprimerProduit(produit.getId());
                chargerProduits();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void initialize() {
        chargerProduits();
        listViewProduits.setOnMouseClicked(event -> afficherDetails());
    }

    private void chargerProduits() {
        try {
            List<Produit> produits = produitService.getProduits();
            produitsList.setAll(produits);
            listViewProduits.setItems(produitsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherDetails() {
        Produit produit = listViewProduits.getSelectionModel().getSelectedItem();
        if (produit != null) {
            txtNom.setText(produit.getNom());
            txtPrix.setText(String.valueOf(produit.getPrix()));
            txtStock.setText(String.valueOf(produit.getStock()));
            txtDescription.setText(produit.getDescription());
            imgProduit.setImage(new Image(new File(produit.getPhoto()).toURI().toString()));
        }
    }
}
