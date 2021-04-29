package pt.ubi.di.model;

import java.util.ArrayList;

public class Order {

    Part part;
    int quantity;

    public Order(Part part, int quantity) {
        this.part = part;//TODO ?? ref or copy what should it be
        this.quantity = quantity;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}