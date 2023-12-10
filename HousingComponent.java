package fromageofempire;

import java.util.ArrayList;

class HousingComponent implements BuildingComponent {

    static HousingObserver housingObserver;
    ArrayList<Villager> inhabitants;
    int capacity;

    public HousingComponent(int capacity) {
        this.capacity = capacity;
        this.inhabitants = new ArrayList<Villager>();
    }

    public static void setHousingObserver(HousingObserver _housingObserver)
    {
        housingObserver = _housingObserver;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAtMaxCapacity() { return getUsersCount() >= this.capacity; }
    
    public boolean addInhabitant(Villager villager) 
    {
        if(isAtMaxCapacity()) return false;
        boolean success =  this.inhabitants.add(villager);
        if(success) villager.setHome(this);
        return success;
    }

    public boolean removeInhabitant(Villager villager) {
        boolean success = this.inhabitants.remove(villager);
        if(success) villager.setHome(null);
        return success;
    }

    @Override
    public void update() {
        
        if(inhabitants.size() < this.capacity) 
        {
            housingObserver.OnEmptyHousing(this);
        }
    }

    @Override
    public void clear() {
        for (Villager villager : inhabitants) {
            // TODO Auto-generated method stub
            villager.setHome(null);
        }

        this.inhabitants.clear();
    }

    @Override
    public int getUsersCount() {
        return inhabitants.size();
    }

    @Override
    public void onBuilt() {
        housingObserver.OnBuiltHousing(this);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "\uD83E\uDDD1" + getUsersCount() + "/" + getCapacity();
    }

    @Override
    public boolean addWorker(Villager villager) {
        // composite..
        return false;
    }

    @Override
    public boolean removeWorker(Villager villager) {
        return false;
    }

    @Override
    public ArrayList<Villager> getWorkers() {
        // TODO Auto-generated method stub
        return null;
    }
}
