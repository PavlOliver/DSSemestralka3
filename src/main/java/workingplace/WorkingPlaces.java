package workingplace;

import furniture.Furniture;

import java.util.ArrayList;
import java.util.List;

public class WorkingPlaces {
    private final List<WorkingPlace> workingPlaces;

    public WorkingPlaces(int size) {
        this.workingPlaces = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            this.workingPlaces.add(new WorkingPlace(i));
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
        for (WorkingPlace workingPlace : workingPlaces) {
            if (workingPlace.getCurrentFurniture() == furniture) {
                return workingPlace;
            }
        }
        return null;
    }
}
