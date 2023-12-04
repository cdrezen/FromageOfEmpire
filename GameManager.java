package fromageofempire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager implements VillagerObserver, BuildingObserver, HousingObserver, ProductionObserver {
    private static GameManager instance;
    private HashMap<ResourceType, Resource> resources;
    BuildingFactory buildingFactory;
    private List<Building> buildings;
    private ArrayList<Villager> villagers;

    private GameManager() {
        resources = new HashMap<ResourceType, Resource>();
        buildingFactory = new BuildingFactory(this);
        buildings = new ArrayList<Building>();
        villagers = new ArrayList<Villager>();
        initializeResources();
        initializeVillagers();

        buildings.add(buildingFactory.createBuilding(BuildingType.House));
        buildings.add(buildingFactory.createBuilding(BuildingType.Farm));
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }


    public void addBuilding(Building building) {
        buildings.add(building);
    }
    public void initializeResources() {
        // Initialiser les ressources de base avec des quantités initiales pour le joueur
        resources.put(ResourceType.GOLD, ResourceFactory.createResource(ResourceType.GOLD, 100));
        resources.put(ResourceType.FOOD, ResourceFactory.createResource(ResourceType.FOOD, 50));
        resources.put(ResourceType.WOOD, ResourceFactory.createResource(ResourceType.WOOD, 30));
        resources.put(ResourceType.STONE, ResourceFactory.createResource(ResourceType.STONE, 30));

        // Ajouter d'autres ressources selon les besoins
    }
    public void initializeVillagers()
    {
        for (int i = 0; i < 10; i++) {
            villagers.add(new Villager(this));
        }
    }

    public void update()
    {
        for (Villager villager : villagers) {
            villager.update();
        }
        for (Building building : buildings) {
            building.update();
        }
    }

    public void changeResourceQuantity(ResourceType type, int amount) {
        if (resources.containsKey(type)) {
            resources.get(type).setQuantity(amount);
        }
    }

    @Override
    public void OnEmpty(Building source) {
        //if(source instanceof HousingBuilding)
    }

    @Override
    public void OnStarving(Villager source) {
        // TODO Auto-generated method stub
        resources.get(ResourceType.FOOD).removeQuantity(1);
        source.full = 2;
    }

    @Override
    public void OnEmptyHousing(HousingComponent source) {
        // TODO Auto-generated method stub
        for (Villager villager : villagers) {
            if(!villager.isHoused())
            {
                villager.setHome(source);
                if(source.capacity == source.inhabitants.size()) break;
            }
        }
    }

    @Override
    public void OnEmptyFactory(ProductionComponent source) {
        // TODO Auto-generated method stub
        for (Villager villager : villagers) {
            if(!villager.isWorker())
            {
                villager.setWorkplace(source);
                if(source.capacity == source.workers.size()) break;
            }
        }
    }

    @Override
    public void OnProducedResource(ProductionComponent source, Resource[] produced, Resource[] cost) {
        for (Resource product : produced) {
            resources.get(product.getType()).addQuantity(product.getQuantity());
        }
        if(cost == null) return;
        for (Resource loss : cost) {
            resources.get(loss.getType()).removeQuantity(loss.getQuantity());
        }
    }

    // ... autres méthodes du GameManager ...
    
    public void displayResources() 
    {
        for (Resource resource : resources.values()) {
            System.out.printf("resource %s %d\n", resource.getType().toString(), resource.getQuantity());
        }        
    }

    public void displayBuildings() 
    {
        for (Building building : buildings) {
            System.out.printf("building %s users:%d\n", building.getClass().getSimpleName(), building.getUsersCount());
        }   
    }
}
