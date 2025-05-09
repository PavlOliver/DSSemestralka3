package agents.agentvyroby;

import OSPABA.*;
import OSPAnimator.AnimImageItem;
import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import OSPStat.WStat;
import furniture.FinishedFurnitureList;
import furniture.Furniture;
import simulation.*;
import worker.WorkerType;
import worker.Workers;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


//meta! id="3"
public class AgentVyroby extends OSPABA.Agent {
    Workers workersA;
    Workers workersC;
    Workers workersB;
    private List<Furniture> furnitureList;
    private FinishedFurnitureList finishedFurnitureList;
    private Stat orderTimeInSystemStat;
    private Stat tovarTimeInSystemStat;
    private double tovarTimeInSystem;
    private double orderTimeInSystem;
    private int finishedTovarCount;
    private int finishedOrderCount;

    public AgentVyroby(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        furnitureList = new ArrayList<>();
        finishedFurnitureList = new FinishedFurnitureList();

        orderTimeInSystemStat = new Stat();
        tovarTimeInSystemStat = new Stat();
        tovarTimeInSystem = 0;
        finishedTovarCount = 0;
        finishedOrderCount = 0;
        orderTimeInSystem = 0;
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

    public void addFurniture(Furniture furniture) {
        furnitureList.add(furniture);
    }

    public List<Furniture> getFurnitureList() {
        return furnitureList;
    }

    public FinishedFurnitureList getFinishedFurnitureList() {
        return finishedFurnitureList;
    }

    public Stat getOrderTimeInSystemStat() {
        return orderTimeInSystemStat;
    }

    public Stat getTovarTimeInSystemStat() {
        return tovarTimeInSystemStat;
    }

    public double getTovarTimeInSystem() {
        return tovarTimeInSystem;
    }

    public void addTovarTimeInSystem(double time) {
        tovarTimeInSystem += time;
    }

    public int getFinishedTovarCount() {
        return finishedTovarCount;
    }

    public void addFinishedTovarCount() {
        finishedTovarCount++;
    }

    public int getFinishedOrderCount() {
        return finishedOrderCount;
    }

    public void addFinishedOrderCount() {
        finishedOrderCount++;
    }

    public double getOrderTimeInSystem() {
        return orderTimeInSystem;
    }

    public void addOrderTimeInSystem(double time) {
        orderTimeInSystem += time;
    }

    public void setSizes(int sizeA, int sizeB, int sizeC) {
        workersA = new Workers(sizeA, WorkerType.A, (MySimulation) mySim());
        workersB = new Workers(sizeB, WorkerType.B, (MySimulation) mySim());
        workersC = new Workers(sizeC, WorkerType.C, (MySimulation) mySim());
    }

}