package heist.utils;

import java.io.Serializable;

/**
 * Address class is used to store an address and respective port for a socket connection.
 * @author Tentone
 */
public class Address implements Serializable
{
    private static final long serialVersionUID = 9023789543L;
    
    /**
     * Server address.
     */
    public String address;
    
    /**
     * Connection port.
     */
    public int port;
    
    /**
     * Address constructor.
     * @param address Server address.
     * @param port Port for connection.
     */
    public Address(String address, int port)
    {
        this.address = address;
        this.port = port;
    }
}
