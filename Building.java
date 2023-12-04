package fromageofempire;
import java.util.ArrayList;

public class Building implements BuildingComponent {
    int level;    // Niveau ou état du bâtiment
    
    ArrayList<BuildingComponent> children;
    //protected ArrayList<Villager> users;
    //protected static BuildingObserver observer;

    public Building() {
        this.level = 1; // Niveau initial
        children = new ArrayList<BuildingComponent>();
        //users = new ArrayList<Villager>();
    }

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

    public static void setObserver(BuildingObserver _observer)
    {
        observer = _observer;
    }

    public abstract void addUser(Villager villager);

    public abstract void removeUser(Villager villager); */
}
