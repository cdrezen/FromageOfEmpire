package fromageofempire;
import java.util.ArrayList;

public abstract class Building {
    protected int capacity; // Capacité du bâtiment, par exemple
    protected int level;    // Niveau ou état du bâtiment
    protected ArrayList<Villager> users;
    protected static BuildingObserver observer;

    public Building(int capacity) {
        this.capacity = capacity;
        this.level = 1; // Niveau initial
        users = new ArrayList<Villager>();
    }

    // Méthodes communes à tous les bâtiments, comme upgrade(), repair(), etc.

    public ArrayList<Villager> getUsers() {
        return users;
    }

    public static void setObserver(BuildingObserver _observer)
    {
        observer = _observer;
    }

    public abstract void update();

    public abstract void addUser(Villager villager);

    public abstract void removeUser(Villager villager);
}
