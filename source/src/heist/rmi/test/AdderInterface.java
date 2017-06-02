package heist.rmi.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AdderInterface extends Remote
{
    public void set(int x, int y) throws RemoteException;
    public int add() throws RemoteException;
    public AdderInterface getCopy() throws RemoteException;
}
