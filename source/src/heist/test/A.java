package heist.test;

import java.util.logging.Level;
import java.util.logging.Logger;

class A extends Thread
{
    SharedZone shared;
    
    public A(SharedZone shared)
    {
        this.shared = shared;
    }
    
    @Override
    public void run()
    {
        try {
            shared.methodA();
        } catch (InterruptedException ex) {
            Logger.getLogger(A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}