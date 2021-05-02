package pt.ubi.di.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Part -> it represents a type of part, with buy/sell price, minStock and a list of the items in stock
 */
public class Part implements Serializable {

    String id;
    String type;
    Float buyPrice;
    Float sellPrice;
    int minStock;
    int stock;
    Date createdAt;
    private ArrayList<Item> items;


    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Part(String type, Float buyPrice, Float sellPrice, int minStock) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.minStock = minStock;
        items = new ArrayList<>();
        this.createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ArrayList<Item> removeStock(int quantity) {
        ArrayList<Item> tempItems = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            stock = stock - 1;
            tempItems.add(items.remove(0));
        }
        return tempItems;
    }

    public Item removeStock() {
        stock = stock - 1;
        return items.remove(0);
    }

    public void addStock(int quantity) {
        for (int i = 0; i < quantity; ++i) {
            stock = stock + 1;
            items.add(new Item(UUID.randomUUID().toString()));
        }
    }

    public Item addStock() {
        Item i = new Item(UUID.randomUUID().toString());
        stock = stock + 1;
        items.add(i);
        return i;
    }

    public int getStock() {
        return this.stock;
    }

    public void alterBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public float getBuyPrice() {
        return this.buyPrice;
    }

    public void alterSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public float getSellPrice() {
        return this.sellPrice;
    }

    public int getMinStock() {
        return this.minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return "id=" + id + " type=" + type + " buyPrice=" + buyPrice + " sellPrice=" + sellPrice + " minStock=" + minStock + " stock=" + stock + " items=" + items;
    }

    public String toStringClean() {
        return "type=" + type + " Price=" + sellPrice + " stock=" + stock;
    }

    public String beautifyOutput() {
        return "ID -> " + this.id +
                "\nType -> " + this.type +
                "\nDate Added: " + this.getCreatedAt().toString() +
                "\nBuy Price -> " + this.buyPrice +
                "\nSell Price -> " + this.sellPrice +
                "\nMinimum Stock -> " + this.minStock +
                "\nItems in stock -> " + this.items.size() + "\n";

    }

}
