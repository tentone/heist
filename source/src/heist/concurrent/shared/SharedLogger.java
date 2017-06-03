package heist.concurrent.shared;

import heist.Configuration;
import heist.interfaces.AssaultParty;
import heist.interfaces.ControlCollectionSite;
import heist.queue.LinkedQueue;
import heist.room.Room;
import heist.queue.iterator.Iterator;
import heist.thief.MasterThief;
import heist.thief.OrdinaryThief;
import heist.interfaces.Logger;
import heist.interfaces.Museum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.LinkedList;
import heist.utils.Log;

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
public class SharedLogger implements Logger, Serializable
{
    private static final long serialVersionUID = 46213846221194000L;
    
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
     * Configuration object.
     */
    private Configuration configuration;
    
    /**
     * Museum to be assaulted by AssaultParties.
     */
    private Museum museum;
    
    /**
     * AssaultParties to be used in the simulation.
     */
    private AssaultParty[] parties;
    
    /**
     * MasterThieve that controls and assigns OrdinaryThieves to AssaultParties.
     */
    private MasterThief master;
    
    /**
     * OrdinaryThieves array
     */
    private OrdinaryThief[] thieves;
    
    /**
     * CollectionSite
     */
    private ControlCollectionSite controlCollection;
    
    /**
     * PrintStream used to output logging data.
     */
    private PrintStream out;
    
    /**
     * Queue with all messages waiting to be sorted.
     */
    private LinkedList<Log> list;
    
    /**
     * Logger constructor Configuration file.
     * Configuration file specifies where the log data is written to (can be written to this.out or to a file).
     * @param parties AssaultParties
     * @param museum Museum
     * @param controlCollection ControlCollectionSite
     * @param configuration Configuration
     */
    public SharedLogger(AssaultParty[] parties, Museum museum, ControlCollectionSite controlCollection, Configuration configuration)
    {
        this.configuration = configuration;
        
        this.thieves = new OrdinaryThief[configuration.numberThieves];
        this.master = null;
        this.parties = parties;
        this.museum = museum;
        this.controlCollection = controlCollection;
        
        this.list = new LinkedList<>();
        
        if(this.configuration.logToFile)
        {
            try
            {
                this.out = new PrintStream(new File(this.configuration.logFile));
            }
            catch(FileNotFoundException e){}
        }
        else
        {
            this.out = System.out;
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
            this.out.println(message);
        }
    }
    
    /**
     * Updates the MasterThief information and creates new log entry.
     * @param master MasterThief
     */
    @Override
    public synchronized void log(MasterThief master) throws Exception
    {
        this.master = master;
        this.log();
    }
    
    /**
     * Updates the OrdinaryThief information and creates new log entry.
     * @param thief OrdinaryThief
     */
    @Override
    public synchronized void log(OrdinaryThief thief) throws Exception
    {        
        this.thieves[thief.getID()] = thief;
        this.log();
    }
    
