package furniture;

import OSPRNG.UniformContinuousRNG;
import OSPRNG.UniformDiscreteRNG;
import simulation.Mc;

import java.util.*;

public class Furnitures {
    private final Queue<Furniture> furnitureList = new LinkedList<>();
    private final int id;
    private double arrivalTime;

    public Furnitures(int id, double arrivalTime, Random seedGenerator) {
        this.id = id;
        UniformContinuousRNG furnitureTypeGenerator = new UniformContinuousRNG(0d, 1d, seedGenerator);
        UniformDiscreteRNG furnitureCountGenerator = new UniformDiscreteRNG(1, 5, seedGenerator);
        int furnitureCount = furnitureCountGenerator.sample();
        for (int i = 0; i < furnitureCount; i++) {
            double randomValue = furnitureTypeGenerator.sample();
            if (randomValue < 0.5) {
                furnitureList.add(new Furniture(i, id, FurnitureType.TABLE, arrivalTime));
            } else if (randomValue < 0.65) {
                furnitureList.add(new Furniture(i, id, FurnitureType.CHAIR, arrivalTime));
            } else {
                furnitureList.add(new Furniture(i, id, FurnitureType.WARDROBE, arrivalTime));
            }
        }
    }

    public Furniture getFurniture() {
        return furnitureList.poll();
    }

    public boolean isEmpty() {
        return furnitureList.isEmpty();
    }

    public Queue<Furniture> getAllFurnituresInOrder() {
        return this.furnitureList;
    }

    public String furnituresInOrder() {
        StringBuilder sb = new StringBuilder();
        for (Furniture furniture : furnitureList) {
            sb.append(furniture.toString()).append(",");
        }
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}