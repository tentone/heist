package heist.interfaces;

import heist.concurrent.thief.OrdinaryThief;
import heist.concurrent.shared.AssaultParty;

public interface ConcentrationSite
{
    /**
     * Fill a Party of OrdinaryThieves with the thieves waiting in this ConcentrationSite.
     * The MasterThief creates the party and adds it to the waiting party list and gets locked until all thieves join the party.
     * The last thief to join the party wakes up the MasterThief.
     * @param party Party to be filled with thieves.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void fillAssaultParty(AssaultParty party) throws Exception;
    
    /**
     * Called by the OrdinaryThieves to enter the concentration site and wait until a party is assigned to them.
     * @param thief OrdinaryThief that is entering the concentration site.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void prepareExcursion(OrdinaryThief thief) throws Exception;
}
