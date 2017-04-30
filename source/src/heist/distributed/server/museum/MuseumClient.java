package heist.distributed.server.museum;

import heist.distributed.communication.Client;
import heist.interfaces.Museum;
import heist.room.Room;
import heist.utils.Address;

public class MuseumClient extends Client implements Museum
{
    public MuseumClient(Address address)
    {
        super(address);
    }

    @Override
    public boolean rollACanvas(int id) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Room[] getRooms() throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
