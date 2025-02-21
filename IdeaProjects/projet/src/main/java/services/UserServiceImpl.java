package services;

import entites.User;
import org.mindrot.jbcrypt.BCrypt;
import utils.dataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserServiceImpl implements UserService {

  private Connection connection;

  public UserServiceImpl() {
    this.connection = dataSource.getInstance().getConnection();
  }

  @Override
  public int register(User user) throws SQLException {
    String sql = "INSERT INTO user (cin, lastName, firstName, gender, phone, roles, email, password) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, user.getCin());
      pstmt.setString(2, user.getLastName());
      pstmt.setString(3, user.getFirstName());
      pstmt.setString(4, user.getGender());
      pstmt.setString(5, user.getPhone());
      pstmt.setString(6, user.getRoles());
      pstmt.setString(7, user.getEmail());

      String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
      System.out.println("Mot de passe haché : " + hashedPassword); // Vérification
      pstmt.setString(8, hashedPassword);

      pstmt.executeUpdate();
      System.out.println("Utilisateur enregistré avec succès !");
    } catch (SQLException e) {
      System.err.println("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
      throw e;
    }
    return 0;
  }

////////////////////////// *****************************///////////////////
@Override
public Optional<User> login(String email, String password) {
  if (isAccountLocked(email)) {
    System.out.println("Compte bloqué. Réessayez plus tard.");
    return Optional.empty();
  }

  String query = "SELECT * FROM user WHERE email = ?";
  try (PreparedStatement ps = connection.prepareStatement(query)) {
    ps.setString(1, email);
    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
      String storedHashedPassword = rs.getString("password");

      // Vérification du mot de passe avec BCrypt
      if (BCrypt.checkpw(password, storedHashedPassword)) {
        resetFailedAttempts(email);
        return Optional.of(new User(
          rs.getInt("id_user"),
          rs.getString("cin"),
          rs.getString("lastName"),
          rs.getString("firstName"),
          rs.getString("gender"),
          rs.getString("phone"),
          rs.getString("roles"),
          rs.getString("email"),
          rs.getString("password"),
          rs.getBoolean("isLocked"),
          rs.getInt("failedAttempts"),
          rs.getTimestamp("lockoutTime") != null ? rs.getTimestamp("lockoutTime").toLocalDateTime() : null
        ));
      } else {
        increaseFailedAttempts(email);
        return Optional.empty();
      }
    }
  } catch (Exception e) {
    e.printStackTrace();
  }
  return Optional.empty();
}

  @Override
  public boolean isAccountLocked(String email) {
    String query = "SELECT isLocked, lockoutTime FROM user WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, email);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        boolean isLocked = rs.getBoolean("isLocked");
        LocalDateTime lockoutTime = rs.getTimestamp("lockoutTime") != null
          ? rs.getTimestamp("lockoutTime").toLocalDateTime()
          : null;

        if (isLocked && lockoutTime != null) {
          if (lockoutTime.plusMinutes(15).isBefore(LocalDateTime.now())) {
            unlockAccount(email);
            return false;
          }
          return true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  private void unlockAccount(String email) {
    String query = "UPDATE user SET isLocked = false, failedAttempts = 0, lockoutTime = NULL WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, email);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void increaseFailedAttempts(String email) {
    String query = "UPDATE user SET failedAttempts = failedAttempts + 1 WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, email);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }

    String checkAttempts = "SELECT failedAttempts FROM user WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(checkAttempts)) {
      ps.setString(1, email);
      ResultSet rs = ps.executeQuery();
      if (rs.next() && rs.getInt("failedAttempts") >= 3) {
        lockAccount(email);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void resetFailedAttempts(String email) {
    String query = "UPDATE user SET failedAttempts = 0 WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, email);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void lockAccount(String email) {
    String query = "UPDATE user SET isLocked = true, lockoutTime = ? WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
      ps.setString(2, email);
      ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public Optional<User> getUserByEmail(String email) {
    String query = "SELECT * FROM user WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
      ps.setString(1, email);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        return Optional.of(new User(
          rs.getInt("id_user"),
          rs.getString("cin"),
          rs.getString("lastName"),
          rs.getString("firstName"),
          rs.getString("gender"),
          rs.getString("phone"),
          rs.getString("roles"),
          rs.getString("email"),
          rs.getString("password"),
          rs.getBoolean("isLocked"),
          rs.getInt("failedAttempts"),
          rs.getTimestamp("lockoutTime") != null ? rs.getTimestamp("lockoutTime").toLocalDateTime() : null
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
