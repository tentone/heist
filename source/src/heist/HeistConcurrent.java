package heist;

/**
 * Concurrent version of the Heist.
 * Runs locally on a single computer.
 * @author Tentone
 */
public class HeistConcurrent
{
    public static void main(String[] args) throws InterruptedException
    {
        Configuration configuration = new Configuration();
        
        GeneralRepository repository = new GeneralRepository(configuration);
        repository.start();
    } 
}
