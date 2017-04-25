package heist.interfaces;

public interface AssaultParty
{
    /**
     * Called by the MasterThief to send this party to the museum.
     * Party can start crawling after this method was called.
     * @throws java.lang.Exception A exception may be thrown depending on the implementation.
     */
    public void sendParty() throws Exception;
}
