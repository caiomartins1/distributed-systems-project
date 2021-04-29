package pt.ubi.di.model;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.UUID;

public class AdvanceReceipt{

    String id;
    ArrayList<Receipt> advanceSlip;
    String whoFor;
    //TODO add date

    public AdvanceReceipt(ArrayList<Receipt> advanceSlip,String whoFor){
        this.advanceSlip = new ArrayList<>();
        this.advanceSlip = (ArrayList<Receipt>) advanceSlip.clone();
        this.id = UUID.randomUUID().toString();
        this.whoFor=whoFor;
    }

    public String toString(){
        ArrayList<Receipt> advanceSlipTemp = (ArrayList<Receipt>) advanceSlip.clone();
        String note = "";//TODO may not work
        while (!advanceSlipTemp.isEmpty()){
            Receipt slip = advanceSlipTemp.remove(0);
            note = note.concat(slip.toString());
        }
        return ("__________________ PRINTING "+whoFor+" RECEIPT FOR: "+id+"\n"+note+"\n__________________"+"__________________ END OF RECEIPT __________________");
    }

}