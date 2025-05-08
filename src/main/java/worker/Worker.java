package worker;

import OSPAnimator.AnimImageItem;
import OSPStat.Stat;
import OSPStat.WStat;
import furniture.Furniture;
import simulation.MySimulation;
import workingplace.WorkingPlace;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

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
        this.getAnimImageItem().setPosition(new Point(id * 50 + 1300, type.getValue() * 50 + 200));
        this.animImageItem.setToolTip(toTooltip());
    }

    public void loadAnimItems(MySimulation mySim) {
        if (mySim.animatorExists()) {
            mySim.animator().register(animImageItem);
            this.updateTooltip();

            WorkingPlace workingPlace = mySim.getWorkingPlaces().getWorkingPlaceByFuriture(currentFurniture);
            if (workingPlace != null) {
                Point2D wp = workingPlace.getAnimShapeItem().getPosition(mySim.currentTime());
                this.animImageItem.setPosition(new Point((int) (wp.getX() + 50), (int) (wp.getY() + 50)));
            } else {
                Random random = new Random();
                this.getAnimImageItem().setPosition(new Point(random.nextInt(1300, 1650), random.nextInt(type.getValue() * 333, type.getValue() * 333 + 250)));
            }
        }
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
        if(!this.isBusy) {
            this.action = WorkerState.WAITING;
        }
        this.updateTooltip();
    }

    public Furniture getCurrentFurniture() {
        return currentFurniture;
    }

    public void setCurrentFurniture(Furniture currentFurniture) {
        this.currentFurniture = currentFurniture;
//        utilityWStat.addSample(currentFurniture != null ? 1d : 0d);
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
        if(action == WorkerState.WAITING) {
            this.animImageItem.setVisible(false);
            this.animImageItem.setZIndex(-1);
        } else {
            this.animImageItem.setVisible(true);
            this.animImageItem.setZIndex(1);
        }
        this.updateTooltip();
    }

    public WStat getUtilityWStat() {
        return utilityWStat;
    }

    public AnimImageItem getAnimImageItem() {
        return animImageItem;
    }

    public void updateTooltip() {
        this.animImageItem.setToolTip(toTooltip());
    }
}
