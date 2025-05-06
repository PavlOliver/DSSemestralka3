package worker;

import simulation.Data;

public enum WorkerState {
    WAITING(Data.waitingImg),
    MOVING_STORAGE(null),
    MOVING_WP(null),
    PREPARING(null),
    CUTTING(Data.cuttingImg),
    PICKLING(Data.picklingImg),
    LACQUERING(Data.lacqueringImg),
    BUILDING(Data.buildingImg),
    FORGING(Data.forgingImg);

    private final String actionImg;

    WorkerState(String action) {
        this.actionImg = action;
    }

    public String getActionImg() {
        return actionImg;
    }
}
