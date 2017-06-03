package heist.distributed.run;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.assaultparty.AssaultPartyServer;

public class AssaultPartyServerDistributed
{
    public static void main(String[] args)
    {
        if(args.length < 1)
        {
            System.out.println("Error: Missing AssaultParty id as argument");
        }
        int id = Integer.parseInt(args[0]);
        
        try
        {
            ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile("configuration.txt");
            
            System.out.println("Info: Starting AssaultParty " + id);
            new AssaultPartyServer(id, configuration).start();
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in AssaultParty " + id);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
