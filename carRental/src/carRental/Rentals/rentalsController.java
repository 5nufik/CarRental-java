package carRental.Rentals;

import carRental.Cars.Cars;
import carRental.DBHandler;
import carRental.Main;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class rentalsController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TableView<Rentals> table;

    @FXML
    private TableColumn<Rentals, String> col_contract;

    @FXML
    private TableColumn<Rentals, String> col_mark;

    @FXML
    private TableColumn<Rentals, String> col_model;

    @FXML
    private TableColumn<Rentals, String> col_vin;

    @FXML
    private TableColumn<Rentals, String> col_fio;

    @FXML
    private TableColumn<Rentals, String> col_rentalDate;

    @FXML
    private TableColumn<Rentals, String> col_receptionDate;

    @FXML
    private TableColumn<Rentals, String> col_price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void refreshTable() throws SQLException {
        ObservableList<Rentals> oblist = FXCollections.observableArrayList();

        ResultSet rs = DBHandler.getConnection().createStatement().executeQuery(
                "SELECT rents.rentalContract, models.mark, models.model, rents.car, clients.fio, rents.rentalDate, rents.receptionDate, rents.totalPrice FROM kurs.rents " +
                    "join kurs.cars on rents.car = cars.carVIN " +
                    "join kurs.models on models.modelID = cars.modelID " +
                    "join kurs.clients on clients.passport = rents.client;");

        while (rs.next()) {
            oblist.add(new Rentals(rs.getString("rentalContract"), rs.getString("mark"),
                                   rs.getString("model"),rs.getString("car"),
                                   rs.getString("fio"),rs.getString("rentalDate"),
                                   rs.getString("receptionDate"),rs.getString("totalPrice")));
        }

        table.setItems(oblist);

        col_contract.setCellValueFactory(new PropertyValueFactory<>("contract"));
        col_mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        col_vin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        col_fio.setCellValueFactory(new PropertyValueFactory<>("fio"));
        col_rentalDate.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));
        col_receptionDate.setCellValueFactory(new PropertyValueFactory<>("receptionDate"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    void back(ActionEvent event) {
        Main.goMain();
    }

    @FXML
    void clear(MouseEvent event) {
        table.getSelectionModel().clearSelection();
        label.requestFocus();
    }
}
