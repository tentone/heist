package heist;

public class Heist
{
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration();
        
        GeneralRepository repository = new GeneralRepository(configuration);
        repository.start();
    } 
}
