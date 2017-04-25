package heist;

import heist.queue.LinkedQueue;
import heist.room.Room;
import heist.queue.iterator.Iterator;
import heist.concurrent.shared.AssaultParty;
import heist.concurrent.thief.MasterThief;
import heist.concurrent.thief.OrdinaryThief;
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
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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
    
    public int c = 0;
    
    /**
     * Create a log entry of everything in the general repository.
     * Flushes after log has been written.
     */
    public synchronized void log()
    {
        out.println(c++);
        out.flush();
        
        if(this.configuration.log)
        {
            MasterThief master = this.repository.getMasterThief();
            OrdinaryThief[] thieves = this.repository.getOrdinaryThieves();
            AssaultParty[] parties = this.repository.getConcentrationSite().getParties();
            
            if(this.configuration.logHeader)
            {
                out.print("\n\nMstT      ");
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

            if(this.configuration.logHeader)
            {
                out.print("\n");
                for(int i = 0; i < parties.length; i++)
                {
                    out.print("              Assault party " + (parties[i] != null ? parties[i].getID() : "--") + "        ");
                }
                out.print("                 Museum");

                out.print("\n");
                for(int i = 0; i < parties.length; i++)
                {
                    out.print("   ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        out.print("     Elem " + j);
                    }
                }
                
                out.print("   ");
                for(int j = 0; j < this.configuration.numberRooms; j++)
                {
                    out.print("  Room " + j);
                }

                out.print("\n");
                for(int i = 0; i < parties.length; i++)
                {
                    out.print("RId  ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        out.print("Id Pos Cv  ");
                    }
                }

                for(int j = 0; j < this.configuration.numberRooms; j++)
                {
                    out.print(" NP DT  ");
                }

                out.print("\n");
            }


            for(int i = 0; i < parties.length; i++)
            {
                if(parties[i].getState() == AssaultParty.DISMISSED)
                {
                    out.print("--   ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        out.print("-- --  --  ");
                    }
                }
                else
                {
                    out.printf("%2d   ", parties[i].getTarget());

                    Iterator<OrdinaryThief> it = parties[i].getThieves();
                    for(int j = 0; j < 3; j++)
                    {
                        OrdinaryThief thief = it.next();
                        if(thief == null)
                        {
                            out.print("-- --  --  ");
                        }
                        else
                        {
                            out.printf("%2d %2d  %2d  ", thief.getID(), thief.getPosition(), thief.hasCanvas());
                        }
                    }
                }
            }

            Room[] rooms = this.repository.getMuseum().getRooms();
            for(int i = 0; i < rooms.length; i++)
            {
                out.printf(" %2d %2d  ", rooms[i].getPaintings(), rooms[i].getDistance());
            }
            out.println("");
            
            out.flush();
        }
    }
    
    /**
     * End the log and close the internal PrintStream.
     */
    public synchronized void end()
    {
        out.println("\nMy friends, tonight's effort produced " + this.repository.getCollectionSite().totalPaintingsStolen() + " priceless paintings!");
        out.close();
    }
    
    /**
     * Compares two strings with the same length and returns an array of indexes where the chars are different.
     * @param a String to be compared.
     * @param b String to be compared.
     * @return Array of indexed where the strings are different
     * 
     */
    public int[] compareStrings(String a, String b)
    {
        if(a.length() != b.length())
        {
            return null;
        }
        
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        
        for(int i = 0; i < a.length(); i++)
        {
            if(a.charAt(i) != b.charAt(i))
            {
                queue.push(i);
            }
        }
        
        Iterator<Integer> it = queue.iterator();
        int[] array = new int[queue.size()];
        for(int i = 0;i < array.length; i++)
        {
            array[i] = it.next();
        }
        
        return array;
    }
}