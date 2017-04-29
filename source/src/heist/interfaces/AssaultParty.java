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
     */
    public int getID();
    
    /**
     * Get assault party target room.
     * @return Target room ID, if no room is assigned to the party returns -1.
     */
    public int getTarget();

    /**
     * Get party state.
     * @return Party state.
     */
    public int getState();
            
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     */
    public boolean partyFull();
    
    /**
     * Get thieves in this party array.
     * @return Array of thieves.
     */
    public OrdinaryThief[] getThieves();
    
    /**
     * Prepare party, set state to WAITING and set target room.
     * @param room TargetRoom.
     */
    public void prepareParty(RoomStatus room);
    
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
