package pt.ubi.di.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadUtils {

    public static String readString() {
        String s = "";

        System.out.println();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1);
            s = in.readLine();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public static float readFloat() {
        String s = "";
        float f = 0.0f;
        try {
            s = readString();
            f = Float.parseFloat(s);
        } catch (Exception e) {
            System.out.println("Manager Client -> " + e.getMessage());
        }

        return f;
    }

    public static int readInt() {
        String s = "";
        int i = 0;
        try {
            s = readString();
            i = Integer.parseInt(s);
        } catch (Exception e) {
            System.out.println("Manager Client -> " + e.getMessage());
        }

        return i;
    }
}
