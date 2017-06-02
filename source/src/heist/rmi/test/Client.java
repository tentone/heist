package heist.rmi.test;

import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;

public class Client
{
    public static void main(String args[])
    {
        try
        {
            AdderInterface adder = (AdderInterface) Naming.lookup(rmiAddress("localhost", 5000, "adder"));
            adder.set(2, 3);
            System.out.println(adder.add());
            
            AdderInterface b = adder.getCopy();
            b.set(5, 6);
            System.out.println(adder.add());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
