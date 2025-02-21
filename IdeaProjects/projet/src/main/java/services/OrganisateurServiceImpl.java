package services;

import entites.Organisateur;
import entites.User;
import org.mindrot.jbcrypt.BCrypt;
import utils.dataSource;
import java.sql.*;
import java.util.Optional;

public class OrganisateurServiceImpl implements OrganisateurService {

  private Connection connection;

  public OrganisateurServiceImpl() {
    this.connection = dataSource.getInstance().getConnection();
  }
  @Override

  public void registerOrganisateur(Organisateur organisateur) throws SQLException {
    String sql = "INSERT INTO organisateur (user_id, yearsOfExperience, numberOfEventsOrganized) VALUES (?, ?, ?)";

    try {
      connection.setAutoCommit(false);

      // Enregistrer l'utilisateur dans la table 'user'
      String userSql = "INSERT INTO user (cin, lastName, firstName, gender, phone, roles, email, password) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      try (PreparedStatement pstmtUser = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
        pstmtUser.setString(1, organisateur.getCin());
        pstmtUser.setString(2, organisateur.getLastName());
        pstmtUser.setString(3, organisateur.getFirstName());
        pstmtUser.setString(4, organisateur.getGender());
        pstmtUser.setString(5, organisateur.getPhone());
        pstmtUser.setString(6, organisateur.getRoles());
        pstmtUser.setString(7, organisateur.getEmail());

        // Hasher le mot de passe
        String hashedPassword = BCrypt.hashpw(organisateur.getPassword(), BCrypt.gensalt(12));
        System.out.println("Mot de passe haché (organisateur) : " + hashedPassword);
        pstmtUser.setString(8, hashedPassword);

        pstmtUser.executeUpdate();

        ResultSet rs = pstmtUser.getGeneratedKeys();
        int generatedUserId = 0;
        if (rs.next()) {
          generatedUserId = rs.getInt(1);
        }

        // Enregistrer l'organisateur avec l'ID utilisateur
        try (PreparedStatement pstmtOrganisateur = connection.prepareStatement(sql)) {
          pstmtOrganisateur.setInt(1, generatedUserId);
          pstmtOrganisateur.setInt(2, organisateur.getYearsOfExperience());
          pstmtOrganisateur.setInt(3, organisateur.getNumberOfEventsOrganized());

          pstmtOrganisateur.executeUpdate();
          connection.commit();
          System.out.println("Organisateur enregistré avec succès !");
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      System.err.println("Erreur lors de l'enregistrement de l'organisateur : " + e.getMessage());
      throw e;
    } finally {
      connection.setAutoCommit(true);
    }
  }


  @Override
  public int register(User user) throws SQLException {
    return 0;
  }

  @Override
  public Optional<User> login(String email, String password) {
    return Optional.empty();
  }

  @Override
  public boolean isAccountLocked(String email) {
    return false;
  }

  @Override
  public void increaseFailedAttempts(String email) {

  }

  @Override
  public void resetFailedAttempts(String email) {

  }

  @Override
  public void lockAccount(String email) {

  }
  public Optional<Organisateur> getOrganisateurByUserId(int userId) {
    String query = "SELECT * FROM organisateur WHERE user_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setInt(1, userId);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return Optional.of(new Organisateur(
          userId,
          rs.getInt("yearsOfExperience"),
          rs.getInt("numberOfEventsOrganized")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
