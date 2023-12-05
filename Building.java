package fromageofempire;
import java.util.ArrayList;

public class Building implements BuildingComponent {
    BuildingType type;
    int level;    // Niveau ou état du bâtiment
    int buildTime = 1;
    int currentBuildStep = 0;

    ArrayList<BuildingComponent> children;
    //protected ArrayList<Villager> users;
    protected static BuildingObserver observer;

    public Building(BuildingType type, int buildTime) {
        this.type = type;
        this.level = 1; // Niveau initial
        this.buildTime = buildTime;
        children = new ArrayList<BuildingComponent>();
        //users = new ArrayList<Villager>();
    }

    public static void setObserver(BuildingObserver _observer)
    {
        observer = _observer;
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

    public void update()
    {
        if(!isBuilt())
        {
            currentBuildStep++;
            if(isBuilt()) observer.OnBuilt(this);
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

    // Méthodes communes à tous les bâtiments, comme upgrade(), repair(), etc.

   /*  public ArrayList<Villager> getUsers() {
        return users;
    }

    public abstract void addUser(Villager villager);

    public abstract void removeUser(Villager villager); */
}
