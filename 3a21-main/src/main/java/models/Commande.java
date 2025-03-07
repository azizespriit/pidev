package models;

import java.time.LocalDateTime;

public class Commande {
    private int id;
    private Panier panier;
    private String email;
    private LocalDateTime dateCommande;
    private String localisation;

    public Commande(int id, Panier panier, String email, String localisation) {
        this.id = id;
        this.panier = panier;
        this.email = email;
        this.dateCommande = LocalDateTime.now();
        this.localisation = localisation;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }
}
