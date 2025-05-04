package agents.agentpresunov.continualassistants;

import OSPABA.*;
import OSPRNG.TriangularRNG;
import agents.agentpresunov.*;
import simulation.*;
import OSPABA.Process;
import worker.WorkerState;
import workingplace.WorkingPlace;

import java.awt.*;
import java.awt.geom.Point2D;

//meta! id="30"
public class ProcessPresunPM extends OSPABA.Process {
    private TriangularRNG moveTimeBetweenWP;

    public ProcessPresunPM(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        moveTimeBetweenWP = new TriangularRNG(120d, 150d, 500d, ((MySimulation) mySim()).getSeedGenerator());
    }

    //meta! sender="AgentPresunov", id="31", type="Start"
    public void processStart(MessageForm message) {
        ((MyMessage) message).getWorker().setAction(WorkerState.MOVING_WP);
        message.setCode(Mc.presunWP);
        double durationOfMove = moveTimeBetweenWP.sample() * 1000;
        hold(durationOfMove, message);

        if (mySim().animatorExists()) {
            Point2D wp = ((MyMessage) message).getWorkingPlace().getAnimShapeItem().getPositionInTime(mySim().currentTime());
            ((MyMessage) message).getWorker().getAnimImageItem().moveTo(mySim().currentTime(),
                    durationOfMove,
                    new Point((int) (wp.getX() + 50), (int) (wp.getY() + 50)));
        }
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.presunWP -> {
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