package agents.agentvyroby;

import OSPABA.*;
import simulation.*;
import worker.Worker;

//meta! id="3"
public class ManagerVyroby extends OSPABA.Manager {
    public ManagerVyroby(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentA", id="19", type="Response"
    public void processKovanieAgentA(MessageForm message) {
    }

    //meta! sender="AgentC", id="22", type="Response"
    public void processKovanieAgentC(MessageForm message) {
    }

    //meta! sender="AgentB", id="20", type="Response"
    public void processSkladanie(MessageForm message) {
    }

    //meta! sender="AgentC", id="21", type="Response"
    public void processMorenie(MessageForm message) {
    }

    //meta! sender="AgentA", id="18", type="Response"
    public void processPrijemTovaru(MessageForm message) {
        System.out.println("Rezanie dokoncene moze ist pracovnik C:" + mySim().currentTime());
        message.setCode(Mc.morenie);

    }

    //meta! sender="AgentModelu", id="17", type="Request"
    public void processSpracujObjednavku(MessageForm message) {
        System.out.println("Agent vyroby spracovava objednavku v case:" + mySim().currentTime());
        Worker worker = myAgent().getWorkersA().getFreeWorker();
        if (worker != null) {
            worker.setBusy(true);
            ((MyMessage) message).setWorker(worker);
            message.setCode(Mc.prijemTovaru);
            message.setAddressee(mySim().findAgent(Id.agentA));
            request(message);
        }
    }

    //meta! sender="AgentPresunov", id="23", type="Response"
    public void processPresunAgentPresunov(MessageForm message) {
        message.setCode(Mc.presun);
        response(message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! sender="AgentA", id="51", type="Request"
    public void processPresunAgentA(MessageForm message) {
        System.out.println("Agent A poziadal Agenta Vyroby o presun v case:" + mySim().currentTime());
        message.setAddressee(mySim().findAgent(Id.agentPresunov));
        request(message);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.prijemTovaru:
                processPrijemTovaru(message);
                break;

            case Mc.presun:
                switch (message.sender().id()) {
                    case Id.agentPresunov:
                        processPresunAgentPresunov(message);
                        break;

                    case Id.agentA:
                        processPresunAgentA(message);
                        break;
                }
                break;

            case Mc.morenie:
                processMorenie(message);
                break;

            case Mc.skladanie:
                processSkladanie(message);
                break;

            case Mc.kovanie:
                switch (message.sender().id()) {
                    case Id.agentA:
                        processKovanieAgentA(message);
                        break;

                    case Id.agentC:
                        processKovanieAgentC(message);
                        break;
                }
                break;

            case Mc.spracujObjednavku:
                processSpracujObjednavku(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentVyroby myAgent() {
        return (AgentVyroby) super.myAgent();
    }

}