package services;

import models.publication;
import utils.MyDb;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class publicationService implements Crud<publication> {

    private final Connection cnx;

    public publicationService() {
        this.cnx = MyDb.getInstance().getConn();
    }

    @Override
    public void create(publication pub) throws Exception {
        String requete = "INSERT INTO publication (imageUrl, contenu, date_pub, description) VALUES (?, ?, NOW(), ?)";

        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setString(1, pub.getImageUrl());
            pst.setString(2, pub.getContenu());
            pst.setString(3, pub.getDescription());

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Publication ajoutée avec succès !");
            } else {
                System.out.println("️ Aucune ligne insérée.");
            }
        } catch (SQLException e) {
            System.err.println(" Erreur lors de l'insertion : " + e.getMessage());
            throw new Exception("Erreur lors de l'insertion", e);
        }
    }

    @Override
    public void update(publication pub) throws Exception {
        String requete = "UPDATE publication SET imageUrl = ?, contenu = ?, date_pub = NOW(), description = ? WHERE Id_pub = ?";

        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setString(1, pub.getImageUrl());
            pst.setString(2, pub.getContenu());
            pst.setString(3, pub.getDescription());
            pst.setInt(4, pub.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(" Publication mise à jour avec succès !");
            } else {
                System.out.println(" Aucune publication trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour : " + e.getMessage());
            throw new Exception("Erreur lors de la mise à jour", e);
        }
    }

    @Override
    public void delete(publication pub) throws Exception {
        String requete = "DELETE FROM publication WHERE Id_pub = ?";

        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, pub.getId());

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(" Publication supprimée avec succès !");
            } else {
                System.out.println(" Aucune publication trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println(" Erreur lors de la suppression : " + e.getMessage());
            throw new Exception("Erreur lors de la suppression", e);
        }
    }

    @Override
    public List<publication> getAll() throws Exception {
        List<publication> list = new ArrayList<>();
        String requete = "SELECT * FROM publication";

        try (PreparedStatement pst = cnx.prepareStatement(requete);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                publication pub = new publication(
                        rs.getString("contenu"),
                        rs.getString("description"),
                        rs.getString("imageUrl")
                );
                pub.setId(rs.getInt("Id_pub"));
                list.add(pub);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des publications : " + e.getMessage());
            throw new Exception("Erreur lors de la récupération des publications", e);
        }
        return list;
    }
}
