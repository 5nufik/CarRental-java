package carRental.Cars;

import carRental.Clients.Clients;
import carRental.DBHandler;
import carRental.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class carsController implements Initializable {

    @FXML
    private TableView<Cars> table;

    @FXML
    private TableColumn<Cars, String> col_mark;

    @FXML
    private TableColumn<Cars, String> col_model;

    @FXML
    private TableColumn<Cars, String> col_type;

    @FXML
    private TableColumn<Cars, String> col_color;

    @FXML
    private TableColumn<Cars, String> col_year;

    @FXML
    private TableColumn<Cars, String> col_power;

    @FXML
    private TableColumn<Cars, String> col_plate;

    @FXML
    private TableColumn<Cars, String> col_transmission;

    @FXML
    private TableColumn<Cars, String> col_vin;

    @FXML
    private TableColumn<Cars, String> col_insurance;

    @FXML
    private TableColumn<Cars, String> col_status;

    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void NewCar(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Новая машина");
        stage.setResizable(false);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("newCar.fxml"))));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../icon.png" )));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.showAndWait();

        try {
            refreshTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Cars selectedCar = table.getSelectionModel().getSelectedItem();

            PreparedStatement prst = DBHandler.getConnection().prepareStatement("select cars.carVIN, cars.carStatus from cars where (carStatus = 0 or carStatus = 2) and carVIN = ?");

            prst.setString(1, selectedCar.getVin());

            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                try {
                    prst = DBHandler.getConnection().prepareStatement("update cars set carStatus = 3 where carVIN = ? and (carStatus = 0 or carStatus = 2)");

                    prst.setString(1, selectedCar.getVin());

                    prst.executeUpdate();

                    refreshTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else
                alert("Нельзя отправить эту машину в архив!");
        } else
            alert("Сначала выберите машину!");
    }

    @FXML
    void repair(ActionEvent event) throws SQLException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Cars selectedCar = table.getSelectionModel().getSelectedItem();

            PreparedStatement prst = DBHandler.getConnection().prepareStatement("select cars.carVIN, cars.carStatus from cars where carStatus = 0 and carVIN = ?");

            prst.setString(1, selectedCar.getVin());

            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                prst = DBHandler.getConnection().prepareStatement("update cars set carStatus = 2 where carVIN = ?");

                prst.setString(1, selectedCar.getVin());

                prst.executeUpdate();

                refreshTable();
            } else
                alert("Нельзя отправить эту машину в ремонт!");
        } else
            alert("Сначала выберите машину!");

    }

    @FXML
    void work(ActionEvent event) throws SQLException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Cars selectedCar = table.getSelectionModel().getSelectedItem();

            PreparedStatement prst = DBHandler.getConnection().prepareStatement("select cars.carVIN, cars.carStatus from cars where carStatus = 2 and carVIN = ?");

            prst.setString(1, selectedCar.getVin());

            ResultSet rs = prst.executeQuery();

            if (rs.next()) {

                prst = DBHandler.getConnection().prepareStatement("update cars set carStatus = 0 where carVIN = ?");

                prst.setString(1, selectedCar.getVin());

                prst.executeUpdate();

                refreshTable();
            } else
                alert("Нельзя принять эту машину из ремонта!");
        } else
            alert("Сначала выберите машину!");
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

    void refreshTable() throws SQLException {
        ObservableList<Cars> oblist = FXCollections.observableArrayList();

        ResultSet rs = DBHandler.getConnection().createStatement().executeQuery("select models.mark, models.model, types.typeName, " +
                "cars.carColor, transmission.transmissionName, cars.carYear, cars.carPower, cars.carPlate, " +
                "cars.carVIN, cars.carInsurance, statuses.statusName from cars " +
                "join kurs.models on models.modelID = cars.modelID " +
                "join kurs.statuses on statuses.statusID = cars.carStatus " +
                "join kurs.types on types.typeID = cars.carType " +
                "join kurs.transmission on transmission.transmissionID = cars.carTransmission " +
                "where cars.carStatus != 3;");

        while (rs.next()) {
            oblist.add(new Cars(rs.getString("mark"),
                    rs.getString("model"), rs.getString("typeName"),
                    rs.getString("carColor"), rs.getString("carYear"),
                    rs.getString("transmissionName"), rs.getString("carPower"),
                    rs.getString("carPlate"), rs.getString("carVIN"),
                    rs.getString("carInsurance"), rs.getString("statusName")));
        }

        table.setItems(oblist);

        col_mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_color.setCellValueFactory(new PropertyValueFactory<>("color"));
        col_transmission.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_power.setCellValueFactory(new PropertyValueFactory<>("power"));
        col_plate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        col_vin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        col_insurance.setCellValueFactory(new PropertyValueFactory<>("insurance"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

public static void alert(String string) {
Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Ошибка!");
alert.setHeaderText(string);
alert.showAndWait();
}
}
