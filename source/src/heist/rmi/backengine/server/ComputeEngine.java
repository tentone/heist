package heist.rmi.backengine.server;

import heist.rmi.backengine.interfaces.Compute;
import heist.rmi.backengine.interfaces.Task;

/**
 * This data type defines a generic functionality that will run mobile code.
 * Communication is based in Java RMI.
 */
public class ComputeEngine implements Compute
{
    /**
     * Execution of remote code.
     * @param t code to be executed remotely
     * @return return value of the invocation of method execute of t
     */
    @Override
    public Object executeTask(Task t)
    {
        return t.execute ();
    }
}
