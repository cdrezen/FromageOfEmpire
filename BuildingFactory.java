package fromageofempire;

public class BuildingFactory {

    public BuildingFactory(GameManager observer)
    {
        HousingComponent.setHousingObserver(observer);
        ProductionComponent.setProductionObserver(observer);
    }

    public Building createBuilding(BuildingType type) {

        Building building = new Building(type);

        switch (type) {
            case House:
                building.add(new HousingComponent(4));
                break;
            case WoodenCabin:
                building.add(new HousingComponent(2));
                building.add(new ProductionComponent(2, type.getProduction()));
                break;
            case ApartmentBuilding:
                building.add(new HousingComponent(60));
                break;
            case Farm:
                building.add(new HousingComponent(5));
                building.add(new ProductionComponent(3, type.getProduction()));
                break;
            case Quarry:
                building.add(new HousingComponent(2));
                building.add(new ProductionComponent(30, type.getProduction()));
                break;
            case LumberMill:
                building.add(new ProductionComponent(10, type.getProduction()));
                break;
            case CementPlant:
                building.add(new ProductionComponent(10, type.getProduction()));
                break;
            case SteelMill:
                building.add(new ProductionComponent(40, type.getProduction()));
                break;
            case ToolFactory:
                building.add(new ProductionComponent(12, type.getProduction()));
                break;
            // Ajouter d'autres types de bâtiments
            default:
                throw new IllegalArgumentException("Invalid building type");
        }

        return building;
    }
}
