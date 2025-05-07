package agents.agentc;

import OSPABA.*;
import OSPRNG.UniformContinuousRNG;
import agents.agentvyroby.AgentVyroby;
import furniture.Furniture;
import furniture.FurnitureState;
import simulation.*;
import worker.Worker;
import worker.WorkerState;

//meta! id="7"
public class ManagerC extends OSPABA.Manager {
    private UniformContinuousRNG lacqueringRNG;

    public ManagerC(int id, Simulation mySim, Agent myAgent) {
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
        this.lacqueringRNG = new UniformContinuousRNG(0d, 1d, ((MySimulation) mySim()).getSeedGenerator());
    }

    //meta! sender="AgentVyroby", id="22", type="Request"
    public void processKovanie(MessageForm message) {
        //tu myslim ze nemoze sa stat ze bude worker null
        //mySim().pauseSimulation();
        //((MyMessage) message).getWorker().setBusy(true);
        message.setCode(Mc.presun);
        message.setAddressee(myAgent().parent());
        request(message);
    }

    //meta! sender="ProcessMorenie", id="44", type="Finish"
    public void processFinishProcessMorenie(MessageForm message) {
//        System.out.println("Pracovnik skoncil morenie v case:" + mySim().currentTime());
        ((MyMessage) message).getFurniture().setState(FurnitureState.PICKLED, mySim().currentTime());
        double rngSample = lacqueringRNG.sample();

        if (rngSample <= 0.15) { //rng.sample()
            message.setAddressee(myAgent().findAssistant(Id.processLakovanie));
            startContinualAssistant(message);
        } else {
//            System.out.println("Pracovnik C skoncil pracu Morenim");
            this.findNewJob((MyMessage) message);

            message.setCode(Mc.morenie);
            response(message);
        }
    }

    //meta! sender="ProcessLakovanie", id="46", type="Finish"
    public void processFinishProcessLakovanie(MessageForm message) {
//        System.out.println("Pracovnik C skoncil pracu Lakovanim");
        ((MyMessage) message).getFurniture().setState(FurnitureState.LACQUERED, mySim().currentTime());
        this.findNewJob((MyMessage) message);

        message.setCode(Mc.morenie);
        response(message);
    }

    private void findNewJob(MyMessage message) {
        message.getWorkingPlace().setCurrentWorker(null);
        Worker worker = message.getWorker();
        message.setWorker(null);

        //najdem mu novu pracu
        if (!((MySimulation) mySim()).getQueueKovaniaPriority().isEmpty()) {
            MyMessage newMessage = (MyMessage) message.createCopy();
            newMessage.setWorker(worker);
            Furniture furniture = ((MySimulation) mySim()).getQueueKovaniaPriority().poll();
            newMessage.setFurniture(furniture);
            newMessage.setWorkingPlace(furniture.getWorkingPlace());
            newMessage.getWorkingPlace().setCurrentWorker(worker);//skuska
            worker.setCurrentFurniture(furniture);

            newMessage.setCode(Mc.presun);
            newMessage.setAddressee(myAgent().parent());
            request(newMessage);
        } else {
            if (!((MySimulation) mySim()).getQueueMoreniaPriority().isEmpty()) {
                MyMessage newMessage = (MyMessage) message.createCopy();

                newMessage.setWorker(worker);
                Furniture furniture = ((MySimulation) mySim()).getQueueMoreniaPriority().poll();
                newMessage.setFurniture(furniture);
                newMessage.setWorkingPlace(furniture.getWorkingPlace());
                newMessage.getWorkingPlace().setCurrentWorker(worker);//skuska
                worker.setCurrentFurniture(furniture);

                newMessage.setCode(Mc.presun);
                newMessage.setAddressee(myAgent().parent());
                request(newMessage);
            } else {
                worker.setBusy(false);
                worker.setCurrentFurniture(null);
            }
        }
    }

    //meta! sender="ProcessKovanie", id="48", type="Finish"
    public void processFinishProcessKovanie(MessageForm message) {
//        System.out.println("Pracovnik C skoncil kovanie");
        ((MyMessage) message).getFurniture().setState(FurnitureState.FORGED, mySim().currentTime());
        this.findNewJob((MyMessage) message);

        message.setCode(Mc.kovanie);
        response(message);
    }

    //meta! sender="AgentVyroby", id="21", type="Request"
    public void processMorenie(MessageForm message) {
//        System.out.println("Ziadam presun na morenie v case:" + mySim().currentTime());
        //myAgent().getQueueMorenia().enqueue(((MyMessage) message).getFurniture());
        if (((MyMessage) message).getWorker() != null) {
            message.setCode(Mc.presun);
            message.setAddressee(mySim().findAgent(Id.agentVyroby));
            request(message);
        } else {
            //myAgent().getQueueMorenia().enqueue(((MyMessage) message).getFurniture());
            ((MySimulation) mySim()).getQueueMoreniaPriority().add(((MyMessage) message).getFurniture());
            //response(message); //nic nezrobilo ani sa robit nebude
        }
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {

        }
    }

    //meta! sender="AgentVyroby", id="56", type="Response"
    public void processPresun(MessageForm message) {
//        System.out.println("Pracovnik je na mieste " + mySim().currentTime());
        if (((MyMessage) message).getFurniture().getState() == FurnitureState.CUT) {
            message.setCode(Mc.morenie);
            message.setAddressee(myAgent().findAssistant(Id.processMorenie));
        } else {
            message.setCode(Mc.kovanie);
            message.setAddressee(myAgent().findAssistant(Id.processKovanie));
        }
        startContinualAssistant(message);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.processMorenie:
                        processFinishProcessMorenie(message);
                        break;

                    case Id.processLakovanie:
                        processFinishProcessLakovanie(message);
                        break;

                    case Id.processKovanie:
                        processFinishProcessKovanie(message);
                        break;
                }
                break;

            case Mc.kovanie:
                processKovanie(message);
                break;

            case Mc.presun:
                processPresun(message);
                break;

            case Mc.morenie:
                processMorenie(message);
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