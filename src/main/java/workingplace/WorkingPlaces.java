package workingplace;

import OSPStat.WStat;
import furniture.Furniture;
import simulation.MySimulation;

import java.util.ArrayList;
import java.util.List;

public class WorkingPlaces {
    private final List<WorkingPlace> workingPlaces;

    public WorkingPlaces(int size, MySimulation mySim) {
        this.workingPlaces = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.workingPlaces.add(new WorkingPlace(i, mySim));
            if (mySim.animatorExists()) {
                mySim.animator().register(workingPlaces.get(i).getAnimShapeItem());
                mySim.animator().register(workingPlaces.get(i).getAnimTextItem());
                mySim.animator().register(workingPlaces.get(i).getAnimImageItem());
            }
        }
    }

    public void loadAnimItems(MySimulation mySim) {
        for (WorkingPlace workingPlace : workingPlaces) {
            workingPlace.loadAnimItems(mySim);
        }
    }

    public List<WorkingPlace> getWorkingPlaces() {
        return workingPlaces;
    }

    public WorkingPlace getWorkingPlace(int index) {
        return workingPlaces.get(index);
    }

    public WorkingPlace getFreeWorkingPlace() {
        for (WorkingPlace workingPlace : workingPlaces) {
            if (!workingPlace.isOccupied()) {
                return workingPlace;
            }
        }
        return null;
    }

    public WorkingPlace getWorkingPlaceByFuriture(Furniture furniture) {
        if (furniture == null) {
            return null;
        }
        for (WorkingPlace workingPlace : workingPlaces) {
            if (workingPlace.getCurrentFurniture() == furniture) {
                return workingPlace;
            }
        }
        return null;
    }

    public double getAverageUtilization() {
        double totalUtilization = 0;
        for (WorkingPlace workingPlace : workingPlaces) {
            totalUtilization += workingPlace.getUtilityWStat().mean();
        }
        return totalUtilization / workingPlaces.size();
    }
}
