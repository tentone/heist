package heist.rmi.register;

import static heist.utils.Address.rmiAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The registry service is used to register external remote objects into the RMI registry.
 * @author Jose Manuel
 */
public class RegistryServer
{
public static void main(String[] args)
    {
        String address = (args.length > 0) ?  args[0] : "localhost";
        int port = (args.length > 1) ?  Integer.parseInt(args[1]) : 22399;
        int portstub = (args.length > 2) ?  Integer.parseInt(args[2]) : 22398;
        boolean createRegistry = (args.length > 3) ?  Boolean.parseBoolean(args[3]) : false;
        
        System.setProperty("java.security.policy", "java.policy");

        if(createRegistry)
        {
            try
            {
                System.setProperty("java.rmi.server.hostname", address);
                LocateRegistry.createRegistry(port);
                
                String hostname = System.getProperty("java.rmi.server.hostname");
                System.out.println("Info: RMI registry started on " + hostname + ":" + port);
            }
            catch(Exception e)
            {
                System.out.println("Error: Failed to create RMI registry (" + e + ")");
                System.exit(1);
            }
        }
        
        try
        {
            String url = rmiAddress(address, port, "registry");
            Remote stub = UnicastRemoteObject.exportObject(new RegistryService(address, port), portstub);
            Naming.rebind(url, stub);

            System.out.println("Info: Registry Server running");
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in Registry server (" + e + ")");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
