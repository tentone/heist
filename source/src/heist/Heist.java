package heist;

public class Heist
{
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration();
        
        new GeneralRepository(30, 9, 3).start();
    } 
}
