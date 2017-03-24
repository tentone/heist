package heist.test;

public class SharedZone
{
    public synchronized void methodA() throws InterruptedException
    {
        System.out.println("A: Going to sleep");
        
        this.wait();

        System.out.println("A: Good morning");
    }

    public synchronized void methodB()
    {
        System.out.println("B: Wake up your lazy fuck");
        this.notify();
    }		
}