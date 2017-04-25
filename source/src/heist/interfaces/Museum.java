package heist.interfaces;

public interface Museum
{
    /**
     * Called by the OrdinaryThieves to roll a canvas.
     * @param id Room id from where the Thief is trying to get a canvas.
     * @return True if was able to get a canvas, false if the room was already empty
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public boolean rollACanvas(int id) throws Exception;
}