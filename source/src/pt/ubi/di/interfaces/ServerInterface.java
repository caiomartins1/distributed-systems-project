package pt.ubi.di.interfaces;

import pt.ubi.di.model.Part;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public void subscribeManager(String name, ManagerClientInterface managerClient) throws RemoteException;

    public void subscribeBuyer(String name, BuyerClientInterface client2) throws RemoteException;

    public void managerOption0(ManagerClientInterface client) throws RemoteException;

    public void managerOption1(ManagerClientInterface client, Part p) throws RemoteException;

    public void managerOption2(ManagerClientInterface client) throws RemoteException;

    public void managerOption3(ManagerClientInterface client) throws RemoteException;

    public void managerOption4(ManagerClientInterface client) throws RemoteException;

    public void managerOption5(ManagerClientInterface client) throws RemoteException;

    public void managerOption6(ManagerClientInterface client) throws RemoteException;

    public void buyerOption0(BuyerClientInterface buyerClient) throws RemoteException;

    public void buyerOption1(BuyerClientInterface client) throws RemoteException;

    public void buyerOption2(BuyerClientInterface client) throws RemoteException;

    public void buyerOption3(BuyerClientInterface client) throws RemoteException;

}
