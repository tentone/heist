package heist.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Adder extends UnicastRemoteObject implements AdderInterface
{
    public Adder() throws RemoteException
    {
        super();
    }

    public int add(int x, int y)
    {
        return x + y;
    }
}
