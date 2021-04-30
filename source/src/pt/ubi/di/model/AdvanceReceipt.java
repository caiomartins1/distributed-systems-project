package pt.ubi.di.model;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A receipt with many receipts within itself
 * it as a unique id
 * it as a id for its "buyer"
 * it as a list of receipts per part
 *
 */
public class AdvanceReceipt {

    String id;
    ArrayList<Receipt> advanceSlip;
    String whoFor;
    float totalCost;
    //TODO add date

    public AdvanceReceipt(ArrayList<Receipt> advanceSlip, String whoFor) {
        this.advanceSlip = new ArrayList<>();
        this.advanceSlip = (ArrayList<Receipt>) advanceSlip.clone();
        this.id = UUID.randomUUID().toString();
        this.whoFor = whoFor;
        this.totalCost=0.0F;
        for (Receipt slip : advanceSlip)
            this.totalCost =totalCost + slip.getPrice();
    }

    public String toString() {
        String note = "";//TODO may not work
        boolean printItem;

        for(Receipt slip : advanceSlip){
            if(slip.getItemsQuantity()>5)
                printItem = false;//TODO complete
            else
                printItem=true;//TODO complete
            note = note.concat(slip.toStringReceipt(printItem)+"\n");
        }
        return ("__________________ PRINTING " + whoFor + " RECEIPT FOR: " + id +
                "\n" + note +
                "\n__________________" + "__________________ END OF RECEIPT __________________\n");
    }

}