public class Plant {
    private String name;
    private int wateringInterval; // in hours

    public Plant(String name, int wateringInterval) {
        this.name = name;
        this.wateringInterval = wateringInterval;
    }

    public String getName() {
        return name;
    }

    public int getWateringInterval() {
        return wateringInterval;
    }
}
