package fromageofempire;

import java.util.ArrayList;
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
    private boolean running;
    private boolean autofill = true;

    private HashMap<ResourceType, Integer> resourceChange;
    private HashMap<ResourceType, Integer> lastResourceQuantities;
    private GameManager() 
    {
        resources = new HashMap<>(); // Initialisation du HashMap
        initializeResources(); // Remplissage du HashMap avec les ressources initiales

        buildingFactory = new BuildingFactory(this);
        buildings = new ArrayList<>();
        villagers = new ArrayList<>();
        dead_villagers = new ArrayList<>();
        last_sustainability = 1;
        resourceChange = new HashMap<>();
        for (ResourceType type : ResourceType.values()) {
            resourceChange.put(type, 0);
        }
        lastResourceQuantities = new HashMap<>();
        for (ResourceType type : ResourceType.values()) {
            lastResourceQuantities.put(type, 0);
        }
        /*buildings.add(buildingFactory.createBuilding(BuildingType.House));
        buildings.add(buildingFactory.createBuilding(BuildingType.WoodenCabin));*/
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public boolean isRunning() { return this.running; }

    public void start() { running = true; }
    public void stop() { running = false; }

    private void initializeResources() 
    {
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, new Resource(type, 0));
        }

        resources.get(ResourceType.GOLD).setQuantity(25);
        resources.get(ResourceType.FOOD).setQuantity(50);
        resources.get(ResourceType.WOOD).setQuantity(100);
        resources.get(ResourceType.STONE).setQuantity(100);

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
            home.addInhabitant(v);
        }
    }

    public void clearDeadVillagers()
    {
        for (Villager v : dead_villagers) {
            if(v.isWorker()) v.workplace.removeWorker(v);
            if(v.isHoused()) v.home.removeInhabitant(v);
            villagers.remove(v);
        }

        dead_villagers.clear();
    }

    public boolean build(BuildingType type)
    {
        //empecher si pas assez de resources
        for (Resource resource : type.getRecipe()) 
        {
            if(resource.getQuantity() > resources.get(resource.getType()).getQuantity())
            {                    
                System.out.printf("Not enough resources for %s\n", type);
                return false;
            }
        }

       for (Resource resource : type.getRecipe()) { resources.get(resource.getType()).removeQuantity(resource.getQuantity()); }
       buildings.add(buildingFactory.createBuilding(type));

       return true;
    }

    public void destroy(int index)
    {
        Building building = buildings.get(index);
        for (Resource resource : building.getType().getRecipe()) { resources.get(resource.getType()).addQuantity(resource.getQuantity()); }
        building.clear();
        buildings.remove(index);
    }

    public void set_autofill(boolean value) 
    { 
        this.autofill = value;
        System.out.println("autofill set to: " + value);
    }
    public void switch_autofill() { set_autofill(!this.autofill); }
    

    public boolean hireWorkers(int index, int nb)
    {
        if(autofill) set_autofill(false);
        Building building = buildings.get(index);
        int count = 0;
        if(nb == -1) nb = villagers.size();

        for (int i = 0; i < villagers.size() && count < nb; i++) 
        {
            Villager v = villagers.get(i);
            if(!v.isWorker() && building.addWorker(v)) count++;
        }
        
        return count == nb;
    }

    public boolean fireWorkers(int index, int nb)
    {
        if(autofill) set_autofill(false);
        Building building = buildings.get(index);
        int count = 0;

        ArrayList<Villager> workers = building.getWorkers();
        if(nb == -1) nb = workers.size();

        for (int i = 0; i < workers.size() && count < nb; i++) 
        {
            Villager v = workers.get(i);
            if(v.isWorker() && building.removeWorker(v)) count++;
        }
        
        return count == nb;
    }

    public void update()
    {
        if(!running) return;

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
        System.out.println("sustainability: " + last_sustainability);
        updateResourceChange();

        // Enregistrer l'état actuel des ressources pour le prochain tour
        for (ResourceType type : ResourceType.values()) {
            lastResourceQuantities.put(type, resources.get(type).getQuantity());
        }
    }

    private void updateResourceChange() {
        for (ResourceType type : ResourceType.values()) {
            int lastQuantity = lastResourceQuantities.getOrDefault(type, 0);
            int currentQuantity = resources.get(type).getQuantity();
            int change = currentQuantity - lastQuantity;
            resourceChange.put(type, change);
        }
    }
    public void changeResourceQuantity(ResourceType type, int amount) {
        if (resources.containsKey(type)) {
            resources.get(type).setQuantity(amount);
        }
    }

    double sustainability() 
    { 
        int food = resources.get(ResourceType.FOOD).getQuantity();
        return (food + (food != 0 ? Villager.MAX_STARVATION_DURATION : 0)) / (villagers.size() * Villager.FOOD_CONSUMPTION + 1);
    }

    public boolean isGameLost()
    {
        if(sustainability() == 0 && villagers.size() == 0) return true;
        
        if(resources.get(ResourceType.GOLD).getQuantity() < 4)
        {
            boolean is_quarry_built = false;
            for (Building building : buildings) {
                if(building.getType() == BuildingType.Quarry) {
                    is_quarry_built = true;
                    break;
                }
            }
            if(!is_quarry_built) return true;
        }


        return false;
    }

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
        if(last_sustainability - 1 > 0)
        {
            System.err.println(last_sustainability + " : spawn");
            addVillagers((int)Math.min(last_sustainability, source.getCapacity()), source);
        } 
    }

    @Override
    public void OnEmptyFactory(ProductionComponent source) 
    {
        if(!autofill) return;

        for (Villager villager : villagers) {
            if(!villager.isWorker())
            {
                if(source.isAtMaxCapacity()) break;
                source.addWorker(villager);
            }
        }
    }

    @Override
    public void OnProducedResource(ProductionComponent source, Production produced) {
        
        if(produced.hasInput())
        {
            for (Resource loss : produced.getInput()) {
                if(!resources.get(loss.getType()).removeQuantity(loss.getQuantity())) return;// return si pas assez de resources
            }
        }
        for (Resource product : produced.getOutput()) {
            resources.get(product.getType()).addQuantity(product.getQuantity());
        }     
    }

    // ... autres méthodes du GameManager ...
    
    public void displayVillagers()
    {
        int unemployed = 0;
        int homeless = 0;

        for (Villager villager : villagers) 
        {
            if(!villager.isWorker()) unemployed++;
            if(!villager.isHoused()) homeless++;
        }

        System.out.printf("villagers: %d unemployed: %d homeless: %d\n", villagers.size(), unemployed, homeless);
    }

    public void displayResources()
    {
        System.out.print("Resources:");
        for (Resource resource : resources.values()) {
            System.out.printf(" %s %d(%d)   ", resource.getType().toString(), resource.getQuantity(), resourceChange.get(resource.getType()));
        }
        System.out.printf("\n");
    }

    public void displayBuildings() 
    {
        int cpt = 0;
        for (Building building : buildings) {
            if(cpt%5 == 0){
                System.out.println(building.toString());
            }
            else{
                System.out.print(building.toString() + "      ");
            }

            cpt++;
        }   
    }

    @Override
    public void OnDeath(Villager source) {
        dead_villagers.add(source);
    }
}
