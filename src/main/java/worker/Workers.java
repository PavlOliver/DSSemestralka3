package worker;

import java.util.ArrayList;
import java.util.List;

public class Workers {
    private final List<Worker> workers;

    public Workers(int size, char type) {
        workers = new ArrayList<Worker>();
        for (int i = 0; i < size; i++) {
            workers.add(new Worker(i, type));
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
