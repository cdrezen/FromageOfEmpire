package fromageofempire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {
    private static GameManager instance;
    private Map<ResourceType, Resource> resources;
    private List<Building> buildings;

    private GameManager() {
        resources = new HashMap<>();
        buildings = new ArrayList<>();
        initializeResources();
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
    private void initializeResources() {
        // Initialiser les ressources de base avec des quantités initiales pour le joueur
        resources.put(ResourceType.GOLD, ResourceFactory.createResource(ResourceType.GOLD, 100));
        resources.put(ResourceType.FOOD, ResourceFactory.createResource(ResourceType.FOOD, 50));
        resources.put(ResourceType.WOOD, ResourceFactory.createResource(ResourceType.WOOD, 30));
        // Ajouter d'autres ressources selon les besoins
    }

    public void changeResourceQuantity(ResourceType type, int amount) {
        if (resources.containsKey(type)) {
            resources.get(type).changeQuantity(amount);
        }
    }

    // ... autres méthodes du GameManager ...
}
