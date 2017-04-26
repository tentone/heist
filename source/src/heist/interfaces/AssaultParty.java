package heist.interfaces;

import heist.concurrent.thief.OrdinaryThief;

public interface AssaultParty
{
    /**
     * Called by the MasterThief to send this party to the museum.
     * Party can start crawling after this method was called.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void sendParty() throws Exception;
    
    /**
     * Called by the OrdinaryThief to crawl inside the museum.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     * @param thief Thief Crawling
     * @return False if the thief has reached its destination True otherwise
     */
    public boolean crawlIn(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the OrdinaryThieves to reverse their direction after they reached the target room.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void reverseDirection() throws Exception;
    
    /**
     * Called by the OrdinaryThief to crawl inside the museum.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     * @param thief Thief Crawling
     * @return False if the thief has reached its destination True otherwise
     */
    public boolean crawlOut(OrdinaryThief thief) throws Exception;
}
