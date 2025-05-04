package agents.agenta;

import OSPABA.*;
import OSPAnimator.AnimImageItem;
import OSPAnimator.AnimItem;
import OSPAnimator.AnimShapeItem;
import agents.agentvyroby.AgentVyroby;
import furniture.Furniture;
import furniture.FurnitureState;
import furniture.Furnitures;
import simulation.*;
import worker.Worker;
import worker.WorkerPosition;
import worker.WorkerState;
import workingplace.WorkingPlace;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

//meta! id="4"
public class ManagerA extends OSPABA.Manager {
    public ManagerA(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentVyroby", id="19", type="Request"
    public void processKovanie(MessageForm message) {
        //tu myslim ze nemoze sa stat ze bude worker null
        //mySim().pauseSimulation();
        //((MyMessage) message).getWorker().setBusy(true);
        message.setCode(Mc.presun);
        message.setAddressee(myAgent().parent());
        request(message);
    }

    //meta! sender="ProcessKovanieA", id="38", type="Finish"
    public void processFinishProcessKovanieA(MessageForm message) {
        //System.out.println("Pracovnik A skoncil kovanie");
        ((MyMessage) message).getFurniture().setState(FurnitureState.FORGED, mySim().currentTime());
//        this.startWorking((MyMessage) message);//asik
        findNewJob((MyMessage) message);

        message.setCode(Mc.kovanie);
        response(message);
    }

    //meta! sender="ProcessPripravy", id="34", type="Finish"
    public void processFinishProcessPripravy(MessageForm message) {
        //System.out.println("Priprava materialu skoncena v case:" + mySim().currentTime());
        ((MyMessage) message).getFurniture().setState(FurnitureState.UNPACKED, mySim().currentTime());
        message.setCode(Mc.presun);
        message.setAddressee(myAgent().parent());
        request(message);
    }

    //meta! sender="ProcessRezania", id="36", type="Finish"
    public void processFinishProcessRezania(MessageForm message) {
        //System.out.println("Rezanie skoncene v case:" + mySim().currentTime());
        ((MyMessage) message).getFurniture().setState(FurnitureState.CUT, mySim().currentTime());
        this.findNewJob((MyMessage) message);

        message.setCode(Mc.prijemTovaru);
        response(message);
    }

    private void findNewJob(MyMessage message) {
        //message.getWorker().setBusy(false);
        message.getWorker().setCurrentFurniture(null);
        message.getWorker().setAction(WorkerState.WAITING);
        message.getWorkingPlace().setCurrentWorker(null);
        Worker worker = message.getWorker();
        message.setWorker(null);

        //najdem mu novu pracu
        if (!((AgentVyroby) myAgent().parent()).getQueueKovaniaPriority().isEmpty()) {
            MyMessage newMessage = (MyMessage) message.createCopy();
            newMessage.setWorker(worker);
            Furniture furniture = ((AgentVyroby) myAgent().parent()).getQueueKovaniaPriority().poll();
            newMessage.setFurniture(furniture);
            newMessage.setWorkingPlace(furniture.getWorkingPlace());
            newMessage.getWorkingPlace().setCurrentWorker(worker);//skuska
            //worker.setBusy(true);
            worker.setCurrentFurniture(furniture);

            newMessage.setCode(Mc.presun);
            newMessage.setAddressee(myAgent().parent());
            request(newMessage);
        } else {
            MyMessage newMessage = (MyMessage) message.createCopy();
            newMessage.setWorkingPlace(null);
            newMessage.setFurniture(null);
            newMessage.setWorker(worker);
            this.startWorking(newMessage);
        }
    }

    private void startWorking(MyMessage message) {
        if (!myAgent().getStorage().isEmpty()) {
            WorkingPlace workingPlace = myAgent().getWorkingPlaces().getFreeWorkingPlace();
            if (workingPlace != null) {
                //Furnitures order = myAgent().getStorage().peek();
                Furniture furniture = myAgent().getStorage().dequeue();
                if (mySim().animatorExists()) {
                    AnimItem ai = ((MySimulation) mySim()).getAnimQueue().remove(furniture.getAnimImageItem());
                    ai.clear();
                }
                furniture.setWorkingPlace(workingPlace);
                //myAgent().addFurniture(furniture); //sledovanie nabytkov v systeme
                ((AgentVyroby) myAgent().parent()).addFurniture(furniture);
                workingPlace.setCurrentFurniture(furniture);
                workingPlace.setCurrentWorker(message.getWorker());
                message.setFurniture(furniture);
                message.setWorkingPlace(workingPlace);
                if(!message.getWorker().isBusy()) {
                    message.getWorker().setBusy(true);
                }
                message.getWorker().setCurrentFurniture(furniture);

                message.setCode(Mc.presun);
                message.setAddressee(myAgent().parent());
                request(message);
            } else {
                message.getWorker().setBusy(false);
            }
        } else {
            //System.out.println("Neni co spracovavat, cakam na dalsiu objednavku v case:" + mySim().currentTime());
            message.getWorker().setBusy(false);
        }
    }

    //meta! sender="AgentVyroby", id="18", type="Request"
    public void processPrijemTovaru(MessageForm message) {
        //System.out.println("Agent A spracovava objednavku v case:" + mySim().currentTime());
//        Furnitures furnitures = new Furnitures(((MySimulation) mySim()).getOrderId(), mySim().currentTime(), ((MySimulation) mySim()).getSeedGenerator());
//        myAgent().getStorage().enqueue(furnitures);
//        ((AgentVyroby) myAgent().parent()).getFinishedFurnitureList().add(furnitures, mySim().currentTime());

//        System.out.println("objednavka " + myAgent().getStorage().peek().getOrderId() + " v case: " + mySim().currentTime());
//        System.out.println("volnych: " + ((AgentVyroby)myAgent().parent()).getWorkersA().getFreeWorkersCount());
        Worker worker = ((AgentVyroby) myAgent().parent()).getWorkersA().getFreeWorker();

        if (worker != null) {
            //worker.setBusy(true); !!!!!!!
            ((MyMessage) message).setWorker(worker);
            startWorking((MyMessage) message);
        } else {
            //response(message); //sam neviem co robit ked neni pracovnik
        }

    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! sender="AgentVyroby", id="51", type="Response"
    public void processPresun(MessageForm message) {
        //ked je v sklade tak prapravuje material, ked je na PM tak reze
        if (((MyMessage) message).getWorker().getPosition() == WorkerPosition.STORAGE) {
            //System.out.println("Pracovnik zacina pripravu materialu v case:" + mySim().currentTime());
            message.setAddressee(myAgent().findAssistant(Id.processPripravy));
            startContinualAssistant(message);
        } else {
            if (((MyMessage) message).getFurniture().getState() == FurnitureState.UNPACKED) {
                //System.out.println("Pracovnik zacina rezanie v case:" + mySim().currentTime());
                if (mySim().animatorExists()) {
                    Point2D p = ((MyMessage) message).getWorkingPlace().getAnimShapeItem().getPositionInTime(mySim().currentTime());
                    ((MyMessage) message).getFurniture().getAnimImageItem().setPosition(mySim().currentTime(), p);

                    //((MyMessage) message).getWorker().getAnimImageItem().setPosition(mySim().currentTime(), new Point((int) p.getX() + 50, (int) p.getY() + 50));
                }
                message.setAddressee(myAgent().findAssistant(Id.processRezania));
                startContinualAssistant(message);
            } else {
                //System.out.println("Pracovnik zacina kovanie v case:" + mySim().currentTime());
                message.setAddressee(myAgent().findAssistant(Id.processKovanieA));
                startContinualAssistant(message);
            }
        }
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

            case Mc.kovanie:
                processKovanie(message);
                break;

            case Mc.presun:
                processPresun(message);
                break;

            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.processKovanieA:
                        processFinishProcessKovanieA(message);
                        break;

                    case Id.processPripravy:
                        processFinishProcessPripravy(message);
                        break;

                    case Id.processRezania:
                        processFinishProcessRezania(message);
                        break;
                }
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