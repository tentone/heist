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
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Logger object is used to create a detailed log of everything inside a GeneralRepository.
 * The logger is called by every active entity (MasterThief and OrdinaryThieves) after every change.
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
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(stream);
        int timestamp = 0;

        if(this.configuration.log)
        {
            //Thieves header
            if(this.configuration.logHeader)
            {
                out.print("\n\nMstT      ");
                for(int i = 0; i < this.thieves.length; i++)
                {
                    out.print("Thief " + i + "      ");
                }
                out.print("              VCk");
                
                
                out.print("\nStat     ");
                for(int i = 0; i < this.thieves.length; i++)
                {
                    out.print("Stat S MD    ");
                }
                out.print("M");
                for(int i = 0; i < this.thieves.length; i++)
                {
                    out.print("    " + i);
                }
            }
            
            //Master thief state
            if(this.master == null)
            {
                out.print("\n----     ");
            }
            else
            {
                out.print("\n" + this.master.state() + "     ");//
            }
            
            //Ordinary thieves state
            for(int i = 0; i < this.thieves.length; i++)
            {
                if(this.thieves[i] == null)
                {
                    out.printf("---- - --    ");
                }
                else
                {
                    out.printf("%4d %c %2d    ", this.thieves[i].state(), this.thieves[i].hasParty(), this.thieves[i].getDisplacement());
                }
            }
            
            //Vectorial clocks
            if(this.master == null)
            {
                out.print("---");
            }
            else
            {
                int time = this.master.getTime();
                timestamp += time;
                out.printf("%3d", time);
            }
            for(int i = 0; i < this.thieves.length; i++)
            {
                if(this.thieves[i] == null)
                {
                    out.printf("  ---");
                }
                else
                {
                    int time = this.thieves[i].getTime();
                    timestamp += time;
                    out.printf("  %3d", time);
                }
            }
            out.print("\n");
            
            //Assault party header
            if(this.configuration.logHeader)
            {
                out.print("\n");
                for(int i = 0; i < this.parties.length; i++)
                {
                    out.print("              Assault party " + (this.parties[i] != null ? this.parties[i].getID() : "--") + "        ");
                }
                out.print("                 Museum");

                out.print("\n");
                for(int i = 0; i < this.parties.length; i++)
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
                for(int i = 0; i < this.parties.length; i++)
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
            
            //Assault party state
            for(int i = 0; i < this.parties.length; i++)
            {
                if(this.parties[i].getState() == SharedAssaultParty.DISMISSED)
                {
                    out.print("--   ");
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        out.print("-- --  --  ");
                    }
                }
                else
                {
                    out.printf("%2d   ", this.parties[i].getTarget());

                    int[] thievesID = this.parties[i].getThieves();
                    
                    for(int j = 0; j < this.configuration.partySize; j++)
                    {
                        if(j < thievesID.length && this.thieves[thievesID[j]] != null)
                        {
                            OrdinaryThief thief = this.thieves[thievesID[j]];
                            out.printf("%2d %2d  %2d  ", thief.getID(), thief.getPosition(), thief.hasCanvas());
                        }
                        else
                        {
                            out.print("-- --  --  "); 
                        }
                    }
                }
            }
            
            //Museum state
            Room[] rooms = this.museum.getRooms();
            for(int i = 0; i < rooms.length; i++)
            {
                out.printf(" %2d %2d  ", rooms[i].getPaintings(), rooms[i].getDistance());
            }
            
            out.println("");
            out.flush();
        }
        
        if(this.configuration.logImmediate)
        {
            this.out.println(new String(stream.toByteArray(), StandardCharsets.UTF_8));
        }
        else
        {
            Log log = new Log(new String(stream.toByteArray(), StandardCharsets.UTF_8), timestamp);
            this.list.push(log);
        }
    }
    
    /**
     * End the log and close the internal PrintStream.
     * @throws Exception Exception
     */
    public synchronized void end() throws Exception
    {
        int paintings = this.controlCollection.totalPaintingsStolen();
        
        //Sort and print log entries
        if(!this.configuration.logImmediate)
        {
            this.list.sort(null);

            java.util.Iterator<Log> it = this.list.iterator();
            while(it.hasNext())
            {
                this.out.println(it.next().message);
            }
        }
        
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
