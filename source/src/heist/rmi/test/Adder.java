package heist.rmi.test;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Adder extends UnicastRemoteObject implements AdderInterface, Serializable
{
    private static final long serialVersionUID = 894265820223L;
    public int x, y;
    
    public Adder() throws RemoteException
    {
        super();
    }
    
    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public int add()
    {
        return this.x + this.y;
    }
    
    public Adder getCopy()
    {
        return this;
    }
}
