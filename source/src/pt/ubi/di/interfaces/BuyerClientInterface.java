package pt.ubi.di.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BuyerClientInterface extends Remote {
    public void printOnClient(String s) throws RemoteException;

    public void printOnClientNoNL(String s) throws RemoteException;

    public String readStringClient() throws RemoteException;

    public int readIntClient() throws RemoteException;

    public char readCharClient() throws RemoteException;

    public String getClientId() throws RemoteException;
}
