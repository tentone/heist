package heist.rmi.implementation;

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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
public class LoggerRMI extends UnicastRemoteObject implements Logger, Serializable
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
    
    /*
     * PrintStream used to output logging data.
     */
    //private PrintStream out;

    /**
     * Logger constructor Configuration file.
     * Configuration file specifies where the log data is written to (can be written to System.out or to a file).
     * @param parties AssaultParties
     * @param museum Museum
     * @param controlCollection ControlCollectionSite
     * @param configuration Configuration
     */
    public LoggerRMI(AssaultParty[] parties, Museum museum, ControlCollectionSite controlCollection, Configuration configuration) throws RemoteException
    {
        super();
        
        this.configuration = configuration;
        
        this.thieves = new OrdinaryThief[configuration.numberThieves];
        this.master = null;
        this.parties = parties;
        this.museum = museum;
        this.controlCollection = controlCollection;
        
        /*if(this.configuration.logToFile)
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
        }*/
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
            System.out.println(message);
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
            if(this.configuration.logHeader)
            {
                System.out.print("\n\nMstT      ");
                for(int i = 0; i < this.thieves.length; i++)
                {
                    System.out.print("Thief " + i + "      ");
                }

                System.out.print("\nStat     ");
                for(int i = 0; i < this.thieves.length; i++)
                {
                    System.out.print("Stat S MD    ");
                }
            }

            if(this.master == null)
            {
                System.out.print("\n----     ");
            }
            else
            {
                System.out.print("\n" + this.master.state() + "     ");//
            }
            
            
            for(int i = 0; i < this.thieves.length; i++)
            {
                if(this.thieves[i] == null)
                {
                    System.out.printf("---- -- --    ");
                }
                else
                {
                    System.out.printf("%4d %c %2d    ", this.thieves[i].state(), this.thieves[i].hasParty(), this.thieves[i].getDisplacement());
                }
            }
            System.out.print("\n");

            if(this.configuration.logHeader)
            {
                System.out.print("\n");
                for(int i = 0; i < this.parties.length; i++)
                {
                    System.out.print("              Assault party " + (this.parties[i] != null ? this.parties[i].getID() : "--") + "        ");
                }
                System.out.print("                 Museum");

                System.out.print("\n");
                for(int i = 0; i < this.parties.length; i++)
                {
                    System.out.print("   ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        System.out.print("     Elem " + j);
                    }
                }
                
                System.out.print("   ");
                for(int j = 0; j < this.configuration.numberRooms; j++)
                {
                    System.out.print("  Room " + j);
                }

                System.out.print("\n");
                for(int i = 0; i < this.parties.length; i++)
                {
                    System.out.print("RId  ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        System.out.print("Id Pos Cv  ");
                    }
                }

                for(int j = 0; j < this.configuration.numberRooms; j++)
                {
                    System.out.print(" NP DT  ");
                }

                System.out.print("\n");
            }

            for(int i = 0; i < this.parties.length; i++)
            {
                if(this.parties[i].getState() == AssaultPartyRMI.DISMISSED)
                {
                    System.out.print("--   ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        System.out.print("-- --  --  ");
                    }
                }
                else
                {
                    System.out.printf("%2d   ", this.parties[i].getTarget());

                    int[] thievesID = this.parties[i].getThieves();
                    
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        if(j < thievesID.length)
                        {
                            OrdinaryThief thief = this.thieves[thievesID[j]];
                            System.out.printf("%2d %2d  %2d  ", thief.getID(), thief.getPosition(), thief.hasCanvas());
                        }
                        else
                        {
                            System.out.print("-- --  --  "); 
                        }
                    }
                }
            }

            Room[] rooms = this.museum.getRooms();
            for(int i = 0; i < rooms.length; i++)
            {
                System.out.printf(" %2d %2d  ", rooms[i].getPaintings(), rooms[i].getDistance());
            }
            
            System.out.println("");
            System.out.flush();
        }
    }
    
    /**
     * End the log and close the internal PrintStream.
     * @throws Exception Exception
     */
    public synchronized void end() throws Exception
    {
        System.out.println("\nMy friends, tonight's effort produced " + this.controlCollection.totalPaintingsStolen() + " priceless paintings!");
        System.out.close();
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
