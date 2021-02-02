package carRental.Models;

import carRental.Cars.Cars;
import carRental.Clients.Clients;
import carRental.DBHandler;
import carRental.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class modelsController implements Initializable {

    @FXML
    private TableView<Models> table;

    @FXML
    private TableColumn<Models, String> col_mark;

    @FXML
    private TableColumn<Models, String> col_model;

    @FXML
    private TableColumn<Models, String>  col_price;

    @FXML
    private TextField txtMark;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtPrice;

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

    void refreshTable() throws SQLException {
        ObservableList<Models> oblist = FXCollections.observableArrayList();

        ResultSet rs = DBHandler.getConnection().createStatement().executeQuery("select * from models");

        while (rs.next()) {
            oblist.add(new Models(rs.getString("modelID"), rs.getString("mark"), rs.getString("model"), String.valueOf(rs.getDouble("modelPrice"))));
        }

        table.setItems(oblist);

        col_mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        col_price.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    void editPrice(TableColumn.CellEditEvent<Models, String> productStringCellEditEvent) throws SQLException {
        Models selectedModel = table.getSelectionModel().getSelectedItem();
        if (isPrice(productStringCellEditEvent.getNewValue())) {
            PreparedStatement prst = DBHandler.getConnection().prepareStatement("update models set modelPrice = ? where modelID = ?");

            prst.setString(1, productStringCellEditEvent.getNewValue());
            prst.setString(2, selectedModel.getId());

            prst.executeUpdate();
        }
        refreshTable();
    }

    @FXML
    void newModel(ActionEvent event) throws SQLException {
        if (isInputValid() && isPrice(txtPrice.getText())) {
            PreparedStatement prst = DBHandler.getConnection().prepareStatement("INSERT INTO models ( mark, model, modelPrice )" + "VALUES(?,?,?)");

            prst.setString(1, txtMark.getText());
            prst.setString(2, txtModel.getText());
            prst.setString(3, txtPrice.getText());

            prst.executeUpdate();

            txtMark.clear();
            txtModel.clear();
            txtPrice.clear();

            refreshTable();
        }
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        Models selectedModels = table.getSelectionModel().getSelectedItem();
        int selectedIndex = table.getSelectionModel().getSelectedIndex();

        if (selectedIndex != -1 && isAlreadyUse(selectedModels.getId())) {
            DBHandler.delete("models", "modelID", selectedModels.getId());
            table.getItems().remove(selectedIndex);
            table.getSelectionModel().clearSelection();
        }
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

    private boolean isInputValid() {
        String errorMessage = "";

        if (txtMark.getText() == null || txtMark.getText().length() == 0) {
            errorMessage += "Не заполнено поле марки!\n";
        }
        if (txtModel.getText() == null || txtModel.getText().length() == 0) {
            errorMessage += "Не заполнено поле модели!\n";
        }
        if (txtPrice.getText() == null || txtPrice.getText().length() == 0) {
            errorMessage += "Не заполнено поле стоимости проката!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Заполнены не все поля");
            alert.setHeaderText("Пожалуйста, заполните все поля.");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    public static boolean isPrice(String str) {
        if (!str.matches("\\d+(\\.\\d+)?")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Некорректный ввод стоимости.");
            alert.setHeaderText("Поле стоимости может состоять только из цифр!");
            alert.showAndWait();

            return false;
        } else
            return true;
    }

    public boolean isAlreadyUse(String id) throws SQLException {
        PreparedStatement prst = DBHandler.getConnection().prepareStatement("select * from cars where modelID = ?");

        prst.setString(1, id);

        ResultSet rs = prst.executeQuery();

        if (rs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Данная модель используется в таблице машин!");
            alert.showAndWait();

            return false;
        }
        else
            return true;
    }
}
