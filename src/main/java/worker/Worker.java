package worker;

import OSPAnimator.AnimImageItem;
import OSPStat.Stat;
import OSPStat.WStat;
import furniture.Furniture;
import simulation.MySimulation;

import java.awt.*;

public class Worker {
    private final int id;
    private boolean isBusy;
    private Furniture currentFurniture;
    private WorkerPosition position;
    private final WorkerType type;
    private WorkerState action;
    private final WStat utilityWStat;
    private final AnimImageItem animImageItem;

    public Worker(int id, WorkerType type, MySimulation mySim) {
        this.id = id;
        this.isBusy = false;
        this.currentFurniture = null;
        this.position = WorkerPosition.STORAGE;
        this.type = type;
        this.action = WorkerState.WAITING;
        this.utilityWStat = new WStat(mySim);
        this.animImageItem = new AnimImageItem(type.getImagePath(), 40, 40);
        this.getAnimImageItem().setPosition(new Point(id * 50 + 1300, 200));
        this.animImageItem.setToolTip(toTooltip());
    }

    public int getId() {
        return id;
    }

    public WorkerType getType() {
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

    public String toTooltip() {
        return "Worker " + id + "(" + type + "\n" +
                "Position: " + position + "\n" +
                "State: " + action;
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

    public AnimImageItem getAnimImageItem() {
        return animImageItem;
    }
}
