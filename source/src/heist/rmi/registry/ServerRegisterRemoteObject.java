package heist.rmi.registry;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import heist.rmi.interfaces.Register;
import java.util.Scanner;

/**
 * This data type instantiates and registers a remote object that enables the
 * registration of other remote objects located in the same or other processing
 * nodes in the local registry service. Communication is based in Java RMI.
 */
public class ServerRegisterRemoteObject
{
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        /* get location of the registry service */

        String rmiRegHostName;
        int rmiRegPortNumb;

        System.out.println("Nome do nó de processamento onde está localizado o serviço de registo? ");
        rmiRegHostName = sc.nextLine();

        System.out.println("Número do port de escuta do serviço de registo? ");
        rmiRegPortNumb = sc.nextInt();

        /* create and install the security manager */
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Security manager was installed!");

        /* instantiate a registration remote object and generate a stub for it */
        RegisterRemoteObject regEngine = new RegisterRemoteObject(rmiRegHostName, rmiRegPortNumb);
        Register regEngineStub = null;
        int listeningPort = 22000;
        /* it should be set accordingly in each case */

        try
        {
            regEngineStub = (Register) UnicastRemoteObject.exportObject(regEngine, listeningPort);
        }
        catch (RemoteException e)
        {
            System.out.println("RegisterRemoteObject stub generation exception: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Stub was generated!");

        /* register it with the local registry service */
        String nameEntry = "RegisterHandler";
        Registry registry = null;

        try
        {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        {
            System.out.println("RMI registry creation exception: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("RMI registry was created!");

        try
        {
            registry.rebind(nameEntry, regEngineStub);
        }
        catch (RemoteException e)
        {
            System.out.println("RegisterRemoteObject remote exception on registration: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("RegisterRemoteObject object was registered!");
    }
}
