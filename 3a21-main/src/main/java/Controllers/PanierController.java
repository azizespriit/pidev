package Controllers;

import models.Panier;
import models.Produit;
import services.PanierService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javafx.scene.control.ListView;
import utils.DataSource;

public class PanierController {

    @FXML
    private ListView<Panier> listViewPaniers;


    private ObservableList<Panier> paniersList = FXCollections.observableArrayList();

    Connection conn = DataSource.getInstance().getConnection();
    PanierService panierService = new PanierService(conn);

    @FXML
    public void initialize() {
        chargerPaniers();
    }

    private void chargerPaniers() {
        try {
            List<Panier> paniers = panierService.getPaniers();
            paniersList.setAll(paniers);
            listViewPaniers.setItems(paniersList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterPanier() {
        try {
            panierService.ajouterPanier();
            chargerPaniers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerPanier() {
        Panier panier = listViewPaniers.getSelectionModel().getSelectedItem();
        if (panier != null) {
            try {
                panierService.supprimerPanier(panier.getId());
                chargerPaniers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
