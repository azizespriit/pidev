package models;

import java.time.LocalDateTime;
import java.util.Collection;

public class commentaire{
    private int id;
    private int Id_pub;
    private String contenu;
    private LocalDateTime datee;;

    public commentaire() {
    }

    public commentaire(int id, int Id_pub, String contenu, LocalDateTime datee) {
        this.id = id;
        this.Id_pub = Id_pub;
        this.contenu = contenu;
        this.datee = datee;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pub() {
        return Id_pub;
    }

    public void setId_pub(int id_pub) {
        Id_pub = id_pub;
    }


    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDate_commentaire() {
        return datee;
    }

    public void setDate_commentaire(LocalDateTime date_commentaire) {
        this.datee = date_commentaire;
    }

    @Override
    public String toString() {
        return "commentaire{" +
                "id=" + id +
                ", Id_pub=" + Id_pub +
                ", contenu='" + contenu + '\'' +
                ", date_commentaire=" + datee +
                '}';
    }
}


