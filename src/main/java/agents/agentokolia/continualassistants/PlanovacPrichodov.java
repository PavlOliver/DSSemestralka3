package agents.agentokolia.continualassistants;

import OSPABA.*;
import OSPAnimator.AnimImageItem;
import OSPRNG.ExponentialRNG;
import OSPRNG.UniformDiscreteRNG;
import agents.agentokolia.*;
import simulation.*;

import java.awt.*;

//meta! id="25"
public class PlanovacPrichodov extends OSPABA.Scheduler {
    private ExponentialRNG arrivalGenerator;

    public PlanovacPrichodov(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        this.arrivalGenerator = new ExponentialRNG(30d, ((MySimulation) mySim()).getSeedGenerator());

    }

    //meta! sender="AgentOkolia", id="26", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.prichodObjednavky);
        double time = arrivalGenerator.sample() * 60 * 1000;

        hold(time, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.prichodObjednavky:
                message.setAddressee(myAgent());
                notice(message);
                break;
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
    public AgentOkolia myAgent() {
        return (AgentOkolia) super.myAgent();
    }
}