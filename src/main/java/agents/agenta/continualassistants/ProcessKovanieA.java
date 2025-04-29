package agents.agenta.continualassistants;

import OSPABA.*;
import OSPRNG.UniformContinuousRNG;
import simulation.*;
import agents.agenta.*;
import OSPABA.Process;

//meta! id="37"
public class ProcessKovanieA extends OSPABA.Process {
    private UniformContinuousRNG forgingTimeGenerator;

    public ProcessKovanieA(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        forgingTimeGenerator = new UniformContinuousRNG(15d, 25d, ((MySimulation) mySim()).getSeedGenerator());
    }

    //meta! sender="AgentA", id="38", type="Start"
    public void processStart(MessageForm message) {
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
    public AgentA myAgent() {
        return (AgentA) super.myAgent();
    }

}