package pt.ubi.di.model;

import java.util.ArrayList;

/**
 * class to purchase items(stock) for a specific part or a list of parts (each part with a specified amount of
 * stock to buy
 */
public class Purchase {

    ArrayList<AdvanceReceipt> purchaseHistory;
    ArrayList<Item> tempItems;
    ArrayList<Receipt> receipts;
    String whoFor;

    /**
     * Constructor for Purchase class
     * Initializes arrays
     */
    public Purchase() {
        purchaseHistory = new ArrayList<>(); // always persistent
        tempItems = new ArrayList<>(); // reset at every part order
        receipts = new ArrayList<>(); // reset at every set of orders
        whoFor = "BUYING(From supplier)";
    }

    public void setPurchaseHistory(ArrayList<AdvanceReceipt> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    /**
     * Creates a receipt for a single part type in a specified quantity
     * Adds to stock
     * @param quantity amount to buy
     * @param part     type to buy
     * @return receipt
     */
    private Receipt buySinglePart(int quantity, Part part) {
        float price = 0.00F;
        tempItems.clear();
        float buyPrice = part.getBuyPrice();
        for (int i = 0; i < quantity; i++) {
            price = price + buyPrice;
            tempItems.add(part.addStock());
        }
        return new Receipt(part.getID(), part.getType(), price, buyPrice, quantity, tempItems);
    }

    /**
     * Receives a list of orders, and order contains the piece to buy and its quantity
     * Adds to stock (from Suppliers)
     * @param orders list of orders(part,quantity)
     * @return advanceReceipt
     */
    public AdvanceReceipt buyOrder(ArrayList<Order> orders,String name) {
        receipts.clear();
        for (Order order : orders) {
            receipts.add(buySinglePart(order.getQuantity(), order.getPart()));
        }
        AdvanceReceipt advanceReceipt = new AdvanceReceipt(receipts, whoFor,name);
        purchaseHistory.add(advanceReceipt);
        return advanceReceipt;
    }

    /**
     * Process a single order, an order contains the piece to buy and its quantity
     * Adds to stock (from Suppliers)
     * @param order a part and its quantity
     * @return advanceReceipt
     */
    public AdvanceReceipt buySingleOrder(Order order, String name) {
        receipts.clear();
        receipts.add(buySinglePart(order.getQuantity(), order.getPart()));
        AdvanceReceipt advanceReceipt = new AdvanceReceipt(receipts,whoFor,name);
        purchaseHistory.add(advanceReceipt);
        return advanceReceipt;
    }

    public ArrayList<AdvanceReceipt> getPurchaseHistory() {
        return purchaseHistory;
    }

    public String getWhoFor() {
        return whoFor;
    }

    @Override
    public String toString() {
        return purchaseHistory.toString();
    }
}