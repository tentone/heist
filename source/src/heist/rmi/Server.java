package heist.rmi;

import java.rmi.Naming;

public class Server
{
    public static void main(String args[])
    {
        try
        {
            Naming.rebind("rmi://localhost:5000/adder", new Adder());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