    /**
     * Create a log entry of everything
     * Flushes after log has been written.
     * @throws Exception Exception
     */
    private void log() throws Exception
    {        
        if(this.configuration.log)
        {
            //Thieves header
            if(this.configuration.logHeader)
            {
                this.out.print("\n\nMstT      ");
                for(int i = 0; i < this.thieves.length; i++)
                {
                    this.out.print("Thief " + i + "      ");
                }
                this.out.print("              VCk");
                
                
                this.out.print("\nStat     ");
                for(int i = 0; i < this.thieves.length; i++)
                {
                    this.out.print("Stat S MD    ");
                }
                this.out.print("M");
                for(int i = 0; i < this.thieves.length; i++)
                {
                    this.out.print("    " + i);
                }
            }
            
            //Master thief state
            if(this.master == null)
            {
                this.out.print("\n----     ");
            }
            else
            {
                this.out.print("\n" + this.master.state() + "     ");//
            }
            
            //Ordinary thieves state
            for(int i = 0; i < this.thieves.length; i++)
            {
                if(this.thieves[i] == null)
                {
                    this.out.printf("---- - --    ");
                }
                else
                {
                    this.out.printf("%4d %c %2d    ", this.thieves[i].state(), this.thieves[i].hasParty(), this.thieves[i].getDisplacement());
                }
            }
            
            //Vectorial clocks
            if(this.master == null)
            {
                this.out.print("---");
            }
            else
            {
                this.out.printf("%3d", this.master.getTime());
            }
            for(int i = 0; i < this.thieves.length; i++)
            {
                if(this.thieves[i] == null)
                {
                    this.out.printf("  ---");
                }
                else
                {
                    this.out.printf("  %3d", this.thieves[i].getTime());
                }
            }
            this.out.print("\n");
            
            //Assault party header
            if(this.configuration.logHeader)
            {
                this.out.print("\n");
                for(int i = 0; i < this.parties.length; i++)
                {
                    this.out.print("              Assault party " + (this.parties[i] != null ? this.parties[i].getID() : "--") + "        ");
                }
                this.out.print("                 Museum");

                this.out.print("\n");
                for(int i = 0; i < this.parties.length; i++)
                {
                    this.out.print("   ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        this.out.print("     Elem " + j);
                    }
                }
                
                this.out.print("   ");
                for(int j = 0; j < this.configuration.numberRooms; j++)
                {
                    this.out.print("  Room " + j);
                }

                this.out.print("\n");
                for(int i = 0; i < this.parties.length; i++)
                {
                    this.out.print("RId  ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        this.out.print("Id Pos Cv  ");
                    }
                }

                for(int j = 0; j < this.configuration.numberRooms; j++)
                {
                    this.out.print(" NP DT  ");
                }

                this.out.print("\n");
            }
            
            //Assault party state
            for(int i = 0; i < this.parties.length; i++)
            {
                if(this.parties[i].getState() == SharedAssaultParty.DISMISSED)
                {
                    this.out.print("--   ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        this.out.print("-- --  --  ");
                    }
                }
                else
                {
                    this.out.printf("%2d   ", this.parties[i].getTarget());

                    int[] thievesID = this.parties[i].getThieves();
                    
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        if(j < thievesID.length && this.thieves[thievesID[j]] != null)
                        {
                            OrdinaryThief thief = this.thieves[thievesID[j]];
                            this.out.printf("%2d %2d  %2d  ", thief.getID(), thief.getPosition(), thief.hasCanvas());
                        }
                        else
                        {
                            this.out.print("-- --  --  "); 
                        }
                    }
                }
            }
            
            //Museum state
            Room[] rooms = this.museum.getRooms();
            for(int i = 0; i < rooms.length; i++)
            {
                this.out.printf(" %2d %2d  ", rooms[i].getPaintings(), rooms[i].getDistance());
            }
            
            this.out.println("");
            this.out.flush();
        }
    }
    
    /**
     * End the log and close the internal PrintStream.
     * @throws Exception Exception
     */
    public synchronized void end() throws Exception
    {
        int paintings = this.controlCollection.totalPaintingsStolen();
        
        System.out.println("Info: My friends, tonight's effort produced " + paintings + " priceless paintings!");
        
        this.out.println("\nMy friends, tonight's effort produced " + paintings + " priceless paintings!");
        this.out.close();
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
    
    /**
     * The writeObject method is called on serialization and is used to override the default java serialization.
     * @param out ObjectOutputStream used on serialization.
     * @throws IOException Exception may be thrown. 
     */
    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.writeObject(this.configuration);
        out.writeObject(this.thieves);
        out.writeObject(this.master);
        out.writeObject(this.parties);
        out.writeObject(this.museum);
        out.writeObject(this.controlCollection);
    }
    
    /**
     * The writeObject method is called when rebuilding the object from serialized data.
     * @param in ObjectInputStream used on serialization.
     * @throws IOException Exception may be thrown. 
     * @throws ClassNotFoundException Exception may be thrown. 
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        this.configuration = (Configuration) in.readObject();
        this.thieves = (OrdinaryThief[]) in.readObject();
        this.master = (MasterThief) in.readObject();
        this.parties = (AssaultParty[]) in.readObject();
        this.museum = (Museum) in.readObject();
        this.controlCollection = (ControlCollectionSite) in.readObject();
    }
}
