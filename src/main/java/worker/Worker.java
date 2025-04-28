package worker;

import furniture.Furniture;

public class Worker {
    private final int id;
    private boolean isBusy;
    private Furniture currentFurniture;
    private WorkerPosition position;
    private char type;

    public Worker(int id, char type) {
        this.id = id;
        this.isBusy = false;
        this.currentFurniture = null;
        this.position = WorkerPosition.STORAGE;
        this.type = type;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
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
}
