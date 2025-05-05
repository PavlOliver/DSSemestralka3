package furniture;

import OSPAnimator.AnimImageItem;
import OSPAnimator.AnimItem;
import OSPAnimator.AnimTextItem;
import workingplace.WorkingPlace;

import java.awt.*;

public class Furniture implements Comparable<Furniture> {
    private final int id;
    private final int orderId;
    private final FurnitureType type;
    private FurnitureState state;
    private WorkingPlace workingPlace;
    private final double arrivalTime;
    private double lastStepTime;
    private final AnimImageItem animImageItem;


    public Furniture(int id, int orderId, FurnitureType type, double arrivalTime) {
        this.id = id;
        this.orderId = orderId;
        this.type = type;
        this.state = FurnitureState.PACKED;
        this.workingPlace = null;
        this.arrivalTime = arrivalTime;
        this.lastStepTime = 0;
        this.animImageItem = new AnimImageItem(type.getImage(), 40, 40);
        animImageItem.setPositionAlignment(AnimImageItem.PositionAlignment.TOP_LEFT);
        animImageItem.setToolTip("id: " + orderId + "(" + id + ")" + "\n" +
                "type: " + type + "\n" +
                "state: " + state);
    }

    public FurnitureState getState() {
        return state;
    }

    public void setState(FurnitureState state, double time) {
        this.state = state;
        this.lastStepTime = time;
        animImageItem.setToolTip("id: " + orderId + "(" + id + ")" + "\n" +
                "type: " + type + "\n" +
                "state: " + state);

    }

    public FurnitureType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return orderId + "(" + id + ") " + type + "(" + state + ")";
    }

    public String toTooltip() {
        return "id: " + orderId + "(" + id + ")" + "\n" +
                "state: " + state;
    }

    public int getOrderId() {
        return orderId;
    }

    public WorkingPlace getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(WorkingPlace workingPlace) {
        this.workingPlace = workingPlace;
    }

    @Override
    public int compareTo(Furniture o) {
        int cmp = Double.compare(this.arrivalTime, o.arrivalTime);
        if (cmp != 0) return cmp;
        cmp = Double.compare(this.lastStepTime, o.lastStepTime);
        if (cmp != 0) return cmp;
        return Integer.compare(this.id, o.id);
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public AnimImageItem getAnimImageItem() {
        return animImageItem;
    }

    public void setPosition(int x, int y) {
        animImageItem.setPosition(new Point(x, y));
    }
}
