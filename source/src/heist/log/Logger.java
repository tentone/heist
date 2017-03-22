package heist.log;

import heist.GeneralRepository;
import heist.struct.Queue;
import java.io.PrintStream;

/**
 * Logger object is used to create a detailed log of everything inside a GeneralRepository.
 * @author Jose Manuel
 */
public class Logger
{
    private Queue<String> log;
    private GeneralRepository repository;
    private PrintStream stream;
    
    /**
     * Constructor from repository and specific PrintStream object to be used to display log messages as they are created.
     * @param repository
     * @param stream 
     */
    public Logger(GeneralRepository repository, PrintStream stream)
    {
        this.log = new Queue<>();
        this.repository = repository;
        this.stream = stream;
    }
    
    /**
     * Constructor from general repository object that uses System.out as PrintStream to display log messages.
     * @param repository 
     */
    public Logger(GeneralRepository repository)
    {
        this.log = new Queue<>();
        this.repository = repository;
        this.stream = System.out;
    }
    
    /**
     * Create a log entry of everything in the general repository
     */
    public void log()
    {
        //TODO <ADD CODE HERE>
    }
}
