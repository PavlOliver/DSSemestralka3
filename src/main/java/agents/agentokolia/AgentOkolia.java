package agents.agentokolia;

import OSPABA.*;
import simulation.*;
import agents.agentokolia.continualassistants.*;



//meta! id="2"
public class AgentOkolia extends OSPABA.Agent {
    public AgentOkolia(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        addOwnMessage(Mc.prichodObjednavky);
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerOkolia(Id.managerOkolia, mySim(), this);
		new PlanovacPrichodov(Id.planovacPrichodov, mySim(), this);
		addOwnMessage(Mc.inicializacia);
	}
	//meta! tag="end"
}