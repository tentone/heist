package heist.distributed.test;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.server.museum.MuseumServer;

public class MuseumServerTest
{
    public static void main(String[] args) throws Exception
    {
        new MuseumServer(new ConfigurationDistributed()).start();
    }
}
