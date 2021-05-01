package pt.ubi.di.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Receipt implements Serializable {

    String id;
    String type;
    float price;
    float costItem;
    int quantity;
    ArrayList<Item> items;


    public Receipt(String id, String type, float price, float costItem, int quantity, ArrayList<Item> items) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.costItem = costItem;
        this.quantity = quantity;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public float getCostItem() {
        return costItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getItemsQuantity() {
        return items.size();
    }

    public String toStringReceipt(boolean printItem) {
        if(printItem)
            return "type=" + type + " costPerItem=" + costItem + " quantity=" + quantity + " price=" + price + "\nitems=" + items;
        else
            return "type=" + type + " costPerItem=" + costItem + " quantity=" + quantity + " price=" + price;
    }

    public String toString(boolean printItems) {
        if(printItems)
            return "id=" + id + " type=" + type + " costPerItem=" + costItem + " quantity=" + quantity + " priceTotal=" + price + "\nitems=" + items;
        else
            return "id=" + id + " type=" + type + " costPerItem=" + costItem + " quantity=" + quantity + " priceTotal=" + price;
    }

    @Override
    public String toString() {
        return "id=" + id + " type=" + type + " costPerItem=" + costItem + " quantity=" + quantity + " priceTotal=" + price + "\nitems=" + items;
    }
}