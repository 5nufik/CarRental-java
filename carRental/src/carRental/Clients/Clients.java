package carRental.Clients;

public class Clients {
    private String fio, passport, address, driverLicense, phone;

    public Clients(String fio, String passport, String address, String driverLicense, String phone) {
        this.fio = fio;
        this.passport = passport;
        this.address = address;
        this.driverLicense = driverLicense;
        this.phone = phone;
    }

    public String getFio() {
        return fio;
    }

    public String getPassport() {
        return passport;
    }

    public String getAddress() {
        return address;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public String getPhone() {
        return phone;
    }
}
