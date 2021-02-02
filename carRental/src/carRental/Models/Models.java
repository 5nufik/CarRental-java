package carRental.Models;

public class Models {
    String id, mark, model, price;

    public Models(String id, String mark, String model, String price) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }
}
