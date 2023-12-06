package fromageofempire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameManager implements VillagerObserver, HousingObserver, ProductionObserver
{
    private static GameManager instance;
    private HashMap<ResourceType, Resource> resources;
    private BuildingFactory buildingFactory;
    private List<Building> buildings;
    private ArrayList<Villager> villagers;
    private ArrayList<Villager> dead_villagers;
    private double last_sustainability;

    private GameManager() 
    {
        resources = new HashMap<>(); // Initialisation du HashMap
        initializeResources(); // Remplissage du HashMap avec les ressources initiales

        buildingFactory = new BuildingFactory(this);
        buildings = new ArrayList<>();
        villagers = new ArrayList<>();
        dead_villagers = new ArrayList<>();
        last_sustainability = 1;

        buildings.add(buildingFactory.createBuilding(BuildingType.House));
        buildings.add(buildingFactory.createBuilding(BuildingType.WoodenCabin));
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private void initializeResources() 
    {
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, new Resource(type, 0));
        }

        resources.get(ResourceType.GOLD).setQuantity(7);
        resources.get(ResourceType.FOOD).setQuantity(20);
        resources.get(ResourceType.WOOD).setQuantity(25);
        resources.get(ResourceType.STONE).setQuantity(25);

        Villager.setFoodSource(resources.get(ResourceType.FOOD));
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }
    public void addVillagers(int quantity, HousingComponent home)
    {
        for (int i = 0; i < quantity; i++) 
        {
            Villager v = new Villager(this);
            villagers.add(v);
            v.setHome(home);
        }
    }

    public void clearDeadVillagers()
    {
        for (Villager v : dead_villagers) {
            v.setHome(null);
            v.setWorkplace(null);
            villagers.remove(v);
        }

        dead_villagers.clear();
    }
    

    public void buildCommand(String buildingName)
    {
        if(buildingName.equals("help"))
        {
            System.out.println("list of buildings:");
            for (BuildingType type : BuildingType.values()) {
                System.out.printf("%s %s\n", type.toString().toLowerCase(), Arrays.toString(type.getRecipe()));
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
            for (Resource resource : type.getRecipe()) {
                if(resource.getQuantity() > resources.get(resource.getType()).getQuantity())
                {
                    System.out.printf("Not enough resources for %s\n", type);
                    return;
                }
            }

            for (Resource resource : type.getRecipe()) { resources.get(resource.getType()).removeQuantity(resource.getQuantity()); }

            buildings.add(buildingFactory.createBuilding(type));
        }
        else buildCommand("help");
    }

    public void update()
    {
        for(Villager villager : villagers)
        {
            villager.update();
        }
        for (Building building : buildings)
        {
            building.update();
        }

        if(dead_villagers.size() > 0) clearDeadVillagers();
        last_sustainability = sustainability();
        System.out.println(last_sustainability);
    }

    public void changeResourceQuantity(ResourceType type, int amount) {
        if (resources.containsKey(type)) {
            resources.get(type).setQuantity(amount);
        }
    }

    double sustainability() { return (resources.get(ResourceType.FOOD).getQuantity() + Villager.MAX_STARVATION_DURATION) / (villagers.size() * Villager.FOOD_CONSUMPTION + 1);}

    @Override
    public void OnStarving(Villager source) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void OnBuiltHousing(HousingComponent source) {
        //addVillagers(source.getCapacity(), source);
    }

     @Override
    public void OnBuiltFactory(ProductionComponent source) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'OnBuiltFactory'");
    }
    
    @Override
    public void OnEmptyHousing(HousingComponent source) {

        last_sustainability = sustainability();
        System.err.println(last_sustainability);
        if(last_sustainability - 1 >= 0)
        {
            addVillagers((int)Math.min(last_sustainability, source.getCapacity()), source);
        } 
    }

    @Override
    public void OnEmptyFactory(ProductionComponent source) {
        // TODO Auto-generated method stub
        for (Villager villager : villagers) {
            if(!villager.isWorker())
            {
                if(source.getCapacity() == source.getUsersCount()) break;
                villager.setWorkplace(source);
            }
        }
    }

    @Override
    public void OnProducedResource(ProductionComponent source, Production produced) {
        for (Resource product : produced.getOutput()) {
            resources.get(product.getType()).addQuantity(product.getQuantity());
        }
        if(!produced.hasInput()) return;
        for (Resource loss : produced.getInput()) {
            resources.get(loss.getType()).removeQuantity(loss.getQuantity());
        }
    }

    // ... autres méthodes du GameManager ...
    
    public void displayVillagers()
    {
        System.out.printf("villagers: %d\n", villagers.size());
    }

    public void displayResources() 
    {
        for (Resource resource : resources.values()) {
            System.out.printf("resource %s %d\n", resource.getType().toString(), resource.getQuantity());
        }        
    }

    public void displayBuildings() 
    {
        for (Building building : buildings) {
            System.out.println(building.toString());
        }   
    }

    @Override
    public void OnDeath(Villager source) {
        dead_villagers.add(source);
    }
}
