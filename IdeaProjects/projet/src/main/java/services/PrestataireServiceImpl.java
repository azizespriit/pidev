package services;

import entites.Prestataire;
import entites.User;
import org.mindrot.jbcrypt.BCrypt;
import utils.dataSource;

import java.sql.*;
import java.util.Optional;

public class PrestataireServiceImpl implements PrestataireService {
  private Connection connection;

  public PrestataireServiceImpl() {
    this.connection = dataSource.getInstance().getConnection();
  }
  @Override
  public void registerPrestataire(Prestataire prestataire) throws SQLException {
    String userSql = "INSERT INTO user (cin, lastName, firstName, gender, phone, roles, email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String prestataireSql = "INSERT INTO prestataire (user_id, speciality, experience) VALUES (?, ?, ?)";

    try {
      connection.setAutoCommit(false);

      try (PreparedStatement pstmtUser = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
        pstmtUser.setString(1, prestataire.getCin());
        pstmtUser.setString(2, prestataire.getLastName());
        pstmtUser.setString(3, prestataire.getFirstName());
        pstmtUser.setString(4, prestataire.getGender());
        pstmtUser.setString(5, prestataire.getPhone());
        pstmtUser.setString(6, prestataire.getRoles());
        pstmtUser.setString(7, prestataire.getEmail());

        // Hasher le mot de passe
        String hashedPassword = BCrypt.hashpw(prestataire.getPassword(), BCrypt.gensalt(12));
        System.out.println("Mot de passe haché (prestataire) : " + hashedPassword);
        pstmtUser.setString(8, hashedPassword);

        pstmtUser.executeUpdate();

        ResultSet rs = pstmtUser.getGeneratedKeys();
        int generatedUserId = 0;
        if (rs.next()) {
          generatedUserId = rs.getInt(1);
        }

        try (PreparedStatement pstmtPrestataire = connection.prepareStatement(prestataireSql)) {
          pstmtPrestataire.setInt(1, generatedUserId);
          pstmtPrestataire.setString(2, prestataire.getSpeciality());
          pstmtPrestataire.setString(3, prestataire.getExperience());

          pstmtPrestataire.executeUpdate();
          connection.commit();
          System.out.println("Prestataire enregistré avec succès !");
        }
      } catch (SQLException e) {
        connection.rollback();
        throw e;
      }
    } catch (SQLException e) {
      System.err.println("Erreur lors de l'enregistrement du prestataire : " + e.getMessage());
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
  public Optional<Prestataire> getPrestataireByUserId(int userId) {
    String query = "SELECT * FROM prestataire WHERE user_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setInt(1, userId);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return Optional.of(new Prestataire(
          userId,
          rs.getString("speciality"),
          rs.getString("experience")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
