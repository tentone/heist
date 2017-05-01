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
     * Create string with address information
     * @return Address information
     */
    @Override
    public String toString()
    {
        return "Name:" + this.name + " IP: " + this.address + " Port:" + this.port;
    }
}
