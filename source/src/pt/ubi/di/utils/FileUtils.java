package pt.ubi.di.utils;

import pt.ubi.di.model.Part;

import java.io.*;
import java.util.ArrayList;

public class FileUtils {
    private static String partsFile = "./db/parts.db";

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
}


