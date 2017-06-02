package heist.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AdderInterface extends Remote
{
    public int add(int x, int y) throws RemoteException;
}
