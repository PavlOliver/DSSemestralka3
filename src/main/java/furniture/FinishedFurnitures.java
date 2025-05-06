package furniture;

public class FinishedFurnitures {
    private final int orderId;
    private final int originalSize;
    private int currentSize;
    private final double arrivalTime;
    private boolean started;

    public FinishedFurnitures(int orderId, int originalSize, double arrivalTime) {
        this.orderId = orderId;
        this.originalSize = originalSize;
        this.arrivalTime = arrivalTime;
        this.currentSize = 0;
        this.started = false;
    }

    public void incCurrentSize() {
        this.currentSize++;
    }

    public boolean isFinished() {
        return currentSize == originalSize;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
