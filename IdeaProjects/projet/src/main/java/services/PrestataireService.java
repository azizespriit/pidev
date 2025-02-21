package services;

import entites.Prestataire;
import entites.User;

import java.sql.SQLException;

public interface PrestataireService extends UserService {
  void registerPrestataire(Prestataire prestataire) throws SQLException;


  int register(User user) throws SQLException;

}
