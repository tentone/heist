package heist.distributed.run;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.museum.MuseumServer;

public class MuseumServerDistributed
{
    public static void main(String[] args)
    {
        try
        {
            ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile("configuration.txt");

            new MuseumServer(configuration).start();
        }
        catch(Exception e)
        {
            System.out.println("Error: Error in MuseumServer");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
