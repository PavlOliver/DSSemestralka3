package agents.agentb.continualassistants;

import OSPABA.*;
import OSPRNG.EmpiricRNG;
import OSPRNG.UniformContinuousRNG;
import simulation.*;
import agents.agentb.*;
import OSPABA.Process;
import worker.WorkerState;

//meta! id="40"
public class ProcessSkladania extends OSPABA.Process {
    private final UniformContinuousRNG tableBuildingGenerator;
    private final UniformContinuousRNG chairBuildingGenerator;
    private final UniformContinuousRNG wardrobeBuildingGenerator;

    public ProcessSkladania(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
        tableBuildingGenerator = new UniformContinuousRNG(30d, 60d, ((MySimulation) mySim()).getSeedGenerator());
        chairBuildingGenerator = new UniformContinuousRNG(14d, 24d, ((MySimulation) mySim()).getSeedGenerator());
        wardrobeBuildingGenerator = new UniformContinuousRNG(35d, 75d, ((MySimulation) mySim()).getSeedGenerator());

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentB", id="41", type="Start"
    public void processStart(MessageForm message) {
        ((MyMessage) message).getWorker().setAction(WorkerState.BUILDING);
        message.setCode(Mc.koniecSkladania);
        double buildingTime = switch (((MyMessage) message).getFurniture().getType()) {
            case TABLE -> tableBuildingGenerator.sample();
            case CHAIR -> chairBuildingGenerator.sample();
            case WARDROBE -> wardrobeBuildingGenerator.sample();
        } * 60 * 1000;
        hold(buildingTime, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.koniecSkladania -> {
                // po skonceni skladania oznamime manazeroviB koniec skladania
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
    public AgentB myAgent() {
        return (AgentB) super.myAgent();
    }

}