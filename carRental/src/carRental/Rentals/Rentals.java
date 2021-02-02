package carRental.Rentals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Rentals {
    String contract, mark, model, vin, fio, rentalDate, receptionDate, price;
    LocalDate date;

    public Rentals(String contract, String mark, String model, String vin, String fio, String rentalDate, String receptionDate, String price) {
        this.contract = contract;
        this.mark = mark;
        this.model = model;
        this.vin = vin;
        this.fio = fio;

        date = LocalDate.parse(rentalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.rentalDate = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru")));

        date = LocalDate.parse(receptionDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.receptionDate = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru")));
        this.price = price;
    }

    public String getContract() {
        return contract;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public String getVin() {
        return vin;
    }

    public String getFio() {
        return fio;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public String getReceptionDate() {
        return receptionDate;
    }

    public String getPrice() {
        return price;
    }
}
