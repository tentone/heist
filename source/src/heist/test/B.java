package heist.test;

class B extends Thread
{
    SharedZone shared;
    
    public B(SharedZone shared)
    {
        this.shared = shared;
    }
    
    @Override
    public void run()
    {
        shared.methodB();
    }
}