package heist.rmi.register;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Registry service is to used to bind remote objects into the remote RMI registry instance.
 * @author Jose Manuel
 */
public class RegistryService implements RegistryServiceInterface
{
    private String address;
    private int port;
   
    public RegistryService(String address, int port)
    {
        this.address = address;
        this.port = port;
    }
    
    public void bind(String name, Remote ref) throws RemoteException, AlreadyBoundException
    {
        Registry registry = LocateRegistry.getRegistry(this.address, this.port);
        registry.bind(name, ref);
    }
    
    public void rebind(String name, Remote ref) throws RemoteException
    {
        Registry registry = LocateRegistry.getRegistry(this.address, this.port);
        registry.rebind(name, ref);
    }
    
    public void unbind(String name) throws RemoteException, NotBoundException
    {
        Registry registry = LocateRegistry.getRegistry(this.address, this.port);
        registry.unbind(name);
    }
}
