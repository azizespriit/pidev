package entites;

import java.time.LocalDateTime;

public class Organisateur extends User {
  private int idOrganisateur;
  private int yearsOfExperience;
  private int numberOfEventsOrganized;

  // Constructeur complet avec tous les attributs
  public Organisateur(int idUser, String cin, String lastName, String firstName, String gender, String phone, String roles,
                      String email, String password, Boolean isLocked, Integer failedAttempts, LocalDateTime lockoutTime,
                      int idOrganisateur, int yearsOfExperience, int numberOfEventsOrganized) {
    super(idUser, cin, lastName, firstName, gender, phone, roles, email, password, isLocked, failedAttempts, lockoutTime);
    this.idOrganisateur = idOrganisateur;
    this.yearsOfExperience = yearsOfExperience;
    this.numberOfEventsOrganized = numberOfEventsOrganized;
  }

  // Constructeur sans idOrganisateur (pour un nouvel organisateur)
  public Organisateur(String cin, String lastName, String firstName, String gender, String phone, String roles,
                      String email, String password, Boolean isLocked, Integer failedAttempts, LocalDateTime lockoutTime,
                      int yearsOfExperience, int numberOfEventsOrganized) {
    super(cin, lastName, firstName, gender, phone, roles, email, password, isLocked, failedAttempts, lockoutTime);
    this.yearsOfExperience = yearsOfExperience;
    this.numberOfEventsOrganized = numberOfEventsOrganized;
  }

  // Constructeur sans gestion du verrouillage
  public Organisateur(String cin, String lastName, String firstName, String gender, String phone, String roles,
                      String email, String password, int yearsOfExperience, int numberOfEventsOrganized) {
    super(cin, lastName, firstName, gender, phone, roles, email, password);
    this.yearsOfExperience = yearsOfExperience;
    this.numberOfEventsOrganized = numberOfEventsOrganized;
  }

  public Organisateur(int idUser, int yearsOfExperience, int eventsOrganized) {
    super();
  }

  public int getIdOrganisateur() {
    return idOrganisateur;
  }

  public void setIdOrganisateur(int idOrganisateur) {
    this.idOrganisateur = idOrganisateur;
  }

  public int getYearsOfExperience() {
    return yearsOfExperience;
  }

  public void setYearsOfExperience(int yearsOfExperience) {
    this.yearsOfExperience = yearsOfExperience;
  }

  public int getNumberOfEventsOrganized() {
    return numberOfEventsOrganized;
  }

  public void setNumberOfEventsOrganized(int numberOfEventsOrganized) {
    this.numberOfEventsOrganized = numberOfEventsOrganized;
  }

  @Override
  public String toString() {
    return "Organisateur{" +
      "idOrganisateur=" + idOrganisateur +
      ", yearsOfExperience=" + yearsOfExperience +
      ", numberOfEventsOrganized=" + numberOfEventsOrganized +
      '}';
  }
}
