package heist.utils;

import java.io.Serializable;

/**
 * Address class is used to store an address and respective port for connections.
 * @author Jose Manuel
 */
public class Address implements Serializable
{
    private static final long serialVersionUID = 9023789543L;
    
    /**
     * Server name
     */
    public String name;
    
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
     * @param name Server name
     */
    public Address(String address, int port, String name)
    {
        this.address = address;
        this.port = port;
        this.name = name;
    }
    
    /**
     * Set address values.
     * @param address Server address.
     * @param port Port for connection.
     * @param name Server name
     */
    public void set(String address, int port, String name)
    {
        this.address = address;
        this.port = port;
        this.name = name;
    }
    
    /**
     * Get RMI URL from address, port and name.
     * @return URL.
     */
    public String rmiURL()
    {
        return Address.rmiAddress(this.address, this.port, this.name);
    }
    
    /**
     * Create string with address information
     * @return Address information
     */
    @Override
    public String toString()
    {
        return "Name:" + this.name + " IP: " + this.address + " Port:" + this.port;
    }
    
    /**
     * Generate an address for RMI access
     * @param address IP of the server.
     * @param port Port being used.
     * @param name RMI registry name.
     * @return URL for RMI access.
     */
    public static String rmiAddress(String address, int port, String name)
    {
        return "rmi://" + address + ":" + port + "/" + name;
    }
}
