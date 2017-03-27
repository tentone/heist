package heist.log;

import heist.Configuration;
import heist.GeneralRepository;
import heist.Room;
import heist.queue.Iterator;
import heist.shared.AssaultParty;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Logger object is used to create a detailed log of everything inside a GeneralRepository.
 * The logger is called by every active entity (MasterThief and OrdinaryThieves) after every change.
 * 
 * MstT Stat    – state of the master thief
 * Thief # Stat - state of the ordinary thief # (# - 1 .. 6)
 * Thief # S    – situation of the ordinary thief # (# - 1 .. 6) either 'W' (waiting to join a party) or 'P' (in party)
 * Thief # MD   – maximum displacement of the ordinary thief # (# - 1 .. 6) a random number between 2 and 6
 * Assault party # RId        – assault party # (# - 1,2) elem # (# - 1 .. 3) room identification (1 .. 5)
 * Assault party # Elem # Id  – assault party # (# - 1,2) elem # (# - 1 .. 3) member identification (1 .. 6)
 * Assault party # Elem # Pos – assault party # (# - 1,2) elem # (# - 1 .. 3) present position (0 .. DT RId)
 * Assault party # Elem # Cv  – assault party # (# - 1,2) elem # (# - 1 .. 3) carrying a canvas (0,1)
 * Museum Room # NP - room identification (1 .. 5) number of paintings presently hanging on the walls
 * Museum Room # DT - room identification (1 .. 5) distance from outside gathering site, a random number between 15 and 30
 * @author Jose Manuel
 */
public class Logger
{
    private final static boolean DEBUG = false;
    private final GeneralRepository repository;
    private final Configuration configuration;
    private PrintStream out;
    
    /**
     * Constructor from repository and specific PrintStream object to be used to display log messages as they are created.
     * @param repository GeneralRepository to be logged.
     * @param file File name for log.
     */
    public Logger(GeneralRepository repository, Configuration configuration, String file)
    {
        this.repository = repository;
        this.configuration = configuration;
        this.out = System.out;
        
        try
        {
            PrintStream pw = new PrintStream(new File(file));
            this.out = pw;
        }
        catch(FileNotFoundException e){}
    }
    
    /**
     * Constructor from general repository object that uses System.out as PrintStream to display log messages.
     * @param repository GeneralRepository to be logged.
     */
    public Logger(GeneralRepository repository, Configuration configuration)
    {
        this.repository = repository;
        this.configuration = configuration;
        this.out = System.out;
    }

    /**
     * Write message directly to the PrintStream.
     * Flushes the PrintStream after every message.
     * @param message Message to display.
     */
    public synchronized void debug(String message)
    {
        if(Logger.DEBUG)
        {
            out.println(message);
            out.flush();
        }
    }
    
    /**
     * Create a log entry of everything in the general repository.
     * Flushes after log has been written.
     */
    public synchronized void log()
    {
        MasterThief master = this.repository.getMasterThief();
        OrdinaryThief[] thieves = this.repository.getOrdinaryThieves();

        out.print("MstT      ");
        for(int i = 0; i < thieves.length; i++)
        {
            out.print("Thief " + i + "      ");
        }
        
        out.print("\nStat     ");
        for(int i = 0; i < thieves.length; i++)
        {
            out.print("Stat S MD    ");
        }

        out.print("\n" + master.state() + "     ");//
        for(int i = 0; i < thieves.length; i++)
        {
            out.printf("%4d %c %2d    ", thieves[i].state(), thieves[i].hasParty(), thieves[i].getDisplacement());
        }
        out.print("\n");
        out.print("\n               Assault party 1                       Assault party 2                       Museum");
        out.print("\n       Elem 1     Elem 2     Elem 3          Elem 1     Elem 2     Elem 3   Room 1  Room 2  Room 3  Room 4  Room 5");
        out.print("\nRId  Id Pos Cv  Id Pos Cv  Id Pos Cv  RId  Id Pos Cv  Id Pos Cv  Id Pos Cv   NP DT   NP DT   NP DT   NP DT   NP DT");
        out.print("\n");
        
        Iterator<AssaultParty> itp = this.repository.getConcentrationSite().getParties();
        for(int i = 0; i < 2; i++)
        {
            AssaultParty party = itp.next();
            if(party == null)
            {
                out.print("-    -  -  -    -  -  -    -  -  -     ");
            }
            else
            {
                out.printf("%2d    ", party.getTarget());
                
                Iterator<OrdinaryThief> itt = party.getThieves();
                for(int j = 0; j < 3; j++)
                {
                    OrdinaryThief thief = itt.next();
                    if(thief == null)
                    {
                        out.print(" -  -  -   ");
                    }
                    else
                    {
                        out.printf(" %d  %2d  %d   ", thief.getID(), thief.getPosition(), thief.hasCanvas());
                    }
                }
            }
        }
        
        Room[] rooms = this.repository.getMuseum().getRooms();
        for(int i = 0; i < rooms.length; i++)
        {
            out.printf("%2d %2d   ", rooms[i].getPaintings(), rooms[i].getDistance());
        }
        
        out.print("\n");
        out.flush();
    }
    
    /**
     * End the log and close the internal PrintStream.
     */
    public synchronized void end()
    {
        out.println("\nMy friends, tonight's effort produced " + this.repository.getCollectionSite().totalPaintingsStolen() + " priceless paintings!");
        out.close();
    }
}
