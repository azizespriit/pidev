package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AfficherUserController {

    @FXML
    private TableColumn<User, Integer> ageCol;

    @FXML
    private TableColumn<User, String> nomCol;

    @FXML
    private TableColumn<User, String> prenomCol;

    @FXML
    private TableView<User> tableview;
    @FXML
    void initialize() throws Exception {
        UserService sc = new UserService();
        System.out.println(sc.getAll());

        ObservableList<User> obs = FXCollections.observableList(sc.getAll());
        tableview.setItems(obs);
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        prenomCol.setCellValueFactory((new PropertyValueFactory<>("lastName")));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));


    }
}