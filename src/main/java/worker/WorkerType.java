package worker;

import simulation.Data;

public enum WorkerType {
    A(Data.workerAImg), B(Data.workerBImg), C(Data.workerCImg);

    private final String imagePath;

    WorkerType(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
