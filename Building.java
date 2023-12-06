package fromageofempire;
import java.util.ArrayList;
import java.util.Arrays;

public class Building implements BuildingComponent {
    BuildingType type;
    int level;    // Niveau ou état du bâtiment
    int buildTime = 1;
    int currentBuildStep = 0;

    ArrayList<BuildingComponent> children;
    //protected ArrayList<Villager> users;
    //protected static BuildingObserver observer;

    public Building(BuildingType type) {
        this.type = type;
        this.level = 1; // Niveau initial
        this.buildTime = type.buildTime;
        children = new ArrayList<BuildingComponent>();
        //users = new ArrayList<Villager>();
    }

    public BuildingType getType() { return this.type; }

    public boolean isBuilt() { return (currentBuildStep > buildTime);}


    public void add(BuildingComponent component)
    {
        children.add(component);
    }

    public void remove(BuildingComponent component)
    {
        children.remove(component);
    }

    @Override
    public void update()
    {
        if(!isBuilt())
        {
            currentBuildStep++;
            if(isBuilt()) onBuilt();
            return;
        }

        for (BuildingComponent buildingComponent : children) {
            buildingComponent.update();
        }
    }

    @Override
    public int getUsersCount() {
        int count = 0;
        for (BuildingComponent buildingComponent : children) {
            count += buildingComponent.getUsersCount();
        }
        return count;
    }

    @Override
    public void onBuilt() 
    {
        for (BuildingComponent buildingComponent : children) {
            buildingComponent.onBuilt();
        }
    }

    @Override
    public String toString() {
        String name = type.toString();
        ArrayList<String> componentDesc = new ArrayList<>();

        for (BuildingComponent buildingComponent : children) {
            componentDesc.add(buildingComponent.toString());
        }

        return String.format("%s : [%s]", name, String.join(", ", componentDesc));
    }

    // Méthodes communes à tous les bâtiments, comme upgrade(), repair(), etc.

   /*  public ArrayList<Villager> getUsers() {
        return users;
    }

    public static void setObserver(BuildingObserver _observer)
    {
        observer = _observer;
    }

    public abstract void addUser(Villager villager);

    public abstract void removeUser(Villager villager); */
}
