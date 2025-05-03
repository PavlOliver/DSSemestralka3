package agents.agentvyroby;

import OSPABA.*;
import agents.agenta.AgentA;
import agents.agentb.AgentB;
import agents.agentc.AgentC;
import furniture.Furniture;
import furniture.FurnitureType;
import furniture.Furnitures;
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

    private void finishFurniture(MyMessage message) {
        double timeInSystem = myAgent().getFinishedFurnitureList().addFinishedFurniture(message.getFurniture());
        myAgent().getTovarTimeInSystemStat().addSample(mySim().currentTime() - message.getFurniture().getArrivalTime());
        myAgent().addTovarTimeInSystem(mySim().currentTime() - message.getFurniture().getArrivalTime());
        myAgent().addFinishedTovarCount();
        if (timeInSystem > 0) {
            //je posledny z objednavky, cize...
//            System.out.println("Nabytok bol v systeme :" + ((mySim().currentTime() - timeInSystem) / 60000));
            myAgent().getOrderTimeInSystemStat().addSample(mySim().currentTime() - timeInSystem);
//            System.out.println("Priemerna doba v systeme:" + myAgent().getOrderTimeInSystemStat().mean() / 60000);
        }
        message.setCode(Mc.spracujObjednavku);
        response(message);
    }

    //meta! sender="AgentA", id="19", type="Response"
    public void processKovanieAgentA(MessageForm message) {
        this.finishFurniture((MyMessage) message);
    }

    //meta! sender="AgentC", id="22", type="Response"
    public void processKovanieAgentC(MessageForm message) {
        this.finishFurniture((MyMessage) message);
    }

    //meta! sender="AgentB", id="20", type="Response"
    public void processSkladanie(MessageForm message) {
        Furniture furniture = ((MyMessage) message).getFurniture();
        if (furniture.getType() == FurnitureType.WARDROBE) {
//            System.out.println("Skladanie dokoncene moze ist pracovnik A/C:" + mySim().currentTime());
            Worker worker = myAgent().getWorkersA().getFreeWorker();
            if (worker == null) {
                worker = myAgent().getWorkersC().getFreeWorker();
                message.setAddressee(mySim().findAgent(Id.agentC));
            } else {
                message.setAddressee(mySim().findAgent(Id.agentA));
            }
            if (worker != null) {
                worker.setBusy(true);
                worker.setCurrentFurniture(furniture);
                ((MyMessage) message).setWorker(worker);
                ((MyMessage) message).getWorkingPlace().setCurrentWorker(worker);//skuska
                message.setCode(Mc.kovanie);
                request(message);
            } else {
                //myAgent().getQueueKovania().enqueue(((MyMessage) message).getFurniture());
                myAgent().getQueueKovaniaPriority().add(((MyMessage) message).getFurniture());
            }

        } else {
//            System.out.println("Skladanie dokoncene uplne:" + mySim().currentTime());
            ((MyMessage) message).getWorkingPlace().setCurrentWorker(null); //todo este nvm ci to nechat tu alebo dat do agentaB
            this.finishFurniture((MyMessage) message);
        }
    }

    //meta! sender="AgentC", id="21", type="Response"
    public void processMorenie(MessageForm message) {
//        System.out.println("Morenie dokoncene moze ist pracovnik B:" + mySim().currentTime());
        Worker worker = myAgent().getWorkersB().getFreeWorker();
        if (worker != null) {
            worker.setBusy(true);
            worker.setCurrentFurniture(((MyMessage) message).getFurniture());
            ((MyMessage) message).setWorker(worker);
            ((MyMessage) message).getWorkingPlace().setCurrentWorker(worker);//skuska
        }
        message.setCode(Mc.skladanie);
        message.setAddressee(mySim().findAgent(Id.agentB));
        request(message);

    }

    //meta! sender="AgentA", id="18", type="Response"
    public void processPrijemTovaru(MessageForm message) {
//        System.out.println("Rezanie dokoncene moze ist pracovnik C:" + mySim().currentTime());
        Worker worker = myAgent().getWorkersC().getFreeWorker();
        if (worker != null) {
            worker.setBusy(true);
            worker.setCurrentFurniture(((MyMessage) message).getFurniture());
            ((MyMessage) message).setWorker(worker);
            ((MyMessage) message).getWorkingPlace().setCurrentWorker(worker);//skuska
        }
        message.setCode(Mc.morenie);
        message.setAddressee(mySim().findAgent(Id.agentC));
        request(message);
    }

    //meta! sender="AgentModelu", id="17", type="Request"
    public void processSpracujObjednavku(MessageForm message) {
//        System.out.println("Agent vyroby spracovava objednavku v case:" + mySim().currentTime());

        message.setCode(Mc.prijemTovaru);
        message.setAddressee(mySim().findAgent(Id.agentA));
        request(message); //skusim mu to poslat bez pracovnika aby to nahodil do skladu, keby neide prehod tie 3 riadky od tade hore hore
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
//        System.out.println("Agent A poziadal Agenta Vyroby o presun v case:" + mySim().currentTime());
        message.setAddressee(mySim().findAgent(Id.agentPresunov));
        request(message);
    }

    //meta! sender="AgentB", id="54", type="Request"
    public void processPresunAgentB(MessageForm message) {
//        System.out.println("Agent B poziadal Agenta Vyroby o presun v case:" + mySim().currentTime());
        message.setAddressee(mySim().findAgent(Id.agentPresunov));
        request(message);
    }

    //meta! sender="AgentC", id="56", type="Request"
    public void processPresunAgentC(MessageForm message) {
//        System.out.println("Agent C poziadal Agenta Vyroby o presun v case:" + mySim().currentTime());
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
                    case Id.agentB:
                        processPresunAgentB(message);
                        break;

                    case Id.agentPresunov:
                        processPresunAgentPresunov(message);
                        break;

                    case Id.agentC:
                        processPresunAgentC(message);
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