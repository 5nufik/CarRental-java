package carRental.Clients;

import carRental.DBHandler;
import carRental.Main;
import carRental.Models.Models;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class clientsController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TableView<Clients> table;

    @FXML
    private TableColumn<Clients, String> col_fio;

    @FXML
    private TableColumn<Clients, String> col_passport;

    @FXML
    private TableColumn<Clients, String> col_address;

    @FXML
    private TableColumn<Clients, String> col_driverLicense;

    @FXML
    private TableColumn<Clients, String> col_phone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void refreshTable() throws SQLException {
        ObservableList<Clients> oblist = FXCollections.observableArrayList();

        ResultSet rs = DBHandler.getConnection().createStatement().executeQuery("select * from clients");

        while (rs.next()) {
            oblist.add(new Clients(rs.getString("fio"), rs.getString("passport"), rs.getString("address"), rs.getString("driverLicense"), rs.getString("phone")));
        }

        table.setItems(oblist);

        col_fio.setCellValueFactory(new PropertyValueFactory<>("fio"));
        col_passport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_driverLicense.setCellValueFactory(new PropertyValueFactory<>("driverLicense"));
        col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    @FXML
    void clear(MouseEvent event) {
        table.getSelectionModel().clearSelection();
        label.requestFocus();
    }

    @FXML
    void back(ActionEvent event) {
        Main.goMain();
    }
}
