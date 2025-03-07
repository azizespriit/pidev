package services;

import models.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.nio.file.*;

public class ProduitService {
    private Connection conn;

    public ProduitService(Connection conn) {
        this.conn = conn;
    }

    // Ajouter un produit
    public void ajouterProduit(Produit produit) throws SQLException {
        String query = "INSERT INTO Produit (nom, prix, photo, description, stock) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, produit.getNom());
            stmt.setDouble(2, produit.getPrix());
            stmt.setString(3, produit.getPhoto());
            stmt.setString(4, produit.getDescription());
            stmt.setInt(5, produit.getStock());
            stmt.executeUpdate();
        }
    }

    // Lire tous les produits
    public List<Produit> getProduits() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM Produit";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                produits.add(new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getString("photo"),
                        rs.getString("description"),
                        rs.getInt("stock")
                ));
            }
        }
        return produits;
    }

    // Supprimer un produit
    public void supprimerProduit(int id) throws SQLException {
        String query = "DELETE FROM Produit WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Mettre à jour un produit
    public void modifierProduit(Produit produit) throws SQLException {
        String query = "UPDATE Produit SET nom=?, prix=?, photo=?, description=?, stock=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, produit.getNom());
            stmt.setDouble(2, produit.getPrix());
            stmt.setString(3, produit.getPhoto());
            stmt.setString(4, produit.getDescription());
            stmt.setInt(5, produit.getStock());
            stmt.setInt(6, produit.getId());
            stmt.executeUpdate();
        }
    }

    // Générer un QR Code pour un produit
    public void genererQRCode(Produit produit, String path) throws Exception {
        String data = "Produit: " + produit.getNom() + ", Prix: " + produit.getPrix();
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
        Path filePath = Paths.get(path);
        MatrixToImageWriter.writeToPath(matrix, "PNG", filePath);
    }
}
