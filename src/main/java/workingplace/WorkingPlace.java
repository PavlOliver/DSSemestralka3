package workingplace;

import OSPAnimator.AnimImageItem;
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
    private final AnimImageItem animImageItem;

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
        this.animImageItem = new AnimImageItem(Data.waitingImg, Data.ACTION_SIZE, Data.ACTION_SIZE);
        this.animImageItem.setPosition(new Point(x + Data.WP_SIZE / 2, y + 10));
    }

    public void loadAnimItems(MySimulation mySim) {
        if (mySim.animatorExists()) {
            mySim.animator().register(animShapeItem);
            this.animShapeItem.setToolTip("Working place " + id + "\n" +
                    "Worker: " + (currentWorker != null ? currentWorker.toTooltip() : "null") + "\n" +
                    "Furniture: " + (currentFurniture != null ? currentFurniture.toTooltip() : "null"));
            mySim.animator().register(animTextItem);
            mySim.animator().register(animImageItem);
            this.update();
        }
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
        //this.update();
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
        return currentFurniture != null;
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

    public AnimImageItem getAnimImageItem() {
        return animImageItem;
    }

    public void update() {
        if (currentWorker != null) {
            this.animTextItem.setText(currentWorker.getAction().toString());
            if (currentWorker.getAction().getActionImg() == null) {
                this.animImageItem.setImage(Data.waitingImg);
            } else {
                this.animImageItem.setImage(currentWorker.getAction().getActionImg());
            }
        } else {
            this.animTextItem.setText("waiting");
            this.animImageItem.setImage(Data.waitingImg);
        }
    }
}
