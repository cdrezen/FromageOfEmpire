package fromageofempire;

public abstract class Building {
    protected int capacity; // Capacité du bâtiment, par exemple
    protected int level;    // Niveau ou état du bâtiment

    public Building(int capacity) {
        this.capacity = capacity;
        this.level = 1; // Niveau initial

    }

    // Méthodes communes à tous les bâtiments, comme upgrade(), repair(), etc.
}
