package agents.agentc;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import agents.agentc.continualassistants.*;
import furniture.Furniture;
import furniture.Furnitures;
import simulation.*;



//meta! id="7"
public class AgentC extends OSPABA.Agent {
    private SimQueue<Furniture> queueMorenia;
    private WStat dlykaMorenieQueueStat;

    public AgentC(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        dlykaMorenieQueueStat = new WStat(mySim());
        queueMorenia = new SimQueue<>(dlykaMorenieQueueStat);
        addOwnMessage(Mc.koniecMorenia);
        addOwnMessage(Mc.koniecLakovania);
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

    public SimQueue<Furniture> getQueueMorenia() {
		return queueMorenia;
	}

}