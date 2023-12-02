package fromageofempire;

public class BuildingFactory {
    public static Building createBuilding(String type, int capacity) {
        switch (type) {
            case "House":
                return new House(capacity);
            case "WoodenCabin":
                return new WoodenCabin(capacity);
            case "ApartmentBuilding":
                return new ApartmentBuilding(capacity);
            case "Quarry":
                return new Quarry(capacity);
            case "LumberMill":
                return new LumberMill(capacity);
            case "Farm":
                return new Farm(capacity);
            case "CementPlant":
                return new CementPlant(capacity);
            case "ToolFactory":
                return new ToolFactory(capacity);
            // Ajouter d'autres types de bâtiments
            default:
                throw new IllegalArgumentException("Invalid building type");
        }
    }
}
