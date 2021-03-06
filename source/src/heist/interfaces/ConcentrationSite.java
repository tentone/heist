package heist.interfaces;

import heist.thief.OrdinaryThief;
import java.rmi.Remote;

/**
 * The concentration site is where OrdinaryThieves wait for the MasterThief to assign them a AssaultParty.
 * ConcentrationSite is accessed by the MasterThief to get OrdinaryThieves to create and join AssaultParties.
 * @author Jose Manuel
 */
public interface ConcentrationSite extends Remote
{
    /**
     * Called by the MasterThief to fill a Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * The MasterThief prepares a party and adds it to the waiting party list and gets locked until all thieves join the party.
     * The last thief to join the party wakes up the MasterThief.
     * @param party Party to be filled with thieves.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void fillAssaultParty(int party) throws Exception;
    
    /**
     * Called by the OrdinaryThieves to enter the concentration site and wait until a party is assigned to them.
     * @param thief OrdinaryThief that is entering the concentration site.
     * @throws Exception A exception may be thrown depending on the implementation.
     * @return ID of the party attributed to the thief.
     */
    public int prepareExcursion(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the MasterThief to end the simulation.
     * May be required for some implementations.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public default void end() throws Exception {}
}
