package pt.ubi.di.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A receipt with many receipts within itself
 * Serves the purpose of registering a purchase of multiple items of different/multiple parts
 * it as a unique id
 * it as a id for its "buyer"
 * it as a list of receipts per part
 * it as a totalCost
 * it as a timeOfPurchase
 */
public class AdvanceReceipt implements Serializable {

    String id;
    ArrayList<Receipt> advanceSlip;
    String whoFor;
    String name;
    float totalCost;
    LocalDateTime timeOfPurchase;

    /**
     * Constructor for an Advance Receipt
     * You would keep this in a list, they can be use either for buying or selling
     * @param advanceSlip
     * @param whoFor
     * @param name
     */
    public AdvanceReceipt(ArrayList<Receipt> advanceSlip, String whoFor, String name) {
        this.advanceSlip = new ArrayList<>();
        this.advanceSlip = (ArrayList<Receipt>) advanceSlip.clone();
        this.id = UUID.randomUUID().toString();
        this.whoFor = whoFor;
        this.name = name;
        this.totalCost = 0.0F;
        timeOfPurchase = LocalDateTime.now();
        for (Receipt slip : advanceSlip)
            this.totalCost = totalCost + slip.getPrice();
    }

    public String getName() {
        return name;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public String getTimeOfPurchase() {
        return "Date: " + timeOfPurchase.getYear() +"/"+ timeOfPurchase.getMonth() +"/"+ timeOfPurchase.getDayOfMonth() +" "+ timeOfPurchase.getHour() +":"+ timeOfPurchase.getMinute();
    }

    public String getWhoFor() {
        return whoFor;
    }

    public LocalDateTime getCreatedAt() {
        return timeOfPurchase;
    }

    public String toString() {
        String note = "";
        boolean printItem;

        for (Receipt slip : advanceSlip) {
            printItem = slip.getItemsQuantity() <= 5;
            note = note.concat(slip.toStringReceipt(printItem) + "\n");
        }
        return ("\n__________________ PRINTING " + whoFor +  " NAME: " + name + "__________________\n"
                + "RECEIPT ID: " + id + "\n"
                + "Date: " + timeOfPurchase.getYear() +"/"+ timeOfPurchase.getMonth() +"/"+ timeOfPurchase.getDayOfMonth() +" "+ timeOfPurchase.getHour() +":"+ timeOfPurchase.getMinute() + "\n"
                + note + "\n"
                + "Total cost=" + totalCost + "\n"
                + "__________________ END OF RECEIPT __________________\n");
    }

}