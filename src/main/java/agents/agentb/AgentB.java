package agents.agentb;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import furniture.Furniture;
import simulation.*;
import agents.agentb.continualassistants.*;


//meta! id="6"
public class AgentB extends OSPABA.Agent {
    private SimQueue<Furniture> queueSkladania;
    private WStat dlzkaSkladaniaQueueStat;

    public AgentB(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        dlzkaSkladaniaQueueStat = new WStat(mySim());
        queueSkladania = new SimQueue<>(dlzkaSkladaniaQueueStat);

        addOwnMessage(Mc.koniecSkladania);

    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerB(Id.managerB, mySim(), this);
        new ProcessSkladania(Id.processSkladania, mySim(), this);
        addOwnMessage(Mc.skladanie);
        addOwnMessage(Mc.presun);
    }
    //meta! tag="end"

    public SimQueue<Furniture> getQueueSkladania() {
		return queueSkladania;
	}
}