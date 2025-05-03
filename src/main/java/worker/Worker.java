package worker;

import OSPStat.Stat;
import OSPStat.WStat;
import furniture.Furniture;
import simulation.MySimulation;

public class Worker {
    private final int id;
    private boolean isBusy;
    private Furniture currentFurniture;
    private WorkerPosition position;
    private char type;
    private WorkerState action;
    private WStat utilityWStat;

    public Worker(int id, char type, MySimulation mySim) {
        this.id = id;
        this.isBusy = false;
        this.currentFurniture = null;
        this.position = WorkerPosition.STORAGE;
        this.type = type;
        this.action = WorkerState.WAITING;
        this.utilityWStat = new WStat(mySim);
    }

    public int getId() {
        return id;
    }

    public char getType() {
        return type;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
        utilityWStat.addSample(busy ? 1 : 0);
    }

    public Furniture getCurrentFurniture() {
        return currentFurniture;
    }

    public void setCurrentFurniture(Furniture currentFurniture) {
        this.currentFurniture = currentFurniture;
    }

    public WorkerPosition getPosition() {
        return position;
    }

    public void setPosition(WorkerPosition position) {
        this.position = position;
    }

    public String toString() {
        return "Worker " + id + " (" + type + ")";
    }

    public WorkerState getAction() {
        return action;
    }

    public void setAction(WorkerState action) {
        this.action = action;
    }

    public WStat getUtilityWStat() {
        return utilityWStat;
    }
}
