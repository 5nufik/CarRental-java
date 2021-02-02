package carRental.Cars;

public class Cars {
    String mark, model, type, color, year, transmission, power, plate, vin, insurance, status, price;

    public Cars(String mark, String model, String type, String color, String year, String transmission, String power, String plate, String vin, String insurance, String status) {
        this.mark = mark;
        this.model = model;
        this.type = type;
        this.color = color;
        this.transmission = transmission;
        this.year = year;
        this.power = power;
        this.plate = plate;
        this.vin = vin;
        this.insurance = insurance;
        this.status = status;
    }

    public Cars(String mark, String model, String color, String type, String transmission, String power, String year, String plate, String vin, String price) {
        this.mark = mark;
        this.model = model;
        this.type = type;
        this.color = color;
        this.transmission = transmission;
        this.year = year;
        this.power = power;
        this.plate = plate;;
        this.vin = vin;
        this.price = price;
    }

    public Cars(String mark, String model, String color, String type, String transmission, String power, String year, String plate, String vin) {
        this.mark = mark;
        this.model = model;
        this.type = type;
        this.color = color;
        this.transmission = transmission;
        this.year = year;
        this.power = power;
        this.plate = plate;;
        this.vin = vin;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getYear() {
        return year;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getPower() {
        return power;
    }

    public String getPlate() {
        return plate;
    }

    public String getVin() {
        return vin;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getStatus() {
        return status;
    }

    public String getPrice() {
        return price;
    }
}
