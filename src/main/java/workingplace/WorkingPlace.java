package workingplace;

import OSPAnimator.AnimShape;
import OSPAnimator.AnimShapeItem;
import OSPAnimator.AnimTextItem;
import OSPStat.WStat;
import furniture.Furniture;
import simulation.Data;
import simulation.MySimulation;
import worker.Worker;

import java.awt.*;

public class WorkingPlace {
    private final int id;
    private Worker currentWorker;
    private Furniture currentFurniture;
    private final WStat utilityWStat;
    private final AnimShapeItem animShapeItem;
    private final AnimTextItem animTextItem;

    public WorkingPlace(int id, MySimulation mySim) {
        this.id = id;
        this.currentWorker = null;
        this.currentFurniture = null;
        this.utilityWStat = new WStat(mySim);
        int x = (id % 5) * (Data.WP_SIZE + 20);
        int y = (id / 5) * (Data.WP_SIZE + 50) + 100;
        this.animShapeItem = new AnimShapeItem(AnimShape.RECTANGLE_EMPTY, Color.BLACK, Data.WP_SIZE);
        this.animShapeItem.setPosition(new Point(x, y));
        this.animShapeItem.setToolTip("Working place " + id + "\n" +
                "Worker: " + (currentWorker != null ? currentWorker.toTooltip() : "null") + "\n" +
                "Furniture: " + (currentFurniture != null ? currentFurniture.toTooltip() : "null"));
        this.animTextItem = new AnimTextItem(currentWorker != null ? currentWorker.getAction().toString() : "null");
        this.animTextItem.setPosition(new Point(x + 20, y + Data.WP_SIZE + 10));
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
        this.animTextItem.setText(currentWorker != null ? currentWorker.getAction().toString() : "null");
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
    public AnimTextItem getAnimTextItem() {
        return animTextItem;
    }
    public void update() {
        if (currentWorker != null) {
            this.animTextItem.setText(currentWorker.getAction().toString());
        } else {
            this.animTextItem.setText("null");
        }
    }
}
