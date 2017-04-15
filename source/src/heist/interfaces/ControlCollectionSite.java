package heist.interfaces;

import heist.room.RoomStatus;

public interface ControlCollectionSite
{
    /**
     * Called by the MasterThief to get his next state
     * @return MasterThief next state.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public int appraiseSit() throws Exception;
    
    /**
     * Called by the MasterThief to get what room to attack next.
     * @return Room to attack next.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public RoomStatus getRoomToAttack() throws Exception;
    
    /**
     * Called by the MasterThief to wait until the OrdinaryThieves return from the museum.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void takeARest() throws Exception;
    
    /**
     * Called by the MasterThief to collect canvas from OrdinaryThieves waiting to deliver a canvas.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void collectCanvas() throws Exception;
    
    /**
     * Called by the MasterThief to terminate the simulation.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void sumUpResults() throws Exception;
    
    /**
     * Called by the OrdinaryThieves to check if they are still needed.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     * @return True if the thief is still needed, false otherwise.
     */
    public boolean amINeeded() throws Exception;
    
    
}
