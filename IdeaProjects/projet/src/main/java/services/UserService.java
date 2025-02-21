package services;

import entites.User;
import java.sql.SQLException;
import java.util.Optional;

public interface UserService {

  // Méthode pour enregistrer un utilisateur
  int register(User user) throws SQLException;
  Optional<User> login(String email, String password);
  boolean isAccountLocked(String email);
  void increaseFailedAttempts(String email);
  void resetFailedAttempts(String email);
  void lockAccount(String email);

}
