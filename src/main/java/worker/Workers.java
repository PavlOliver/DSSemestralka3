package worker;

import java.util.ArrayList;
import java.util.List;

public class Workers {
    private final List<Worker> workers;

    public Workers(int size) {
        workers = new ArrayList<Worker>();
        for (int i = 0; i < size; i++) {
            workers.add(new Worker(i));
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
}
