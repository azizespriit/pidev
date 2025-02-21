package entites;

import java.time.LocalDateTime;

public class User {
  private int idUser;
  private String cin;
  private String lastName;
  private String firstName;
  private String gender;
  private String phone;

  private String roles;
  private String email;
  private String password;
  private Boolean isLocked;
  private Integer failedAttempts;
  private LocalDateTime lockoutTime;


  // Constructeur complet
  public User(int idUser, String cin, String lastName, String firstName, String gender, String phone, String roles,
              String email, String password, Boolean isLocked, Integer failedAttempts, LocalDateTime lockoutTime) {
    this.idUser = idUser;
    this.cin = cin;
    this.lastName = lastName;
    this.firstName = firstName;
    this.gender = gender;
    this.phone = phone;
    this.roles = roles;
    this.email = email;
    this.password = password;
    this.isLocked = isLocked;
    this.failedAttempts = failedAttempts;
    this.lockoutTime = lockoutTime;
  }

  // Constructeur sans idUser (cas d'un nouvel utilisateur)
  public User(String cin, String lastName, String firstName, String gender, String phone, String roles,
              String email, String password, Boolean isLocked, Integer failedAttempts, LocalDateTime lockoutTime) {
    this.cin = cin;
    this.lastName = lastName;
    this.firstName = firstName;
    this.gender = gender;
    this.phone = phone;
    this.roles = roles;
    this.email = email;
    this.password = password;
    this.isLocked = isLocked;
    this.failedAttempts = failedAttempts;
    this.lockoutTime = lockoutTime;
  }

  // Constructeur sans les champs de verrouillage (si on ne gère pas encore cette logique)
  public User(String cin, String lastName, String firstName, String gender, String phone, String roles, String email, String password) {
    this.cin = cin;
    this.lastName = lastName;
    this.firstName = firstName;
    this.gender = gender;
    this.phone = phone;
    this.roles = roles;
    this.email = email;
    this.password = password;
    this.isLocked = false;
    this.failedAttempts = 0;
    this.lockoutTime = null;
  }

  public User(int id, String cin, String lastName, String firstName, String gender, String phone, String roles, String email, String password) {
  }

  public User() {

  }


  public int getIdUser() {
    return idUser;
  }

  public void setIdUser(int idUser) {
    this.idUser = idUser;
  }

  public String getCin() {
    return cin;
  }

  public void setCin(String cin) {
    this.cin = cin;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }



  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getLocked() {
    return isLocked;
  }

  public void setLocked(Boolean locked) {
    isLocked = locked;
  }

  public Integer getFailedAttempts() {
    return failedAttempts;
  }

  public void setFailedAttempts(Integer failedAttempts) {
    this.failedAttempts = failedAttempts;
  }

  public LocalDateTime getLockoutTime() {
    return lockoutTime;
  }

  public void setLockoutTime(LocalDateTime lockoutTime) {
    this.lockoutTime = lockoutTime;
  }

  @Override
  public String toString() {
    return "User{" +
      "idUser=" + idUser +
      ", cin='" + cin + '\'' +
      ", lastName='" + lastName + '\'' +
      ", firstName='" + firstName + '\'' +
      ", gender='" + gender + '\'' +
      ", phone='" + phone + '\'' +
      ", roles='" + roles + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +

      '}';
  }
}
