package heist.interfaces;

public interface ConcentrationSite
{
    /**
     * Called by the MasterThief to create a new AssaultParty.
     * @return AssaultParty created.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public AssaultParty createNewParty() throws Exception;
    
    /**
     * Called by the OrdinaryThieves to enter the concentration site and wait until a party is assigned to them.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void prepareExcursion() throws Exception;
}
