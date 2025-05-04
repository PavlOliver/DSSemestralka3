package worker;

import simulation.MySimulation;

import java.util.ArrayList;
import java.util.List;

public class Workers {
    private final List<Worker> workers;

    public Workers(int size, WorkerType type, MySimulation mySim) {
        workers = new ArrayList<Worker>();
        for (int i = 0; i < size; i++) {
            workers.add(new Worker(i, type, mySim));
            if (mySim.animatorExists()) {
                mySim.animator().register(workers.get(i).getAnimImageItem());
            }
        }
    }

    public Worker getFreeWorker() {
        for (Worker worker : workers) {
            if (!worker.isBusy()) {
                return worker;
            }
        }
        return null;
    }

    public double getAverageUtilization() {
        double totalUtilization = 0;
        for (Worker worker : workers) {
            totalUtilization += worker.getUtilityWStat().mean();
        }
        return totalUtilization / workers.size();
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public int getFreeWorkersCount() {
        int count = 0;
        for (Worker worker : workers) {
            if (!worker.isBusy()) {
                count++;
            }
        }
        return count;
    }
}
