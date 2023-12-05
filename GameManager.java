package fromageofempire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager implements VillagerObserver, BuildingObserver, HousingObserver, ProductionObserver {
    private static GameManager instance;



    private HashMap<ResourceType, Resource> resources;

    private void initializeResources() {
        resources = new HashMap<>();
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, new Resource(type, 0));
        }
    }

    BuildingFactory buildingFactory;
    private List<Building> buildings;
    private ArrayList<Villager> villagers;

    private GameManager() {
        resources = new HashMap<>(); // Initialisation du HashMap
        initializeResources(); // Remplissage du HashMap avec les ressources initiales

        buildingFactory = new BuildingFactory(this);
        buildings = new ArrayList<>();
        villagers = new ArrayList<>();

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
    public void initializeVillagers()
    {
        for (int i = 0; i < 100; i++) {
            villagers.add(new Villager(this));
        }
    }

    public void buildCommand(String buildingName)
    {
        if(buildingName.equals("help"))
        {
            System.err.println("list of buildings:");
            for (BuildingType type : BuildingType.values()) {
                System.err.println(type.toString().toLowerCase());
            }
            return;
        }

        BuildingType type = null;
        for (BuildingType t : BuildingType.values()) {
            if(t.toString().toLowerCase().equalsIgnoreCase(buildingName)) { 
                type = t;
                break;
            }
        }

        if(type != null)
        {
            //!\\empecher si pas assez de resources
            buildings.add(buildingFactory.createBuilding(type));
        }
        else buildCommand("help");
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
