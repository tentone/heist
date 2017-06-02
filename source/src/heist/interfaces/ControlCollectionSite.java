package heist.interfaces;

import heist.thief.OrdinaryThief;
import heist.room.RoomStatus;
import java.rmi.Remote;

/**
 * The ControlCollection site is where the OrdinaryThieves deliver the canvas to the MasterThief.
 * It is also responsible for decisions taken by booth the MasterThief and the OrdinaryThieves.
 * @author Jose Manuel
 */
public interface ControlCollectionSite extends Remote
{
    /**
     * Called by the OrdinaryThieves to check if they are still needed.
     * @param thief Thief checking if he is needed.
     * @throws Exception A exception may be thrown depending on the implementation.
     * @return True if the thief is still needed, false otherwise.
     */
    public boolean amINeeded(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the MasterThief to get his next state
     * @return MasterThief next state.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public int appraiseSit() throws Exception;
    
    /**
     * Called by the MasterThief to prepare an assault party to be filled with OrdinaryThieves waiting in the concentration site.
     * @param room Target room.
     * @return AssaultParty created.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public int prepareNewParty(RoomStatus room) throws Exception;
    
    /**
     * Called by the MasterThief to get what room to attack next.
     * @return Room to attack next.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public RoomStatus getRoomToAttack() throws Exception;
    
    /**
     * Called by the MasterThief to wait until the OrdinaryThieves return from the museum.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void takeARest() throws Exception;
    
    /**
     * Called by the OrdinaryThief to be added to the collection site and wake up the MasterThief to collect its canvas.
     * The OrdinaryThief enters the queue wakes up the MasterThief and waits until is waken up.
     * @param thief Thief to be added.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void handACanvas(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the MasterThief to collect canvas from OrdinaryThieves waiting to deliver a canvas.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void collectCanvas() throws Exception;
    
    /**
     * Called by the MasterThief to terminate the simulation.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void sumUpResults() throws Exception;
    
    /**
     * Check how many paintings were stolen.
     * @return Number of paintings.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public int totalPaintingsStolen() throws Exception;
    
    /**
     * Called by the MasterThief to end the simulation.
     * May be required for some implementations.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public default void end() throws Exception {}
}
