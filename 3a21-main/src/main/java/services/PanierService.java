package services;

import models.Panier;
import models.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierService {
    private Connection conn;

    public PanierService(Connection conn) {
        this.conn = conn;
    }

    // Ajouter un panier
    public int ajouterPanier() throws SQLException {
        String query = "INSERT INTO Panier (prix_total) VALUES (0)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    // Lire tous les paniers
    public List<Panier> getPaniers() throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String query = "SELECT * FROM Panier";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                paniers.add(new Panier(rs.getInt("id")));
            }
        }
        return paniers;
    }

    // Ajouter un produit à un panier
    public void ajouterProduitAuPanier(int panierId, Produit produit) throws SQLException {
        String query = "INSERT INTO Panier_Produit (panier_id, produit_id, quantité) VALUES (?, ?, 1)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, panierId);
            stmt.setInt(2, produit.getId());
            stmt.executeUpdate();
        }
    }

    // Supprimer un panier
    public void supprimerPanier(int id) throws SQLException {
        String query = "DELETE FROM Panier WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
