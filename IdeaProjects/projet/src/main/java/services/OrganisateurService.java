package services;

import entites.Organisateur;
import java.sql.SQLException;

public interface OrganisateurService extends UserService {
  void registerOrganisateur(Organisateur organisateur) throws SQLException;


}
