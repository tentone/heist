package heist.interfaces;

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
     */
    public void debug(String message);
    
    /**
     * Create a log entry of everything in the general repository.
     * Flushes after log has been written.
     */
    public void log();
   
            
    /**
     * End the log and close the internal PrintStream.
     */
    public void end();
}
