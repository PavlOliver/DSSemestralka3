package furniture;

import simulation.Data;

public enum FurnitureType {
    TABLE(Data.tableImg), CHAIR(Data.chairImg), WARDROBE(Data.wardrobeImg);

    private final String image;

    FurnitureType(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
