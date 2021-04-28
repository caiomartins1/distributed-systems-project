package pt.ubi.di.model;


import java.io.Serializable;
import java.util.UUID;

public class Part implements Serializable {
    String id;
    String type;
    Float buyPrice;
    Float sellPrice;
    int minStock;
    int stock;
    String[] items;

    public Part(String type, Float buyPrice, Float sellPrice, int minStock) {
        UUID id = UUID.randomUUID();
        this.id = id.toString();
        this.type = type;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.minStock = minStock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}



