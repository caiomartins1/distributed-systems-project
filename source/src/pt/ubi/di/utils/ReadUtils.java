package pt.ubi.di.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ReadUtils {

    public static String readString() {
        String s = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1);
            s = in.readLine();
        } catch (IOException e) {
            System.out.println("Error on read String: "+e.getMessage());
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
            System.out.println("Error on read Float: " + e.getMessage());
        }

        return f;
    }

    public static int readInt() {
        String s = "";
        int i = -3;
        try {
            s = readString();
            i = Integer.parseInt(s);
        } catch (Exception e) {
            System.out.println("Error on read int: " + e.getMessage());
        }
        return i;
    }

    public static int readIntAlt() {
        Scanner s = new Scanner(System.in);
        int num=-3;
        try {
            s.nextInt();
        } catch(Exception e) {
            System.out.println("Error on readIntAlt: "+e.getMessage());
        }
        return num;
    }

    public static char readChar() {
        String s = "";
        char c=' ';
        try {
            s = readString();
            c = s.charAt(0);
        } catch (Exception e) {
            System.out.println("Error on read char: "+e.getMessage());
        }
        return c;
    }

}
