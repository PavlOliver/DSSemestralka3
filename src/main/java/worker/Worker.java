package worker;

import furniture.Furniture;

public class Worker {
    private final int id;
    private boolean isBusy;
    private Furniture currentFurniture;
    private WorkerPosition position;

    public Worker(int id) {
        this.id = id;
        this.isBusy = false;
        this.currentFurniture = null;
        this.position = WorkerPosition.STORAGE;
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
}
