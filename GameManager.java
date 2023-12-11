package fromageofempire;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager implements VillagerObserver, HousingObserver, ProductionObserver
{
    private static GameManager instance;
    private HashMap<ResourceType, Resource> resources;
    private BuildingFactory buildingFactory;
    private HashMap<Integer, Building> buildings;
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
        buildings = new HashMap<>();
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
        buildings.put(building.getId(), building);
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

    public boolean build(BuildingType type) {
        // Vérifier si suffisamment de ressources sont disponibles pour construire le bâtiment
        for (Resource resource : type.getRecipe()) {
            // Comparer la quantité de ressources requise pour la construction du bâtiment avec la quantité disponible.
            if(resource.getQuantity() > resources.get(resource.getType()).getQuantity()) {
                System.out.printf("Not enough resources for %s\n", type);
                return false;
            }
        }

        // Si toutes les ressources requises sont disponibles, on enlève des ressources globales.
        for (Resource resource : type.getRecipe()) {
            resources.get(resource.getType()).removeQuantity(resource.getQuantity());
        }
        addBuilding(buildingFactory.createBuilding(type));
        return true;
    }


    // Méthode qui détruit un bâtiment en prenant en paramètre son id.
    // Lors de la destruction, les ressources qui ont été utilisé pour construire le bâtiment sont redonnés au joueur.
    public void destroy(int id)
    {
        Building building = buildings.get(id);
        if(building == null) return;
        for (Resource resource : building.getType().getRecipe()) { resources.get(resource.getType()).addQuantity(resource.getQuantity()); }
        building.clear();
        buildings.remove(id);
    }

    // Gestion d'activation du système d'auto-remplissage.
    public void set_autofill(boolean value) 
    { 
        this.autofill = value;
        System.out.println("autofill set to: " + value);
    }
    public void switch_autofill() { set_autofill(!this.autofill); }
    

    public boolean hireWorkers(int id, int nb)
    {
        // On récupère le bâtiment basé sur son id
        Building building = buildings.get(id);
        if(building == null) return false; // On retourne false si le bâtiment n'est pas trouvé ou a été détruit.

        // On désactive l'auto-remplissage si c'est activé
        if(autofill) set_autofill(false);
        int count = 0;
        if(nb == -1) nb = villagers.size();

        // On parcoure la liste des travailleurs et les ajouter jusqu'à atteindre le nombre souhaité.
        for (int i = 0; i < villagers.size() && count < nb; i++) 
        {
            // Si nb est égal à -1, ajouter tous les travailleurs.
            Villager v = villagers.get(i);

            // On vérifie si le villageois est un chômeur et on l'ajoute au bâtiment.
            if(!v.isWorker() && building.addWorker(v)) count++;
        }
        
        return count == nb;
    }

    public boolean fireWorkers(int id, int nb) {
        // On récupère le bâtiment basé sur son id
        Building building = buildings.get(id);
        if (building == null) return false;  // On retourne false si le bâtiment n'est pas trouvé ou a été détruit.

        // On désactive l'auto-remplissage si c'est activé.
        if(autofill) set_autofill(false);

        int count = 0;  // Compteur pour le nombre de travailleurs licenciés.

        ArrayList<Villager> workers = building.getWorkers();

        // Si nb est égal à -1, licencier tous les travailleurs
        if(nb == -1) nb = workers.size();

        // On parcoure la liste des travailleurs et les licencier jusqu'à atteindre le nombre souhaité.
        for (int i = 0; i < workers.size() && count < nb; i++) {
            Villager v = workers.get(i);

            // On vérifie si le villageois est un travailleur et le retirer du bâtiment.
            if(v.isWorker() && building.removeWorker(v)) count++;
        }
        // Retourne true si le nombre de travailleurs licenciés correspond au nombre demandé.
        return count == nb;
    }


    public void update()
    {
        if(!running) return;

        for(Villager villager : villagers)
        {
            villager.update();
        }

        for (Building building : buildings.values())
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

    // Méthode pour avoir la différence de quantité entre le tour d'avant et celui-ci pour l'afficher dans la méthode displayResources().
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


    double sustainability() {
        int food = resources.get(ResourceType.FOOD).getQuantity();
        // Si la nourriture est disponible, on ajoute à cette quantité le temps maximal de survie sans nourriture (MAX_STARVATION_DURATION)
        // Diviser par le total de la consommation de nourriture par les villageois et ajouter 1 pour éviter la division par zéro
        return (food + (food != 0 ? Villager.MAX_STARVATION_DURATION : 0)) / (villagers.size() * Villager.FOOD_CONSUMPTION + 1);
    }


    public boolean isGameLost() {
        // On vérifie si le jeu est perdu en fonction de l'état de durabilité et de la population des villageois
        // Si la durabilité est de 0 et qu'il n'y a plus de villageois, le jeu est perdu
        if(sustainability() == 0 && villagers.size() == 0) return true;

        // On vérifie si la quantité de ressources d'or est inférieur à 4
        // car c'est le minimum pour construire une carrière (car elle produit de l'or, donc qui permettra de construire d'autre bâtiment).
        if(resources.get(ResourceType.GOLD).getQuantity() < 4) {
            //Si c'est le cas, alors on vérifie si une carrière a déjà été construit
            boolean is_quarry_built = false;
            for (Building building : buildings.values()) {
                if(building.getType() == BuildingType.Quarry) {
                    is_quarry_built = true;
                    break; // Arrêter la recherche dès qu'une carrière est trouvée
                }
            }
            // Si aucune carrière n'est construite et que l'or est insuffisant, le jeu est perdu
            if(!is_quarry_built) return true;
        }

        // Si aucune des conditions ci-dessus n'est remplie, le jeu n'est pas perdu
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

    // Réagit lorsqu'un logement est vide ou partiellement vide.
    @Override
    public void OnEmptyHousing(HousingComponent source) {
        // Parcourir tous les villageois
        for (Villager villager : villagers) {
            // Si un villageois n'est pas logé et que le logement n'est pas à pleine capacité
            if(!villager.isHoused() && !source.isAtMaxCapacity()) {
                // Ajouter le villageois au logement
                source.addInhabitant(villager);
            }
            if(source.isAtMaxCapacity()) return;
        }
        // Calcule de l'indice de durabilité actuel du jeu
        last_sustainability = sustainability();
        if(last_sustainability - 1 > 0) {
            // Calcule du nombre de villageois à générer et ajouter dans la liste. Cela dépend de la durabilité et de la capacité du logement.
            int nb_to_spawn = (int)Math.min(last_sustainability, source.getCapacity());
            addVillagers(nb_to_spawn, source);
            System.out.printf("%.0f : %d spawned\n", last_sustainability, nb_to_spawn);
        }
    }


    /*Réagit lorsqu'une usine n'a pas atteint sa capacité max de travailleurs.
      Si l'auto-remplissage est activé, les villageois chômeurs sont automatiquement placés dans des bâtiments de production qui ne sont pas remplis*/
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

    // Met à jour les ressources lorsqu'une production est réalisée
    @Override
    public void OnProducedResource(ProductionComponent source, Production produced) {
        // Vérifier si le bâtiment doit consommer des ressources avant de produire
        if(produced.hasInput()) {
            // Parcourir toutes les ressources nécessaires pour la production
            for (Resource loss : produced.getInput()) {
                 //Si la ressource nécessaire à la consommation n'est pas suffisant, return sans ajouter la quantité produite
                if(!resources.get(loss.getType()).removeQuantity(loss.getQuantity())) {
                    return;
                }
            }
        }
        // Parcourir toutes les ressources produites par le bâtiment
        for (Resource product : produced.getOutput()) {
            // Ajouter la quantité de chaque ressource produite
            resources.get(product.getType()).addQuantity(product.getQuantity());
        }
    }


    // Affiche des informations sur les villageois
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

    // Affiche l'état actuel des ressources
    public void displayResources()
    {
        System.out.print("Resources:");
        for (Resource resource : resources.values()) {
            System.out.printf(" %s %d(%d)   ", resource.getType().toString(), resource.getQuantity(), resourceChange.get(resource.getType()));
        }
        System.out.printf("\n");
    }

    // Affiche les bâtiments actuellement en jeu
    public void displayBuildings()
    {
        int cpt = 0;
        for (Building building : buildings.values()) {
            // Afficher maximum 5 bâtiments dans une même ligne.
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
