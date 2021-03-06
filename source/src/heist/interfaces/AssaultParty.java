package heist.interfaces;

import heist.room.RoomStatus;
import heist.thief.OrdinaryThief;
import java.rmi.Remote;

/**
 * AssaultParty represents a group of OrdinaryThieves attacking the museum.
 * Its used as a synchronization point between thieves.
 * AsaultParties are dynamically created and destructed during the simulation
 * Assault party is shared between thieves.
 */
public interface AssaultParty extends Remote
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
    public static final int CRAWLING_IN = 2000;
    
    /**
     * Crawling out state.
     * When the party is in this state the OrdinaryThieves are crawling out.
     */
    public static final int CRAWLING_OUT = 3000;
    
    /**
     * Dismissed state.
     * The party is set to this state after the last OrdinaryThieve delivers the canvas (or notifies the MasterThieve that he arrived empty handed).
     */
    public static final int DISMISSED = 4000;
    
    /**
     * Get party id
     * @return Party ID
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public int getID() throws Exception;
    
    /**
     * Get assault party target room.
     * @return Target room ID, if no room is assigned to the party returns -1.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public int getTarget() throws Exception;

    /**
     * Get party state.
     * @return Party state.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public int getState() throws Exception;
            
    /**
     * Check if the party is full.
     * @return True if the party element queue has the same size as the party size.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public boolean partyFull() throws Exception;
    
    /**
     * Get thieves in this party array.
     * @return Array with the id of the thieves that belong to this party.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public int[] getThieves() throws Exception;
    
    /**
     * Prepare party, set state to WAITING and set target room.
     * @param room TargetRoom.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void prepareParty(RoomStatus room) throws Exception;
    
    /**
     * Add thief to party if the party is full the thief is not added.
     * @param thief Thief to be added to the party.
     * @throws Exception Throws exception if party is already full.
     */
    public void addThief(OrdinaryThief thief) throws Exception;
    
    /**
     * Remove thief from party.
     * After all thieves are removed from the party the party is dismissed.
     * @param id ID of the thief to be removed
     * @throws Exception Throws exception if party is already full.
     */
    public void removeThief(int id) throws Exception;
            
    /**
     * Called by the MasterThief to send this party to the museum.
     * Party can start crawling after this method was called.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void sendParty() throws Exception;
    
    /**
     * Called by the OrdinaryThief to crawl inside the museum.
     * @throws Exception A exception may be thrown depending on the implementation.
     * @param thief Thief Crawling
     * @return Position of the thief to be updated
     */
    public int crawlIn(OrdinaryThief thief) throws Exception;
    
    /**
     * Check if the OrdinaryThief needs to keep crawling.
     * @throws Exception A exception may be thrown depending on the implementation.
     * @param thief Thief Crawling
     * @return True if the thief still did not reach its destination.
     */
    public boolean keepCrawling(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the OrdinaryThieves to reverse their direction after they reached the target room.
     * @param thief Thief that is going to reverse direction
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void reverseDirection(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the OrdinaryThief to crawl inside the museum.
     * @throws Exception A exception may be thrown depending on the implementation.
     * @param thief Thief Crawling
     * @return Position of the thief to be updated
     */
    public int crawlOut(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the MasterThief to end the simulation.
     * May be required for some implementations.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public default void end() throws Exception {}
}
