package heist.distributed.server.museum;

import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import heist.interfaces.Museum;
import java.io.IOException;
import java.net.Socket;

public class MuseumClientHandler extends ClientHandler
{
    private final Museum museum;
    
    public MuseumClientHandler(Socket socket, Museum museum) throws IOException
    {
        super(socket);
        
        this.museum = museum;
    }

    @Override
    public void processMessage(Message msg) throws Exception
    {
        MuseumMessage message = (MuseumMessage) msg;
        MuseumMessage response = new MuseumMessage(Message.DEFAULT);
        int type = message.type;
        
        if(type == MuseumMessage.ROLL_A_CANVAS)
        {
            response.gotCanvas = this.museum.rollACanvas(message.roomID);
        }
        else if(type == MuseumMessage.GET_ROOMS)
        {
            response.rooms = this.museum.getRooms();
        }
        else
        {
            response.status = Message.ERROR;
        }
        
        this.sendMessage(response);
    }
}
