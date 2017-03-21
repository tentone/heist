package heist.debug;

import heist.GeneralRepository;
import heist.struct.Queue;

public class Logger
{
    private Queue<String> log;
    private GeneralRepository repository;
    
    public Logger(GeneralRepository repository)
    {
        this.log = new Queue<>();
        this.repository = repository;
    }
    
    public void update()
    {
        //TODO <ADD CODE HERE>
    }
}
