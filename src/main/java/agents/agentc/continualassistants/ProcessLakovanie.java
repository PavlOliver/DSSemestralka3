package agents.agentc.continualassistants;

import OSPABA.*;
import OSPRNG.EmpiricPair;
import OSPRNG.EmpiricRNG;
import OSPRNG.UniformContinuousRNG;
import simulation.*;
import agents.agentc.*;
import OSPABA.Process;

//meta! id="45"
public class ProcessLakovanie extends OSPABA.Process {
    private EmpiricRNG tableLacqueringGenerator;
    private UniformContinuousRNG chairLacqueringGenerator;
    private UniformContinuousRNG wardrobeLacqueringGenerator;

    public ProcessLakovanie(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        tableLacqueringGenerator = new EmpiricRNG(((MySimulation) mySim()).getSeedGenerator(),
                new EmpiricPair(new UniformContinuousRNG(50d, 70d), 0.1),
                new EmpiricPair(new UniformContinuousRNG(70d, 150d), 0.6),
                new EmpiricPair(new UniformContinuousRNG(150d, 200d), 0.3));

        chairLacqueringGenerator = new UniformContinuousRNG(40d, 200d, ((MySimulation) mySim()).getSeedGenerator());
        wardrobeLacqueringGenerator = new UniformContinuousRNG(250d, 560d, ((MySimulation) mySim()).getSeedGenerator());
    }

    //meta! sender="AgentC", id="46", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.koniecLakovania);
        double lacqueringTime = switch (((MyMessage) message).getFurniture().getType()) {
            case TABLE -> (double) tableLacqueringGenerator.sample();
            case CHAIR -> chairLacqueringGenerator.sample();
            case WARDROBE -> wardrobeLacqueringGenerator.sample();
        } * 60 * 1000;
        hold(lacqueringTime, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.koniecLakovania -> {
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