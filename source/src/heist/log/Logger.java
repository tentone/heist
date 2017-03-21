package heist.log;

import heist.GeneralRepository;
import heist.struct.Queue;
import java.io.PrintStream;

public class Logger
{
    private Queue<String> log;
    private GeneralRepository repository;
    private PrintStream stream;
    
    public Logger(GeneralRepository repository, PrintStream stream)
    {
        this.log = new Queue<>();
        this.repository = repository;
        this.stream = stream;
    }
    
    public Logger(GeneralRepository repository)
    {
        this.log = new Queue<>();
        this.repository = repository;
        this.stream = System.out;
    }
    
    //Create a log of everything in the general repository
    public void log()
    {
        //TODO <ADD CODE HERE>
    }
}
