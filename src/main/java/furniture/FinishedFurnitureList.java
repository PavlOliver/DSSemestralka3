package furniture;

import java.util.ArrayList;
import java.util.List;

public class FinishedFurnitureList {
    private final List<FinishedFurnitures> finishedFurnituresList;

    public FinishedFurnitureList() {
        this.finishedFurnituresList = new ArrayList<>();
    }

    public void add(Furnitures furnitures, double arrivalTime) {
        FinishedFurnitures finishedFurniture = new FinishedFurnitures(furnitures.getId(), furnitures.getAllFurnituresInOrder().size(), arrivalTime);
        finishedFurnituresList.add(finishedFurniture);
    }

    public List<FinishedFurnitures> getFinishedFurnituresList() {
        return finishedFurnituresList;
    }

    public double addFinishedFurniture(Furniture furniture) { //vrati -1 ak to nebol posledny kus z objednavky, ak bol tak vrati cas v systeme
        for (FinishedFurnitures finishedFurniture : finishedFurnituresList) {
            if (finishedFurniture.getOrderId() == furniture.getOrderId()) {
                finishedFurniture.incCurrentSize();
                if (finishedFurniture.isFinished()) {
                    finishedFurnituresList.remove(finishedFurniture);
                    return finishedFurniture.getArrivalTime();
                }
            }
        }
        return -1d;
    }

    public void addStartedFurniture(Furniture furniture) {
        for (FinishedFurnitures finishedFurniture : finishedFurnituresList) {
            if (finishedFurniture.getOrderId() == furniture.getOrderId()) {
                finishedFurniture.setStarted(true);
            }
        }
    }

    public int getUnstartedFurnituresCount() {
        int count = 0;
        for (FinishedFurnitures finishedFurniture : finishedFurnituresList) {
            if (!finishedFurniture.isStarted()) {
                count++;
            }
        }
        return count;
    }

}
