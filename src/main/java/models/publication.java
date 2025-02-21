package models;

import java.util.Date;
import java.util.List;

public class publication {
    private int id;
    private String imageUrl;
    private String contenu;
    private String description;
    private Date date_pub;
    private List<String> commentaires;


    // Constructeur avec tous les paramètres
    public publication(String contenu, String description, String imageUrl) {
        this.contenu = contenu;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDate_pub() { return date_pub; }
    public void setDate_pub(Date date_pub) { this.date_pub = date_pub; }

    public List<String> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<String> commentaires) {
        this.commentaires = commentaires;
    }

    // Méthode pour ajouter un commentaire
    public void addComment(String commentaire) {
        if (commentaire != null && !commentaire.trim().isEmpty()) {
            commentaires.add(commentaire);
            System.out.println(" Commentaire ajouté : " + commentaire);
        } else {
            System.out.println(" Erreur : Le commentaire ne peut pas être vide.");
        }
    }


    // Méthode pour modifier un commentaire existant
    public void modifyComment(int index, String newComment) {
        if (index >= 0 && index < commentaires.size() && newComment != null && !newComment.trim().isEmpty()) {
            commentaires.set(index, newComment);
            System.out.println(" Commentaire modifié : " + newComment);
        } else {
            System.out.println(" Erreur : Indice invalide ou commentaire vide.");
        }
    }


    public void deleteComment(int index) {
        if (index >= 0 && index < commentaires.size()) {
            String removedComment = commentaires.remove(index);
            System.out.println(" Commentaire supprimé : " + removedComment);
        } else {
            System.out.println(" Erreur : Indice invalide pour supprimer un commentaire.");
        }
    }
}
