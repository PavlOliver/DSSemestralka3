package agents.agenta.continualassistants;

import OSPABA.*;
import OSPRNG.TriangularRNG;
import simulation.*;
import agents.agenta.*;
import OSPABA.Process;
import worker.WorkerState;

//meta! id="33"
public class ProcessPripravy extends OSPABA.Process {
    private final TriangularRNG preparationTimeRNG = new TriangularRNG(300d, 900d, 500d, ((MySimulation) mySim()).getSeedGenerator());

    public ProcessPripravy(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentA", id="34", type="Start"
    public void processStart(MessageForm message) {
        ((MyMessage) message).getWorker().setAction(WorkerState.PREPARING);
        message.setCode(Mc.pripravaMaterialu);
        hold(preparationTimeRNG.sample() * 1000, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.pripravaMaterialu -> {
                message.setAddressee(myAgent());
                assistantFinished(message);
            }
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentA myAgent() {
        return (AgentA) super.myAgent();
    }

}