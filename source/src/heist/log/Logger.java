package heist.log;

import heist.GeneralRepository;
import heist.queue.Queue;
import java.io.PrintStream;

/**
 * Logger object is used to create a detailed log of everything inside a GeneralRepository.
 * @author Jose Manuel
 */
public class Logger
{
    private Queue<String> log;
    private GeneralRepository repository;
    private PrintStream out;
    
    /**
     * Constructor from repository and specific PrintStream object to be used to display log messages as they are created.
     * @param repository GeneralRepository to be logged.
     * @param out PrintStream to write log to.
     */
    public Logger(GeneralRepository repository, PrintStream out)
    {
        this.log = new Queue<>();
        this.repository = repository;
        this.out = out;
    }
    
    /**
     * Constructor from general repository object that uses System.out as PrintStream to display log messages.
     * @param repository GeneralRepository to be logged.
     */
    public Logger(GeneralRepository repository)
    {
        this.log = new Queue<>();
        this.repository = repository;
        this.out = System.out;
    }
    
    /**
     * Create a log entry of everything in the general repository
     */
    public void log()
    {
        out.println("MstT      Thief 1      Thief 2      Thief 3      Thief 4      Thief 5      Thief 6");
        out.println("Stat    Stat S MD    Stat S MD    Stat S MD    Stat S MD    Stat S MD    Stat S MD");
        out.println("####    #### #  #    #### #  #    #### #  #    #### #  #    #### #  #    #### #  #");
        
        out.println("                   Assault party 1                       Assault party 2                       Museum");
        out.println("           Elem 1     Elem 2     Elem 3          Elem 1     Elem 2     Elem 3   Room 1  Room 2  Room 3  Room 4  Room 5");
        out.println("    RId  Id Pos Cv  Id Pos Cv  Id Pos Cv  RId  Id Pos Cv  Id Pos Cv  Id Pos Cv   NP DT   NP DT   NP DT   NP DT   NP DT");
        out.println("     #    #  ##  #   #  ##  #   #  ##  #   #    #  ##  #   #  ##  #   #  ##  #   ## ##   ## ##   ## ##   ## ##   ## ##");
        
        out.println("My friends, tonight's effort produced ## priceless paintings!");
        
        /*Legend:
        MstT Stat    – state of the master thief
        Thief # Stat - state of the ordinary thief # (# - 1 .. 6)
        Thief # S    – situation of the ordinary thief # (# - 1 .. 6) either 'W' (waiting to join a party) or 'P' (in party)
        Thief # MD   – maximum displacement of the ordinary thief # (# - 1 .. 6) a random number between 2 and 6
        Assault party # RId        – assault party # (# - 1,2) elem # (# - 1 .. 3) room identification (1 .. 5)
        Assault party # Elem # Id  – assault party # (# - 1,2) elem # (# - 1 .. 3) member identification (1 .. 6)
        Assault party # Elem # Pos – assault party # (# - 1,2) elem # (# - 1 .. 3) present position (0 .. DT RId)
        ssault party # Elem # Cv  – assault party # (# - 1,2) elem # (# - 1 .. 3) carrying a canvas (0,1)
        Museum Room # NP - room identification (1 .. 5) number of paintings presently hanging on the walls
        Museum Room # DT - room identification (1 .. 5) distance from outside gathering site, a random number between 15 and 30*/
    }
}
