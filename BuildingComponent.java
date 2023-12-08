package fromageofempire;

import java.util.ArrayList;

public interface BuildingComponent {
    public void onBuilt();
    public void update();
    public int getUsersCount();
    public void clear();
    public boolean isAtMaxCapacity();
    public ArrayList<Villager> getWorkers();
    public boolean addWorker(Villager villager);
    public boolean removeWorker(Villager villager);
    public boolean addInhabitant(Villager villager);
    public boolean removeInhabitant(Villager villager);
    @Override
    String toString();
    //public void setObserver(BuildingObserver observer);
}
