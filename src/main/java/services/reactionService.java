package services;

import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reactionService {
    private Connection cnx;

    public reactionService() {
        this.cnx = MyDb.getInstance().getConn();
    }

    // Vérifie si l'utilisateur a déjà une réaction sur une publication
    private String getUserReaction(int Id_user, int Id_pub) {
        try {
            String query = "SELECT type FROM reactions WHERE Id_user = ? AND Id_pub= ?";
            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setInt(1, Id_user );
            stmt.setInt(2, Id_pub);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Ajoute ou met à jour une réaction
    public void ajouterReaction(int Id_user, int Id_pub, String type) {
            String currentReaction = getUserReaction( Id_user, Id_pub);

        try {
            if (currentReaction == null) { // Si l'utilisateur n'a pas encore réagi
                String insertQuery = "INSERT INTO reactions (Id_user, Id_pub, type) VALUES (?, ?, ?)";
                PreparedStatement stmt = cnx.prepareStatement(insertQuery);
                stmt.setInt(1, Id_user);
                stmt.setInt(2, Id_pub);
                stmt.setString(3, type);
                stmt.executeUpdate();
            } else if (!currentReaction.equals(type)) { // S'il change de type (like → dislike ou dislike → like)
                String updateQuery = "UPDATE reactions SET type = ? WHERE Id_user = ? AND Id_pub = ?";
                PreparedStatement stmt = cnx.prepareStatement(updateQuery);
                stmt.setString(1, type);
                stmt.setInt(2, Id_user);
                stmt.setInt(3, Id_pub);
                stmt.executeUpdate();
            } else { // Si l'utilisateur clique à nouveau sur le même bouton (annulation de la réaction)
                String deleteQuery = "DELETE FROM reactions WHEREId_user = ? AND Id_pub = ?";
                PreparedStatement stmt = cnx.prepareStatement(deleteQuery);
                stmt.setInt(1, Id_user);
                stmt.setInt(2, Id_pub);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Compter le nombre de réactions d'un type donné
    public int countReactions(int Id_pub, String type) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) AS count FROM reactions WHERE Id_pub = ? AND type = ?";
            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setInt(1, Id_pub);
            stmt.setString(2, type);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
