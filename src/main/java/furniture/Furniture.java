package furniture;

import workingplace.WorkingPlace;

public class Furniture {
    private final int id;
    private final int orderId;
    private final FurnitureType type;
    private FurnitureState state;
    private double arrivalTime;
    private WorkingPlace workingPlace;

    public Furniture(int id, int orderId, FurnitureType type, double arrivalTime) {
        this.id = id;
        this.orderId = orderId;
        this.type = type;
        this.state = FurnitureState.PACKED;
        this.arrivalTime = arrivalTime;
        this.workingPlace = null;
    }

    public FurnitureState getState() {
        return state;
    }

    public void setState(FurnitureState state) {
        this.state = state;
    }

    public FurnitureType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return type + "(" + state + ")";
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
}
