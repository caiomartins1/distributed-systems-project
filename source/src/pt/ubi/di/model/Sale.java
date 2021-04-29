package pt.ubi.di.model;

import java.util.ArrayList;

public class Sale {
    ArrayList<AdvanceReceipt> purchaseHistory;
    ArrayList<String> tempItems;

    /**
     * Constructor for Purchase class
     * Initializes arrays
     */
    public Sale() {
        purchaseHistory = new ArrayList<>();
        tempItems = new ArrayList<>();
    }

    /**
     * Creates a receipt for a single part type in a specified quantity
     *
     * @param quantity amount to sell
     * @param part     type to sell
     * @return receipt
     */
    public Receipt SellSinglePart(int quantity, Part part) { // TODO we dont do validation on stock
        float price = 0.00F;//TODO initialize here or somewhere else
        float sellPrice = part.getSellPrice();
        for (int i = 0; i < quantity; i++) {
            price = price + sellPrice;
            tempItems.add(part.removeStock());
        }
        return new Receipt(part.getID(), part.getType(), price, sellPrice, quantity, tempItems);
    }

    /**
     * Receives a list of orders, and order contains the piece to sell and its quantity
     *
     * @param orders
     * @return advanceReceipt
     */
    public AdvanceReceipt SellOrder(ArrayList<Order> orders) {
        ArrayList<Receipt> receipts = new ArrayList<>();
        while (!orders.isEmpty()) {
            Order order = orders.get(0);
            receipts.add(SellSinglePart(order.quantity, order.part));
        }
        AdvanceReceipt advanceReceipt = new AdvanceReceipt(receipts, "SELLING");
        purchaseHistory.add(advanceReceipt);
        return advanceReceipt;
    }
}