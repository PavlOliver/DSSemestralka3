package agents.agentpresunov;

import OSPABA.*;
import furniture.FurnitureState;
import simulation.*;

//meta! id="5"
public class ManagerPresunov extends OSPABA.Manager {
    public ManagerPresunov(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
    }

    //meta! sender="ProcessPresunPM", id="31", type="Finish"
    public void processFinishProcessPresunPM(MessageForm message) {
    }

    //meta! sender="ProcessPresunSklad", id="29", type="Finish"
    public void processFinishProcessPresunSklad(MessageForm message) {
        System.out.println("Pracovnik sa presunul do skladu v case:" + mySim().currentTime());
        message.setCode(Mc.presun);
        response(message);
    }

    //meta! sender="AgentVyroby", id="23", type="Request"
    public void processPresun(MessageForm message) {
        System.out.println("Agent presunov spracovava presun v case:" + mySim().currentTime());
        FurnitureState state = ((MyMessage) message).getFurniture().getState();
        if (state == FurnitureState.PACKED ||
                state == FurnitureState.UNPACKED) {
            message.setAddressee(myAgent().findAssistant(Id.processPresunSklad));
            startContinualAssistant(message);
        }
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.processPresunPM:
                        processFinishProcessPresunPM(message);
                        break;

                    case Id.processPresunSklad:
                        processFinishProcessPresunSklad(message);
                        break;
                }
                break;

            case Mc.presun:
                processPresun(message);
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