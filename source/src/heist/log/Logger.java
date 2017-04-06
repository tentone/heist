package heist.log;

import heist.Configuration;
import heist.GeneralRepository;
import heist.room.Room;
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
    /**
     * Repository to be logged.
     */
    private final GeneralRepository repository;
    
    /**
     * Configuration object.
     */
    private final Configuration configuration;
    
    /**
     * PrintStream used to write the log.
     */
    private PrintStream out;

    /**
     * Logger constructor from GeneralRepository and Configuration file.
     * Configuration file specifies where the log data is written to (can be written to System.out or to a file).
     * @param repository GeneralRepository to be logged.
     * @param configuration Configuration
     */
    public Logger(GeneralRepository repository, Configuration configuration)
    {
        this.repository = repository;
        this.configuration = configuration;
        this.out = System.out;
        
        if(this.configuration.logToFile)
        {
            try
            {
                PrintStream pw = new PrintStream(new File(this.configuration.logFile));
                this.out = pw;
            }
            catch(FileNotFoundException e){}
        }
    }

    /**
     * Write message directly to the PrintStream.
     * Flushes the PrintStream after every message.
     * @param message Message to display.
     */
    public synchronized void debug(String message)
    {
        if(this.configuration.debug)
        {
            out.println(message);
        }
    }
    
    /**
     * Create a log entry of everything in the general repository.
     * Flushes after log has been written.
     */
    public synchronized void log()
    {
        if(this.configuration.log)
        {
            if(configuration.partySize == 3 && configuration.numberThieves == 6 && configuration.numberRooms == 5)
            {
                MasterThief master = this.repository.getMasterThief();
                OrdinaryThief[] thieves = this.repository.getOrdinaryThieves();

                if(this.configuration.logHeader)
                {
                    out.print("\n\n\nMstT      ");
                    for(int i = 0; i < thieves.length; i++)
                    {
                        out.print("Thief " + i + "      ");
                    }

                    out.print("\nStat     ");
                    for(int i = 0; i < thieves.length; i++)
                    {
                        out.print("Stat S MD    ");
                    }
                }

                out.print("\n" + master.state() + "     ");//
                for(int i = 0; i < thieves.length; i++)
                {
                    out.printf("%4d %c %2d    ", thieves[i].state(), thieves[i].hasParty(), thieves[i].getDisplacement());
                }

                out.print("\n");


                AssaultParty[] parties = this.repository.getConcentrationSite().getParties();

                if(this.configuration.logHeader)
                {
                    out.print("\n               Assault party " + (parties[0] != null ? parties[0].getID() : 'X') + "                       Assault party " + (parties[1] != null ? parties[1].getID() : 'X') + "                       Museum");
                    out.print("\n       Elem 1     Elem 2     Elem 3          Elem 1     Elem 2     Elem 3   Room 1  Room 2  Room 3  Room 4  Room 5");
                    out.print("\nRId  Id Pos Cv  Id Pos Cv  Id Pos Cv  RId  Id Pos Cv  Id Pos Cv  Id Pos Cv   NP DT   NP DT   NP DT   NP DT   NP DT");
                    out.print("\n");
                }


                for(int i = 0; i < parties.length; i++)
                {
                    if(parties[i] == null)
                    {
                        out.print("-    -  -  -    -  -  -    -  -  -     ");
                    }
                    else
                    {
                        out.printf("%2d    ", parties[i].getTarget());

                        Iterator<OrdinaryThief> it = parties[i].getThieves();
                        for(int j = 0; j < 3; j++)
                        {
                            OrdinaryThief thief = it.next();
                            if(thief == null)
                            {
                                out.print(" -  -  -   ");
                            }
                            else
                            {
                                out.printf(" %d %2d  %d   ", thief.getID(), thief.getPosition(), thief.hasCanvas());
                            }
                        }
                    }
                }

                Room[] rooms = this.repository.getMuseum().getRooms();
                for(int i = 0; i < rooms.length; i++)
                {
                    out.printf("%2d %2d   ", rooms[i].getPaintings(), rooms[i].getDistance());
                }

                out.flush();
            }
            else
            {
                out.println("Run with default configuration to generate log");
            }
        }
    }
    
    /**
     * End the log and close the internal PrintStream.
     */
    public synchronized void end()
    {
        out.println("\nMy friends, tonight's effort produced " + this.repository.getCollectionSite().totalPaintingsStolen() + " priceless paintings!");
    }
}
