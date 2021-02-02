package carRental;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainController {

    @FXML
    private Label label;

    @FXML
    void rentCar(ActionEvent event) throws IOException {
        Main.setScene("Доступные для сдачи автомобили", new Scene(FXMLLoader.load(getClass().getResource("rentCar/rentCar.fxml"))));
    }

    @FXML
    void takeCar(ActionEvent event) throws IOException {
        Main.setScene("Доступные для приёма автомобили", new Scene(FXMLLoader.load(getClass().getResource("takeCar/takeCar.fxml"))));
    }

    @FXML
    void showRentals(ActionEvent event) throws IOException {
        Main.setScene("Прокаты", new Scene(FXMLLoader.load(getClass().getResource("Rentals/rentals.fxml"))));
    }

    @FXML
    void showClients(ActionEvent event) throws IOException {
        Main.setScene("Клиенты", new Scene(FXMLLoader.load(getClass().getResource("Clients/clients.fxml"))));
    }

    @FXML
    void showCars(ActionEvent event) throws IOException {
        Main.setScene("Машины", new Scene(FXMLLoader.load(getClass().getResource("Cars/cars.fxml"))));
    }

    @FXML
    void showModels(ActionEvent event) throws IOException {
        Main.setScene("Модели машин", new Scene(FXMLLoader.load(getClass().getResource("Models/models.fxml"))));
    }

    @FXML
    void clear(MouseEvent event) {
        label.requestFocus();
    }
}