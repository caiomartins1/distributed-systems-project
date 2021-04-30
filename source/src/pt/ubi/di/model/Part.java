package pt.ubi.di.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Part implements Serializable {

    String id;
    String type;
    Float buyPrice;
    Float sellPrice;
    int minStock;
    int stock;

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    private ArrayList<String> items;

    public Part(String type, Float buyPrice, Float sellPrice, int minStock) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.minStock = minStock;
        items = new ArrayList<>();
    }

    public ArrayList<String> removeStock(int quantity) {
        ArrayList<String> tempItems = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            stock = stock - 1;
            tempItems.add(items.remove(0));
        }
        return tempItems;
    }

    public String removeStock() {
        stock = stock - 1;
        return items.remove(0);
    }

    public void addStock(int quantity) {
        for (int i = 0; i < quantity; ++i) {
            stock = stock + 1;
            items.add(UUID.randomUUID().toString());
        }
    }

    public String addStock() {
            String id = UUID.randomUUID().toString();
            stock = stock + 1;
            items.add(id);
        return id;
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
        return "id=" +id+ " type=" +type+ " buyPrice=" +buyPrice+ " sellPrice=" +sellPrice+ " minStock=" + minStock + " stock=" + stock + " items=" + items;
    }
    public String toStringClean() {
        return "type=" +type+" Price=" +sellPrice+" stock=" + stock;
    }

    public String beautifyOutput() {
        return "ID -> " + this.id +
                "\nType -> " + this.type +
                "\nBuy Price -> " + this.buyPrice +
                "\nSell Price -> " + this.sellPrice +
                "\nMinimum Stock -> " + this.minStock +
                "\nItems in stock -> " + this.items.size() + "\n";

    }

}
