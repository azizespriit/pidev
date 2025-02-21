package services;

import models.commentaire;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class commentaireService implements Crud<commentaire> {
    private Connection cnx;
    private PreparedStatement pst;
    private ResultSet rs;

    public commentaireService() {
        cnx = MyDb.getInstance().getConn();
    }

    @Override
    public void create(commentaire com) throws Exception {
        String requete = "INSERT INTO commentaire (Id_pub, contenu, date_commentaire) VALUES (?, ?, ?, NOW())";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, com.getId_pub());
            pst.setString(3, com.getContenu());

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Commentaire ajouté avec succès !");
            } else {
                throw new Exception("Erreur : Le commentaire n'a pas été ajouté !");
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de l'insertion du commentaire : " + e.getMessage(), e);
        }
    }

    @Override
    public void update(commentaire com) throws Exception {
        String requete = "UPDATE commentaire SET contenu = ?, date_commentaire = NOW() WHERE id = ?";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setString(1, com.getContenu());
            pst.setInt(2, com.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated == 0) {
                throw new Exception("Aucune mise à jour effectuée, ID introuvable.");
            }
            System.out.println("Commentaire mis à jour avec succès !");
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la mise à jour du commentaire : " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(commentaire com) throws Exception {
        String requete = "DELETE FROM commentaire WHERE id = ?";
        try {
            pst = cnx.prepareStatement(requete);
            pst.setInt(1, com.getId());

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted == 0) {
                throw new Exception("Aucun commentaire trouvé avec cet ID.");
            }
            System.out.println("Commentaire supprimé avec succès !");
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la suppression du commentaire : " + e.getMessage(), e);
        }
    }

    @Override
    public List<commentaire> getAll() throws Exception {
        List<commentaire> list = new ArrayList<>();
        String requete = "SELECT id, Id_pub, contenu, date_commentaire FROM commentaire";
        try {
            pst = cnx.prepareStatement(requete);
            rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new commentaire(
                        rs.getInt("id"),
                        rs.getInt("Id_pub"),
                        rs.getString("contenu"),
                        rs.getTimestamp("date_commentaire").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération des commentaires : " + e.getMessage(), e);
        }
        return list;
    }
    public List<commentaire> getCommentairesByPublication(int idPub) throws Exception {
        List<commentaire> list = new ArrayList<>();
        String requete = "SELECT id, Id_pub, contenu, date_commentaire FROM commentaire WHERE Id_pub = ?";

        try (PreparedStatement pst = cnx.prepareStatement(requete)) {
            pst.setInt(1, idPub);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new commentaire(
                            rs.getInt("id"),
                            rs.getInt("Id_pub"),
                            rs.getString("contenu"),
                            rs.getTimestamp("date_commentaire").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération des commentaires : " + e.getMessage(), e);
        }
        return list;
    }


}
