package models;

import java.security.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class publication {
    private int id;
    private String imagePath;  // ✅ Changement de imageUrl à imagePath pour correspondre au contrôleur
    private String contenu;
    private String description;
    private LocalDateTime date_pub;
    private List<String> commentaires;

    // 🔹 Constructeur avec initialisation des commentaires pour éviter NullPointerException
    public publication(String contenu, String description, String imagePath) {
        this.contenu = contenu;
        this.description = description;
        this.imagePath = imagePath;
        this.commentaires = new ArrayList<>();
        this.date_pub = LocalDateTime.now();
    // ✅ Initialisation de la liste pour éviter les erreurs
    }

    public  String getdate_pub() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (date_pub != null) {
            return date_pub.format(formatter); // (✔) Directement formater LocalDateTime
        }

        return "Date invalide";
        }


    // 🔹 Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImagePath() { return imagePath; }  // ✅ Changement du nom du getter
    public void setImagePath(String imagePath) { this.imagePath = imagePath; } // ✅ Setter corrigé

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(LocalDateTime date_pub) {
        this.date_pub = date_pub;
    }

    public List<String> getCommentaires() { return commentaires; }
    public void setCommentaires(List<String> commentaires) {
        if (commentaires != null) {
            this.commentaires = commentaires;
        }
    }

    // 🔹 Méthode pour ajouter un commentaire
    public void addComment(String commentaire) {
        if (commentaires == null) {
            commentaires = new ArrayList<>();
        }

        if (commentaire != null && !commentaire.trim().isEmpty()) {
            commentaires.add(commentaire);
            System.out.println(" Commentaire ajouté : " + commentaire);
        } else {
            System.out.println(" Erreur : Le commentaire ne peut pas être vide.");
        }
    }

    // 🔹 Méthode pour modifier un commentaire existant
    public void modifyComment(int index, String newComment) {
        if (commentaires == null || commentaires.isEmpty()) {
            System.out.println(" Erreur : Aucun commentaire à modifier.");
            return;
        }

        if (index >= 0 && index < commentaires.size() && newComment != null && !newComment.trim().isEmpty()) {
            commentaires.set(index, newComment);
            System.out.println("Commentaire modifié : " + newComment);
        } else {
            System.out.println(" Erreur : Indice invalide ou commentaire vide.");
        }
    }

    // 🔹 Méthode pour supprimer un commentaire
    public void deleteComment(int index) {
        if (commentaires == null || commentaires.isEmpty()) {
            System.out.println(" Erreur : Aucun commentaire à supprimer.");
            return;
        }

        if (index >= 0 && index < commentaires.size()) {
            String removedComment = commentaires.remove(index);
            System.out.println(" Commentaire supprimé : " + removedComment);
        } else {
            System.out.println(" Erreur : Indice invalide pour supprimer un commentaire.");
        }
    }
}
