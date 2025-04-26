package agents.agentpresunov;

import OSPABA.*;
import simulation.*;
import agents.agentpresunov.continualassistants.*;


//meta! id="5"
public class AgentPresunov extends OSPABA.Agent {
    public AgentPresunov(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        addOwnMessage(Mc.presunDoSkladu);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerPresunov(Id.managerPresunov, mySim(), this);
        new ProcessPresunSklad(Id.processPresunSklad, mySim(), this);
        new ProcessPresunPM(Id.processPresunPM, mySim(), this);
        addOwnMessage(Mc.presun);
    }
    //meta! tag="end"
}