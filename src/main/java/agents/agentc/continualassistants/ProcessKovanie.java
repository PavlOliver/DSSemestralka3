package agents.agentc.continualassistants;

import OSPABA.*;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;
import simulation.*;
import agents.agentc.*;
import OSPABA.Process;
import worker.WorkerState;

//meta! id="47"
public class ProcessKovanie extends OSPABA.Process {
    private UniformContinuousRNG forgingTimeGenerator;

    public ProcessKovanie(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        forgingTimeGenerator = new UniformContinuousRNG(15d, 25d, ((MySimulation) mySim()).getSeedGenerator());
    }

    //meta! sender="AgentC", id="48", type="Start"
    public void processStart(MessageForm message) {
        ((MyMessage) message).getWorker().setAction(WorkerState.FORGING);
        message.setCode(Mc.koniecKovania);
        hold(forgingTimeGenerator.sample() * 60 * 1000, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.koniecKovania -> {
                // Process the forging of the furniture
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
    public AgentC myAgent() {
        return (AgentC) super.myAgent();
    }

}