package heist.rmi.test;

import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;

public class ClientB
{
    public static void main(String args[])
    {
        try
        {
            AdderInterface adder = (AdderInterface) Naming.lookup(rmiAddress("localhost", 5000, "adder"));
            System.out.println(adder.add());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
