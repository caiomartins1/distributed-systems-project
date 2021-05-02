package pt.ubi.di.model;

import java.util.ArrayList;

/** Registers the part and quantity desired to buy
 * Keep it in a list to form a "buy/sell order" meaning having
 * a list of all the parts and quantities desired
 */
public class Order {

    Part part;
    int quantity;

    public Order(Part part, int quantity) {
        this.part = part;
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

    @Override
    public String toString() {
        return "part=" + part.toString() + "--->quantity= " + quantity;
    }
}