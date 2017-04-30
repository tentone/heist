package heist.interfaces;

import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;

public interface AssaultParty
{
    /**
     * Waiting state.
     * When the party is in this state the OrdinaryThieves are joining or waiting for the MasterThieve to send them.
     */
    public static final int WAITING = 1000;
    
    /**
     * Crawling state.
     * When the party is in this state the OrdinaryThieves are crawling, or inside the room.
     */
    public static final int CRAWLING = 2000;
    
    /**
     * Dismissed state.
     * The party is set to this state after the last OrdinaryThieve delivers the canvas (or notifies the MasterThieve that he arrived empty handed).
     */
    public static final int DISMISSED = 3000;
    
    /**
     * Get party id
     * @return Party ID
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public int getID() throws Exception;
    
    /**
     * Get assault party target room.
     * @return Target room ID, if no room is assigned to the party returns -1.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public int getTarget() throws Exception;

    /**
     * Get party state.
     * @return Party state.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public int getState() throws Exception;
            
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public boolean partyFull() throws Exception;
    
    /**
     * Get thieves in this party array.
     * @return Array of thieves.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public OrdinaryThief[] getThieves() throws Exception;
    
    /**
     * Prepare party, set state to WAITING and set target room.
     * @param room TargetRoom.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void prepareParty(RoomStatus room) throws Exception;
    
    /**
     * Add thief to party if the party is full the thief is not added.
     * @param thief Thief to be added to the party.
     * @throws java.lang.Exception Throws exception if party is already full.
     */
    public void addThief(OrdinaryThief thief) throws Exception;
    
    /**
     * Remove thief from party.
     * After all thieves are removed from the party the party is dismissed.
     * @param id ID of the thief to be removed
     * @throws java.lang.Exception Throws exception if party is already full.
     */
    public void removeThief(int id) throws Exception;
            
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
     * @param thief Thief that is going to reverse direction
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void reverseDirection(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the OrdinaryThief to crawl inside the museum.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     * @param thief Thief Crawling
     * @return False if the thief has reached its destination True otherwise
     */
    public boolean crawlOut(OrdinaryThief thief) throws Exception;
}
