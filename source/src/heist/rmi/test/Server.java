package heist.rmi.test;

import java.rmi.Naming;
import static heist.utils.Address.rmiAddress;

public class Server
{
    public static void main(String args[])
    {
        try
        {
            Naming.rebind(rmiAddress("localhost", 5000, "adder"), new Adder());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
