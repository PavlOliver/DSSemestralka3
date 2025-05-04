package agents.agentpresunov.continualassistants;

import OSPABA.*;
import OSPRNG.ExponentialRNG;
import OSPRNG.TriangularRNG;
import agents.agentpresunov.*;
import furniture.FurnitureState;
import simulation.*;
import OSPABA.Process;
import worker.WorkerPosition;
import worker.WorkerState;

//meta! id="28"
public class ProcessPresunSklad extends OSPABA.Process {
    private final TriangularRNG presunSkladRNG = new TriangularRNG(60d, 120d, 480d, ((MySimulation) mySim()).getSeedGenerator());

    public ProcessPresunSklad(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentPresunov", id="29", type="Start"
    public void processStart(MessageForm message) {
        ((MyMessage) message).getWorker().setAction(WorkerState.MOVING_STORAGE);
        message.setCode(Mc.presunDoSkladu);
        double moveTime;
        if (((MyMessage) message).getWorker().getPosition() == WorkerPosition.STORAGE
                && ((MyMessage) message).getFurniture().getState() == FurnitureState.PACKED) {
            moveTime = 0;
        } else {
            moveTime = presunSkladRNG.sample() * 1000;
        }
        hold(moveTime, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.presunDoSkladu -> {
                if (((MyMessage) message).getFurniture().getState() == FurnitureState.PACKED) {
                    ((MyMessage) message).getWorker().setPosition(WorkerPosition.STORAGE);
                } else {
                    ((MyMessage) message).getWorker().setPosition(WorkerPosition.WORKING_PLACE);
                }
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
    public AgentPresunov myAgent() {
        return (AgentPresunov) super.myAgent();
    }

}