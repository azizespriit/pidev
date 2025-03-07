package services;

import models.Commande;
import models.Panier;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CommandeService {
    private Connection conn;

    // Constructeur : Vérifie que la connexion est valide
    public CommandeService(Connection conn) {
        if (conn == null) {
            throw new IllegalArgumentException("La connexion à la base de données est null !");
        }
        this.conn = conn;
    }

    // Ajouter une commande
    public void ajouterCommande(Commande commande) throws SQLException {
        String query = "INSERT INTO commande (id_panier, email, date_commande, localisation) VALUES (?, ?, NOW(), ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Associer l'ID du panier à la commande
            stmt.setInt(1, commande.getPanier().getId());
            stmt.setString(2, commande.getEmail());
            stmt.setString(3, commande.getLocalisation());

            // Exécuter l'insertion de la commande
            stmt.executeUpdate();

            // Récupérer l'ID généré pour la commande
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    commande.setId(generatedKeys.getInt(1)); // Assigner l'ID généré à la commande
                }
            }
        }

        // Envoyer un email de confirmation après l'ajout de la commande
        envoyerEmailConfirmation(commande);
    }


    // Récupérer toutes les commandes
    public List<Commande> getCommandes() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String query = "SELECT c.id, c.id_panier, c.email, c.date_commande, c.localisation "
                + "FROM commande c "
                + "JOIN panier p ON c.id_panier = p.id";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Charger le panier à partir des informations récupérées dans la requête
                Panier panier = new Panier(
                        rs.getInt("id_panier")

                );

                // Créer la commande avec le panier associé
                Commande commande = new Commande(
                        rs.getInt("id"),
                        panier, // Panier associé
                        rs.getString("email"),
                        rs.getString("localisation")
                );

                commandes.add(commande);
            }
        }
        return commandes;
    }


    // Envoyer un email de confirmation
    private void envoyerEmailConfirmation(Commande commande) {
        final String username = "haythemabdellaoui007@gmail.com";  // 🔴 Remplace par un vrai email
        final String password = "owpf wjpj msns suzy";  // 🔴 Remplace par un vrai mot de passe

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(commande.getEmail()));
            message.setSubject("Confirmation de commande");
            message.setText("Votre commande #" + commande.getId() + " a été enregistrée avec succès.");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
