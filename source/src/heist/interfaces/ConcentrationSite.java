package heist.interfaces;

public interface ConcentrationSite
{
    /**
     * Called by the MasterThief to create a new AssaultParty.
     * @return AssaultParty created.
     */
    public AssaultParty createNewParty();
    
    /**
     * Called by the OrdinaryThieves to enter the concentration site and wait until a party is assigned to them.
     */
    public void prepareExcursion();
    
    
}
