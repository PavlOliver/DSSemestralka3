package workingplace;

import OSPAnimator.AnimShape;
import OSPAnimator.AnimShapeItem;
import OSPStat.WStat;
import furniture.Furniture;
import simulation.MySimulation;
import worker.Worker;

import java.awt.*;

public class WorkingPlace {
    private final int id;
    private Worker currentWorker;
    private Furniture currentFurniture;
    private final WStat utilityWStat;
    private final AnimShapeItem animShapeItem;

    public WorkingPlace(int id, MySimulation mySim) {
        this.id = id;
        this.currentWorker = null;
        this.currentFurniture = null;
        this.utilityWStat = new WStat(mySim);
        int x = (id % 10) * 105 + 200;
        int y = (id / 10) * 105 + 200;
        this.animShapeItem = new AnimShapeItem(AnimShape.RECTANGLE_EMPTY, Color.BLACK, 100);
        this.animShapeItem.setPosition(new Point(x, y));
        this.animShapeItem.setToolTip("Working place " + id + "\n" +
                "Worker: " + (currentWorker != null ? currentWorker.toTooltip() : "null") + "\n" +
                "Furniture: " + (currentFurniture != null ? currentFurniture.toTooltip() : "null"));
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
        this.animShapeItem.setToolTip("Working place " + id + "\n" +
                "Worker: " + (currentWorker != null ? currentWorker.toTooltip() : "null") + "\n" +
                "Furniture: " + (currentFurniture != null ? currentFurniture.toTooltip() : "null"));
    }

    public Furniture getCurrentFurniture() {
        return currentFurniture;
    }

    public void setCurrentFurniture(Furniture currentFurniture) {
        this.currentFurniture = currentFurniture;
        this.animShapeItem.setToolTip("Working place " + id + "\n" +
                "Worker: " + (currentWorker != null ? currentWorker.toTooltip() : "null") + "\n" +
                "Furniture: " + (currentFurniture != null ? currentFurniture.toTooltip() : "null"));
    }

    public boolean isOccupied() {
        return currentFurniture != null;//urrentWorker != null && currentFurniture != null;
    }

    public WStat getUtilityWStat() {
        return utilityWStat;
    }

    public AnimShapeItem getAnimShapeItem() {
        return animShapeItem;
    }
}
