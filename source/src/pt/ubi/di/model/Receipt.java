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

    public Receipt(String id, String type, float price, float costItem, int quantity, ArrayList<String> items){
        this.id=id;
        this.type=type;
        this.price=price;
        this.costItem=costItem;
        this.quantity=quantity;
        this.items = items;//TODO: make sure its getting copied right
    }

    public String getId(){return id;}

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

    @Override
    public String toString() {//TODO organize print better
        return "id="+id+" type="+type+" costItem="+costItem+" quantity="+quantity+" price="+price+"\nitems="+items;
    }
}