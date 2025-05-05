package agents.agenta.continualassistants;

import OSPABA.*;
import OSPRNG.EmpiricPair;
import OSPRNG.EmpiricRNG;
import OSPRNG.UniformContinuousRNG;
import simulation.*;
import agents.agenta.*;
import OSPABA.Process;
import worker.WorkerState;

//meta! id="35"
public class ProcessRezania extends OSPABA.Process {
    private EmpiricRNG tableCuttingGenerator;
    private UniformContinuousRNG chairCuttingGenerator;
    private UniformContinuousRNG wardrobeCuttingGenerator;

    public ProcessRezania(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        this.tableCuttingGenerator = new EmpiricRNG(
                ((MySimulation) mySim()).getSeedGenerator(),
                new EmpiricPair(new UniformContinuousRNG(10.0, 25.0, ((MySimulation) mySim()).getSeedGenerator()), 0.6),
                new EmpiricPair(new UniformContinuousRNG(25.0, 50.0, ((MySimulation) mySim()).getSeedGenerator()), 0.4)
        );
        this.chairCuttingGenerator = new UniformContinuousRNG(12.0, 16.0,
                ((MySimulation) mySim()).getSeedGenerator());
        this.wardrobeCuttingGenerator = new UniformContinuousRNG(15.0, 80.0,
                ((MySimulation) mySim()).getSeedGenerator());
    }

    //meta! sender="AgentA", id="36", type="Start"
    public void processStart(MessageForm message) {
        ((MyMessage) message).getWorker().setAction(WorkerState.CUTTING);
        message.setCode(Mc.koniecRezania);
        double cuttingTime = switch (((MyMessage) message).getFurniture().getType()) {
            case TABLE -> (double) tableCuttingGenerator.sample();
            case CHAIR -> chairCuttingGenerator.sample();
            case WARDROBE -> wardrobeCuttingGenerator.sample();
        } * 60 * 1000;
        hold(cuttingTime, message);

        if(mySim().animatorExists()) {
            ((MyMessage) message).getWorker().updateTooltip();
            ((MyMessage) message).getWorkingPlace().update();
        }
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.koniecRezania -> {
                // po skonceni rezania oznamime manazeroviA koniec rezania
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