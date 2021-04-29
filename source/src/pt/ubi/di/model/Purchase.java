package pt.ubi.di.model;

import java.util.ArrayList;

public class Purchase {
    ArrayList<AdvanceReceipt> purchaseHistory;
    ArrayList<String> tempItems;

    /**
     * Constructor for Purchase class
     * Initializes arrays
     */
    public Purchase() {
        purchaseHistory = new ArrayList<>();
        tempItems = new ArrayList<>();
    }

    /**
     * Creates a receipt for a single part type in a specified quantity
     *
     * @param quantity amount to buy
     * @param part     type to buy
     * @return receipt
     */
    public Receipt buySinglePart(int quantity, Part part) { // TODO we dont do validation on stock
        float price = 0.00F;//TODO initialize here or somewhere else
        float buyPrice = part.getBuyPrice();
        for (int i = 0; i < quantity; i++) {
            price = price + buyPrice;
            tempItems.add(part.removeStock());
        }
        return new Receipt(part.getID(), part.getType(), price, buyPrice, quantity, tempItems);
    }

    /**
     * Receives a list of orders, and order contains the piece to buy and its quantity
     *
     * @param orders
     * @return advanceReceipt
     */
    public AdvanceReceipt buyOrder(ArrayList<Order> orders) {
        ArrayList<Receipt> receipts = new ArrayList<>();
        while (!orders.isEmpty()) {
            Order order = orders.get(0);
            receipts.add(buySinglePart(order.getQuantity(), order.getPart()));
        }
        AdvanceReceipt advanceReceipt = new AdvanceReceipt(receipts, "BUYING");
        purchaseHistory.add(advanceReceipt);
        return advanceReceipt;
    }
}