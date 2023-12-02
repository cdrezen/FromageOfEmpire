package fromageofempire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {
    private static GameManager instance;
    private static Map<ResourceType, Resource> resources;
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
    public static void initializeResources() {
        // Initialiser les ressources de base avec des quantités initiales pour le joueur
        resources.put(ResourceType.GOLD, new Resource(ResourceType.GOLD, 100));
        resources.put(ResourceType.FOOD, new Resource(ResourceType.FOOD, 50));
        resources.put(ResourceType.WOOD, new Resource(ResourceType.WOOD, 30));
        // Ajouter d'autres ressources selon les besoins
    }

    public void changeResourceQuantity(ResourceType type, int amount) {
        if (resources.containsKey(type)) {
            resources.get(type).setQuantity(amount);
        }
    }

    // ... autres méthodes du GameManager ...
}
