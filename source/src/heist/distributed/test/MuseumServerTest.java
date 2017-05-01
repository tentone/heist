package heist.distributed.test;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.museum.MuseumServer;

public class MuseumServerTest
{
    public static void main(String[] args) throws Exception
    {
        ConfigurationDistributed configuration = ConfigurationDistributed.readFromFile();
        
        new MuseumServer(configuration).start();
    }
}
