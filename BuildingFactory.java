package fromageofempire;

import java.util.HashMap;
import java.util.Map;

public class BuildingFactory {

    public BuildingFactory(GameManager observer)
    {
        HousingComponent.setHousingObserver(observer);
        ProductionComponent.setProductionObserver(observer);
    }

    static final Map<BuildingType, Resource[][]> productionMap = new HashMap<>(){{        
        put(BuildingType.Farm, new Resource[][]{ null, new Resource[]{ new Resource(ResourceType.FOOD, 10) }});
        put(BuildingType.Quarry, new Resource[][]{ null, new Resource[]{ new Resource(ResourceType.STONE, 4), new Resource(ResourceType.IRON, 4), new Resource(ResourceType.COAL, 4), new Resource(ResourceType.GOLD, 2)  }});
        put(BuildingType.LumberMill, new Resource[][]{ new Resource[]{ new Resource(ResourceType.WOOD, 4)}, new Resource[]{ new Resource(ResourceType.LUMBER, 4)  }});
        put(BuildingType.CementPlant, new Resource[][]{ new Resource[]{ new Resource(ResourceType.STONE, 4), new Resource(ResourceType.COAL, 4)}, new Resource[]{ new Resource(ResourceType.CEMENT, 4)  }});
        put(BuildingType.SteelMill, new Resource[][]{ new Resource[]{ new Resource(ResourceType.IRON, 4), new Resource(ResourceType.COAL, 2)}, new Resource[]{ new Resource(ResourceType.STEEL, 4)  }});
        put(BuildingType.ToolFactory, new Resource[][]{ new Resource[]{ new Resource(ResourceType.STEEL, 4), new Resource(ResourceType.COAL, 4)}, new Resource[]{ new Resource(ResourceType.TOOLS, 4)  }});
    }};

    public Building createBuilding(BuildingType type) {

        Building building = new Building();

        switch (type) {
            case House:
                building.add(new HousingComponent(4));
                break;
            case WoodenCabin:
                building.add(new HousingComponent(2));
                break;
            case ApartmentBuilding:
                building.add(new HousingComponent(60));
                break;
            case Farm:
                building.add(new HousingComponent(5));
                building.add(new ProductionComponent(3, productionMap.get(type)[0], productionMap.get(type)[1]));
                break;
            case Quarry:
                building.add(new HousingComponent(2));
                building.add(new ProductionComponent(30, productionMap.get(type)[0], productionMap.get(type)[1]));
                break;
            case LumberMill:
                building.add(new ProductionComponent(10, productionMap.get(type)[0], productionMap.get(type)[1]));
                break;
            case CementPlant:
                building.add(new ProductionComponent(10, productionMap.get(type)[0], productionMap.get(type)[1]));
                break;
            case SteelMill:
                building.add(new ProductionComponent(40, productionMap.get(type)[0], productionMap.get(type)[1]));
                break;
            case ToolFactory:
                building.add(new ProductionComponent(12, productionMap.get(type)[0], productionMap.get(type)[1]));
                break;
            // Ajouter d'autres types de bâtiments
            default:
                throw new IllegalArgumentException("Invalid building type");
        }

        return building;
    }
}
