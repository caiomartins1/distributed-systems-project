package pt.ubi.di.utils;

import pt.ubi.di.model.AdvanceReceipt;
import pt.ubi.di.model.Part;

import java.io.*;
import java.util.ArrayList;

public class FileUtils {
    private static String partsFile = "./db/parts.db";
    private static String purchaseFile = "./db/purchaseHistory.db";
    private static String saleFile = "./db/saleHistory.db";

    public static void saveParts(ArrayList<Part> parts) {

        try {
            FileOutputStream writeData = new FileOutputStream(partsFile);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(parts);
            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            System.out.println("FileUtils -> " + e.getMessage());
        }
    }

    public static ArrayList<Part> retrieveParts() {
        ArrayList<Part> parts;

        try {
            FileInputStream readData = new FileInputStream(partsFile);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            parts = (ArrayList<Part>) readStream.readObject();
            readStream.close();

        } catch (Exception e) {
            System.out.println("File Utils -> " + e.getMessage());
            return new ArrayList<Part>();
        }

        return parts;
    }

    public static void savePurchaseHistory(ArrayList<AdvanceReceipt> advanceReceipts) {

        try {
            FileOutputStream writeData = new FileOutputStream(purchaseFile);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(advanceReceipts);
            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            System.out.println("FileUtils -> " + e.getMessage());
        }
    }

    public static ArrayList<AdvanceReceipt> retrievePurchaseHistory() {
        ArrayList<AdvanceReceipt> advanceReceipts;

        try {
            FileInputStream readData = new FileInputStream(purchaseFile);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            advanceReceipts = (ArrayList<AdvanceReceipt>) readStream.readObject();
            readStream.close();

        } catch (Exception e) {
            System.out.println("File Utils -> " + e.getMessage());
            return new ArrayList<AdvanceReceipt>();
        }

        return advanceReceipts;
    }

    public static void saveSalesHistory(ArrayList<AdvanceReceipt> advanceReceipts) {

        try {
            FileOutputStream writeData = new FileOutputStream(saleFile);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(advanceReceipts);
            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            System.out.println("FileUtils -> " + e.getMessage());
        }
    }

    public static ArrayList<AdvanceReceipt> retrieveSalesHistory() {
        ArrayList<AdvanceReceipt> advanceReceipts;

        try {
            FileInputStream readData = new FileInputStream(saleFile);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            advanceReceipts = (ArrayList<AdvanceReceipt>) readStream.readObject();
            readStream.close();

        } catch (Exception e) {
            System.out.println("File Utils -> " + e.getMessage());
            return new ArrayList<AdvanceReceipt>();
        }

        return advanceReceipts;
    }
}


