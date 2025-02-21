package entites;


import java.time.LocalDateTime;

public class Prestataire extends User {
  private int idPrestataire;
  private String speciality;
  private String experience;

  // Constructeur complet avec tous les attributs
  public Prestataire(int idUser, String cin, String lastName, String firstName, String gender, String phone, String roles,
                     String email, String password, Boolean isLocked, Integer failedAttempts, LocalDateTime lockoutTime,
                     int idPrestataire, String speciality, String experience) {
    super(idUser, cin, lastName, firstName, gender, phone, roles, email, password, isLocked, failedAttempts, lockoutTime);
    this.idPrestataire = idPrestataire;
    this.speciality = speciality;
    this.experience = experience;
  }

  // Constructeur sans idPrestataire (pour un nouveau prestataire)
  public Prestataire(String cin, String lastName, String firstName, String gender, String phone, String roles,
                     String email, String password, Boolean isLocked, Integer failedAttempts, LocalDateTime lockoutTime,
                     String speciality, String experience) {
    super(cin, lastName, firstName, gender, phone, roles, email, password, isLocked, failedAttempts, lockoutTime);
    this.speciality = speciality;
    this.experience = experience;
  }

  // Constructeur sans gestion du verrouillage
  public Prestataire(String cin, String lastName, String firstName, String gender, String phone, String roles,
                     String email, String password, String speciality, String experience) {
    super(cin, lastName, firstName, gender, phone, roles, email, password);
    this.speciality = speciality;
    this.experience = experience;
  }

  public Prestataire(int userId, String speciality, String experience) {
  }

  public int getIdPrestataire() {
    return idPrestataire;
  }

  public void setIdPrestataire(int idPrestataire) {
    this.idPrestataire = idPrestataire;
  }

  public String getSpeciality() {
    return speciality;
  }

  public void setSpeciality(String speciality) {
    this.speciality = speciality;
  }

  public String getExperience() {
    return experience;
  }

  public void setExperience(String experience) {
    this.experience = experience;
  }
}
