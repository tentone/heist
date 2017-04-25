package heist.interfaces;

import heist.concurrent.thief.OrdinaryThief;
import heist.room.RoomStatus;

public interface ConcentrationSite
{
    /**
     * Called by the MasterThief to create a new AssaultParty.
     * @return AssaultParty created.
     * @param room Target room for the party
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public AssaultParty prepareNewParty(RoomStatus room) throws Exception;
    
    /**
     * Called by the OrdinaryThieves to enter the concentration site and wait until a party is assigned to them.
     * @param thief OrdinaryThief that is entering the concentration site.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void prepareExcursion(OrdinaryThief thief) throws Exception;
}
