package workingplace;

import furniture.Furniture;
import worker.Worker;

public class WorkingPlace {
    private final int id;
    private Worker currentWorker;
    private Furniture currentFurniture;

    public WorkingPlace(int id) {
        this.id = id;
        this.currentWorker = null;
        this.currentFurniture = null;
    }

    public int getId() {
        return id;
    }

    public Worker getCurrentWorker() {
        return currentWorker;
    }

    public void setCurrentWorker(Worker currentWorker) {
        this.currentWorker = currentWorker;
    }

    public Furniture getCurrentFurniture() {
        return currentFurniture;
    }

    public void setCurrentFurniture(Furniture currentFurniture) {
        this.currentFurniture = currentFurniture;
    }

    public boolean isOccupied() {
        return currentWorker != null && currentFurniture != null;
    }
}
