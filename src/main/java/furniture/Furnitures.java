package furniture;

import OSPRNG.UniformContinuousRNG;
import OSPRNG.UniformDiscreteRNG;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Furnitures {
    private final Queue<Furniture> furnitureList = new LinkedList<>();
    private final int id;
    private double arrivalTime;

    public Furnitures(int id, double arrivalTime) {
        this.id = id;
        UniformContinuousRNG furnitureTypeGenerator = new UniformContinuousRNG(0d, 1d);
        for (int i = 0; i < new UniformDiscreteRNG(1, 5).sample(); i++) {
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
}

