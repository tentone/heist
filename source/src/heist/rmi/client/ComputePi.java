package heist.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import heist.rmi.interfaces.Compute;
import java.util.Scanner;

/**
 *  Client side.<p>
 *  Back engine application: code is moved and executed in a platform supposed to be more powerful.
 *  Communication is based in Java RMI.
 *  In this case, the computation of pi with a variable number of digits takes place.
 */

public class ComputePi
{
   private static Scanner sc = new Scanner(System.in);
   
   public static void main(String args[])
   {
     /* get location of the generic registry service */

     String rmiRegHostName;
     int rmiRegPortNumb;

     System.out.println("Nome do nó de processamento onde está localizado o serviço de registo? ");
     rmiRegHostName = sc.nextLine();
     System.out.println("Número do port de escuta do serviço de registo? ");
     rmiRegPortNumb = sc.nextInt();

    /* look for the remote object by name in the remote host registry */

     String nameEntry = "Compute";
     Compute comp = null;
     Registry registry = null;

     try
     { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
     }
     catch (RemoteException e)
     { System.out.println ("RMI registry creation exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }

     try
     { comp = (Compute) registry.lookup (nameEntry);
     }
     catch (RemoteException e)
     { System.out.println ("ComputePi look up exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }
     catch (NotBoundException e)
     { System.out.println ("ComputePi not bound exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }

    /* instantiate the mobile code object to be run remotely */

     Pi task = null;
     BigDecimal pi = null;

     try
     { task = new Pi (Integer.parseInt (args[0]));
     }
     catch (NumberFormatException e)
     { System.out.println ("Pi instantiation exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }

    /* invoke the remote method (run the code at a ComputeEngine remote object) */

     try
     { pi = (BigDecimal) (comp.executeTask (task));
     }
     catch (RemoteException e)
     { System.out.println ("ComputePi remote invocation exception: " + e.getMessage ());
       e.printStackTrace ();
       System.exit (1);
     }

     /* print the result */
     System.out.println (pi.toString ());
  }
}
