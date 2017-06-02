package heist.rmi.backengine.interfaces;

import java.io.Serializable;

/**
 * This data type defines the operational interface of objects passed as a parameter to the method executeTask of a remote object of type ComputeEngine.
 * This is an example of mobile code.
 */
public interface Task extends Serializable
{
    /**
     * Code execution.
     * @return whatever it is be returned
     */

    Object execute();
}
