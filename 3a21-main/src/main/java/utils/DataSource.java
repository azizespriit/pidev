package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static final String URL = "jdbc:mysql://localhost:3306/rj25"; // Base de données "rj25"
    private static final String USER = "root"; // Remplacez par votre utilisateur MySQL
    private static final String PASSWORD = ""; // Remplacez par votre mot de passe MySQL

    private static DataSource instance; // Instance unique (Singleton)
    private Connection connection;

    /**
     * Constructeur privé pour empêcher l'instanciation multiple.
     */
    private DataSource() {
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Établir la connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connexion réussie à la base de données !");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    /**
     * Retourne l'instance unique de `DataSource`.
     *
     * @return L'instance unique de `DataSource`
     */
    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    /**
     * Retourne la connexion active à la base de données.
     *
     * @return Connection JDBC
     */
    public Connection getConnection() {
        return connection;
    }
}
