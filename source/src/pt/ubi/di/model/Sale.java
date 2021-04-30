package pt.ubi.di.model;

import java.util.ArrayList;

public class Sale {

    ArrayList<AdvanceReceipt> sellHistory;
    ArrayList<String> tempItems;
    ArrayList<Receipt> receipts;

    /**
     * Constructor for Sale class
     * Initializes arrays
     */
    public Sale() {
        sellHistory = new ArrayList<>(); // always persistent
        tempItems = new ArrayList<>(); // reset at every part order
        receipts = new ArrayList<>(); // reset at every set of orders
    }

    /**
     * Creates a receipt for a single part type in a specified quantity
     * removes from stock
     * @param quantity amount to sell
     * @param part     type to sell
     * @return receipt
     */
    private Receipt sellingSinglePart(int quantity, Part part) {
        float price = 0.00F;
        tempItems.clear();
        float sellPrice = part.getSellPrice();
        for (int i = 0; i < quantity; i++) {
            price = price + sellPrice;
            tempItems.add(part.removeStock());
        }
        return new Receipt(part.getID(), part.getType(), price, sellPrice, quantity, tempItems);
    }

    /**
     * Receives a list of orders, and order contains the piece to sell and its quantity
     * removes from stock (from Store to clients)
     * @param orders list of orders(part,quantity)
     * @return advanceReceipt
     */
    public AdvanceReceipt sellOrder(ArrayList<Order> orders) {
        receipts.clear();
        for (Order order : orders) {
            receipts.add(sellingSinglePart(order.getQuantity(), order.getPart()));
        }
        AdvanceReceipt advanceReceipt = new AdvanceReceipt(receipts, "BUYING(from store)");
        sellHistory.add(advanceReceipt);
        return advanceReceipt;
    }

    /**
     * Process a single order, an order contains the piece to sell and its quantity
     * removes from stock
     * @param order a part and its quantity
     * @return advanceReceipt
     */
    public AdvanceReceipt sellSingleOrder(Order order) {
        receipts.clear();
        receipts.add(sellingSinglePart(order.getQuantity(), order.getPart()));
        AdvanceReceipt advanceReceipt = new AdvanceReceipt(receipts, "BUYING(from store)");
        sellHistory.add(advanceReceipt);
        return advanceReceipt;
    }

    public ArrayList<AdvanceReceipt> getSellHistory() {
        return sellHistory;
    }
}