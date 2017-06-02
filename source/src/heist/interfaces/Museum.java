package heist.interfaces;

import heist.room.Room;
import java.rmi.Remote;

public interface Museum extends Remote
{
    /**
     * Called by the OrdinaryThieves to roll a canvas.
     * @param id Room id from where the Thief is trying to get a canvas.
     * @return True if was able to get a canvas, false if the room was already empty
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public boolean rollACanvas(int id) throws Exception;
    
    /**
     * Get room array
     * @return Array of Room objects
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public Room[] getRooms() throws Exception;
    
    /**
     * Called by the MasterThief to end the simulation.
     * May be required for some implementations.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public default void end() throws Exception {}
}
