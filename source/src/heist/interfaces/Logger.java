package heist.interfaces;

import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;

/**
 * Logger object is used to create a detailed log of everything inside the GeneralRepository.
 * @author Jose Manuel
 */
public interface Logger
{
    /**
     * Write message directly to the PrintStream.
     * Flushes the PrintStream after every message.
     * @param message Message to display.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void debug(String message) throws Exception;
    
    /**
     * Called by the OrdinaryThief to create a log entry using the data sent.
     * Flushes after log has been written.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void log(OrdinaryThief thief) throws Exception;
    
    /**
     * Called by the MasterThief to create a log entry using the data sent.
     * Flushes after log has been written.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void log(MasterThief master) throws Exception;
    
    /**
     * End the log and close the internal PrintStream.
     * @throws Exception A exception may be thrown depending on the implementation.
     */
    public void end() throws Exception;
}
