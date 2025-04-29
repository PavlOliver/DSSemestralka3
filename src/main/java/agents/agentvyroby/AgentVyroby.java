package agents.agentvyroby;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import furniture.Furniture;
import simulation.*;
import worker.Workers;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


//meta! id="3"
public class AgentVyroby extends OSPABA.Agent {
    Workers workersA;
    Workers workersC;
    Workers workersB;
    private SimQueue<Furniture> queueKovania;
    private PriorityQueue<Furniture> queueKovaniaPriority;
    private WStat dlzkaKovaniaQueueStat;
    private List<Furniture> furnitureList;


    public AgentVyroby(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        workersA = new Workers(15, 'A');
        workersC = new Workers(40, 'C');
        workersB = new Workers(5, 'B');

        dlzkaKovaniaQueueStat = new WStat(mySim());
        queueKovania = new SimQueue<>(dlzkaKovaniaQueueStat);
        queueKovaniaPriority = new PriorityQueue<>();

        furnitureList = new ArrayList<>();

    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerVyroby(Id.managerVyroby, mySim(), this);
        addOwnMessage(Mc.kovanie);
        addOwnMessage(Mc.skladanie);
        addOwnMessage(Mc.morenie);
        addOwnMessage(Mc.prijemTovaru);
        addOwnMessage(Mc.spracujObjednavku);
        addOwnMessage(Mc.presun);
    }
    //meta! tag="end"

    public Workers getWorkersA() {
        return workersA;
    }

    public Workers getWorkersC() {
        return workersC;
    }

    public Workers getWorkersB() {
        return workersB;
    }

    public SimQueue<Furniture> getQueueKovania() {
        return queueKovania;
    }

    public void addFurniture(Furniture furniture) {
        furnitureList.add(furniture);
    }

    public List<Furniture> getFurnitureList() {
        return furnitureList;
    }

    public PriorityQueue<Furniture> getQueueKovaniaPriority() {
        return queueKovaniaPriority;
    }
}