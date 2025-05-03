package workingplace;

import OSPStat.WStat;
import furniture.Furniture;
import simulation.MySimulation;
import worker.Worker;

public class WorkingPlace {
    private final int id;
    private Worker currentWorker;
    private Furniture currentFurniture;
    private WStat utilityWStat;

    public WorkingPlace(int id, MySimulation mySim) {
        this.id = id;
        this.currentWorker = null;
        this.currentFurniture = null;
        this.utilityWStat = new WStat(mySim);
    }

    public int getId() {
        return id;
    }

    public Worker getCurrentWorker() {
        return currentWorker;
    }

    public void setCurrentWorker(Worker currentWorker) {
        this.currentWorker = currentWorker;
        utilityWStat.addSample(currentWorker != null ? 1 : 0);
    }

    public Furniture getCurrentFurniture() {
        return currentFurniture;
    }

    public void setCurrentFurniture(Furniture currentFurniture) {
        this.currentFurniture = currentFurniture;
    }

    public boolean isOccupied() {
        return currentFurniture != null;//urrentWorker != null && currentFurniture != null;
    }

    public WStat getUtilityWStat() {
        return utilityWStat;
    }
}
