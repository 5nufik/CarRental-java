package carRental.Cars;

import carRental.DBHandler;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class newCarColtroller implements Initializable {

    @FXML
    private Label label;

    @FXML
    private ComboBox<String> comboMark;
    ArrayList<String> markID = new ArrayList<>();

    @FXML
    private ComboBox<String> comboType;
    ArrayList<String> typeID = new ArrayList<>();

    @FXML
    private ComboBox<String> comboTransmission;
    ArrayList<String> transmissionID = new ArrayList<>();

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtPower;

    @FXML
    private TextField txtPlate;

    @FXML
    private TextField txtVIN;

    @FXML
    private TextField txtInsurance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> markList = FXCollections.observableArrayList();
        ObservableList<String> typeList = FXCollections.observableArrayList();
        ObservableList<String> transmissionList = FXCollections.observableArrayList();

        try {
            ResultSet rs = DBHandler.getConnection().createStatement().executeQuery("select modelID, mark, model, modelPrice from models");

            while (rs.next()) {
                markID.add(rs.getString("modelID"));
                String model = rs.getString("mark") + " " + rs.getString("model") + " " + rs.getString("modelPrice") + " руб/день";
                markList.add(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        comboMark.setItems(markList);

        try {
            ResultSet rs = DBHandler.getConnection().createStatement().executeQuery("select * from types");

            while (rs.next()) {

                typeID.add(rs.getString("typeID"));
                typeList.add(rs.getString("typeName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        comboType.setItems(typeList);

        try {
            ResultSet rs = DBHandler.getConnection().createStatement().executeQuery("select * from transmission");

            while (rs.next()) {

                transmissionID.add(rs.getString("transmissionID"));
                transmissionList.add(rs.getString("transmissionName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        comboTransmission.setItems(transmissionList);
    }

    @FXML
    public void add(ActionEvent actionEvent) {
        if(isInputValid()) {
            insertCar(markID.get(comboMark.getSelectionModel().getSelectedIndex()), typeID.get(comboType.getSelectionModel().getSelectedIndex()),
                    txtColor.getText(), txtYear.getText(),transmissionID.get(comboTransmission.getSelectionModel().getSelectedIndex()), txtPower.getText(), txtPlate.getText(), txtVIN.getText(), txtInsurance.getText());
            close(actionEvent);
        }
    }

    @FXML
    public void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clear(MouseEvent event) {
        label.requestFocus();
    }

    public void insertCar(String modelID, String type, String color, String year, String transmission, String power, String plate, String vin, String insurance) {
        try {
            PreparedStatement prst = DBHandler.getConnection().prepareStatement(
                    "INSERT INTO cars ( modelID, carType, carColor, carYear, carTransmission, carPower, carPlate, carVIN, carInsurance, carStatus )" + "VALUES(?,?,?,?,?,?,?,?,?,0)");

            prst.setString(1, modelID);
            prst.setString(2, type);
            prst.setString(3, color);
            prst.setString(4, year);
            prst.setString(5, transmission);
            prst.setString(6, power);
            prst.setString(7, plate);
            prst.setString(8, vin);
            prst.setString(9, insurance);

            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (comboMark.getSelectionModel().getSelectedIndex() == -1)
            errorMessage += "Не выбрана модель\n";

        if (comboType.getSelectionModel().getSelectedIndex() == -1)
            errorMessage += "Не выбран тип ТС\n";

        if (comboTransmission.getSelectionModel().getSelectedIndex() == -1)
            errorMessage += "Не выбран тип коробки передач\n";

        if (txtColor.getText() == null || txtColor.getText().length() == 0)
            errorMessage += "Не заполнен цвет\n";

        if (txtYear.getText() == null || txtYear.getText().length() == 0)
            errorMessage += "Не заполнен год выпуска\n";
        else if (!isNumeric(txtYear.getText()) || !(Integer.parseInt(txtYear.getText()) >= 1855 && Integer.parseInt(txtYear.getText()) <= Calendar.getInstance().get(Calendar.YEAR)))
            errorMessage += "Некорректный ввод года выпуска\n";

        if (txtPower.getText() == null || txtPower.getText().length() == 0)
            errorMessage += "Не заполнена мощность\n";
        else if (!isNumeric(txtPower.getText()) || !(Integer.parseInt(txtPower.getText()) <= 1500))
            errorMessage += "Некорректный ввод мощности\n";

        if (txtPlate.getText() == null || txtPlate.getText().length() == 0)
            errorMessage += "Не заполнен госномер\n";
        else if (txtPlate.getText().length() != 9)
            errorMessage += "Некорректный ввод госномера\n";

        if (txtVIN.getText() == null || txtVIN.getText().length() == 0)
            errorMessage += "Не заполнен VIN номер\n";
        else if (txtVIN.getText().length() != 17)
            errorMessage += "Некорректный ввод VIN номера\n";

        if (txtInsurance.getText() == null || txtInsurance.getText().length() == 0)
            errorMessage += "Не заполнен страховой полис\n";
        else if (txtInsurance.getText().length() != 10)
            errorMessage += "Некорректный ввод страхового полиса\n";

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Заполните корректно все поля!");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+?");
    }
}
