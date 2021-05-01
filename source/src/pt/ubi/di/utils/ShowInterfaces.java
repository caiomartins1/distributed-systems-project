package pt.ubi.di.utils;

import javax.naming.StringRefAddr;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class ShowInterfaces
{
    public static String getIp() {
        String ip="172.0.0.1";
        String[] stringCompare = {"/192","168","1"};
        boolean val = true;
        try {
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            while (n.hasMoreElements()) {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements()) {
                    String address = a.nextElement().toString();
                    String[] stringAddress = address.split("\\.");

                    for (int i=0;i<(stringAddress.length-1);++i) {
                        if(!stringAddress[i].equals(stringCompare[i])) {
                            val = false;
                        }
                        else if(i==stringAddress.length-2){
                            ip = address;
                            ip = ip.replace("/","");
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
