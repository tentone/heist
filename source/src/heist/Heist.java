package heist;

public class Heist
{
    public static void main(String[] args) throws InterruptedException
    {
        Configuration configuration = new Configuration();
        
        GeneralRepository repository = new GeneralRepository(configuration);
        repository.start();
    } 
}
