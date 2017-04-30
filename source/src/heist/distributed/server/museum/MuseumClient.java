package heist.distributed.server.museum;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Client;
import heist.interfaces.Museum;
import heist.room.Room;

public class MuseumClient extends Client implements Museum
{
    public MuseumClient(ConfigurationDistributed configuration)
    {
        super(configuration.museumServer);
    }

    @Override
    public boolean rollACanvas(int id) throws Exception
    {
        MuseumMessage send = new MuseumMessage(MuseumMessage.ROLL_A_CANVAS);
        send.roomID = id;
        
        MuseumMessage response = (MuseumMessage) this.sendMessage(send);
        
        return response.gotCanvas;
    }

    @Override
    public Room[] getRooms() throws Exception
    {
        MuseumMessage send = new MuseumMessage(MuseumMessage.GET_ROOMS);
        
        MuseumMessage response = (MuseumMessage) this.sendMessage(send);
        
        return response.rooms;
    }
    
}
