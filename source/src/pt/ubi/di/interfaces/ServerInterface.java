package pt.ubi.di.interfaces;

import pt.ubi.di.model.Part;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public void subscribeManager(String name, ManagerClientInterface managerClient) throws RemoteException;

    public void managerOption0() throws RemoteException;
    public void managerOption1(Part p) throws RemoteException;
    public void managerOption2() throws RemoteException;
    public void managerOption3() throws RemoteException;
    public void managerOption4() throws RemoteException;
    public void managerOption5() throws RemoteException;



}
