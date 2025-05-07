package agents.agentc;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import agents.agentc.continualassistants.*;
import furniture.Furniture;
import simulation.*;

import java.util.PriorityQueue;


//meta! id="7"
public class AgentC extends OSPABA.Agent {

    public AgentC(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        addOwnMessage(Mc.koniecMorenia);
        addOwnMessage(Mc.koniecLakovania);
        addOwnMessage(Mc.koniecKovania);
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerC(Id.managerC, mySim(), this);
		new ProcessKovanie(Id.processKovanie, mySim(), this);
		new ProcessMorenie(Id.processMorenie, mySim(), this);
		new ProcessLakovanie(Id.processLakovanie, mySim(), this);
		addOwnMessage(Mc.kovanie);
		addOwnMessage(Mc.morenie);
		addOwnMessage(Mc.presun);
	}
	//meta! tag="end"

}