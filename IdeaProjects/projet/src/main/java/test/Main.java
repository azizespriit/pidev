package test;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {


  @Override
  public void start(Stage primaryStage) throws Exception {
    // Charger le fichier FXML
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
    Parent root = loader.load();

    // Créer la scène
    Scene scene = new Scene(root, 1000, 600);

    // Charger et appliquer le fichier CSS
    scene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());

    // Définir le titre et la scène de la fenêtre
    primaryStage.setTitle("Formulaire d'inscription");
    primaryStage.setScene(scene);
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
