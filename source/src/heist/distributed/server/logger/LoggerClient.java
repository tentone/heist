package heist.distributed.server.logger;

import heist.distributed.communication.Client;
import heist.interfaces.Logger;
import heist.utils.Address;

public class LoggerClient extends Client implements Logger
{
    public LoggerClient(Address address)
    {
        super(address);
    }

    @Override
    public void debug(String message) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void log() throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void end() throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
