package heist.distributed.server.concentration;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Client;
import heist.distributed.communication.Message;
import heist.interfaces.ConcentrationSite;
import heist.thief.OrdinaryThief;

public class ConcentrationSiteClient extends Client implements ConcentrationSite
{
    public ConcentrationSiteClient(ConfigurationDistributed configuration)
    {
        super(configuration.concentrationServer);
    }

    @Override
    public void fillAssaultParty(int party) throws Exception
    {
        ConcentrationSiteMessage message = new ConcentrationSiteMessage(ConcentrationSiteMessage.FILL_ASSAULT_PARTY);
        message.partyID = party;
        
        this.sendMessage(message);
    }

    @Override
    public int prepareExcursion(OrdinaryThief thief) throws Exception
    {
        ConcentrationSiteMessage message = new ConcentrationSiteMessage(ConcentrationSiteMessage.PREPARE_EXCURSION);
        message.thief = thief;
        
        ConcentrationSiteMessage response = (ConcentrationSiteMessage) this.sendMessage(message);
        thief.copyState(response.thief);
        
        return response.partyID;
    }
    
    @Override
    public void end() throws Exception
    {
        ConcentrationSiteMessage message = new ConcentrationSiteMessage(Message.END);
        this.sendMessage(message);
    }
}
