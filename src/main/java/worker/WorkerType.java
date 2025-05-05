package worker;

import simulation.Data;

public enum WorkerType {
    A(Data.workerAImg, 0), B(Data.workerBImg, 1), C(Data.workerCImg, 2);

    private final String imagePath;
    private final int value;

    WorkerType(String imagePath, int value) {
        this.imagePath = imagePath;
        this.value = value;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getValue() {
        return value;
    }
}
