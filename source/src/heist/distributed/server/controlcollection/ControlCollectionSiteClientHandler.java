package heist.distributed.server.controlcollection;

import heist.concurrent.shared.SharedControlCollectionSite;
import heist.distributed.communication.ClientHandler;
import heist.distributed.communication.Message;
import java.io.IOException;
import java.net.Socket;

public class ControlCollectionSiteClientHandler extends ClientHandler
{
    private SharedControlCollectionSite controlCollection;
    
    public ControlCollectionSiteClientHandler(Socket socket, SharedControlCollectionSite controlCollection) throws IOException
    {
        super(socket);
        
        this.controlCollection = controlCollection;
    }

    @Override
    public void processMessage(Message msg) throws Exception
    {
        ControlCollectionSiteMessage message = (ControlCollectionSiteMessage) msg;
        int type = message.type;
        
        //if(type == )
        //{
        //}
    }
}
