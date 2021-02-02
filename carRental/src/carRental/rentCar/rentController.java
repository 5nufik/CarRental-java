package carRental.rentCar;

import carRental.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class rentController {

    @FXML
    private Label label;

    @FXML
    private TextField txtCar;

    @FXML
    private TextField txtFIO;

    @FXML
    private TextField txtPassport;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtLicense;

    @FXML
    private TextField txtPhone;

    @FXML
    private DatePicker dateRental;

    @FXML
    private DatePicker dateReception;

    @FXML
    public void add(ActionEvent actionEvent) throws SQLException{
        if(isInputValid()) {
            insertClient(txtFIO.getText(), txtPassport.getText(), txtAddress.getText(), txtLicense.getText(), txtPhone.getText());

            String contract;

            while (true) {
                contract = generateContract();
                if (!(foundContract(contract))) {
                    break;
                }
            }

            Date rental = Date.valueOf(dateRental.getValue());
            Date reception = Date.valueOf(dateReception.getValue());
            long difference = reception.getTime() - rental.getTime();
            int days = (int) difference/(24*60*60*1000);

            double price = getPrice(txtCar.getText()) * days;

            insertRent(contract, txtPassport.getText(), txtCar.getText(), dateRental.getValue().toString(), dateReception.getValue().toString(), price);
            updateCarStatus(txtCar.getText());

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

    public void insertClient(String fio, String passport, String address, String license, String phone) {
        try {
            PreparedStatement prst = DBHandler.getConnection().prepareStatement(
                    "INSERT IGNORE INTO clients ( fio, passport, address, driverLicense, phone )" + "VALUES(?,?,?,?,?)");

            prst.setString(1, fio);
            prst.setString(2, passport);
            prst.setString(3, address);
            prst.setString(4, license);
            prst.setString(5, phone);

            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateContract(){
        final String string = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(10);
        for( int i = 0; i < 10; i++)
            sb.append(string.charAt(rnd.nextInt(string.length())));
        return sb.toString();
    }

    public boolean foundContract(String contract) throws SQLException {
        PreparedStatement prst = DBHandler.getConnection().prepareStatement("select * from rents where rentalContract = ?");

        prst.setString(1, contract);

        ResultSet rs = prst.executeQuery();

        if (rs.next()) {
            return true;
        }
        else
            return false;
    }

    private Double getPrice(String vin) throws SQLException {
        Double price;
        ResultSet rs = DBHandler.getConnection().createStatement().executeQuery("select models.modelPrice from cars " +
                "join kurs.models on cars.modelID = models.modelID where cars.carVIN = '" + vin + "';");
        while (rs.next()) {
            price = rs.getDouble("modelPrice");
            return price;
        }
        return 0.00;
    }

    public void insertRent(String contract, String client, String car, String rentalDate, String receptionDate, double price) {
        try {
            PreparedStatement prst = DBHandler.getConnection().prepareStatement(
                    "INSERT INTO rents ( rentalContract, client, car, rentalDate, receptionDate, totalPrice )" + "VALUES(?,?,?,?,?,?)");

            prst.setString(1, contract);
            prst.setString(2, client);
            prst.setString(3, car);
            prst.setString(4, rentalDate);
            prst.setString(5, receptionDate);
            prst.setDouble(6, price);

            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCarStatus(String vin) {
        try {
            PreparedStatement prst = DBHandler.getConnection().prepareStatement("update cars set carStatus = 1 where carVIN = ?");

            prst.setString(1, vin);

            prst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean isInputValid() throws SQLException {
        String errorMessage = "";

        if (txtFIO.getText() == null || txtFIO.getText().length() == 0)
            errorMessage += "Не заполено ФИО\n";

        if (txtPassport.getText() == null || txtPassport.getText().length() == 0)
            errorMessage += "Не заполнен паспорт\n";
        else if (txtPassport.getText().length() != 10 || !isNumeric(txtPassport.getText()))
            errorMessage += "Некорректный ввод паспорта\n";

        if (txtAddress.getText() == null || txtAddress.getText().length() == 0)
            errorMessage += "Не заполнен адрес проживания\n";

        if (txtLicense.getText() == null || txtLicense.getText().length() == 0)
            errorMessage += "Не заполнено водительское удостоверение\n";
        else if (txtLicense.getText().length() != 10 || !isNumeric(txtPassport.getText()))
            errorMessage += "Некорректный ввод водительского удостоверения\n";

        if (txtPhone.getText() == null || txtPhone.getText().length() == 0)
            errorMessage += "Не заполнен телефон\n";
        else if (txtPhone.getText().length() != 11 || !isNumeric(txtPhone.getText()))
            errorMessage += "Некорректный ввод телефона\n";

        if(txtCar.getText() == null || txtCar.getText().length() == 0)
            errorMessage += "Не заполнен VIN автомобиля\n";
        else if (txtCar.getText().length() != 17)
            errorMessage += "Некорректный ввод VIN номера\n";
        else if (!(foundCar(txtCar.getText())))
            errorMessage += "Введённая машина находится в работе, либо не существует.\n";

        if (dateRental.getValue() == null)
            errorMessage += "Не выбрана дата сдачи автомобиля\n";
        else if (dateReception.getValue() == null)
            errorMessage += "Не выбрана дата приёма автомобиля\n";
        else if (dateRental.getValue().equals(dateReception.getValue()) || !(dateRental.getValue().isBefore(dateReception.getValue())))
            errorMessage += "Даты выбраны некорректно\n";

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

    private boolean foundCar(String vin) throws SQLException {
        ResultSet rs = DBHandler.getConnection().createStatement().executeQuery("select carVIN, carStatus from cars where carVIN = '" + vin + "' and carStatus = 0;");
        while (rs.next()) {
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String str)
    {
        return str.matches("\\d+?");  //match a number.
    }
}
