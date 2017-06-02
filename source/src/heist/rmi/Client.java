package heist.rmi;

import java.rmi.*;

public class Client
{
    public static void main(String args[])
    {
        try
        {
            AdderInterface stub = (AdderInterface) Naming.lookup("rmi://localhost:5000/adder");
            System.out.println(stub.add(34, 4));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
