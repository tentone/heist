/*
 * Used to register objects in the remote RMI server.
 */
package heist.rmi.register;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Registry service is to used to bind remote objects into the remote RMI registry instance.
 * @author Jose Manuel
 */
public interface RegistryServiceInterface extends Remote
{
    /**
     * Bind remote reference.
     * @param name Binding name
     * @param ref Remote object
     * @throws RemoteException An exception may be thrown.
     * @throws AlreadyBoundException An exception may be thrown.
     */
    public void bind(String name, Remote ref) throws RemoteException, AlreadyBoundException;
    
    /**
     * Bind remote reference.
     * @param name Binding name
     * @param ref Remote object
     * @throws RemoteException An exception may be thrown.
     */
    public void rebind(String name, Remote ref) throws RemoteException;
    
    /**
     * Unbind remote reference.
     * @param name Binding name
     * @throws RemoteException An exception may be thrown.
     * @throws NotBoundException An exception may be thrown.
     */
    public void unbind(String name) throws RemoteException, NotBoundException;
}
