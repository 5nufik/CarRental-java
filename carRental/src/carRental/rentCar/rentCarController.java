package carRental.rentCar;

import carRental.Cars.Cars;
import carRental.DBHandler;
import carRental.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class rentCarController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TableView<Cars> table;

    @FXML
    private TableColumn<Cars, String> col_mark;

    @FXML
    private TableColumn<Cars, String> col_model;

    @FXML
    private TableColumn<Cars, String> col_color;

    @FXML
    private TableColumn<Cars, String> col_type;

    @FXML
    private TableColumn<Cars, String> col_transmission;

    @FXML
    private TableColumn<Cars, String> col_power;

    @FXML
    private TableColumn<Cars, String> col_year;

    @FXML
    private TableColumn<Cars, String> col_plate;

    @FXML
    private TableColumn<Cars, String> col_vin;

    @FXML
    private TableColumn<Cars, String> col_price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void rent(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Сдать автомобиль");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("rent.fxml"))));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../icon.png" )));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.showAndWait();

        try {
            refreshTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table.getSelectionModel().clearSelection();
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

    void refreshTable() throws SQLException {
        ObservableList<Cars> oblist = FXCollections.observableArrayList();

        ResultSet rs = DBHandler.getConnection().createStatement().executeQuery(
                "select models.mark, models.model, cars.carColor, types.typeName, " +
                        "transmission.transmissionName, cars.carPower, cars.carYear, cars.carPlate, cars.carVIN, models.modelPrice from cars " +
                        "join kurs.models on models.modelID = cars.modelID " +
                        "join kurs.statuses on statuses.statusID = cars.carStatus " +
                        "join kurs.types on types.typeID = cars.carType " +
                        "join kurs.transmission on transmission.transmissionID = cars.carTransmission " +
                        "where cars.carStatus = 0;");

        while (rs.next()) {
            oblist.add(new Cars(rs.getString("mark"), rs.getString("model"),
                    rs.getString("carColor"), rs.getString("typeName"),
                    rs.getString("transmissionName"), rs.getString("carPower"),
                    rs.getString("carYear"), rs.getString("carPlate"),
                    rs.getString("carVIN"), rs.getString("modelPrice")));
        }

        table.setItems(oblist);

        col_mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        col_color.setCellValueFactory(new PropertyValueFactory<>("color"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_transmission.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        col_power.setCellValueFactory(new PropertyValueFactory<>("power"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_plate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        col_vin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
