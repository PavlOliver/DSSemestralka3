package furniture;

import workingplace.WorkingPlace;

public class Furniture implements Comparable<Furniture> {
    private final int id;
    private final int orderId;
    private final FurnitureType type;
    private FurnitureState state;
    private WorkingPlace workingPlace;

    public Furniture(int id, int orderId, FurnitureType type) {
        this.id = id;
        this.orderId = orderId;
        this.type = type;
        this.state = FurnitureState.PACKED;
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

    @Override
    public int compareTo(Furniture o) {
        int cmp = Integer.compare(this.orderId, o.orderId);
        if (cmp != 0)
            return cmp;
        return Integer.compare(this.id, o.id);
    }
}
