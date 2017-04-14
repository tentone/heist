package heist.interfaces;

import heist.room.RoomStatus;

public interface ControlCollectionSite
{
    /**
     * Called by the MasterThief to get his next state
     * @return MasterThief next state.
     */
    public int appraiseSit();
    
    /**
     * Called by the MasterThief to get what room to attack next.
     * @return Room to attack next.
     */
    public RoomStatus getRoomToAttack();
    
    /**
     * Called by the MasterThief to wait until the OrdinaryThieves return from the museum.
     */
    public void takeARest();
    
    /**
     * Called by the MasterThief to collect canvas from OrdinaryThieves waiting to deliver a canvas.
     */
    public void collectCanvas();
    
    /**
     * Called by the MasterThief to terminate the simulation.
     */
    public void sumUpResults();
    
    /**
     * Called by the OrdinaryThieves to check if they are still needed.
     */
    public boolean amINeeded();
    
    
}
