package heist.concurrent;

import heist.Configuration;
import heist.thief.OrdinaryThief;

/**
 * Concurrent version of the Heist.
 * Runs locally on a single computer.
 * @author Jose Manuel
 */
public class HeistConcurrent
{
    public static void main(String[] args) throws InterruptedException, Exception
    {
        GeneralRepository repository = new GeneralRepository(new Configuration());
        
        OrdinaryThief[] thieves = repository.getOrdinaryThieves();
        for(int i = 0; i < thieves.length; i++)
        {
            thieves[i].start();
        }

        repository.getMasterThief().start();
    }
}
