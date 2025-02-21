package entites;

public class Sessions {
  private static User currentUser;

  // Récupérer l'utilisateur actuellement connecté
  public static User getCurrentUser() {
    return currentUser;
  }

  // Définir l'utilisateur actuellement connecté
  public static void setCurrentUser(User user) {
    currentUser = user;
  }

  // Vérifier si un utilisateur est connecté
  public static boolean isLoggedIn() {
    return currentUser != null;
  }

  // Déconnexion de l'utilisateur
  public static void logout() {
    currentUser = null;
  }
}
