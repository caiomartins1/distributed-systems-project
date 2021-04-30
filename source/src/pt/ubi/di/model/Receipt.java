package pt.ubi.di.model;

import java.util.ArrayList;

public class Receipt {

    String id;
    String type;
    float price;
    float costItem;
    int quantity;
    ArrayList<String> items;
    //TODO add date

    public Receipt(String id, String type, float price, float costItem, int quantity, ArrayList<String> items) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.costItem = costItem;
        this.quantity = quantity;
        this.items = items;//TODO: make sure its getting copied right
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

    public String toStringReceipt(boolean printItem) {//TODO add id? maybe when its simplified more
        if(printItem)
            return "type=" + type + " costPerItem=" + costItem + " quantity=" + quantity + " costPerItem*quantity=priceTotal=" + price + "\nitems=" + items;
        else
            return "type=" + type + " costPerItem=" + costItem + " quantity=" + quantity + " costPerItem*quantity=priceTotal=" + price;
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