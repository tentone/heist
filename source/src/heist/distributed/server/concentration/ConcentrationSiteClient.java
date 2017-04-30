package heist.distributed.server.concentration;

import heist.distributed.ConfigurationDistributed;
import heist.distributed.communication.Client;
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
        //TODO <ADD CODE HERE>
    }

    @Override
    public void prepareExcursion(OrdinaryThief thief) throws Exception
    {
        //TODO <ADD CODE HERE>
    }
}
