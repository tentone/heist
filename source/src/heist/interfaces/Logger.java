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
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void debug(String message) throws Exception;
    
    /**
     * Create a log entry of everything in the general repository.
     * Flushes after log has been written.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void log() throws Exception;
   
            
    /**
     * End the log and close the internal PrintStream.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void end() throws Exception;
}
