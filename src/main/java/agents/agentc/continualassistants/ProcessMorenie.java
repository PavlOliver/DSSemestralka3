package agents.agentc.continualassistants;

import OSPABA.*;
import OSPRNG.UniformContinuousRNG;
import furniture.FurnitureType;
import simulation.*;
import agents.agentc.*;
import OSPABA.Process;
import worker.WorkerState;

//meta! id="43"
public class ProcessMorenie extends OSPABA.Process {
    private UniformContinuousRNG tablePicklingGenerator;
    private UniformContinuousRNG chairPicklingGenerator;
    private UniformContinuousRNG wardrobePicklingGenerator;

    public ProcessMorenie(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        tablePicklingGenerator = new UniformContinuousRNG(100d, 480d, ((MySimulation) mySim()).getSeedGenerator());
        chairPicklingGenerator = new UniformContinuousRNG(90d, 400d, ((MySimulation) mySim()).getSeedGenerator());
        wardrobePicklingGenerator = new UniformContinuousRNG(300d, 600d, ((MySimulation) mySim()).getSeedGenerator());
    }

    //meta! sender="AgentC", id="44", type="Start"
    public void processStart(MessageForm message) {
        ((MyMessage) message).getWorker().setAction(WorkerState.PICKLING);
        message.setCode(Mc.koniecMorenia);
        double durationOfPickling = switch (((MyMessage) message).getFurniture().getType()) {
            case FurnitureType.TABLE -> tablePicklingGenerator.sample();
            case FurnitureType.CHAIR -> chairPicklingGenerator.sample();
            case FurnitureType.WARDROBE -> wardrobePicklingGenerator.sample();
        } * 60 * 1000;

        hold(durationOfPickling, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.koniecMorenia -> {
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