package agents.agentb;

import OSPABA.*;
import furniture.Furniture;
import furniture.FurnitureState;
import simulation.*;
import worker.Worker;
import worker.WorkerState;

//meta! id="6"
public class ManagerB extends OSPABA.Manager {
    public ManagerB(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentVyroby", id="20", type="Request"
    public void processSkladanie(MessageForm message) {
        if (((MyMessage) message).getWorker() != null) {
            message.setCode(Mc.presun);
            message.setAddressee(mySim().findAgent(Id.agentVyroby));
            request(message);
        } else {
            //myAgent().getQueueSkladania().enqueue(((MyMessage) message).getFurniture());
            myAgent().getQueueSkladaniaPriority().add(((MyMessage) message).getFurniture());
        }
    }

    //meta! sender="ProcessSkladania", id="41", type="Finish"
    public void processFinish(MessageForm message) {
//        System.out.println("Pracovnik skoncil skladanie v case:" + mySim().currentTime());
        ((MyMessage) message).getFurniture().setState(FurnitureState.BUILT, mySim().currentTime());
        ((MyMessage) message).getWorkingPlace().setCurrentWorker(null);

        Worker worker = ((MyMessage) message).getWorker();
        ((MyMessage) message).setWorker(null);

        //najdem mu novu pracu
        if(!myAgent().getQueueSkladaniaPriority().isEmpty()) {
            MyMessage newMessage = (MyMessage) message.createCopy();

            newMessage.setWorker(worker);
            //Furniture furniture = myAgent().getQueueSkladania().dequeue();
            Furniture furniture = myAgent().getQueueSkladaniaPriority().poll();
            newMessage.setFurniture(furniture);
            newMessage.setWorkingPlace(furniture.getWorkingPlace());
            newMessage.getWorkingPlace().setCurrentWorker(worker);//skuska

            newMessage.setCode(Mc.presun);
            newMessage.setAddressee(myAgent().parent());
            request(newMessage);
        } else {
            worker.setBusy(false);
            worker.setCurrentFurniture(null);
            worker.setAction(WorkerState.WAITING);
        }

        message.setCode(Mc.skladanie);
        response(message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! sender="AgentVyroby", id="54", type="Response"
    public void processPresun(MessageForm message) {
//        System.out.println("Pracovnik je na mieste " + mySim().currentTime());
        message.setCode(Mc.skladanie);
        message.setAddressee(myAgent().findAssistant(Id.processSkladania));
        startContinualAssistant(message);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.skladanie:
                processSkladanie(message);
                break;

            case Mc.presun:
                processPresun(message);
                break;

            case Mc.finish:
                processFinish(message);
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