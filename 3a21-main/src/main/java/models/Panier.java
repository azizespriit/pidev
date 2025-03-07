package models;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    private int id;
    private double prixTotal;
    private List<Produit> produits;

    public Panier(int id) {
        this.id = id;
        this.prixTotal = 0;
        this.produits = new ArrayList<>();
    }

    // Méthodes
    public void ajouterProduit(Produit produit) {
        produits.add(produit);
        calculerPrixTotal();
    }

    public void supprimerProduit(Produit produit) {
        produits.remove(produit);
        calculerPrixTotal();
    }

    public void calculerPrixTotal() {
        prixTotal = 0;
        for (Produit produit : produits) {
            prixTotal += produit.getPrix();
        }
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getPrixTotal() { return prixTotal; }

    public List<Produit> getProduits() { return produits; }
}
